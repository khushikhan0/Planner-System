package original.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A model.PlannerSystem representing a system with a host user and other users as well as their
 * schedules.
 */
public class PlannerSystem implements PlannerModel {
  private final HashMap<String, ScheduleInterface> usersAndSchedule;
  private String user;

  /**
   * A model.PlannerSystem contains users with schedules.
   */
  public PlannerSystem() {
    this.usersAndSchedule = new HashMap<>();
  }

  /**
   * A convenience constructor that allows the initialization of a planner system with
   * the given schedules.
   * Assumes the given list of schedules are valid schedules.
   * @param schedules the list of schedules to initialize the planner with
   */
  public PlannerSystem(List<ScheduleInterface> schedules) {
    this.usersAndSchedule = new HashMap<>();
    this.user = "<none>";

    for (ScheduleInterface sched : schedules) {
      this.usersAndSchedule.put(sched.getUser(), sched);
    }
  }

  /**
   * Add a schedule to a planner system.
   * @param user a user to add to system.
   * @param schedule a schedule to add to a system.
   * @param addSchedule if true, add a schedule to a system. if false, remove a schedule from a
   *                    system.
   */
  @Override
  public void modifySystem(String user, ScheduleInterface schedule, boolean addSchedule) {
    if (!this.usersAndSchedule.containsKey(user) && !this.usersAndSchedule.containsValue(schedule)
            && addSchedule) {
      this.usersAndSchedule.put(user, schedule);
    }
    else if (this.usersAndSchedule.containsKey(user)
            && this.usersAndSchedule.containsValue(schedule)
            && !addSchedule) {
      this.usersAndSchedule.remove(user, schedule);
    }
  }

  /**
   * Gets an unmodifiable map of all the users and corresponding schedules in this system.
   * @return unmodifiable map of users and schedules
   */
  public Map<String, ScheduleInterface> getUsersAndSchedule() {
    return Collections.unmodifiableMap(this.usersAndSchedule);
  }

  /**
   * Schedule an event for multiple users (if there are multiple).
   * @param event an event to schedule
   * @throws IllegalArgumentException if the event being scheduled does not fit into all schedules
   * @throws IllegalArgumentException if the system has no schedule in it
   */
  @Override
  public void scheduleEvent(EventInterface event) {
    if (usersAndSchedule.isEmpty()) {
      throw new IllegalArgumentException("There are no schedules in this system to add to.");
    }
    usersAndSchedule.forEach((key, value) -> {
      for (String person : event.getInvitees()) {
        if (key.equals(person)) {
          value.addEvent(event);
        }
      }
    });
  }

  /**
   * Removes an event for multiple users (if there are multiple).
   * @param event an event to remove
   * @throws IllegalArgumentException if there are no schedules in the system to remove from
   */
  @Override
  public void removeEvent(EventInterface event) {
    if (usersAndSchedule.isEmpty()) {
      throw new IllegalArgumentException("There are no schedules in this system to remove from.");
    }
    usersAndSchedule.forEach((key, value) -> {
      for (String person : event.getInvitees()) {
        if (key.equals(person)) {
          value.removeEvent(event);
        }
      }
    });
  }

  @Override
  public ScheduleInterface pickCurrentDisplay(String user) {
    if (usersAndSchedule.isEmpty()) {
      throw new IllegalArgumentException("There are no schedules in this system to display.");
    }
    ScheduleInterface schedule = usersAndSchedule.get(user);

    if (schedule == null) {
      throw new IllegalArgumentException("No such user in system");
    }

    this.user = user;
    return new Schedule(schedule.getUser(), schedule.getEvents());
  }

  @Override
  public List<String> getTheUsers() {
    List<String> users = new ArrayList<>();

    for (String user : this.usersAndSchedule.keySet()) {
      users.add(user);
    }

    return Collections.unmodifiableList(users);
  }

  @Override
  public List<EventInterface> getTheEvents(String user) {
    if (!this.usersAndSchedule.containsKey(user)) {
      throw new IllegalArgumentException("There is no user in this system with that uid!");
    }

    return Collections.unmodifiableList(this.usersAndSchedule.get(user).getEvents());
  }

  @Override
  public boolean fitsSchedule(Day startDay, Time startTime, Day endDay, Time endTime) {
    boolean fits = true;
    Event psuedoEvent = new Event(startDay, startTime, endDay, endTime);
    for (String key : usersAndSchedule.keySet()) {
      fits = usersAndSchedule.get(key).fitsInSchedule(psuedoEvent);

      if (!fits) {
        return false;
      }
    }

    return fits;
  }

  @Override
  public boolean fitsUserSchedule(Day startDay, Time startTime, Day endDay, Time endTime,
                                  List<String> user) {
    boolean fits = true;
    EventInterface psuedoEvent = new Event(startDay, startTime, endDay, endTime);
    for (String key : usersAndSchedule.keySet()) {
      for (String name : user) {
        fits = usersAndSchedule.get(key).fitsInSchedule(psuedoEvent);
        if (!fits) {
          return false;
        }
      }
    }

    return fits;
  }

  @Override
  public void createEvent(String name, String location, boolean isOnline,
                          Day startDay, Time startTime, Day endDay, Time endTime,
                          List<String> invitees) {
    invitees.remove(user);
    List<String> inviteesHostFirst = new ArrayList<>();
    inviteesHostFirst.add(user);
    inviteesHostFirst.addAll(invitees);

    this.scheduleEvent(new Event(name, location, isOnline, startDay, startTime, endDay, endTime,
            inviteesHostFirst));
  }

  @Override
  public void changeEventAt(Time givenStart, Day givenStartDay, String eventName, String location,
                            boolean onlineBool, Day startDay, Time startTime, Day endDay,
                            Time endTime, List<String> inviteesList) {
    EventInterface old = this.getEventAtTime(givenStart,this.user, givenStartDay);
    List<String> inviteesList2 = new ArrayList<String>();
    inviteesList2.addAll(inviteesList);
    usersAndSchedule.forEach((key, value) -> {
      for (String person : inviteesList2) {
        if (key.equals(person)) {
          value.modifyEvent(givenStart, givenStartDay, eventName, location,
                  onlineBool, startDay, startTime, endDay, endTime, inviteesList);
        }
      }
    });
  }

  @Override
  public void removeEventAt(Time givenStart, Day givenStartDay, String user) {
    usersAndSchedule.forEach((key, value) -> {
      if (key.equals(user)) {
        if (user.equals(value.getEventAtTime(givenStart, givenStartDay).getHost())) {
          this.removeEvent(value.getEventAtTime(givenStart, givenStartDay));
        }
        else {
          value.removeEvent(givenStart, givenStartDay, user);
        }
      }
    });
  }

  @Override
  public void setUser(String user) {
    this.user = user;
  }

  @Override
  public boolean eventFitsSchedule(EventInterface event) {
    boolean fits = true;
    for (String key : usersAndSchedule.keySet()) {
      for (String user : event.getInvitees()) {
        if (key.equals(user)) {
          fits = usersAndSchedule.get(key).fitsInSchedule(event);
          if (!fits) {
            return false;
          }
        }
      }
    }

    return fits;
  }

  @Override
  public boolean eventAtTime(Time time, String user, Day day) {
    if (!usersAndSchedule.containsKey(user)) {
      throw new IllegalArgumentException("User not in system");
    }

    return usersAndSchedule.get(user).checkEventAt(day, time);
  }

  @Override
  public EventInterface getEventAtTime(Time time, String user, Day day) {
    if (!usersAndSchedule.containsKey(this.user)) {
      throw new IllegalArgumentException("User not in system");
    }

    return usersAndSchedule.get(this.user).getEventAtTime(time, day);
  }

  @Override
  public String getUser() {
    return this.user;
  }


}

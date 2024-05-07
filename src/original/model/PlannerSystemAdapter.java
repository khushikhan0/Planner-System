package original.model;

import java.util.ArrayList;
import java.util.List;

import cs3500.plannersystem.model.DayOfWeek;
import cs3500.plannersystem.model.ICentralSystem;
import cs3500.plannersystem.model.IEvent;
import cs3500.plannersystem.model.ISchedule;

import static original.model.AdapterUtils.getEventInterface;
import static original.model.AdapterUtils.toDay;
import static original.model.AdapterUtils.toTime;

/**
 * A PlannerSystemAdapter adapts a PlannerModel to work with an ICentralSystem.
 */
public class PlannerSystemAdapter extends ReadOnlySystemAdapter implements ICentralSystem {
  private PlannerModel plannerSystem;

  public PlannerSystemAdapter(PlannerModel plannerSystem) {
    super(plannerSystem);
    this.plannerSystem = plannerSystem;
  }

  /**
   * Determines if the list of users in this schedule contains the given uid.
   * @param uid the user's id
   * @return true if the user is in the system, false if they are not
   */
  private boolean doesUserExist(String uid) {
    for (String user : plannerSystem.getTheUsers()) {
      return user.equals(uid);
    }
    return false;
  }

  @Override
  public void addUser(String uid, String name) {
    //takes in a user and gives them an empty schedule
    this.addUserSchedule(uid, new ScheduleAdapter(new Schedule(uid)));
  }

  @Override
  public void removeUser(String uid) {
    plannerSystem.modifySystem(uid, plannerSystem.pickCurrentDisplay(uid),
              false);
  }

  @Override
  public void createEvent(IEvent event, String hostUid) {
    if (!this.doesUserExist(hostUid)) {
      throw new IllegalArgumentException("There is no such host user in the system.");
    }
    else {
      //sets the host of the event
      event.setHostUserId(hostUid);

      //gets each person invited to the event
      for (String invitee : event.getParticipants()) {
        //gets the ISchedule representation of the invitee's schedule to gain access to other
        //methods
        ISchedule schedule = new ScheduleAdapter(plannerSystem.pickCurrentDisplay(invitee));

        //if there is no scheduling conflict and the event doesn't already exist in their schedule,
        //add the event to their schedule
        if (!schedule.hasConflict(event) && schedule.getEventByName(event.getName()) != null) {
          schedule.addEvent(event);
        }
      }
    }
  }

  @Override
  public void modifyEvent(IEvent originalEvent, IEvent modifiedEvent) {
    //gets each person who was invited to the original event
    for (String invitee : originalEvent.getParticipants()) {
      //checks if they're still invited to the modified event
      if (modifiedEvent.getParticipants().contains(invitee)) {
        //gets the ISchedule representation of the invitee's event
        ISchedule schedule = new ScheduleAdapter(plannerSystem.pickCurrentDisplay(invitee));

        //checks that there is no scheduling conflict
        if (!schedule.hasConflict(modifiedEvent)) {
          //modifies the event
          schedule.modifyEvent(originalEvent, modifiedEvent);
        }

      }
      else {
        //gets the invitees schedule
        ISchedule schedule = new ScheduleAdapter(plannerSystem.pickCurrentDisplay(invitee));

        //removes the original event from their schedule if they're no longer invited
        schedule.removeEvent(originalEvent);
      }
    }
  }

  @Override
  public void removeEvent(IEvent event, String requestingUid) {
    //checks if the requestingUid is the host of the event
    if (requestingUid.equals(event.getHostUserId())) {
      //gets each person invited to the event
      this.removeEvent(event);
    }
    else {
      //gets the schedule of the non-host requesting to delete the event
      ISchedule schedule = new ScheduleAdapter(plannerSystem.pickCurrentDisplay(requestingUid));

      //removes the event from that one person's schedule
      schedule.removeEvent(event);
    }
  }

  @Override
  public void addUserSchedule(String userId, ISchedule schedule) {
    List<EventInterface> events = getEventInterfaces(schedule);

    ScheduleInterface newSchedule = new Schedule(userId, events);

    plannerSystem.modifySystem(userId, newSchedule, true);
  }

  private static List<EventInterface> getEventInterfaces(ISchedule schedule) {
    List<EventInterface> events = new ArrayList<>();

    for (IEvent event : schedule.getEvents()) {
      events.add(getEventInterface(event));
    }
    return events;
  }

  @Override
  public void addUsersSchedule(String[] userIds, ISchedule[] schedules) {
    //checks if the two arrays are the same length
    if (userIds.length != schedules.length) {
      throw new IllegalArgumentException("Each user must have only one schedule.");
    }
    else {
      //iterates through each list
      for (int user = 0; user < userIds.length; user++) {
        ArrayList<String> userIdsArray = new ArrayList<>();
        userIdsArray.addAll(List.of(userIds));

        ArrayList<ISchedule> schedulesArray = new ArrayList<>();
        schedulesArray.addAll(List.of(schedules));

        ScheduleInterface newSchedule = new Schedule(userIdsArray.get(user),
                getEventInterfaces(schedulesArray.get(user)));

        plannerSystem.modifySystem(userIdsArray.get(user), newSchedule,
                true);
      }
    }
  }

  @Override
  public void removeUserSchedule(String userId) {
    plannerSystem.modifySystem(userId, plannerSystem.pickCurrentDisplay(userId), false);
  }

  @Override
  public boolean addEventToUser(String userId, IEvent event) {
    //gets the user's schedule to add an event to
    ScheduleInterface schedule = plannerSystem.pickCurrentDisplay(userId);

    //checks that the event fits in the schedule
    if (schedule.fitsInSchedule(getEventInterface(event))) {
      schedule.addEvent(getEventInterface(event));
    }

    //returns true if the event exists, returns false if not
    return schedule.checkEventAt(getEventInterface(event).getAStartDay(),
            getEventInterface(event).getAStartTime());
  }

  /**
   * Add a schedule to a planner system.
   * @param user a user to add to system.
   * @param schedule a schedule to add to a system.
   * @param addSchedule if true, add a schedule to a system. if false, remove a schedule from a
   *                    system.
   */
  public void modifySystem(String user, ISchedule schedule, boolean addSchedule) {
    if (addSchedule) {
      this.addUserSchedule(user, schedule);
    }
    else {
      this.removeUserSchedule(user);
    }
  }

  /**
   * Schedule an event for multiple users (if there are multiple).
   * @param event an event to schedule
   * @throws IllegalArgumentException if the event being scheduled does not fit into all schedules
   * @throws IllegalArgumentException if the system has no schedule in it
   */
  public void scheduleEvent(IEvent event) {
    this.createEvent(event, event.getHostUserId());
  }

  /**
   * Removes an event for multiple users (if there are multiple).
   * @param event an event to remove
   */
  public void removeEvent(IEvent event) {
    for (String invitee : event.getParticipants()) {
      //gets the schedule of the person invited
      ISchedule schedule = new ScheduleAdapter(plannerSystem.pickCurrentDisplay(invitee));

      //removes the event from each person's schedule
      schedule.removeEvent(event);
    }
  }

  /**
   * Returns true if the given time frame is available for everyone in this planner.
   * @param startDay the event's start day
   * @param startTime the event's start time
   * @param endDay the event's end day
   * @param endTime the event's end time
   *
   */
  public boolean fitsSchedule(DayOfWeek startDay, int startTime, DayOfWeek endDay, int endTime) {
    return plannerSystem.fitsSchedule(toDay(startDay), toTime(startTime), toDay(endDay),
            toTime(endTime));
  }

  /**
   * Returns true if the given time frame is available for everyone in the given list of users.
   * @param startDay the event's start day
   * @param startTime the event's start time
   * @param endDay the event's end day
   * @param endTime the event's end time
   * @param users the users whose schedules to check
   *
   */
  public boolean fitsUserSchedule(DayOfWeek startDay, int startTime, DayOfWeek endDay, int endTime,
                                  List<String> users) {
    return plannerSystem.fitsUserSchedule(toDay(startDay), toTime(startTime), toDay(endDay),
            toTime(endTime), users);
  }

  /**
   * Creates a new event with the given and adds it the schedule.
   * @param name event name
   * @param startDay start day
   * @param startTime start time
   * @param endDay end day
   * @param endTime end time
   * @param invitees list of people to add
   */
  public void createEvent(String name, String location, boolean isOnline,
                          DayOfWeek startDay, int startTime, DayOfWeek endDay, int endTime,
                          List<String> invitees) {
    plannerSystem.createEvent(name, location, isOnline, toDay(startDay), toTime(startTime),
            toDay(endDay), toTime(endTime), invitees);
  }

  /**
   * changes the event at the given time.
   * @param givenStart start time of event to change
   * @param givenStartDay start day of event to change
   * @param eventName new name
   * @param location new location
   * @param onlineBool new online toggle
   * @param startDay new start day
   * @param startTime new start time
   * @param endDay new end day
   * @param endTime new end time
   * @param inviteesList updated list of invitees
   */
  public void changeEventAt(int givenStart, DayOfWeek givenStartDay, String eventName,
                            String location, boolean onlineBool, DayOfWeek startDay, int startTime,
                            DayOfWeek endDay, int endTime, List<String> inviteesList) {
    plannerSystem.changeEventAt(toTime(givenStart), toDay(givenStartDay), eventName, location,
            onlineBool, toDay(startDay), toTime(startTime), toDay(endDay), toTime(endTime),
            inviteesList);
  }

  /**
   * removes the event at the given time for the given user.
   * @param givenStart start time of event to remove
   * @param givenStartDay start day of event to remove
   * @param user user to remove for
   */
  public void removeEventAt(int givenStart, DayOfWeek givenStartDay, String user) {
    plannerSystem.removeEventAt(toTime(givenStart), toDay(givenStartDay), user);
  }

  /**
   * Sets the user to the correct user.
   * @param user to set to
   */
  public void setUser(String user) {
    plannerSystem.setUser(user);
  }
}

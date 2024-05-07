package original.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A model.Schedule has a user as well as an arraylist of events.
 */
public class Schedule implements ScheduleInterface {
  private final String user;
  private final List<EventInterface> events;

  /**
   * A model.Schedule with a user and an empty list of events.
   * @param user the user who owns this schedule.
   */
  public Schedule(String user) {
    this.user = Objects.requireNonNull(user);
    this.events = new ArrayList<EventInterface>();
  }

  /**
   * Convenience constructor for PlannerSystem.
   * @param user the user who owns this schedule.
   * @param events the events in the user's schedule.
   */
  public Schedule(String user, List<EventInterface> events) {
    this.user = Objects.requireNonNull(user);
    this.events = Objects.requireNonNull(events);
    for (int index = 0; index < events.size(); index ++) {
      EventInterface currEvent = events.remove(0);

      for (int index2 = 0; index2 < events.size(); index2++) {
        if (!currEvent.doEventsFit(events.get(index2))) {
          throw new IllegalArgumentException("Some events in this schedule overlap");
        }
      }

      events.add(currEvent);
    }
  }

  public ScheduleInterface addEvent(EventInterface event) {
    if (!this.fitsInSchedule(event)) {
      throw new IllegalArgumentException("This is event time conflicts with other events");
    }
    if (!this.events.contains(event)) {
      this.events.add(event);
    }
    return this;
  }

  public ScheduleInterface removeEvent(EventInterface event) {
    this.events.remove(event);
    return this;
  }

  public void removeEvent(Time time, Day day, String user) {
    EventInterface toRemove = null;
    for (EventInterface event : this.events) {
      if (event.isAt(day, time)) {
        toRemove = event;
      }
    }

    if (toRemove != null) {
      toRemove.removeInvitee(this.user);
      this.removeEvent(toRemove);
    }
  }

  public String getUser() {
    return this.user;
  }

  public ArrayList<EventInterface> getEvents() {
    ArrayList<EventInterface> clone = new ArrayList<>();
    clone.addAll(this.events);
    return (clone);
  }

  public boolean fitsInSchedule(EventInterface event) {
    boolean fits = true;
    for (EventInterface value : this.events) {

      fits = value.doEventsFit(event);

      if (!fits) {
        return false;
      }
    }
    return fits;
  }

  public EventInterface getEventAtTime(Time time, Day day) {
    for (EventInterface event : this.events) {
      if (event.isAt(day, time)) {
        return event;
      }
    }
    throw new IllegalArgumentException("No events at this time");
  }

  public boolean checkEventAt(Day day, Time time) {
    boolean isEvent = false;
    for (EventInterface event : this.events) {
      isEvent = event.isAt(day, time);

      if (isEvent) {
        return true;
      }
    }

    return false;
  }

  public void modifyEvent(Time givenStart, Day givenStartDay, String eventName, String location,
                          boolean onlineBool, Day startDay, Time startTime, Day endDay,
                          Time endTime, List<String> inviteesList) {
    for (EventInterface event : this.events) {
      if (event.isAt(givenStartDay, givenStart)) {
        event.changeName(eventName);
        event.changeLocation(location,onlineBool);
        event.changeDay(startDay,true);
        event.changeDay(endDay,false);
        event.changeTime(startTime, true);
        event.changeTime(endTime, false);
        event.changeInvitees(inviteesList);

      }
    }
  }
}


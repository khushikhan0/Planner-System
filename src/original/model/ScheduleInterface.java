package original.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an interface for a Schedule for a planner.
 */
public interface ScheduleInterface {
  /**
   * Add an event to the list of events, can build on top of it to make it easier to add multiple
   * events.
   * @param event event to add to the list of events.
   * @throws IllegalArgumentException if the event time conflicts with another
   *                                  event in this schedule
   */
  ScheduleInterface addEvent(EventInterface event);

  /**
   * Removes an event from the list of events, can build on top of it to make it easier to remove
   * multiple events.
   * @param event event to remove from the list of events.
   */
  ScheduleInterface removeEvent(EventInterface event);

  /**
   * removes an event at a given time for a user.
   * @param time time to remove event from
   * @param day to remove event from
   * @param user user to remove event from
   */
  void removeEvent(Time time, Day day, String user);

  /**
   * Gets this owner.
   * @return the user of this Schedule
   */
  String getUser();

  /**
   * Gets an unmodifiable list of events.
   * @return unmodifiable list of events.
   */
  ArrayList<EventInterface> getEvents();

  /**
   * Does the given event fit in this schedule.
   * @param event the event to be fitted
   * @return true if it doesn't conflict with any other events in this schedule, false if otherwise
   */
  boolean fitsInSchedule(EventInterface event);

  /**
   * Gets an event at the given time.
   * @param time the time to get
   * @return the Event if there is an event occurring at the given time.
   */
  EventInterface getEventAtTime(Time time, Day day);

  /**
   * Determines if there is an event at the given day and time.
   * @param day day to check
   * @param time time to check
   * @return true if there is, false if otherwise
   */
  boolean checkEventAt(Day day, Time time);

  /**
   * Changes this event.
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
  void modifyEvent(Time givenStart, Day givenStartDay, String eventName, String location,
                   boolean onlineBool, Day startDay, Time startTime, Day endDay,
                   Time endTime, List<String> inviteesList);
}

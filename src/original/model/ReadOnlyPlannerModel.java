package original.model;

import java.util.List;

/**
 * A ReadOnly version of the PlannerModel. Does not allow user to modify the system at all.
 */
public interface ReadOnlyPlannerModel {
  /**
   * Returns an unmodifiable schedule to be displayed.
   * @param user the user whose schedule should be displayed
   */
  ScheduleInterface pickCurrentDisplay(String user);

  /**
   * Gets an unmodifiable list of users in the current system.
   * @return an unmodifiable list of users in the system
   */
  List<String> getTheUsers();

  /**
   * Returns an unmodifiable list of all the events of a user.
   * @return an unmodifiable list of events
   */
  List<EventInterface> getTheEvents(String user);

  /**
   * Returns true if the given time frame is available for everyone in the event.
   * @param event checks if the event fits in everyone's schedule.
   */
  boolean eventFitsSchedule(EventInterface event);

  /**
   * Is there an event at the given time.
   *
   * @param time the time to check
   * @param user user to check
   * @param day day to check
   * @return true if there is an event occuring at this time
   */
  boolean eventAtTime(Time time, String user, Day day);

  /**
   * Returns the event happening at this time.
   *
   * @param time is the time to retrieve event
   * @param user user to check
   * @param day day to check
   * @return event that is occurring at this time
   * @throws IllegalArgumentException if there is no event at the given time.
   */
  EventInterface getEventAtTime(Time time, String user, Day day);

  /**
   * Returns the user of the current Schedule being displayed.
   *
   * @return the user of this schedule
   */
  String getUser();
}

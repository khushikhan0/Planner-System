package original.model;

import java.util.List;

/**
 * A PlannerModel is a planner system that contains users and their corresponding schedules.
 * In a system, there can be more than one user.
 */
public interface PlannerModel extends ReadOnlyPlannerModel {
  /**
   * Modifies a system depending on the boolean you input.
   * @param user a user to search for in a hashmap of users and schedules.
   * @param schedule a corresponding schedule to search for in mentioned hashmap.
   * @param addSchedule true == add a schedule to hashmap, false == remove a schedule from hashmap
   */
  void modifySystem(String user, ScheduleInterface schedule, boolean addSchedule);

  /**
   * Schedule an event for multiple users.
   * @param event an event to schedule.
   * @throws IllegalArgumentException if there are no schedules in the system
   * @throws IllegalArgumentException if the event doesn't fit into any of the invitees schedules
   */
  void scheduleEvent(EventInterface event);

  /**
   * Remove an event for multiple users.
   * @param event an event to remove.
   * @throws IllegalArgumentException if there are no schedules in the system
   */
  void removeEvent(EventInterface event);

  /**
   * Returns true if the given time frame is available for everyone in this planner.
   * @param startDay the event's start day
   * @param startTime the event's start time
   * @param endDay the event's end day
   * @param endTime the event's end time
   *
   */
  boolean fitsSchedule(Day startDay, Time startTime, Day endDay, Time endTime);

  /**
   * Returns true if the given time frame is available for everyone in the given list of users.
   * @param startDay the event's start day
   * @param startTime the event's start time
   * @param endDay the event's end day
   * @param endTime the event's end time
   * @param user the users whose schedules to check
   *
   */
  boolean fitsUserSchedule(Day startDay, Time startTime, Day endDay, Time endTime,
                           List<String> user);

  /**
   * Creates a new event with the given and adds it the schedule.
   * @param name event name
   * @param startDay start day
   * @param startTime start time
   * @param endDay end day
   * @param endTime end time
   * @param invitees list of people to add
   */
  void createEvent(String name, String location, boolean isOnline, Day startDay, Time startTime,
                   Day endDay, Time endTime, List<String> invitees);

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
  void changeEventAt(Time givenStart, Day givenStartDay, String eventName, String location,
                     boolean onlineBool, Day startDay, Time startTime, Day endDay,
                     Time endTime, List<String> inviteesList);

  /**
   * removes the event at the given time for the given user.
   * @param givenStart start time of event to remove
   * @param givenStartDay start day of event to remove
   * @param user user to remove for
   */
  void removeEventAt(Time givenStart, Day givenStartDay, String user);

  /**
   * Sets the user to the correct user.
   * @param user to set to
   */
  void setUser(String user);
}

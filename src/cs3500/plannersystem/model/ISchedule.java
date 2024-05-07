package cs3500.plannersystem.model;

import java.util.List;

/**
 * An interface for a Schedule.
 */
public interface ISchedule {

  /**
   * Adds an event to the schedule.
   *
   * @param event the event wanting to be added
   */
  void addEvent(IEvent event);

  /**
   * Removes an event from the schedule.
   *
   * @param event the event wanting to be removed
   * @return the status of removing the event
   */
  boolean removeEvent(IEvent event);

  /**
   * Gets the events in the schedule for a certain day.
   *
   * @param day the day being viewed
   * @return a list of events for that day
   */
  List<IEvent> getEventsForDay(DayOfWeek day);

  /**
   * Gets the Event by its name.
   *
   * @param eventName the name of the event
   * @return the event specified
   */
  IEvent getEventByName(String eventName);

  /**
   * Modifies the event.
   *
   * @param oldEvent the original event
   * @param newEvent the modified event
   * @return the status of modifying the event
   */
  boolean modifyEvent(IEvent oldEvent, IEvent newEvent);

  /**
   * Checks if an event has a conflict with another.
   *
   * @param event the event wanting to be checked
   * @return the status of checking
   */
  boolean hasConflict(IEvent event);

  /**
   * Gets the events in a schedule.
   *
   * @return a list of events in a schedule
   */
  List<IEvent> getEvents();
}

package original.model;
import java.util.List;

/**
 * Represents an interface for an Event for a planner.
 */
public interface EventInterface {

  /**
   * Handles adding invitees.
   *
   * @param user a user to add or remove.
   */
  EventInterface addInvitee(String user);

  /**
   * Handles removing invitees.
   *
   * @param user a user to add or remove.
   */
  EventInterface removeInvitee(String user);

  /**
   * Changes the name of the event.
   *
   * @param newName the name to change the current event name to.
   */
  void changeName(String newName);

  /**
   * Change the location of the event.
   *
   * @param location the location to change the current location to.
   * @param isOnline whether the new location is online.
   */
  void changeLocation(String location, boolean isOnline);

  /**
   * Change the time of the event depending on the boolean given.
   *
   * @param newTime         the time to change the current time to.
   * @param changeStartTime if true, change the start time. if false, change the end time.
   */
  void changeTime(Time newTime, boolean changeStartTime);

  /**
   * Change the day of the event depending on the boolean given.
   *
   * @param newDay         the day to change the current day to.
   * @param changeStartDay if true, change the start day. if false, change the end day.
   */
  void changeDay(Day newDay, boolean changeStartDay);

  /**
   * Get start day of the event.
   *
   * @return start day of event.
   */
  Day getAStartDay();

  /**
   * Get end day of the event.
   *
   * @return end day of event.
   */
  Day getAEndDay();

  /**
   * Get start time of the event.
   *
   * @return start time of event.
   */
  Time getAStartTime();

  /**
   * Get end time of the event.
   *
   * @return end time of event.
   */
  Time getAEndTime();

  /**
   * Get name of the event.
   *
   * @return name of the event.
   */
  String getAName();

  /**
   * Get location of the event.
   *
   * @return location of the event.
   */
  String getALocation();

  /**
   * Get whether the event is online.
   *
   * @return whether the event is online or not.
   */
  boolean getIsOnline();

  /**
   * Gets the invitees of this event.
   *
   * @return invitees.
   */
  List<String> getInvitees();

  /**
   * Gets the host of an event -> the first person in a list of invitees.
   *
   * @return the host of an event
   */
  String getHost();

  /**
   * Converts the invitees in a list of strings to usernames on separate lines.
   *
   * @return line separated users
   */
  String inviteesToString();

  /**
   * Determines how many days this event spans.
   * Events that start and end on the same day have a span of 1.
   *
   * @return the number of days this event takes place on
   */
  int getSpan();

  /**
   * returns the duration of this event in minutes.
   *
   * @return the duration of this event in minutes
   */
  int getDurationInMinutes();

  /**
   * Can these events occur without overlapping?
   * Returns true if they don't overlap, false if they do.
   *
   * @param other the other event to check
   */
  boolean doEventsFit(EventInterface other);

  /**
   * Makes an unmodifiable copy of this event.
   *
   * @return an unmodifiable copy of this event.
   */
  EventInterface makeCopy();

  /**
   * Does this day fall within or on the span of this event.
   *
   * @param day day to check
   * @return true if it occurs on the same day or in the middle of this event
   */
  boolean isDayDuring(Day day);

  /**
   * Determines if this day and time fall within the span of this event.
   *
   * @param day  to check
   * @param time to check
   * @return true if it does, false if it doesn't
   */
  boolean isAt(Day day, Time time);

  /**
   * Changes this list to the given one, ensuring that the host is still 0.
   *
   * @param inviteesList new list
   */
  void changeInvitees(List<String> inviteesList);


  /**
   * Checks if the events overlaps by checking the start days.
   * @param other other event to check
   * @return true if these events overlaps
   */
  boolean checkDays(EventInterface other);

  // assumes that this event and other start on the same day
  // and that this event has an earlier start time other

  /**
   * Checks if the events overlaps by checking the start days.
   * Assumes that this event starts the same day as the given event.
   * @param other other event to check
   * @return true if these events overlap
   */
  boolean checkSameDayStart(EventInterface other);

  /**
   * Checks if the events overlaps by checking the start days.
   * Assumes that this event starts before the given event.
   * @param other the other event to check
   * @return true if the events overlap
   */
  boolean checkDifferentDayStart(EventInterface other);

  /**
   * Checks if the events overlap by time.
   * Assumes that this event starts before or on the same day as the given event.
   * @param other the other event to check
   * @return true if this event times overlap
   */
  boolean checkTimes(EventInterface other);
}

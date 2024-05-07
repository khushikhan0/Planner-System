package cs3500.plannersystem.model;

import java.util.List;

/**
 * Interface for an event.
 */
public interface IEvent {

  /**
   * Gets the name of the event.
   * @return the name of the event
   */
  String getName();

  /**
   * Sets the name of the event.
   * @param name the new name of the event
   */
  void setName(String name);

  /**
   * Returns the boolean value of if the event is online.
   * @return the value of the event's onlien status
   */
  boolean isOnline();

  /**
   * Sets the event's online status.
   * @param online whether or not the event's online
   */
  void setOnline(boolean online);

  /**
   * Gets the start day of the event.
   * @return the start day of the event
   */
  DayOfWeek getStartDay();

  /**
   * Sets the start day of the event.
   * @param startDay the day the event will start
   */
  void setStartDay(DayOfWeek startDay);

  /**
   * Gets the start time of the event.
   * @return the time the event will start
   */
  int getStartTime();

  /**
   * Sets the start time of the event.
   * @param startTime the start time of the event
   */
  void setStartTime(int startTime);

  /**
   * Gets the ending day of the event.
   * @return the day the event ends
   */
  DayOfWeek getEndDay();

  /**
   * Sets the day the event ends.
   * @param endDay the day the event ends
   */
  void setEndDay(DayOfWeek endDay);

  /**
   * Gets the end time of the event.
   * @return the time the event will end
   */
  int getEndTime();

  /**
   * Sets the ending time of the event.
   * @param endTime the time the will end
   */
  void setEndTime(int endTime);

  /**
   * Gets the list of participants attending the event.
   * @return a list of event attendees
   */
  List<String> getParticipants();

  /**
   * Sets the list of participants attending the event.
   * @param participants the list of event attendees
   */
  void setParticipants(List<String> participants);

  /**
   * Gets the host's userId.
   * @return the uId of the host
   */
  String getHostUserId();

  /**
   * Sets the host's userId.
   * @param hostUserId the userId of the host
   */
  void setHostUserId(String hostUserId);

  /**
   * Gets the location of the event.
   * @return the location of the event
   */
  String getLocation();

  /**
   * Sets the location of the event.
   * @param newLoc the location of the event
   */
  void setLocation(String newLoc);
}

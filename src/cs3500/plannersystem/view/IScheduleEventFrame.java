package cs3500.plannersystem.view;

import java.util.List;

/**
 * Interface for event frame that automatically schedules an event for selected users.
 */
public interface IScheduleEventFrame {

  /**
   * A brute force approach at automatically scheduling an event for the first open time slot.
   * @param eventName name of event.
   * @param eventDuration how long the event is.
   * @param participants who's participating.
   * @return boolean value of whether the function was able to run.
   */
   boolean bruteForceScheduleEvent(String eventName, int eventDuration, List<String> participants);
}

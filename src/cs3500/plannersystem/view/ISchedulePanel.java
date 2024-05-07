package cs3500.plannersystem.view;

import cs3500.plannersystem.model.DayOfWeek;

/**
 * Interface for the schedule panel that will be displayed on the main screen.
 */
public interface ISchedulePanel {

  /**
   * Adds an event entry to the schedule seen in the main screen.
   * @param day day of the week to add.
   * @param startHour starting time.
   * @param endHour ending time.
   * @param selectedId ID of the host.
   */
  void addEventEntry(DayOfWeek day, Double startHour, Double endHour, String selectedId);

  /**
   * Clears existing schedule and redraws the events.
   */
  void clearSchedule();
}

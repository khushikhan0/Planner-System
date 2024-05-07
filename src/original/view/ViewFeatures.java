package original.view;

import java.io.File;
import java.util.List;

import original.model.Day;
import original.model.PlannerModel;
import original.model.ScheduleInterface;
import original.model.Time;

/**
 * Contains view features that a plannerSystem gui should be able to do.
 */
public interface ViewFeatures {
  /**
   * Sets the view to visible
   */
  void startGame();

  /**
   * Changes the schedule being displayed.
   */
  void changeSchedule(String user);

  /**
   * Allows the user to pick a calendar from their files and upload it.
   * @param selectedFile file to be uploaded as a calendar
   */
  void addCal(File selectedFile);

  /**
   * Allows the user to save the current schedule to a xml file.
   * @param schedule the schedule to save
   */
  void saveCal(ScheduleInterface schedule);

  /**
   * Uses the given values to add a new event to the current user's schedule.
   * @param eventName name of the event
   * @param onlineBool is it online
   * @param startDay day to start
   * @param startTime time to start
   * @param endDay day to end
   * @param endTime time to end
   * @param invitees list of users Invited
   */
  void createEvent(String eventName, String location, boolean onlineBool, String startDay,
                   String startTime, String endDay, String endTime, List<String> invitees);

  /**
   * Modifies the event starting at the given start day and time to include the information given.
   * @param givenStart start time of event to modify
   * @param givenStartDay start day of the event to modify
   * @param eventName new name for event
   * @param location new location for event
   * @param onlineBool new location toggle for event
   * @param startDay new event start day
   * @param startTime new event start time
   * @param endDay new end day for event
   * @param endTime new end time for event
   * @param invitees updated list of invitees
   */
  void modifyEvent(Time givenStart, Day givenStartDay, String eventName, String location,
                   boolean onlineBool, String startDay, String startTime, String endDay,
                   String endTime, List<String> invitees);

  /**
   * removes the event at the given time.
   * @param givenStart time to remove event from.
   * @param givenStartDay day to remove event from
   */
  void removeEvent(Time givenStart, Day givenStartDay);

  /**
   * Sets the user of the model to the selected user.
   * @param user to select
   */
  void setUser(String user);

  /**
   * returns this model.
   * @return the associated model.
   */
  PlannerModel getModel();

  /**
   * returns the view
   * @return
   */
  PlannerViewGUI getView();
}

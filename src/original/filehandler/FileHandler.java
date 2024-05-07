package original.filehandler;

import java.io.File;

import original.model.Schedule;
import original.model.ScheduleInterface;

/**
 * Handles reading Files and conversion of Files to and from Schedules.
 */
public interface FileHandler {

  /**
   * Creates a File from the given schedule.
   * @param schedule the schedule to be turned into a file
   */
  public void createFile(ScheduleInterface schedule);

  /**
   * Turns the given file into a schedule if it is in the expected format for a schedule.
   * @param file file to be converted
   * @return a Schedule that represents the data in the given file
   * @throws IllegalArgumentException if the given file does not represent a calendar
   */
  public Schedule interpretFile(File file);
}

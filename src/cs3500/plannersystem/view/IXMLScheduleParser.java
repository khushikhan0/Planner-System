package cs3500.plannersystem.view;

import cs3500.plannersystem.model.ICentralSystem;

/**
 * Interface for XML scheduler parser that reads and loads up the schedule.
 */
public interface IXMLScheduleParser {

  /**
   * Actually parse the file and load up a corresponding schedule.
   * @param filePath the absolute path to the xml file you wish to parse.
   * @param centralSystem the empty instance of a central system.
   */
  void parseAndLoadSchedule(String filePath, ICentralSystem centralSystem);
}

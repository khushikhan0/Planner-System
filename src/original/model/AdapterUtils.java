package original.model;

import cs3500.plannersystem.model.DayOfWeek;
import cs3500.plannersystem.model.IEvent;

/**
 * An AdapterUtils contains static methods all the adapters need to convert between different
 * representations of days and time.
 */
public class AdapterUtils {

  /**
   * Converts a Day to a DayOfWeek representation.
   * @param theDay a Day to convert.
   * @return the DayOfWeek equivalent to Day.
   */
  public static DayOfWeek toDayOfWeek(Day theDay) {
    DayOfWeek day = DayOfWeek.SUNDAY;
    switch (theDay.getOrder()) {
      case 1:
      case 2:
        day = DayOfWeek.MONDAY;
        break;
      case 3:
        day = DayOfWeek.TUESDAY;
        break;
      case 4:
        day = DayOfWeek.WEDNESDAY;
        break;
      case 5:
        day = DayOfWeek.THURSDAY;
        break;
      case 6:
        day = DayOfWeek.FRIDAY;
        break;
      case 7:
        day = DayOfWeek.SATURDAY;
        break;
    }
    return day;
  }

  /**
   * Converts a DayOfWeek to a Day representation.
   * @param theDay a DayOfWeek to convert.
   * @return the Day equivalent to DayOfWeek.
   */
  public static Day toDay(DayOfWeek theDay) {
    Day day = Day.SUN;
    switch (theDay.getDayInInteger()) {
      case 0:
      case 1:
        day = Day.MON;
        break;
      case 2:
        day = Day.TUES;
        break;
      case 3:
        day = Day.WED;
        break;
      case 4:
        day = Day.THURS;
        break;
      case 5:
        day = Day.FRI;
        break;
      case 6:
        day = Day.SAT;
        break;
    }
    return day;
  }

  /**
   * Converts an integer representation of time to a Time representation.
   * @param oldTime an integer representation of time.
   * @return the Time equivalent of an integer time.
   */
  public static Time toTime(int oldTime) {
    int hrs = oldTime / 100;
    int mins = oldTime % 100;

    return new Time(hrs, mins);
  }

  /**
   * Converts a Time representation of time to an integer representation.
   * @param oldTime a Time representation of time.
   * @return the integer Time equivalent of a Time.
   */
  public static int toIntTime(Time oldTime) {
    int hrs = oldTime.getHours() * 100;
    int mins = oldTime.getMinutes();

    return hrs + mins;
  }

  /**
   * Turns an IEvent to an EventInterface.
   * @param event an IEvent to turn into an EventInterface.
   * @return an EventInterface
   */
  public static EventInterface getEventInterface(IEvent event) {
    return new Event(event.getName(), event.getLocation(), event.isOnline(),
            toDay(event.getStartDay()), toTime(event.getStartTime()), toDay(event.getEndDay()),
            toTime(event.getEndTime()), event.getParticipants());
  }
}

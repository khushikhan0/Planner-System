package cs3500.plannersystem.model;

/**
 * Enum class to represent the days of the week.
 */
public enum DayOfWeek {
  SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY;

  /**
   * Return the day of the week in an integer format (0 = Sunday, 6 = Saturday).
   * @return the integer value for the day.
   */
  public int getDayInInteger() {
    int day = 0;
    switch (this) {
      case SUNDAY:
        break;
      case MONDAY:
        day = 1;
        break;
      case TUESDAY:
        day = 2;
        break;
      case WEDNESDAY:
        day = 3;
        break;
      case THURSDAY:
        day = 4;
        break;
      case FRIDAY:
        day = 5;
        break;
      case SATURDAY:
        day = 6;
        break;
      default:
        day = 8;
    }
    return day;
  }
}

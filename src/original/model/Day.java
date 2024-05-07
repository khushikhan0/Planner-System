package original.model;

/**
 * A model.Day represents the days of the week with values that specify their order as well as
 * string representations of the day. Days of the week start on Sunday.
 */
public enum Day {
  SUN(1, "Sunday"), MON(2, "Monday"), TUES(3, "Tuesday"),
  WED(4, "Wednesday"), THURS(5, "Thursday"), FRI(6, "Friday"),
  SAT(7, "Saturday");

  private final int order;
  private final String name;

  /**
   * A model.Day with an order.
   *
   * @param order the order of the day. ex: sunday comes before thursday.
   * @param name  the name of the day in string format.
   */
  Day(int order, String name) {
    this.order = order;
    this.name = name;
  }

  /**
   * Convert an Enum to a String.
   *
   * @return a string version of the day of the week
   */
  public String enumToString() {
    return this.name;
  }

  /**
   * Determines if this day is before the given day.
   *
   * @param that the given day
   * @return true if this day is before the given day, false if not.
   */
  public boolean isDayBefore(Day that) {
    return this.order < that.order;
  }

  /**
   * Determines how many days this day is away from Sunday.
   * @return the number of days away from Sunday
   */
  public int getOrder() {
    return this.order;
  }
}

package original.model;

/**
 * A model.Time represents a time. It is in military time format. The hours can be between 0 and 23
 * (inclusive) and the minutes can be between 0 and 59 (inclusive).
 */
public class Time {
  private int hours;
  private int minutes;

  /**
   * This represents a model.Time with hours and minutes.
   * @param hours hours between 0 and 23 inclusive.
   * @param minutes minutes between 0 and 59 inclusive.
   */
  public Time(int hours, int minutes) {
    if ((hours < 0) || (minutes < 0) || (minutes > 59) || (hours > 23)) {
      throw new IllegalArgumentException("Hours and minutes must be 2 digits long.\n" +
              "Hours must be between 0 and 23 (inclusive), and minutes must be between 0 and 59 " +
              "(inclusive)");
    }
    this.hours = hours;
    this.minutes = minutes;
  }

  /**
   * Returns time as a four digit representation.
   * @return four number string of hours and minutes.
   */
  public String timeToString() {
    String hours = String.valueOf(this.hours);
    String minutes = String.valueOf(this.minutes);
    if (this.hours < 10) {
      hours = "0" + String.valueOf(hours);
    }
    if (this.minutes < 10) {
      minutes = "0" + String.valueOf(this.minutes);
    }
    return hours + minutes;
  }

  /**
   * Determines if this time is before the given time.
   * @param that the time to compare to
   * @return -1 if the given time is before this time
   *         0 if they are the same
   *         1 if this time is before the given time
   */
  public int isTimeBefore(Time that) {
    // same hour
    if (this.hours == that.hours) {
      // same minute
      if (this.minutes == that.minutes) {
        // same time
        return 0;
      }
      // this is before
      else if (this.minutes < that.minutes) {
        return 1;
      }
      // this is after
      else {
        return -1;
      }
    }
    // this before that
    else if (this.hours < that.hours) {
      return 1;
    }
    // that before this
    else {
      return -1;
    }
  }

  /**
   * returns the hour of this time.
   * @return the hour of this time
   */
  public int getHours() {
    return this.hours;
  }

  /**
   * returns the hour of this time.
   * @return the hour of this time
   */
  public int getMinutes() {
    return this.minutes;
  }
}

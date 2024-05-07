package original.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


/**
 * A model.Event has a name, location, something indicating whether the event is online or not,
 * a start day, a start time, an end day, an end time, and a list of invitees. An invariant of this
 * class is that the invitees list can never be empty. It always has to have at least one invitee.
 */
public class Event implements EventInterface {
  private String name;
  private String location;
  private boolean isOnline;
  private Day startDay;
  private Time startTime;
  private Day endDay;
  private Time endTime;
  private List<String> invitees; // invariant: invitees is not empty

  /**
   * An model.Event with a name, location, something indicating whether the event is online or not,
   * * a start day, a start time, an end day, an end time, and a list of invitees.
   *
   * @param name      name of the event.
   * @param location  the location where the event is being held.
   * @param isOnline  whether the event is online.
   * @param startDay  the day the event starts.
   * @param startTime the time the event starts.
   * @param endDay    the day the event ends.
   * @param endTime   the time the event ends.
   * @param invitees  invitees who are participating in this event.
   */
  public Event(String name, String location, boolean isOnline, Day startDay, Time startTime,
               Day endDay, Time endTime, List<String> invitees) {

    if (startTime.isTimeBefore(endTime) == 0 && startDay.equals(endDay)) {
      throw new IllegalArgumentException("Event must occur for longer than a minute.");
    }
    if (invitees.isEmpty()) {
      throw new IllegalArgumentException("Must have a user!");
    }
    this.name = Objects.requireNonNull(name);
    this.location = Objects.requireNonNull(location);
    this.isOnline = isOnline;
    this.startDay = Objects.requireNonNull(startDay);
    this.startTime = Objects.requireNonNull(startTime);
    this.endDay = Objects.requireNonNull(endDay);
    this.endTime = Objects.requireNonNull(endTime);
    this.invitees = Objects.requireNonNull(invitees);
  }

  /**
   * A convenience constructor that just takes in the times an event would be
   * to make checking if a time is available easier.
   * @param startDay day for event start
   * @param startTime time for event to start
   * @param endDay day for event to end
   * @param endTime time for the event to end
   */
  public Event(Day startDay, Time startTime, Day endDay, Time endTime) {
    if (startTime.isTimeBefore(endTime) == 0 && startDay.equals(endDay)) {
      throw new IllegalArgumentException("Event must occur for longer than a minute.");
    }
    this.name = " ";
    this.location = " ";
    this.isOnline = false;
    this.startDay = Objects.requireNonNull(startDay);
    this.startTime = Objects.requireNonNull(startTime);
    this.endDay = Objects.requireNonNull(endDay);
    this.endTime = Objects.requireNonNull(endTime);
    this.invitees = new ArrayList<String>(Arrays.asList("No One"));
  }

  /**
   * Handles adding invitees.
   *
   * @param user a user to add or remove.
   */
  public EventInterface addInvitee(String user) {
    if (!this.invitees.contains(user)) {
      this.invitees.add(user);
    }
    return this;
  }

  /**
   * Handles removing invitees.
   *
   * @param user a user to add or remove.
   */
  public EventInterface removeInvitee(String user) {
    this.invitees.remove(user);
    return this;
  }

  /**
   * Changes the name of the event.
   *
   * @param newName the name to change the current event name to.
   */
  public void changeName(String newName) {
    this.name = newName;
  }

  /**
   * Change the location of the event.
   *
   * @param location the location to change the current location to.
   * @param isOnline whether the new location is online.
   */
  public void changeLocation(String location, boolean isOnline) {
    this.location = location;
    this.isOnline = isOnline;
  }

  /**
   * Change the time of the event depending on the boolean given.
   *
   * @param newTime         the time to change the current time to.
   * @param changeStartTime if true, change the start time. if false, change the end time.
   */
  public void changeTime(Time newTime, boolean changeStartTime) {
    if (changeStartTime) {
      this.startTime = newTime;
    } else {
      this.endTime = newTime;
    }
  }

  /**
   * Change the day of the event depending on the boolean given.
   *
   * @param newDay         the day to change the current day to.
   * @param changeStartDay if true, change the start day. if false, change the end day.
   */
  public void changeDay(Day newDay, boolean changeStartDay) {
    if (changeStartDay) {
      this.startDay = newDay;
    } else {
      this.endDay = newDay;
    }
  }

  /**
   * Get start day of the event.
   *
   * @return start day of event.
   */
  public Day getAStartDay() {
    return this.startDay;
  }

  /**
   * Get end day of the event.
   *
   * @return end day of event.
   */
  public Day getAEndDay() {
    return this.endDay;
  }

  /**
   * Get start time of the event.
   *
   * @return start time of event.
   */
  public Time getAStartTime() {
    return this.startTime;
  }

  /**
   * Get end time of the event.
   *
   * @return end time of event.
   */
  public Time getAEndTime() {
    return this.endTime;
  }

  /**
   * Get name of the event.
   *
   * @return name of the event.
   */
  public String getAName() {
    return this.name;
  }

  /**
   * Get location of the event.
   *
   * @return location of the event.
   */
  public String getALocation() {
    return this.location;
  }

  /**
   * Get whether the event is online.
   *
   * @return whether the event is online or not.
   */
  public boolean getIsOnline() {
    return this.isOnline;
  }

  /**
   * Gets the invitees of this event.
   *
   * @return invitees.
   */
  public List<String> getInvitees() {
    return Collections.unmodifiableList(this.invitees);
  }

  /**
   * Gets the host of an event -> the first person in a list of invitees.
   * @return the host of an event
   */
  public String getHost() {
    return this.invitees.get(0);
  }

  /**
   * Converts the invitees in a list of strings to usernames on separate lines.
   *
   * @return line separated users
   */
  public String inviteesToString() {
    String users = "";

    users = users + this.invitees.get(0);

    if (this.invitees.size() == 2) {
      for (int user = 1; user < this.invitees.size(); user++) {
        users = users + "\n\t\t" + this.invitees.get(user);
      }
    } else if (this.invitees.size() > 2) {
      for (int user = 1; user < this.invitees.size() - 1; user++) {
        users = users + "\n\t\t" + this.invitees.get(user);
      }
      users = users + "\n\t\t" + this.invitees.get(this.invitees.size() - 1);
    }

    return users;
  }

  /**
   * Determines how many days this event spans.
   * Events that start and end on the same day have a span of 1.
   * @return the number of days this event takes place on
   */
  public int getSpan() {
    if (this.startDay.isDayBefore(this.endDay)) {
      return this.endDay.getOrder() - this.startDay.getOrder() + 1;
    }
    else if (this.startDay.equals(this.endDay)) {
      int timeComp = this.startTime.isTimeBefore(this.endTime);
      if ( timeComp == 1) {
        return 1;
      }
      else if (timeComp == -1) {
        return 7;
      }
      else {
        // should not reach this statement
        // throw an error?
        return 0;
      }
    }
    else {
      return 8 - this.startDay.getOrder() + this.endDay.getOrder();
    }

  }

  /**
   * returns the duration of this event in minutes.
   * @return the duration of this event in minutes
   */
  public int getDurationInMinutes() {
    int start = this.hoursToMins(startTime.getHours()) + startTime.getMinutes();
    int end = this.hoursToMins(24 - endTime.getHours()) - endTime.getMinutes();
    return this.dayToMins(this.getSpan()) - start - end;
  }

  private int dayToMins(int numDays) {
    return numDays * 1440;
  }

  private int hoursToMins(int hours) {
    return hours * 60;
  }

  /**
   * Can these events occur without overlapping?
   * Returns true if they don't overlap, false if they do.
   * @param other the other event to check
   */
  public boolean doEventsFit(EventInterface other) {
    // this event starts the same day, or day(s) before other
    if (this.startDay.isDayBefore(other.getAStartDay())) {
      return this.checkDays(other);
    }
    else {
      return other.checkDays(this);
    }
  }

  @Override
  public boolean checkDays(EventInterface other) {
    // event starts on the same day as this event
    if (this.startDay.equals(other.getAStartDay())) {
      int thisBeforeOther = this.startTime.isTimeBefore(other.getAStartTime());
      // this starts before other
      if (thisBeforeOther == 1) {
        return this.checkSameDayStart(other);
      }
      // other starts before this
      else if (thisBeforeOther == -1) {
        return other.checkSameDayStart(this);
      }
      // they start at the same time
      else {
        return false;
      }
    }
    // event starts day(s) after this event
    else {
      return this.checkDifferentDayStart(other);
    }
  }

  @Override
  public boolean checkSameDayStart(EventInterface other) {
    // this starts and ends on the same day
    if (this.getSpan() == 1) {
      // other also starts and ends on this day
      if (other.getSpan() == 1) {
        // returns true if they don't overlap
        return this.checkTimes(other);
      }
      // other goes to the next day
      else {
        return this.endTime.isTimeBefore(other.getAStartTime()) >= 0;
      }
    }
    // this spans to the next day
    else {
      // other starts after ev1
      return false;
    }
  }

  @Override
  public boolean checkDifferentDayStart(EventInterface other) {
    // this ends the same day as it start
    if (this.getSpan() == 1) {
      return true;
    }
    // this spans over multiple days
    else {
      // this ends at least a day before other starts
      if (this.endDay.isDayBefore(other.getAStartDay())) {
        return true;
      }
      // this ends the same day other starts
      else if (this.endDay.equals(other.getAStartDay())) {
        return this.endTime.isTimeBefore(other.getAStartTime()) >= 0;
      }
      // other starts day(s) before this ends
      else {
        return false;
      }
    }
  }

  @Override
  public boolean checkTimes(EventInterface other) {
    // start1 is before start2
    if (this.startTime.isTimeBefore(other.getAStartTime()) == 1) {
      // true if end1 is before start2
      return this.endTime.isTimeBefore(other.getAStartTime()) >= 0;
    }
    // start 2 is before start 1
    else if (this.startTime.isTimeBefore(other.getAStartTime()) == -1) {
      // true if end 2 is before start 1
      return other.getAEndTime().isTimeBefore(this.startTime) >= 0;
    }
    // start 1 and start 2 are at the same time
    else {
      return false;
    }
  }

  /**
   * Makes an unmodifiable copy of this event.
   * @return an unmodifiable copy of this event.
   */
  public EventInterface makeCopy() {
    return new Event(this.name, this.location, this.isOnline, this.startDay, this.startTime,
            this.endDay, this.endTime, this.invitees);
  }

  /**
   * Does this day fall within or on the span of this event.
   * @param day day to check
   * @return true if it occurs on the same day or in the middle of this event
   */
  public boolean isDayDuring(Day day) {
    if (this.startDay.isDayBefore(day) || this.startDay.equals(day)) {
      return this.getSpan() >= (day.getOrder() - startDay.getOrder());
    }
    else {
      return false;
    }
  }

  /**
   * Determines if this day and time fall within the span of this event.
   * @param day to check
   * @param time to check
   * @return true if it does, false if it doesn't
   */
  public boolean isAt(Day day, Time time) {
    if (this.isDayDuring(day)) {
      if (this.getSpan() == 1) {
        return this.startTime.isTimeBefore(time) >= 0 && this.endTime.isTimeBefore(time) <= 0;
      }
      if (this.startDay.equals(day)) {
        return this.startTime.isTimeBefore(time) >= 0;
      }
      if (this.endDay.equals(day)) {
        return this.endTime.isTimeBefore(time) <= 0;
      }
      return true;
    }
    return false;
  }

  /**
   * Changes this list to the given one, ensuring that the host is still 0.
   * @param inviteesList new list
   */
  public void changeInvitees(List<String> inviteesList) {
    List<String> update = new ArrayList<>();
    if (inviteesList.contains(this.invitees.get(0))) {
      inviteesList.remove(this.invitees.get(0));
    }

    update.add(this.invitees.get(0));

    update.addAll(inviteesList);

    this.invitees = update;

  }
}


















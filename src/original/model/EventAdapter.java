package original.model;

import java.util.ArrayList;
import java.util.List;

import cs3500.plannersystem.model.DayOfWeek;
import cs3500.plannersystem.model.IEvent;

import static original.model.AdapterUtils.toDay;
import static original.model.AdapterUtils.toDayOfWeek;
import static original.model.AdapterUtils.toIntTime;
import static original.model.AdapterUtils.toTime;

/**
 * A EventAdapter adapts an EventInterface to work with an IEvent.
 */
public class EventAdapter implements IEvent {
  private EventInterface event;

  /**
   * A EventAdapter takes in a EventInterface which can be delegated to.
   * @param event a EventInterface
   */
  public EventAdapter(EventInterface event) {
    this.event = event;
  }

  @Override
  public String getName() {
    return event.getAName();
  }

  @Override
  public void setName(String name) {
    event.changeName(name);
  }

  @Override
  public boolean isOnline() {
    return event.getIsOnline();
  }

  @Override
  public void setOnline(boolean online) {
    event.changeLocation(event.getALocation(), online);
  }

  @Override
  public DayOfWeek getStartDay() {
    return toDayOfWeek(event.getAStartDay());
  }

  @Override
  public void setStartDay(DayOfWeek startDay) {
    event.changeDay(toDay(startDay), true);
  }

  @Override
  public int getStartTime() {
    return toIntTime(event.getAStartTime());
  }

  @Override
  public void setStartTime(int startTime) {
    event.changeTime(toTime(startTime), true);
  }

  @Override
  public DayOfWeek getEndDay() {
    return toDayOfWeek(event.getAEndDay());
  }

  @Override
  public void setEndDay(DayOfWeek endDay) {
    event.changeDay(toDay(endDay), false);
  }

  @Override
  public int getEndTime() {
    return 0;
  }

  @Override
  public void setEndTime(int endTime) {
    event.changeTime(toTime(endTime), false);
  }

  @Override
  public List<String> getParticipants() {
    return event.getInvitees();
  }

  @Override
  public void setParticipants(List<String> participants) {
    event.changeInvitees(participants);
  }

  @Override
  public String getHostUserId() {
    return event.getHost();
  }

  @Override
  public void setHostUserId(String hostUserId) {
    List<String> participants = new ArrayList<>();

    participants.add(hostUserId);

    if (event.getInvitees().contains(hostUserId)) {
      event.removeInvitee(hostUserId);
    }

    participants.addAll(event.getInvitees());

    event.changeInvitees(participants);
  }

  @Override
  public String getLocation() {
    return event.getALocation();
  }

  @Override
  public void setLocation(String newLoc) {
    event.changeLocation(newLoc, event.getIsOnline());
  }

  /**
   * Handles adding invitees.
   *
   * @param user a user to add or remove.
   */
  public IEvent addInvitee(String user) {
    if (!event.getInvitees().contains(user)) {
      event.getInvitees().add(user);
    }
    return new EventAdapter(event);
  }

  /**
   * Handles removing invitees.
   *
   * @param user a user to add or remove.
   */
  public IEvent removeInvitee(String user) {
    event.getInvitees().remove(user);
    return new EventAdapter(event);
  }

  /**
   * Changes the name of the event.
   *
   * @param newName the name to change the current event name to.
   */
  public void changeName(String newName) {
    event.changeName(newName);
  }

  /**
   * Change the location of the event.
   *
   * @param location the location to change the current location to.
   * @param isOnline whether the new location is online.
   */
  public void changeLocation(String location, boolean isOnline) {
    event.changeLocation(location, isOnline);
  }

  /**
   * Change the time of the event depending on the boolean given.
   *
   * @param newTime         the time to change the current time to.
   * @param changeStartTime if true, change the start time. if false, change the end time.
   */
  public void changeTime(int newTime, boolean changeStartTime) {
    event.changeTime(toTime(newTime), changeStartTime);
  }

  /**
   * Change the day of the event depending on the boolean given.
   *
   * @param newDay         the day to change the current day to.
   * @param changeStartDay if true, change the start day. if false, change the end day.
   */
  public void changeDay(DayOfWeek newDay, boolean changeStartDay) {
    event.changeDay(toDay(newDay), changeStartDay);
  }


  /**
   * Get start day of the event.
   *
   * @return start day of event.
   */
  public Day getAStartDay() {
    return event.getAStartDay();
  }

  /**
   * Get end day of the event.
   *
   * @return end day of event.
   */
  public Day getAEndDay() {
    return event.getAEndDay();
  }

  /**
   * Get start time of the event.
   *
   * @return start time of event.
   */
  public Time getStartATime() {
    return event.getAStartTime();
  }

  /**
   * Get end time of the event.
   *
   * @return end time of event.
   */
  public Time getAEndTime() {
    return event.getAEndTime();
  }

  /**
   * Get name of the event.
   *
   * @return name of the event.
   */
  public String getAName() {
    return event.getALocation();
  }

  /**
   * Get location of the event.
   *
   * @return location of the event.
   */
  public String getALocation() {
    return event.getALocation();
  }

  /**
   * Get whether the event is online.
   *
   * @return whether the event is online or not.
   */
  public boolean getIsOnline() {
    return event.getIsOnline();
  }

  /**
   * Gets the invitees of this event.
   *
   * @return invitees.
   */
  public List<String> getInvitees() {
    return event.getInvitees();
  }

  /**
   * Gets the host of an event -> the first person in a list of invitees.
   *
   * @return the host of an event
   */
  public String getHost() {
    return event.getHost();
  }

  /**
   * Converts the invitees in a list of strings to usernames on separate lines.
   *
   * @return line separated users
   */
  public String inviteesToString() {
    return event.inviteesToString();
  }

  /**
   * Determines how many days this event spans.
   * Events that start and end on the same day have a span of 1.
   *
   * @return the number of days this event takes place on
   */
  public int getSpan() {
    return event.getSpan();
  }

  /**
   * returns the duration of this event in minutes.
   *
   * @return the duration of this event in minutes
   */
  public int getDurationInMinutes() {
    return event.getDurationInMinutes();
  }

  /**
   * Can these events occur without overlapping?
   * Returns true if they don't overlap, false if they do.
   *
   * @param other the other event to check
   */
  public boolean doEventsFit(EventInterface other) {
    return event.doEventsFit(other);
  }

  /**
   * Makes an unmodifiable copy of this event.
   *
   * @return an unmodifiable copy of this event.
   */
  public EventInterface makeCopy() {
    return event.makeCopy();
  }

  /**
   * Does this day fall within or on the span of this event.
   *
   * @param day day to check
   * @return true if it occurs on the same day or in the middle of this event
   */
  public boolean isDayDuring(Day day) {
    return event.isDayDuring(day);
  }

  /**
   * Determines if this day and time fall within the span of this event.
   *
   * @param day  to check
   * @param time to check
   * @return true if it does, false if it doesn't
   */
  public boolean isAt(Day day, Time time) {
    return event.isAt(day, time);
  }

  /**
   * Changes this list to the given one, ensuring that the host is still 0.
   *
   * @param inviteesList new list
   */
  public void changeInvitees(List<String> inviteesList) {
    event.changeInvitees(inviteesList);
  }


  /**
   * Checks if the events overlaps by checking the start days.
   *
   * @param other other event to check
   * @return true if these events overlaps
   */
  public boolean checkDays(EventInterface other) {
    return event.checkDays(other);
  }

  // assumes that this event and other start on the same day
  // and that this event has an earlier start time other

  /**
   * Checks if the events overlaps by checking the start days.
   * Assumes that this event starts the same day as the given event.
   *
   * @param other other event to check
   * @return true if these events overlap
   */
  public boolean checkSameDayStart(EventInterface other) {
    return event.checkSameDayStart(other);
  }

  /**
   * Checks if the events overlaps by checking the start days.
   * Assumes that this event starts before the given event.
   *
   * @param other the other event to check
   * @return true if the events overlap
   */
  public boolean checkDifferentDayStart(EventInterface other) {
    return event.checkDifferentDayStart(other);
  }

  /**
   * Checks if the events overlap by time.
   * Assumes that this event starts before or on the same day as the given event.
   *
   * @param other the other event to check
   * @return true if this event times overlap
   */
  public boolean checkTimes(EventInterface other) {
    return event.checkTimes(other);
  }
}

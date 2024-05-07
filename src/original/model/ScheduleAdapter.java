package original.model;

import java.util.ArrayList;
import java.util.List;

import cs3500.plannersystem.model.DayOfWeek;
import cs3500.plannersystem.model.IEvent;
import cs3500.plannersystem.model.ISchedule;

import static original.model.AdapterUtils.getEventInterface;
import static original.model.AdapterUtils.toDayOfWeek;
import static original.model.AdapterUtils.toDay;
import static original.model.AdapterUtils.toTime;

/**
 * A ScheduleAdapter adapts a ScheduleInterface to work with an ISchedule.
 */
public class ScheduleAdapter implements ISchedule {
  private ScheduleInterface schedule;

  /**
   * A ScheduleAdapter takes in a ScheduleInterface which can be delegated to.
   * @param schedule a ScheduleInterface
   */
  public ScheduleAdapter(ScheduleInterface schedule) {
    this.schedule = schedule;
  }

  @Override
  public void addEvent(IEvent event) {
    EventInterface newEvent = getEventInterface(event);

    schedule.addEvent(newEvent);
  }

  @Override
  public boolean removeEvent(IEvent event) {
    EventInterface newEvent = getEventInterface(event);

    schedule.removeEvent(newEvent);

    if (!schedule.getEvents().contains(newEvent)) {
      return true;
    }
    return false;
  }

  @Override
  public List<IEvent> getEventsForDay(DayOfWeek day) {
    List<IEvent> events = new ArrayList<>();

    for (EventInterface event : schedule.getEvents()) {
      if (toDayOfWeek(event.getAStartDay()).equals(day)) {
        IEvent newEvent = new EventAdapter(event);
        events.add(newEvent);
      }
    }
    return events;
  }

  @Override
  public IEvent getEventByName(String eventName) {
    for (EventInterface event : schedule.getEvents()) {
      if (event.getAStartDay().name().equals(eventName)) {
        return new EventAdapter(event);
      }
    }
    return null;
  }

  @Override
  public boolean modifyEvent(IEvent oldEvent, IEvent newEvent) {
    schedule.modifyEvent(toTime(oldEvent.getStartTime()), toDay(oldEvent.getStartDay()),
            newEvent.getName(), newEvent.getLocation(), newEvent.isOnline(),
            toDay(newEvent.getStartDay()), toTime(newEvent.getStartTime()),
            toDay(newEvent.getEndDay()), toTime(newEvent.getEndTime()),
            newEvent.getParticipants());

    return schedule.getEventAtTime(toTime(newEvent.getStartTime()),
            toDay(newEvent.getStartDay())).getAName().equals(newEvent.getName());
  }

  @Override
  public boolean hasConflict(IEvent event) {
    EventInterface newEvent = getEventInterface(event);

    return schedule.fitsInSchedule(newEvent);
  }

  @Override
  public List<IEvent> getEvents() {
    List<IEvent> iEventList = new ArrayList<>();

    for (EventInterface eventInterface : schedule.getEvents()) {
      IEvent newEvent = new EventAdapter(eventInterface);
      iEventList.add(newEvent);
    }

    return iEventList;
  }

  /**
   * removes an event at a given time for a user.
   * @param startTime time to remove event from
   * @param day to remove event from
   * @param user user to remove event from
   */
  public void removeEvent(int startTime, DayOfWeek day, String user) {
    schedule.removeEvent(toTime(startTime), toDay(day), user);
  }

  /**
   * Gets this owner.
   * @return the user of this Schedule
   */
  public String getUser() {
    return schedule.getUser();
  }

  /**
   * Does the given event fit in this schedule.
   * @param event the event to be fitted
   * @return true if it doesn't conflict with any other events in this schedule, false if otherwise
   */
  public boolean fitsInSchedule(IEvent event) {
    EventInterface newEvent = getEventInterface(event);

    return schedule.fitsInSchedule(newEvent);
  }

  /**
   * Gets an event at the given time.
   * @param startTime the time to get
   * @return the Event if there is an event occurring at the given time.
   */
  public IEvent getEventAtTime(int startTime, DayOfWeek day) {
    return new EventAdapter(schedule.getEventAtTime(toTime(startTime), toDay(day)));
  }

  /**
   * Determines if there is an event at the given day and time.
   * @param day day to check
   * @param time time to check
   * @return true if there is, false if otherwise
   */
  public boolean checkEventAt(DayOfWeek day, int time) {
    return schedule.checkEventAt(toDay(day), toTime(time));
  }

  /**
   * changes this event.
   * @param givenStart start time of event to change
   * @param givenStartDay start day of event to change
   * @param eventName new name
   * @param location new location
   * @param onlineBool new online toggle
   * @param startDay new start day
   * @param startTime new start time
   * @param endDay new end day
   * @param endTime new end time
   * @param inviteesList updated list of invitees
   */
  public void modifyEvent(int givenStart, DayOfWeek givenStartDay, String eventName,
                          String location, boolean onlineBool, DayOfWeek startDay, int startTime,
                          DayOfWeek endDay, int endTime, List<String> inviteesList) {
    schedule.modifyEvent(toTime(givenStart), toDay(givenStartDay), eventName, location, onlineBool,
            toDay(startDay), toTime(startTime), toDay(endDay), toTime(endTime), inviteesList);
  }
}

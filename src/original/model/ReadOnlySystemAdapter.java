package original.model;

import java.util.ArrayList;
import java.util.List;

import cs3500.plannersystem.model.DayOfWeek;
import cs3500.plannersystem.model.IEvent;
import cs3500.plannersystem.model.IReadOnlyCentralSystem;
import cs3500.plannersystem.model.ISchedule;
import cs3500.plannersystem.model.IUser;
import cs3500.plannersystem.model.UserExample;

import static original.model.AdapterUtils.getEventInterface;
import static original.model.AdapterUtils.toDay;
import static original.model.AdapterUtils.toTime;

public class ReadOnlySystemAdapter implements IReadOnlyCentralSystem {
  private ReadOnlyPlannerModel readOnlyModel;

  public ReadOnlySystemAdapter(ReadOnlyPlannerModel model) {
    this.readOnlyModel = model;
  }


  @Override
  public IUser getUser(String uid) {
    ISchedule isched = new ScheduleAdapter(readOnlyModel.pickCurrentDisplay(uid));

    IUser user = new UserExample(uid, uid, isched);

    return user;
  }

  @Override
  public List<IUser> getUsers() {
    List<IUser> users = new ArrayList<>();

    for (String user : readOnlyModel.getTheUsers()) {
      users.add(this.getUser(user));
    }

    return users;
  }

  @Override
  public List<IEvent> getEvents(String uid) {
    return this.getUser(uid).getSchedule().getEvents();
  }

  @Override
  public boolean hasConflict(IEvent event) {
    for (String user : readOnlyModel.getTheUsers()) {
      ISchedule schedule = new ScheduleAdapter(readOnlyModel.pickCurrentDisplay(user));

      return schedule.hasConflict(event);
    }

    return false;
  }

  /**
   * Returns an unmodifiable schedule to be displayed.
   * @param user the user whose schedule should be displayed
   */
  public ScheduleInterface pickCurrentDisplay(String user) {
    return readOnlyModel.pickCurrentDisplay(user);
  }

  /**
   * Gets an unmodifiable list of users in the current system.
   * @return an unmodifiable list of users in the system
   */
  public List<String> getTheUsers() {
    return readOnlyModel.getTheUsers();
  }

  /**
   * Returns an unmodifiable list of all the events of a user.
   * @return an unmodifiable list of events
   */
  public List<EventInterface> getTheEvents(String user) {
    return readOnlyModel.getTheEvents(user);
  }

  /**
   * Returns true if the given time frame is available for everyone in the event.
   *
   * @param event checks if the event fits in everyone's schedule.
   */
  public boolean eventFitsSchedule(IEvent event) {
    return readOnlyModel.eventFitsSchedule(getEventInterface(event));
  }

  /**
   * Is there an event at the given time.
   *
   * @param time the time to check
   * @param user user to check
   * @param day  day to check
   * @return true if there is an event occuring at this time
   */
  public boolean eventAtTime(int time, String user, DayOfWeek day) {
    return readOnlyModel.eventAtTime(toTime(time), user, toDay(day));
  }

  /**
   * Returns the event happening at this time.
   *
   * @param time is the time to retrieve event
   * @param user user to check
   * @param day  day to check
   * @return event that is occurring at this time
   * @throws IllegalArgumentException if there is no event at the given time.
   */
  public EventInterface getEventAtTime(int time, String user, DayOfWeek day) {
    return readOnlyModel.getEventAtTime(toTime(time), user, toDay(day));
  }

  /**
   * Returns the user of the current Schedule being displayed.
   *
   * @return the user of this schedule
   */
  public String getUser() {
    return readOnlyModel.getUser();
  }
}

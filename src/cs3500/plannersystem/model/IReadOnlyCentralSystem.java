package cs3500.plannersystem.model;

import java.util.List;

/**
 * Read Only Interface for the Central System.
 */
public interface IReadOnlyCentralSystem {

  /**
   * Returns the user associated with the uid.
   *
   * @param uid the unique userId associated with the user
   * @return the User with that id
   */
  IUser getUser(String uid);

  /**
   * Returns all the users in the central system.
   * @return a list of users in the system
   */
  List<IUser> getUsers();

  /**
   * Returns the events in a user's schedule.
   * @param uid the unique user id
   * @return the events that user has
   */
  List<IEvent> getEvents(String uid);

  /**
   * Checks the event's participant's schedule to see if their exist a conflict with this event.
   * @param event the event being checked against
   */
  boolean hasConflict(IEvent event);
}

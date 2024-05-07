package cs3500.plannersystem.model;

/**
 * Interface for the Central System.
 */
public interface ICentralSystem extends IReadOnlyCentralSystem {

  /**
   * Adds a new user to the Central System's user database.
   *
   * @param name the name that will be associated with the user
   * @param uid the unique userId that will be associated with the user
   */
  void addUser(String uid, String name);

  /**
   * Removes a user from the Central System's user database.
   *
   * @param uid the unique userId associated with the user
   */
  void removeUser(String uid);

  /**
   * Adds a new event to the host and participants' schedules.
   *
   * @param event   the event created
   * @param hostUid the userId associated with creator of the event
   */
  void createEvent(IEvent event, String hostUid);

  /**
   * Modifies an event in all schedules that contain the original event.
   *
   * @param originalEvent the original event
   * @param modifiedEvent the altered event
   */
  void modifyEvent(IEvent originalEvent, IEvent modifiedEvent);

  /**
   * Removes an event from the host's and all participants' schedules.
   *
   * @param event         the event selected
   * @param requestingUid the uid of the person wanting to remove the event
   */
  void removeEvent(IEvent event, String requestingUid);

  /**
   * Adds a user's schedule to the planner.
   *
   * @param userId   specific userId unique to each user
   * @param schedule the Schedule the user created
   */
  void addUserSchedule(String userId, ISchedule schedule);

  /**
   * Add a list of user's schedules to the planner.
   * @param userIds the list of userIds
   * @param schedules the list of schedules
   */
  void addUsersSchedule(String[] userIds, ISchedule[] schedules);

  /**
   * Removes the user's schedule from the planner.
   *
   * @param userId specific userId unique to each user
   */
  void removeUserSchedule(String userId);

  /**
   * Adds the event to user's schedule.
   *
   * @param userId the uId of the user in question
   * @param event  the event trying to be added
   * @return the status of the action
   */
  boolean addEventToUser(String userId, IEvent event);
}

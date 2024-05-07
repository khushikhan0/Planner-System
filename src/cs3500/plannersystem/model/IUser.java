package cs3500.plannersystem.model;

/**
 * Interface for a user.
 */
public interface IUser {

  /**
   * Gets the userId for the user.
   *
   * @return the userId associated with the user
   */
  String getUid();

  /**
   * Gets the name of the user.
   * @return the name of the user
   */
  String getName();

  /**
   * Gets the schedule for the user.
   *
   * @return the schedule of the user
   */
  ISchedule getSchedule();

  /**
   * Creates a schedule for the user.
   *
   * @param schedule the schedule personalized for the user
   */
  void setSchedule(ISchedule schedule);
}

package cs3500.plannersystem.controller;

import cs3500.plannersystem.model.ICentralSystem;
import cs3500.plannersystem.model.IEvent;
import cs3500.plannersystem.view.IMainFrame;

/**
 * Interface for the features that the controller provides to the view.
 */
public interface Features {

  /**
   * Adds a new event to the system.
   * @param event The event to be added.
   */
  void createEvent(IEvent event);

  /**
   * Modifies an existing event with new details.
   * @param oldEvent The existing event to be modified.
   * @param newEvent The new event details to replace the old event.
   */
  void modifyEvent(IEvent oldEvent, IEvent newEvent);

  /**
   * Removes an event from the system.
   * @param event The event to be removed.
   */
  void removeEvent(IEvent event);

  /**
   * Returns the central system.
   * @return the instance of the central system.
   */
  ICentralSystem getCentralSystem();

  /**
   * Returns the main screen displaying the schedules and buttons.
   * @return the instance of the main frame
   */
  IMainFrame getMainFrame();
}

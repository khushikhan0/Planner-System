package cs3500.plannersystem.view;

import cs3500.plannersystem.model.IEvent;

/**
 * Interface for the Main Frame.
 */
public interface IMainFrame {

  /**
   * Sets up the GUI screen.
   */
  void prepareGUI();

  /**
   * Updates the schedule's view itself.
   * @param userName the userName of the update needed.
   */
  void updateScheduleView(String userName);

  /**
   * Makes the view visible.
   */
  void makeVisible();

  /**
   * Adds created event to the schedule panel.
   * @param event the event that was just created.
   */
  void onEventCreated(IEvent event);
}

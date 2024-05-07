package original.view;

/**
 * View for the Planner that works with a java swing GUI.
 */
public interface PlannerViewGUI {

  /**
   * Allows the view to make calls to the features class.
   * @param features interface
   */
  void addFeatureListener(ViewFeatures features);

  /**
   * To display this plannerView.
   * @param show true sets the display to show, false hides it
   */
  void display(boolean show);

  /**
   * Updates the schedule panel to display the correct schedule.
   * @param user the user whose schedule is to be displayed.
   */
  void changeSchedule(String user);

  /**
   * Updates the main panel.
   */
  void update();

  /**
   * gets the selected user.
   * @return the user currently selected in the view
   */
  String getUser();

  /**
   * Adds user to view.
   * @param user a user to add
   */
  void addUser(String user);
}

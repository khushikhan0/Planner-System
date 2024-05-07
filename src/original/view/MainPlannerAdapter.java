package original.view;

import cs3500.plannersystem.view.IMainFrame;

/**
 * Adapts the MainFrame to a PlannerViewGUI through delegation to a MainFrame.
 */
public class MainPlannerAdapter implements PlannerViewGUI {

  IMainFrame delegateMainFrame;

  /**
   * Creates the adapter and sets the given view as the delegate.
   * Implements the methods of PlannerViewGUI and sends them to the delegate.
   */
  public MainPlannerAdapter(IMainFrame view) {
    this.delegateMainFrame = view;
  }

  @Override
  public void addFeatureListener(ViewFeatures features) {
    //delegateMainFrame.setController(/*adapted controller*/);
    // method cannot be implemented because it would require giving the adapted version of
    //a controller and their model doesn't use the controller.
  }

  @Override
  public void display(boolean show) {
    delegateMainFrame.makeVisible();
  }

  @Override
  public void changeSchedule(String user) {
    delegateMainFrame.updateScheduleView(user);
  }

  @Override
  public void update() {
    this.delegateMainFrame.updateScheduleView(this.getUser());
  }

  @Override
  public String getUser() {
    return null;
    // asked provider to add a method to return the selected user
    // didn't complete request on time
  }

  @Override
  public void addUser(String user) {
    // Providers view directly adds schedules to models, so we cannot use this method
  }
}

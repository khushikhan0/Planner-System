package original.view;

/**
 * An EventView has methods needed for the event view.
 */
public interface EventView {

  /**
   * Allows the view to call feature's methods.
   * @param features the class that can holds what the system can do
   */
  void addFeatureListener(ViewFeatures features);

  /**
   * Shows the view.
   * @param show true = show the view, false = don't show the view
   */
  void display(boolean show);
}

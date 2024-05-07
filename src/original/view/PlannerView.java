package original.view;

/**
 * View for the Planner. For now, represents the planner in a textual view but will eventually
 * implement a GUI.
 */
public interface PlannerView {
  /**
   * Renders a model in some manner (e.g. as text, or as graphics, etc.).
   * @return a string representation of a planner system.
   */
  String render();
}

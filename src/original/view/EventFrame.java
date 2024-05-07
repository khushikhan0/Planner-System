package original.view;

import java.awt.Dimension;

import javax.swing.JFrame;

import original.model.EventInterface;
import original.model.ReadOnlyPlannerModel;

/**
 * An EventFrame is a view that holds the event panel to display a single event.
 * Events can be already existing, in which actions can include modify and remove,
 * or can be new events in which actions include creation.
 */
public class EventFrame extends JFrame implements EventView {
  private final AbstractEventPanel panel;

  /**
   * An EventFrame takes in a ReadOnlyPlannerModel.
   * This is used to create a new Event.
   * @param model a model.
   */
  public EventFrame(ReadOnlyPlannerModel model) {
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.panel = new EventPanel(model);
    this.panel.setPreferredSize(new Dimension(300, 380));
    this.add(panel);
    this.setResizable(false);
    this.pack();
  }

  /**
   * An EventFrame takes in an Existing event from a model and autofills its information into the
   * fields.
   * This is used to modify/remove and existing event.
   * @param model the model to access the event's information.
   * @param event the event being changed/removed.
   *
   * @implNote may end changing this method to get a specified event by name or time from the model
   */
  public EventFrame(ReadOnlyPlannerModel model, EventInterface event) {
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.panel = new EventPanel(model, event);
    this.panel.setPreferredSize(new Dimension(300, 380));
    this.add(panel);
    this.setResizable(false);
    this.pack();
  }

  /**
   * An EventFrame that takes in a boolean for newEvent.
   * @param model the model to access existing information in the system.
   * @param newEvent should this show the newEvent view or exiting event view
   *
   * @implNote this exists for testing purposes only during development- to be removed
   */
  public EventFrame(ReadOnlyPlannerModel model, boolean newEvent) {
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.panel = new ScheduleEventPanel(model);
    this.panel.setPreferredSize(new Dimension(300, 380));
    this.add(panel);
    this.setResizable(false);
    this.pack();
  }

  @Override
  public void addFeatureListener(ViewFeatures features) {
    this.panel.addFeaturesListener(features);
  }

  @Override
  public void display(boolean show) {
    this.setVisible(show);
  }


}

package original.view;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;

import original.model.ReadOnlyPlannerModel;

/**
 * Represents and event panel which shows the schedule event view.
 * It is very similar to eventPanel, but allows the user to only enter a duration.
 */
class ScheduleEventPanel extends AbstractEventPanel {
  private JButton scheduleEvent;

  /**
   * Constructs the panel to allow the user to input a duration for the event and gives the option
   * to auto schedule it.
   * @param model the model to attain information from.
   */
  public ScheduleEventPanel(ReadOnlyPlannerModel model) {
    super(model);
    this.setLabels();
    this.setComponents();
    this.setButtons();

  }

  @Override
  protected void setLabels() {
    super.setLabels();

    this.configConstraints(0,4);
    JLabel durationLabel = new JLabel("Duration in Minutes:");
    this.add(durationLabel, this.constraints);

    this.configConstraints(0,6);
    JLabel availableUsersLabel = new JLabel("Available Users:");
    this.add(availableUsersLabel, this.constraints);
  }

  @Override
  protected void setComponents() {
    super.setComponents();

    this.configConstraints(0,5);
    this.constraints.gridwidth = 2;
    JTextField inputDuration = new JTextField();
    this.add(inputDuration, this.constraints);

    this.configConstraints(0,7);
    this.constraints.gridwidth = 2;
    this.add(this.availableUsers, this.constraints);
  }

  @Override
  protected void setButtons() {
    this.configConstraints(0,9);
    this.constraints.gridwidth = 2;
    this.scheduleEvent = new JButton("Schedule Event");
    this.add(this.scheduleEvent, this.constraints);
  }

  @Override
  public void addFeaturesListener(ViewFeatures features) {
    this.scheduleEvent.addActionListener(evt -> System.out.println("Schedule"));
  }


}

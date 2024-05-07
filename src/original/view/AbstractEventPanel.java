package original.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Dimension;
import java.util.Objects;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;


import original.model.ReadOnlyPlannerModel;

/**
 * Abstract class for the common components of an event panel.
 * Includes event name, location, and the panel layout/settings.
 * Fields made protected so that they can be used in the inheritor classes.
 */
public abstract class AbstractEventPanel extends JPanel {
  protected JTextField inputEventName;
  protected JTextField inputLocation;
  protected JComboBox<String> isOnlineSelector;
  protected JList<String> availableUsers;
  protected final GridBagConstraints constraints;
  protected String[] availUsersList;
  protected final ReadOnlyPlannerModel model;


  /**
   * Builds the beginning of the event panel and sets constraints for the layout.
   * @param model the model to get the contents of the display
   */
  public AbstractEventPanel(ReadOnlyPlannerModel model) {
    this.model = Objects.requireNonNull(model);
    this.availUsersList = this.getAvailableUsers();

    this.constraints = new GridBagConstraints();
    this.constraints.fill = GridBagConstraints.BOTH;
    this.constraints.ipady = 1;

    this.setLayout(new GridBagLayout());
  }

  /**
   * configure GridBagLayout constraints.
   * @param x the x coordinate for the component
   * @param y the y coordinate for the component
   */
  protected void configConstraints(int x, int y) {
    this.constraints.gridx = x;
    this.constraints.gridy = y;
    this.constraints.gridwidth = 1;
    this.constraints.gridheight = 1;
  }

  /**
   * sets the labels components.
   */
  protected void setLabels() {
    this.configConstraints(0,0);
    JLabel eventNameLabel = new JLabel("Event Name:");
    this.add(eventNameLabel, this.constraints);

    this.configConstraints(0,2);
    JLabel locationLabel = new JLabel("Location:");
    this.add(locationLabel, this.constraints);

  }

  /**
   * sets the action buttons for this event.
   */
  protected abstract void setButtons();

  /**
   * sets the fields to be filled out for this panel.
   */
  protected void setComponents() {
    this.configConstraints(0,1);
    this.constraints.gridwidth = 2;
    this.inputEventName = new JTextField();
    this.add(this.inputEventName, this.constraints);

    this.configConstraints(0,3);
    this.isOnlineSelector = new JComboBox<>(new String[]{"Online", "In-Person"});
    this.add(this.isOnlineSelector, this.constraints);

    this.configConstraints(1,3);
    this.inputLocation = new JTextField();
    this.inputLocation.setPreferredSize(new Dimension(100, 20));
    this.add(this.inputLocation, this.constraints);

    this.availableUsers = new JList<String>(this.availUsersList);
    this.availableUsers.setPreferredSize(new Dimension(100, 100));
  }

  /**
   *   gets the users available from the model.
    */
  protected String[] getAvailableUsers() {
    String[] users = new String[model.getTheUsers().size()];
    //users[0] = "<none>";
    for (int index = 0; index < model.getTheUsers().size(); index++) {
      users[index] = model.getTheUsers().get(index);
    }

    return users;
  }

  /**
   * adds listeners to the panel so that components can be interacted with.
   * @param features the class that is called to help make changes
   */
  public abstract void addFeaturesListener(ViewFeatures features);
}

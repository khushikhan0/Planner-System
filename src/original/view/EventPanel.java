package original.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;

import original.model.Day;
import original.model.EventInterface;
import original.model.ReadOnlyPlannerModel;
import original.model.Time;

/**
 * The event panel is to display the panel contents inside the event frame.
 * This type of panel handles the event view of existing events and of creating new events with
 * a time.
 */
class EventPanel extends AbstractEventPanel {

  private JComboBox<String> pickStartDay;
  private JTextField inputStartTime;
  private JComboBox<String> pickEndDay;
  private JTextField inputEndTime;
  private JButton createEvent;
  private JButton modifyEvent;
  private JButton removeEvent;
  private boolean newEvent;
  private Time givenStart;
  private Day givenStartDay;

  /**
   * Constructs an event and fills out its components to the given event for display.
   * @param model model to get information from
   * @param event event to prefill info from
   */
  public EventPanel(ReadOnlyPlannerModel model, EventInterface event) {
    super(model);
    this.newEvent = false;
    this.givenStart = event.getAStartTime();
    this.givenStartDay = event.getAStartDay();
    this.setLabels();
    this.setComponents();
    this.setButtons();
    this.fill(event);

  }

  /**
   * Constructs an event that has open fields to be filled out by the user.
   * @param model model to get information from.
   */
  public EventPanel(ReadOnlyPlannerModel model) {
    super(model);
    this.newEvent = true;
    this.setLabels();
    this.setComponents();
    this.setButtons();
  }

  @Override
  protected void setLabels() {
    super.setLabels();

    this.configConstraints(0,4);
    JLabel startDayLabel = new JLabel("Start Day:");
    this.add(startDayLabel, this.constraints);

    this.configConstraints(0,5);
    JLabel startTimeLabel = new JLabel("Start Time:");
    this.add(startTimeLabel, this.constraints);

    this.configConstraints(0,6);
    JLabel endDayLabel = new JLabel("End Day:");
    this.add(endDayLabel, this.constraints);

    this.configConstraints(0,7);
    JLabel endTimeLabel = new JLabel("End Time:");
    this.add(endTimeLabel, this.constraints);

    this.configConstraints(0,8);
    JLabel availableUsersLabel = new JLabel("Available Users:");
    this.add(availableUsersLabel, this.constraints);
  }

  @Override
  protected void setButtons() {
    if (this.newEvent) {
      this.configConstraints(0,10);
      this.constraints.gridwidth = 2;
      this.createEvent = new JButton("Create Event");
      this.add(this.createEvent, this.constraints);
    }
    else {
      this.configConstraints(0, 10);
      this.modifyEvent = new JButton("Modify Event");
      this.add(this.modifyEvent, this.constraints);

      this.configConstraints(1, 10);
      this.removeEvent = new JButton("Remove Event");
      this.add(this.removeEvent, this.constraints);
    }
  }

  @Override
  protected void setComponents() {
    super.setComponents();
    String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

    this.configConstraints(1,4);
    this.pickStartDay = new JComboBox<String>(days);
    this.pickStartDay.setSelectedItem("Sunday");
    this.add(this.pickStartDay, this.constraints);

    this.configConstraints(1,5);
    this.inputStartTime = new JTextField();
    this.add(this.inputStartTime, this.constraints);

    this.configConstraints(1,6);
    this.pickEndDay = new JComboBox<String>(days);
    this.pickEndDay.setSelectedItem("Sunday");
    this.add(this.pickEndDay, this.constraints);

    this.configConstraints(1,7);
    this.inputEndTime = new JTextField();
    this.add(this.inputEndTime, this.constraints);

    this.configConstraints(0,9);
    this.constraints.gridwidth = 2;
    this.add(this.availableUsers, this.constraints);

  }

  private void fill(EventInterface event) {
    this.inputEventName.setText(event.getAName());

    if (!event.getIsOnline()) {
      isOnlineSelector.setSelectedItem("In-Person");
    }

    this.inputLocation.setText(event.getALocation());
    this.pickStartDay.setSelectedItem(event.getAStartDay().enumToString());
    this.inputStartTime.setText(event.getAStartTime().timeToString());
    this.pickEndDay.setSelectedItem(event.getAEndDay().enumToString());
    this.inputEndTime.setText(event.getAEndTime().timeToString());
    this.availableUsers.setSelectedIndices(selectUsers(event.getInvitees()));
  }

  private int[] selectUsers(List<String> invitees) {
    ArrayList<Integer> indices = new ArrayList<Integer>();
    
    for (int index = 0; index < availUsersList.length; index++) {
      if (invitees.contains(availUsersList[index])) {
        indices.add(index);
      }
    }
    
    int[] indicesList = new int[indices.size()];
    for (int index = 0; index < indices.size(); index++) {
      indicesList[index] = indices.get(index);
    }
    
    return indicesList;
  }

  private boolean getOnlineBool() {
    return this.isOnlineSelector.getSelectedItem().toString().equals("Online");
  }

  @Override
  public void addFeaturesListener(ViewFeatures features) {
    if (this.newEvent) {
      this.createEvent.addActionListener(evt -> {
        this.checkValidInput();
        this.checkValidTimes();
        features.createEvent( this.inputEventName.getText(), this.inputLocation.getText(),
                this.getOnlineBool(),
                this.pickStartDay.getSelectedItem().toString(), this.inputStartTime.getText(),
                this.pickEndDay.getSelectedItem().toString(), this.inputEndTime.getText(),
                this.availableUsers.getSelectedValuesList());
      });

    }
    else {
      this.modifyEvent.addActionListener(evt -> {
        this.checkValidInput();
        this.checkValidTimes();
        features.modifyEvent(
               this.givenStart, this.givenStartDay, this.inputEventName.getText(),
               this.inputLocation.getText(), this.getOnlineBool(),
               this.pickStartDay.getSelectedItem().toString(), this.inputStartTime.getText(),
               this.pickEndDay.getSelectedItem().toString(), this.inputEndTime.getText(),
               this.availableUsers.getSelectedValuesList());

      });

      this.removeEvent.addActionListener(evt -> {
        this.checkValidInput();
        this.checkValidTimes();
        features.removeEvent(
                this.givenStart,
                this.givenStartDay);
      });
    }
  }

  private void checkValidInput() {
    if (this.inputEventName.equals("") || this.inputLocation.equals("")
            || this.pickStartDay.getSelectedItem().toString().equals("")
            || this.inputStartTime.getText().equals("")
            || this.pickEndDay.getSelectedItem().toString().equals("")
            || this.inputEndTime.getText().equals("")) {
      throw new IllegalArgumentException("Please fill in all fields");
    }
  }

  private void checkValidTimes() {
    if (inputStartTime.getText().length() != 4 || inputEndTime.getText().length() != 4) {
      throw new IllegalArgumentException("Invalid Time");
    }
    try {
      Integer.parseInt(inputStartTime.getText());
      Integer.parseInt(inputEndTime.getText());
    } catch (NumberFormatException error) {
      throw new IllegalArgumentException("Non-Integers entered");
    }
  }

}

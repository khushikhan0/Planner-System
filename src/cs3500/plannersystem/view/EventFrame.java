//package cs3500.plannersystem.view;
//
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Objects;
//import java.util.function.Consumer;
//import java.util.stream.Collectors;
//
//import javax.swing.*;
//import javax.swing.border.Border;
//
//import cs3500.plannersystem.controller.Features;
//import cs3500.plannersystem.model.DayOfWeek;
//import cs3500.plannersystem.model.Event;
//import cs3500.plannersystem.model.IEvent;
//import cs3500.plannersystem.model.IReadOnlyCentralSystem;
//import original.model.EventAdapter;
//import original.model.EventInterface;
//
//
///**
// * The Frame/Screen you see when creating an event.
// */
//public class EventFrame extends JFrame implements IEventFrame {
//  private final IReadOnlyCentralSystem iReadOnlyCentralSystem;
//  private final List<String> usersName;
//  private Consumer<String> commandCallback;
//  private JTextField eventNameField;
//  private JComboBox<String> isOnlineComboBox;
//  private JTextField isOnlineField;
//  private JComboBox<String> startingDayComboBox;
//  private JTextField startingTimeField;
//  private JComboBox<String> endingDayComboBox;
//  private JTextField endingTimeField;
//  private String hostUID;
//  private JList<Object> userList;
//  private IMainFrame mainFrame;
//  private Features controller;
//
//  /**
//   * A constructor for our event frame.
//   * @param iReadOnlyCentralSystem an instance of a read only central system.
//   * @param event an event to be displayed.
//   * @param hostUID the ID of the host user.
//   */
//  public EventFrame(IReadOnlyCentralSystem iReadOnlyCentralSystem, IEvent event, String hostUID,
//                    Features controller) {
//    super("Event");
//    this.iReadOnlyCentralSystem = iReadOnlyCentralSystem;
//    this.usersName = new ArrayList<>();
//    this.iReadOnlyCentralSystem.getUsers().forEach(user -> usersName.add(user.toString()));
//    this.hostUID = hostUID;
//    this.controller = controller;
//
//    JPanel panel = new JPanel();
//    panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
//
//    addToPanel(panel, "Event Name:");
//    eventNameField = new JTextField(event.getName());
//    addToPanel(panel, eventNameField);
//
//    addToPanel(panel, "Location:");
//    isOnlineComboBox =  new JComboBox<>(
//            new String[]{"is Online", "not Online"});
//
//    if (event.isOnline()) {
//      isOnlineComboBox.setSelectedItem("is Online");
//    } else {
//      isOnlineComboBox.setSelectedItem("not Online");
//    }
//
//    isOnlineField = new JTextField(event.getLocation());
//    addToPanelConnected(panel, isOnlineComboBox, isOnlineField);
//
//    startingDayComboBox = new JComboBox<>(new String[]{"Sunday", "Monday", "Tuesday", "Wednesday",
//        "Thursday", "Friday", "Saturday"});
//    startingDayComboBox.setSelectedIndex(event.getStartDay().getDayInInteger());
//    addToPanelConnected(panel, "Starting Day: ", startingDayComboBox);
//
//    startingTimeField = new JTextField("" + event.getStartTime());
//    addToPanelConnected(panel, "Starting Time: ", startingTimeField);
//
//    endingDayComboBox =  new JComboBox<>(new String[]{"Sunday", "Monday", "Tuesday", "Wednesday",
//        "Thursday", "Friday", "Saturday"});
//    endingDayComboBox.setSelectedIndex(event.getEndDay().getDayInInteger());
//    addToPanelConnected(panel, "Ending Day: ", endingDayComboBox);
//
//    endingTimeField = new JTextField("" + event.getEndTime());
//    addToPanelConnected(panel, "Ending Time: ", endingTimeField);
//    addToPanel(panel, "Available Users: ");
//
//    userList = new JList<>(this.usersName.toArray());
//
//    List<String> participantIds = event.getParticipants();
//    String[] usersArray = usersName.toArray(new String[0]);
//
//    int[] participantIndices = participantIds.stream()
//            .mapToInt(participantId -> Arrays.asList(usersArray).indexOf(participantId))
//            .filter(index -> index >= 0) // filter out -1, which indicates not found
//            .toArray();
//
//    userList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
//
//    userList.setSelectedIndices(participantIndices);
//
//    addToPanel(panel, userList);
//
//
//    // Buttons
//    JPanel buttonPanel = getjPanel();
//    panel.add(buttonPanel, BorderLayout.SOUTH);
//
//
//
//    this.getContentPane().add(panel, BorderLayout.CENTER);
//  }
//
//  /**
//   * The event frame to be displayed.
//   * @param iReadOnlyCentralSystem a read only central system to be passed.
//   * @param hostUID ID of the host.
//   */
//  public EventFrame(IReadOnlyCentralSystem iReadOnlyCentralSystem, String hostUID,
//                    Features controller) {
//    super("Event");
//    this.iReadOnlyCentralSystem = iReadOnlyCentralSystem;
//    this.usersName = new ArrayList<>();
//    this.iReadOnlyCentralSystem.getUsers().forEach(user -> usersName.add(user.toString()));
//    this.hostUID = hostUID;
//    this.controller = controller;
//
//    JPanel panel = new JPanel();
//    panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
//
//    addToPanel(panel, "Event Name:");
//    eventNameField = new JTextField();
//    addToPanel(panel, eventNameField);
//
//    addToPanel(panel, "Location:");
//    isOnlineComboBox =  new JComboBox<>(
//            new String[]{"is Online", "not Online"});
//    isOnlineField = new JTextField();
//    addToPanelConnected(panel, isOnlineComboBox, isOnlineField);
//
//    startingDayComboBox = new JComboBox<>(
//            new String[]{"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday",
//                         "Saturday"});
//    addToPanelConnected(panel, "Starting Day: ", startingDayComboBox);
//
//    startingTimeField = new JTextField();
//    addToPanelConnected(panel, "Starting Time: ", startingTimeField);
//
//    endingDayComboBox =  new JComboBox<>(
//            new String[]{"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday",
//                         "Saturday"});
//    addToPanelConnected(panel, "Ending Day: ", endingDayComboBox);
//
//    endingTimeField = new JTextField();
//    addToPanelConnected(panel, "Ending Time: ", endingTimeField);
//    addToPanel(panel, "Available Users: ");
//
//    userList = new JList<>(this.usersName.toArray());
//    userList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
//    addToPanel(panel, userList);
//
//
//    // Buttons
//    JPanel buttonPanel = getjPanel();
//    panel.add(buttonPanel, BorderLayout.SOUTH);
//
//
//
//    this.getContentPane().add(panel, BorderLayout.CENTER);
//  }
//
//  private JPanel getjPanel() {
//    JPanel buttonPanel = new JPanel();
//    buttonPanel.setLayout(new FlowLayout());
//
//    JButton modifyButton = new JButton("Modify Event");
//    modifyButton.addActionListener((ActionEvent e) -> {
//      System.out.println("Modify Event Button Clicked");
//      EventInterface newEvent = createEventFromFields();
//      if (commandCallback != null) {
//        commandCallback.accept("Modify Event");
//      }
//    });
//    buttonPanel.add(modifyButton);
//
//    JButton createButton = getCreateOrRemoveButton("Create");
//    createButton.addActionListener((ActionEvent e) -> {
//      System.out.println("Create Event Button Clicked");
//      boolean validInput = validateInputFields();
//      if (validInput) {
//        try {
//          EventInterface newEvent = createEventFromFields();
//          System.out.println(newEvent);
//          // Use the controller to create the event
//          this.controller.createEvent(new EventAdapter(newEvent));
//          // Close the frame after creating the event
//          this.dispose();
//        } catch (NumberFormatException ex) {
//          JOptionPane.showMessageDialog(this,
//                  "Please enter valid times in HHMM format.", "Error",
//                  JOptionPane.ERROR_MESSAGE);
//        }
//      }
//      if (commandCallback != null) {
//        commandCallback.accept("Create Event");
//      }
//    });
//    buttonPanel.add(createButton);
//
//    JButton removeButton = getCreateOrRemoveButton("Remove");
//    removeButton.addActionListener((ActionEvent e) -> {
//      System.out.println("Remove Event Button Clicked");
//      List<String> selectedUsers = userList.getSelectedValuesList().stream()
//              .map(Object::toString)
//              .collect(Collectors.toList());
//      for (String user : selectedUsers) {
//        List<IEvent> listEvents = this.controller.getCentralSystem().getEvents(user);
//        for (IEvent indivEvent : listEvents) {
//          if (indivEvent.getName().equals(eventNameField.getText())) {
//            this.controller.removeEvent(indivEvent);
//          }
//        }
//      }
//      if (commandCallback != null) {
//        commandCallback.accept("Remove Event");
//      }
//    });
//    buttonPanel.add(removeButton);
//
//    return buttonPanel;
//  }
//
//  private JButton getCreateOrRemoveButton(String type) {
//    JButton button = new JButton(type + " Event");
//    button.addActionListener(e -> {
//      if ("Create".equals(type) && this.controller != null) {
//        boolean validInput = validateInputFields();
//        if (validInput) {
//          try {
//            EventInterface newEvent = createEventFromFields();
//            // Use the controller to create the event
//            this.controller.createEvent(new EventAdapter(newEvent));
//            // Close the frame after creating the event
//            this.dispose();
//          } catch (NumberFormatException ex) {
//            JOptionPane.showMessageDialog(this,
//                    "Please enter valid times in HHMM format.", "Error",
//                    JOptionPane.ERROR_MESSAGE);
//          }
//        }
//      }
//      // Handling for "Modify" and "Remove" can be implemented similarly
//    });
//
//    return button;
//  }
//
//
//  private EventInterface createEventFromFields() {
//    String eventName = eventNameField.getText();
//    boolean isOnline = "is Online".equals(isOnlineComboBox.getSelectedItem());
//    DayOfWeek startDay = DayOfWeek.valueOf(((String)
//            Objects.requireNonNull(startingDayComboBox.getSelectedItem())).toUpperCase());
//    int startTime = Integer.parseInt(startingTimeField.getText());
//    DayOfWeek endDay = DayOfWeek.valueOf(((String)
//            Objects.requireNonNull(endingDayComboBox.getSelectedItem())).toUpperCase());
//    int endTime = Integer.parseInt(endingTimeField.getText());
//    String location = isOnline ? "Online" : isOnlineField.getText();
//    List<String> selectedUsers = userList.getSelectedValuesList().stream()
//            .map(Object::toString)
//            .collect(Collectors.toList());
//    System.out.println(new Event(eventName, isOnline, startDay, startTime, endDay, endTime,
//            selectedUsers, location, hostUID));
//    return new Event(eventName, isOnline, startDay, startTime, endDay, endTime, selectedUsers,
//            location, hostUID);
//  }
//
//
//
//  private boolean validateInputFields() {
//    StringBuilder errorMessageBuilder = new StringBuilder();
//    boolean isValid = true;
//
//    String eventName = eventNameField.getText();
//    String isOnline = (String) isOnlineComboBox.getSelectedItem();
//    String location = isOnlineField.getText();
//    String startingDay = (String) startingDayComboBox.getSelectedItem();
//    String startingTime = startingTimeField.getText();
//    String endingDay = (String) endingDayComboBox.getSelectedItem();
//    String endingTime = endingTimeField.getText();
//    List<Object> selectedUsers = userList.getSelectedValuesList();
//
//    // Validate Event Name
//    if (eventName.isEmpty()) {
//      isValid = false;
//      errorMessageBuilder.append("\n- Event Name is required.");
//    }
//
//    // Validate Location (only if event is not online)
//    if ("not Online".equals(isOnline) && location.isEmpty()) {
//      isValid = false;
//      errorMessageBuilder.append("\n- Location is required for in-person events.");
//    }
//
//    // Validate Starting Day
//    if (startingDay == null || startingDay.isEmpty()) {
//      isValid = false;
//      errorMessageBuilder.append("\n- Starting Day is required.");
//    }
//
//    // Validate Starting Time
//    if (!startingTime.matches("\\d{4}")) { // Regex to match a 4-digit time
//      isValid = false;
//      errorMessageBuilder.append("\n- Starting Time must be in the format HHMM.");
//    }
//
//    // Validate Ending Day
//    if (endingDay == null || endingDay.isEmpty()) {
//      isValid = false;
//      errorMessageBuilder.append("\n- Ending Day is required.");
//    }
//
//    // Validate Ending Time
//    if (!endingTime.matches("\\d{4}")) { // Regex to match a 4-digit time
//      isValid = false;
//      errorMessageBuilder.append("\n- Ending Time must be in the format HHMM.");
//    }
//
//    // Validate User Selection
//    if (selectedUsers.isEmpty()) {
//      isValid = false;
//      errorMessageBuilder.append("\n- At least one user must be selected.");
//    }
//
//    // Display error message if validation failed
//    if (!isValid) {
//      JOptionPane.showMessageDialog(this, errorMessageBuilder.toString(),
//              "Validation Error", JOptionPane.ERROR_MESSAGE);
//    }
//
//    return isValid;
//  }
//
//
//  private void addToPanel(JPanel mainPanel, String labelText) {
//    JPanel panel = new JPanel(new BorderLayout());
//    JLabel label = new JLabel(labelText, SwingConstants.LEFT);
//    panel.add(label, BorderLayout.WEST);
//
//    Border margin = BorderFactory.createEmptyBorder(5, 5, 0, 5);
//    panel.setBorder(margin);
//
//    mainPanel.add(panel);
//  }
//
//  private void addToPanel(JPanel mainPanel, Component component) {
//    JPanel panel = new JPanel(new BorderLayout());
//    panel.add(component, BorderLayout.CENTER);
//
//    Border margin = BorderFactory.createEmptyBorder(5, 5, 5, 5);
//    panel.setBorder(margin);
//
//    mainPanel.add(panel);
//  }
//
//  private void addToPanelConnected(JPanel mainPanel, String labelText, Component component) {
//    JPanel panel = new JPanel(new GridBagLayout());
//    GridBagConstraints constraints = new GridBagConstraints();
//
//    JLabel label = new JLabel(labelText);
//
//    // Label constraints
//    constraints.gridx = 0;
//    constraints.gridy = 0;
//    constraints.anchor = GridBagConstraints.WEST;
//    constraints.insets = new Insets(0, 5, 0, 0);
//    panel.add(label, constraints);
//
//    // Component constraints
//    constraints.gridx = 1;
//    constraints.gridy = 0;
//    constraints.weightx = 1.0;
//    constraints.fill = GridBagConstraints.HORIZONTAL;
//    constraints.insets = new Insets(0, 0, 0, 5);
//    panel.add(component, constraints);
//
//    mainPanel.add(panel);
//  }
//
//  private void addToPanelConnected(JPanel mainPanel, Component component1, Component component2) {
//    JPanel panel = new JPanel(new GridBagLayout());
//    GridBagConstraints constraints = new GridBagConstraints();
//
//    // Comp1 constraints
//    constraints.gridx = 0;
//    constraints.gridy = 0;
//    constraints.anchor = GridBagConstraints.WEST;
//    constraints.insets = new Insets(0, 5, 0, 0);
//    panel.add(component1, constraints);
//
//    // Component constraints
//    constraints.gridx = 1;
//    constraints.gridy = 0;
//    constraints.weightx = 1.0;
//    constraints.fill = GridBagConstraints.HORIZONTAL;
//    constraints.insets = new Insets(0, 0, 0, 5);
//    panel.add(component2, constraints);
//
//    mainPanel.add(panel);
//  }
//
//
//  @Override
//  public void prepareGUI() {
//    this.setSize(500, 800);
//    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//    this.setResizable(false);
//  }
//
//
//  @Override
//  public void makeVisible() {
//    this.setVisible(true);
//  }
//}

//package cs3500.plannersystem.view;
//
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import javax.swing.*;
//
//import cs3500.plannersystem.controller.Features;
//import cs3500.plannersystem.model.DayOfWeek;
//import cs3500.plannersystem.model.Event;
//import cs3500.plannersystem.model.IEvent;
//import cs3500.plannersystem.model.IReadOnlyCentralSystem;
//import cs3500.plannersystem.model.ISchedule;
//import cs3500.plannersystem.model.IUser;
//
///**
// * Our frame for the schedule event part of the planner.
// */
//public class ScheduleEventFrame extends JFrame implements IScheduleEventFrame {
//
//  private JTextField eventNameField;
//  private JComboBox<String> locationComboBox;
//  private JTextField durationField;
//  private JList<String> userList;
//  private JTextField locationTextField;
//  private Features controller;
//  private IReadOnlyCentralSystem centralSystem;
//
//  /**
//   * Constructor for the schedule an event frame.
//   * @param users the list of users to be passed.
//   */
//  public ScheduleEventFrame(List<String> users) {
//    JButton scheduleButton;
//    setTitle("Schedule Event");
//    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//    setLayout(new BorderLayout());
//
//    // Main panel with BoxLayout for vertical stacking
//    JPanel mainPanel = new JPanel();
//    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
//
//    // Event name
//    JPanel eventNamePanel = new JPanel(new FlowLayout());
//    eventNamePanel.add(new JLabel("Event name:"));
//    eventNameField = new JTextField(20);
//    eventNamePanel.add(eventNameField);
//    mainPanel.add(eventNamePanel);
//
//    // Location
//    JPanel locationPanel = new JPanel(new FlowLayout());
//    locationPanel.add(new JLabel("Location:"));
//    locationComboBox = new JComboBox<>(new String[]{"Not online", "Online"});
//
//    locationComboBox.addActionListener(e -> {
//      boolean isOnlineSelected = "Online".equals(locationComboBox.getSelectedItem());
//      locationTextField.setEnabled(!isOnlineSelected);
//      if (isOnlineSelected) {
//        locationTextField.setText("");
//      }
//    });
//    locationPanel.add(locationComboBox);
//
//    // Add a text field for entering the location
//    locationTextField = new JTextField(20);
//    locationTextField.setEnabled(false);
//    locationPanel.add(locationTextField);
//
//    mainPanel.add(locationPanel);
//
//    // Duration
//    JPanel durationPanel = new JPanel(new FlowLayout());
//    durationPanel.add(new JLabel("Duration in minutes:"));
//    durationField = new JTextField(20);
//    durationPanel.add(durationField);
//    mainPanel.add(durationPanel);
//
//    // Users list
//    JPanel usersPanel = new JPanel();
//    usersPanel.setLayout(new BorderLayout());
//    usersPanel.add(new JLabel("Available users"), BorderLayout.NORTH);
//    userList = new JList<>(users.toArray(new String[0]));
//    userList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
//    JScrollPane listScroller = new JScrollPane(userList);
//    listScroller.setPreferredSize(new Dimension(250, 80));
//    usersPanel.add(listScroller, BorderLayout.CENTER);
//    mainPanel.add(usersPanel);
//
//    // Schedule event button
//    scheduleButton = new JButton("Schedule event");
//    mainPanel.add(scheduleButton);
//
//    scheduleButton.addActionListener((ActionEvent e) -> {
//      boolean isEventOnline = "Online".equals(locationComboBox.getSelectedItem().toString());
//      System.out.println("Scheduling Activated");
//      List<String> selectedUsers = Arrays.stream(userList.getSelectedValues())
//              .map(Object::toString)
//              .collect(Collectors.toList());
//      bruteForceScheduleEvent(eventNameField.getText(), Integer.parseInt(durationField.getText()),
//              selectedUsers);
//
//    });
//
//    add(mainPanel, BorderLayout.CENTER);
//
//    pack();
//    setLocationRelativeTo(null); // Center the window
//  }
//
//  @Override
//  public boolean bruteForceScheduleEvent(String eventName, int eventDuration,
//                                         List<String> participants) {
//    boolean isEventOnline = "Online".equals(locationComboBox.getSelectedItem().toString());
//    final int dayStart = 0;
//    final int dayEnd = 2359;
//    final int increment = 1;
//
//    for (DayOfWeek day : DayOfWeek.values()) {
//      int startTime = dayStart;
//
//      while (startTime < dayEnd) {
//        int endTime = calculateEndTime(startTime, eventDuration);
//        if (endTime > dayEnd) {
//          break;
//        }
//
//        if (canScheduleEvent(day, startTime, endTime, participants, centralSystem)) {
//          IEvent newEvent = new Event(eventName, isEventOnline, day,
//                  startTime, day, endTime, participants, locationTextField.getText(),
//                  userList.getName());
//          try {
//            controller.createEvent(newEvent);
//            return true;
//          } catch (Exception e) {
//            throw new IllegalStateException("Error");
//          }
//        }
//
//        startTime = incrementTime(startTime, increment); // Move to the next time slot
//      }
//    }
//
//    return false;
//  }
//
//  // Helper method to add minutes to a time in military format
//  private int calculateEndTime(int startTime, int duration) {
//    int endHours = (startTime / 100) + (duration / 60);
//    int endMinutes = (startTime % 100) + (duration % 60);
//
//    if (endMinutes >= 60) {
//      endHours += 1;
//      endMinutes -= 60;
//    }
//
//    return (endHours * 100) + endMinutes;
//  }
//
//  // Helper method to increment the time by a given amount of minutes
//  private int incrementTime(int time, int increment) {
//    int newMinutes = (time % 100) + increment;
//    int newHours = time / 100;
//
//    if (newMinutes >= 60) {
//      newHours += 1;
//      newMinutes -= 60;
//    }
//
//    return (newHours * 100) + newMinutes;
//  }
//
//  private boolean canScheduleEvent(DayOfWeek day, int startTime, int endTime,
//                                   List<String> participants,
//                                   IReadOnlyCentralSystem centralSystem) {
//    for (String participant : participants) {
//      IUser user = centralSystem.getUser(participant);
//      if (user == null) {
//        continue;
//      }
//
//      ISchedule schedule = user.getSchedule();
//      List<IEvent> events = schedule.getEventsForDay(day);
//
//      for (IEvent event : events) {
//        int existingStart = event.getStartTime();
//        int existingEnd = event.getEndTime();
//
//        if (timeOverlaps(startTime, endTime, existingStart, existingEnd)) {
//          return false;
//        }
//      }
//    }
//
//    // If no conflicts are found, the event can be scheduled.
//    return true;
//  }
//
//  private boolean timeOverlaps(int start1, int end1, int start2, int end2) {
//    // Return true if there is an overlap.
//    return !(end1 <= start2 || start1 >= end2);
//  }
//}
package cs3500.plannersystem.view;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import cs3500.plannersystem.controller.Features;
import cs3500.plannersystem.model.CentralSystem;
import cs3500.plannersystem.model.ICentralSystem;
import cs3500.plannersystem.model.IEvent;
import cs3500.plannersystem.model.IReadOnlyCentralSystem;

/**
 * The first screen you see that displays the schedules.
 */
public class MainFrame extends JFrame implements IMainFrame {

  private final IReadOnlyCentralSystem iReadOnlyCentralSystem;
  private String selectedUserID;
  private SchedulePanel schedulePanel;
  private List<String> usersName;
  private Map<String, String> userIds;

  private Features controller;

  /**
   * Constructor for the main frame of the calendar.
   * @param iReadOnlyCentralSystem a read only central system to be passed.
   */
  public MainFrame(IReadOnlyCentralSystem iReadOnlyCentralSystem) {
    super("Main");
    this.iReadOnlyCentralSystem = iReadOnlyCentralSystem;
    this.usersName = new ArrayList<>();
    this.userIds = new HashMap<>();
    this.iReadOnlyCentralSystem.getUsers().forEach(user -> {
      usersName.add(user.toString());
      userIds.put(user.toString(), user.getUid());
    });
    this.schedulePanel = new SchedulePanel(this.iReadOnlyCentralSystem, controller);
    this.selectedUserID = "";

    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));


    // Menu
    JMenuBar menuBar = new JMenuBar();
    JMenu file = new JMenu("File");

    JMenuItem addCal = new JMenuItem("Add Calendar");
    addCal.addActionListener(e -> {
      JFileChooser fileChooser = new JFileChooser();
      fileChooser.setDialogTitle("Select an XML File");
      fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
      fileChooser.setAcceptAllFileFilterUsed(false);
      fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("XML Files",
              "xml"));

      int result = fileChooser.showOpenDialog(this);
      schedulePanel.clearSchedule();
      if (result == JFileChooser.APPROVE_OPTION) {

        File selectedFile = fileChooser.getSelectedFile();
        System.out.println("Selected file: " + selectedFile.getAbsolutePath());

        XMLScheduleParser parser = new XMLScheduleParser();
        ICentralSystem centralSystem2 = new CentralSystem();
        parser.parseAndLoadSchedule(selectedFile.getAbsolutePath(), centralSystem2);


        this.dispose();

        EventQueue.invokeLater(() -> {
          MainFrame newMainFrame = new MainFrame(centralSystem2);
          newMainFrame.prepareGUI();
          newMainFrame.makeVisible();
        });
      }
    });


    JMenuItem saveCal = new JMenuItem("Save Calendars");
    saveCal.addActionListener(e -> {
      JFileChooser dirChooser = new JFileChooser();
      dirChooser.setDialogTitle("Select a Directory to Save Calendars");
      dirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
      dirChooser.setAcceptAllFileFilterUsed(false);

      int result = dirChooser.showSaveDialog(this);
      if (result == JFileChooser.APPROVE_OPTION) {
        File selectedDirectory = dirChooser.getSelectedFile();
        System.out.println("Selected directory: " + selectedDirectory.getAbsolutePath());
      }
    });

    file.add(addCal);
    file.add(saveCal);
    menuBar.add(file);
    this.setJMenuBar(menuBar);

    this.getContentPane().add(schedulePanel, BorderLayout.CENTER);

    // Buttons
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout());

    JComboBox<String> userDropdown = new JComboBox<>(usersName.toArray(new String[0]));
    userDropdown.addActionListener(e -> {
      JComboBox<String> combo = (JComboBox<String>) e.getSource();
      String selectedUserName = (String) combo.getSelectedItem();
      updateScheduleView(selectedUserName);
    });
    buttonPanel.add(userDropdown);



    JButton createEventButton = new JButton("Create Event");
    createEventButton.addActionListener(e -> {
      EventFrame createEventFrame = new EventFrame(iReadOnlyCentralSystem, selectedUserID,
              controller);
      createEventFrame.prepareGUI();
      createEventFrame.makeVisible();
    });

    buttonPanel.add(createEventButton);

    // "Schedule Event" button setup
    JButton scheduleEventButton = new JButton("Schedule Event");
    scheduleEventButton.addActionListener(e -> {
      // Here, 'usersName' is assumed to be a list of usernames available for event scheduling
      ScheduleEventFrame scheduleEventFrame = new ScheduleEventFrame(usersName);
      scheduleEventFrame.setVisible(true);
    });
    buttonPanel.add(scheduleEventButton);
    this.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
  }

  /**
   * Updates the schedule's view.
   * @param userName the userName of the update needed.
   */
  public void updateScheduleView(String userName) {
    schedulePanel.clearSchedule();
    this.selectedUserID = this.userIds.get(userName);
    List<IEvent> events = this.iReadOnlyCentralSystem.getEvents(this.userIds.get(userName));
    for (IEvent event : events) {
      double startTime = convertTimeToDouble(event.getStartTime());
      double endTime = convertTimeToDouble(event.getEndTime());
      schedulePanel.addEventEntry(event.getStartDay(), startTime, endTime, this.selectedUserID);
    }
    schedulePanel.repaint();
  }

  @Override
  public void prepareGUI() {
    this.setSize(500, 800);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(false);
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  @Override
  public void onEventCreated(IEvent event) {
    schedulePanel.addEventEntry(event.getStartDay(),
            convertTimeToDouble(event.getStartTime()),
            convertTimeToDouble(event.getEndTime()),
            selectedUserID);
    schedulePanel.repaint();
  }

  private double convertTimeToDouble(int time) {
    int hours = time / 100;
    int minutes = time % 100;
    return hours + minutes / 60.0;
  }

  // Setter method for the controller
  public void setController(Features controller) {
    this.controller = controller;
    // You can also initialize anything that depends on the controller here
  }
}

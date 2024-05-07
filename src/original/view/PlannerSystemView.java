package original.view;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.BorderLayout;


import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import original.model.ReadOnlyPlannerModel;
import original.model.Schedule;

/**
 *  A View of the PlannerSystem containing the background, a schedule to display,
 *  and its components.
 */
public class PlannerSystemView extends JFrame implements PlannerViewGUI {
  private final PlannerSystemPanel panel;

  private final List<ViewFeatures> featuresListeners;
  private Schedule schedule;
  private final JComboBox<String> usersBox;
  private final JButton createEventButton;
  private final JButton schedEventButton;

  //NEW CODE!!
  private final JButton toggleHostButton;

  private ReadOnlyPlannerModel model;

  /**
   * Constructs the plannerView with 3 layers, a background layer, a component layer, and a
   * layer to display a schedule.
   * @param model the planner model to receive information about the display from.
   */
  public PlannerSystemView(ReadOnlyPlannerModel model) {
    this.featuresListeners = new ArrayList<>();
    this.model = model;

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setPreferredSize(new Dimension(875,929));
    this.setResizable(false);
    this.setLayout(new BorderLayout());

    JPanel componentPanel = new JPanel();
    componentPanel.setLayout(new FlowLayout());

    usersBox = new JComboBox<String>();
    usersBox.addItem("<none>");
    usersBox.setSelectedItem(0);
    for (String user : model.getTheUsers()) {
      usersBox.addItem(user);
    }

    createEventButton = new JButton("Create event");
    createEventButton.setForeground(Color.BLACK);
    createEventButton.setBackground(Color.white);

    schedEventButton = new JButton("Schedule event");
    schedEventButton.setForeground(Color.BLACK);
    schedEventButton.setBackground(Color.white);

    toggleHostButton = new JButton("Toggle Host Color");
    toggleHostButton.setForeground(Color.BLACK);
    toggleHostButton.setBackground(Color.white);

    componentPanel.add(usersBox);
    componentPanel.add(createEventButton);
    componentPanel.add(schedEventButton);
    componentPanel.add(toggleHostButton);

    this.add(componentPanel, BorderLayout.SOUTH);

    this.panel = new PlannerSystemPanel(model);
    this.add(panel, BorderLayout.CENTER);

    this.pack();

  }

  @Override
  public void display(boolean show) {
    JMenuBar usersBar = new JMenuBar();
    JMenu userMenu = new JMenu("File");
    userMenu.setBackground(Color.white);

    JMenuItem addCal = new JMenuItem("Add calendar");
    JMenuItem saveCal = new JMenuItem("Save calendars");

    addCal.addActionListener(this::addCalendarClickListener);
    saveCal.addActionListener(this::saveCalendarClickListener);

    userMenu.add(addCal);
    userMenu.add(saveCal);
    usersBar.add(userMenu);

    this.setJMenuBar(usersBar);

    this.setVisible(show);
  }

  @Override
  public void changeSchedule(String user) {
    this.panel.setUser(user);
    this.panel.repaint();
  }

  @Override
  public void update() {

    this.panel.setUser(this.usersBox.getSelectedItem().toString());

    this.repaint();
  }

  @Override
  public void addFeatureListener(ViewFeatures features) {
    this.panel.addFeaturesListener(features);
    this.featuresListeners.add(features);

    usersBox.addActionListener(
            evt -> {features.changeSchedule((String) usersBox.getSelectedItem());
              features.setUser((String) usersBox.getSelectedItem());
            });
    createEventButton.addActionListener(
            evt -> {
              EventFrame eventFrame = new EventFrame(this.model);
              eventFrame.addFeatureListener(features);
              eventFrame.display(true);
            });
    schedEventButton.addActionListener(
            evt -> {
              EventFrame eventFrame = new EventFrame(model, true);
              eventFrame.addFeatureListener(features);
              eventFrame.display(true);
            });
    toggleHostButton.addActionListener(
            evt -> {
//              PlannerSystemDecorator decorator = new PlannerSystemDecorator();
//              decorator.addFeatureListener(features);
//              decorator.display(true);
            }
    );
  }

  private void saveCalendarClickListener(ActionEvent actionEvent) {
    for (ViewFeatures features : this.featuresListeners) {
      features.saveCal(this.schedule);
    }
  }

  @Override
  public String getUser() {
    if (this.usersBox.getSelectedItem().toString() == null) {
      throw new IllegalStateException("Pick a user first");
    }
    return this.usersBox.getSelectedItem().toString();
  }

  @Override
  public void addUser(String user) {
    usersBox.addItem(user);
  }

  private void addCalendarClickListener(ActionEvent event) {
    JFileChooser chooser = new JFileChooser();
    chooser.setCurrentDirectory(new File(System.getProperty("user.home")));

    //show dialog window in which the user can select a file to open
    int result = chooser.showOpenDialog(PlannerSystemView.this);

    //user selected a file
    if (result == JFileChooser.APPROVE_OPTION) {
      File selectedFile = chooser.getSelectedFile(); //saves the file the user selected
      for (ViewFeatures features : this.featuresListeners) {
        features.addCal(selectedFile);
      }
    }
  }

}

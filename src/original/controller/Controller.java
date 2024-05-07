package original.controller;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import original.filehandler.FileHandler;
import original.filehandler.XMLFileHandler;
import original.model.Day;
import original.model.EventInterface;
import original.model.PlannerModel;
import original.model.Schedule;
import original.model.ScheduleInterface;
import original.model.Time;
import original.view.EventFrame;
import original.view.PlannerViewGUI;
import original.view.ViewFeatures;

/**
 * The controller for the plannerSystem. Tells the model and view what to do.
 */
public class Controller implements ViewFeatures {
  private final PlannerModel model;
  private final PlannerViewGUI view;

  /**
   * A Controller contains a model and a view.
   * @param model a model
   * @param view a view
   */
  public Controller(PlannerModel model, PlannerViewGUI view) {
    this.model = model;
    this.view = view;
    this.view.addFeatureListener(this);
  }

  @Override
  public void startGame() {
    this.view.display(true);
  }

  @Override
  public void changeSchedule(String user) {
    view.changeSchedule(user);
  }

  @Override
  public void createEvent(String eventName, String location, boolean onlineBool, String startDay,
                          String startTime, String endDay, String endTime, List<String> invitees) {
    if (invitees == null || invitees.isEmpty()) {
      invitees = new ArrayList<>();
      invitees.add(view.getUser());
    }
    model.createEvent(eventName, location, onlineBool, this.stringToEnum(startDay),
            this.stringToTime(startTime), this.stringToEnum(endDay), this.stringToTime(endTime),
            (ArrayList<String>) invitees);

    view.update();
  }

  @Override
  public void modifyEvent(Time givenStart, Day givenStartDay, String eventName, String location,
                          boolean onlineBool, String startDay, String startTime, String endDay,
                          String endTime, List<String> invitees) {
    model.changeEventAt(givenStart, givenStartDay, eventName, location, onlineBool,
            this.stringToEnum(startDay), this.stringToTime(startTime), this.stringToEnum(endDay),
            this.stringToTime(endTime), invitees);
    view.update();
  }

  @Override
  public void removeEvent(Time givenStart, Day givenStartDay) {
    model.removeEventAt(givenStart, givenStartDay, view.getUser());
    view.update();
  }

  @Override
  public void setUser(String user) {
    if (user != null && !user.equals("<none>")) {
      model.setUser(user);
    }
  }

  @Override
  public PlannerModel getModel() {
    return this.model;
  }

  @Override
  public PlannerViewGUI getView() {
    return this.view;
  }

  // changes string to time
  private Time stringToTime(String time) {
    return new Time(Integer.parseInt(time.substring(0,2)),
            Integer.parseInt(time.substring(2,4)));
  }

  //  turns the string to enum
  private Day stringToEnum(String day) {
    switch (day) {
      case "Saturday":
        return Day.SAT;
      case "Sunday":
        return Day.SUN;
      case "Monday":
        return Day.MON;
      case "Tuesday":
        return Day.TUES;
      case "Wednesday":
        return Day.WED;
      case "Thursday":
        return Day.THURS;
      default:
        return Day.FRI;
    }
  }

  @Override
  public void addCal(File selectedFile) {
    FileHandler handler = new XMLFileHandler();
    Schedule schedule = handler.interpretFile(selectedFile);

    this.model.modifySystem(schedule.getUser(),schedule, true);

    view.addUser(schedule.getUser());

  }

  @Override
  public void saveCal(ScheduleInterface schedule) {
    FileHandler handler = new XMLFileHandler();

    JFileChooser chooser = new JFileChooser();
    chooser.setCurrentDirectory(new File(System.getProperty("user.home")));

    //show dialog window in which the user can select a file to open
    int result = chooser.showSaveDialog((Component) this.view);

    //user selected a file
    if (result == JFileChooser.APPROVE_OPTION) {
      handler.createFile(schedule);
    }

  }
}

package original.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

import original.model.Day;
import original.model.EventInterface;
import original.model.ReadOnlyPlannerModel;
import original.model.ScheduleInterface;
import original.model.Time;

/**
 * Represents the interactive components for the planner system.
 * This includes: selecting a user to display, creating and event, and scheduling an event.
 */
public class PlannerSystemPanel extends JPanel {
  private final ReadOnlyPlannerModel model;
  private ScheduleInterface schdeuleDisplayed;
  private String currentUser;
  private final List<ViewFeatures> featuresListeners;

  /**
   * Constructs the panel holding interactive components for the PlannerSystem view.
   * @param model the model to get information about the system from.
   */
  public PlannerSystemPanel(ReadOnlyPlannerModel model) {
    this.model = Objects.requireNonNull(model);
    this.featuresListeners = new ArrayList<>();

    this.setBackground(new Color(0,0,0,0));
    this.setOpaque(false);
    this.setFocusable(true);
    this.setVisible(true);
  }

  /**
   * sets the current user.
   * @param user user to set to
   */
  public void setUser(String user) {
    this.currentUser = user;
    if (!this.currentUser.equals("<none>")) {
      this.schdeuleDisplayed = this.model.pickCurrentDisplay(currentUser);
    }
  }

  /**
   * Allows the view to make calls to the feature methods.
   * @param features the interface of things this view can do.
   */
  public void addFeaturesListener(ViewFeatures features) {
    this.featuresListeners.add(features);
    MouseEventsListener mouseListener = new MouseEventsListener();
    this.addMouseListener(mouseListener);
  }

  /**
   * Paints components of this panel.
   * @param g the <code>Graphics</code> object to protect
   */
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g.create();

    this.setBackground(Color.lightGray);

    g2d.setColor(Color.black);

    g2d.setStroke(new BasicStroke(1));

    for (int verticalLineAmt = 1; verticalLineAmt < 7; verticalLineAmt++) {
      g2d.drawLine(verticalLineAmt * this.getWidth() / 7, 0,
              verticalLineAmt * this.getWidth() / 7, this.getHeight());
    }

    g2d.setColor(Color.black);
    g2d.setStroke(new BasicStroke(3));
    g2d.drawLine(0, 1, this.getWidth(), 1);

    for (int lineAmt = 1; lineAmt < 25; lineAmt++) {
      if (lineAmt % 4 == 0) {
        g2d.setStroke(new BasicStroke(3));
        g2d.drawLine(0, lineAmt * this.getHeight() / 24, this.getWidth(),
                lineAmt * this.getHeight() / 24);
      }
      else {
        g2d.setStroke(new BasicStroke(1));
        g2d.drawLine(0, lineAmt * this.getHeight() / 24, this.getWidth(),
                lineAmt * this.getHeight() / 24);
      }
    }

    if (this.currentUser != null && !this.currentUser.equals("<none>")) {
      this.drawSchedule(g2d);
    }

  }

  // draws the displayed schedule
  private void drawSchedule(Graphics2D g) {
    PlannerSystemDecorator decorator = new PlannerSystemDecorator(this.model,
            this.schdeuleDisplayed, this.featuresListeners);

    decorator.drawEvent(g);

    this.setFocusable(true);
    /*
    for (EventInterface event : this.schdeuleDisplayed.getEvents()) {
      double minToHour = ((double) event.getDurationInMinutes() / 60)
              * ((double) this.getHeight() / 24);
      int length = (int) Math.round(minToHour);

      int span = event.getSpan();
      int xStart = dayToX(event.getAStartDay().getOrder());
      int yStart = timeToY(event.getAStartTime().getHours(), event.getAStartTime().getMinutes());

      g.setColor(new Color(0x9EFF4646, true));

      if (span == 1) {
        g.fillRect(xStart, yStart, this.getWidth() / 7,
                length);
      }
      else {
        g.fillRect(xStart, yStart, this.getWidth() / 7, this.getWidth() - yStart);

        if (span > 2) {
          for (int day = 1; day <= span - 2 && day < 8; day ++) {
            g.fillRect(dayToX(event.getAStartDay().getOrder() + day), 0,
                    this.getWidth() / 7, this.getHeight());
          }
        }

        if (event.getAEndDay().getOrder() > event.getAStartDay().getOrder()) {
          g.fillRect(dayToX(event.getAEndDay().getOrder()),0,
                  this.getWidth() / 7,
                  this.timeToY(event.getAEndTime().getHours(), event.getAEndTime().getMinutes()));
        }
      }

    }
    */
  }

  // turns day into an x coordinate
  public int dayToX(int day) {
    return (day - 1) * (this.getWidth() / 7);
  }

  // turns time into the correct y coordinate
  public int timeToY(int hour, int min) {
    double minToHour = ((double) min / 60) * ((double) this.getHeight() / 24);

    return (hour * (this.getHeight() / 24)) + (int) Math.round(minToHour);
  }

  // turns x coordinate back into a corresponding day
  private String xToDay(int x) {
    int day = (x / (this.getWidth() / 7));
    switch (day) {
      case 0:
        return "Sunday";
      case 1:
        return "Monday";
      case 2:
        return "Tuesday";
      case 3:
        return "Wednesday";
      case 4:
        return "Thursday";
      case 5:
        return "Friday";
      default:
        return "Saturday";
    }
  }

  // turns y coordinate into a time
  private String yToTime(int y) {
    double min = y / (((double) this.getHeight() / 24) / 60);
    int hours = this.minsToHours(min);
    int mins = this.getMins(min);
    String minString = mins + "";
    String hourString = hours + "";
    if (mins < 10) {
      minString = "0" + mins;
    }
    if (hours < 10) {
      hourString = "0" + hours;
    }
    return hourString + "" + minString;
  }

  private int minsToHours(double mins) {
    if (mins >= 60) {
      return (int) Math.floor((mins / 60));
    }
    return 0;
  }

  private int getMins(double mins) {
    return (int) mins % 60;
  }

  private class MouseEventsListener extends MouseInputAdapter {
    @Override
    public void mousePressed(MouseEvent e) {
      try {
        if (!PlannerSystemPanel.this.currentUser.equals("<none>")) {
          if (model.eventAtTime(stringToTime(PlannerSystemPanel.this.yToTime(e.getY())),
                  model.getUser(),
                  stringToEnum(PlannerSystemPanel.this.xToDay(e.getX())))) {

            EventFrame eventFrame = new EventFrame(model,
                    model.getEventAtTime(stringToTime(PlannerSystemPanel.this.yToTime(e.getY())),
                            model.getUser(),
                            stringToEnum(PlannerSystemPanel.this.xToDay(e.getX()))));

            eventFrame.addFeatureListener(PlannerSystemPanel.this.featuresListeners.get(0));
            eventFrame.display(true);
          }
        }
      } catch (NullPointerException ignore) {
        //ignore
      }
    }
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
}

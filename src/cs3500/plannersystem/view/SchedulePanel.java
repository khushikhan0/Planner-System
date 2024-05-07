//package cs3500.plannersystem.view;
//
//import java.awt.*;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.swing.*;
//
//import cs3500.plannersystem.controller.Features;
//import cs3500.plannersystem.model.DayOfWeek;
//import cs3500.plannersystem.model.IEvent;
//import cs3500.plannersystem.model.IReadOnlyCentralSystem;
//
///**
// * Our schedule panel for the schedule part of the GUI.
// */
//public class SchedulePanel extends JPanel implements ISchedulePanel{
//
//  private final Map<Integer, Map<Double, Double>> duration;
//  private final IReadOnlyCentralSystem iReadOnlyCentralSystem;
//  private String userId;
//
//  private Features controller;
//
//
//  /**
//   * The constructor for our panel that displays schedules.
//   * @param iReadOnlyCentralSystem the read only central system to pass in.
//   */
//  public SchedulePanel(IReadOnlyCentralSystem iReadOnlyCentralSystem,
//                       Features controller) {
//    this.duration = new HashMap<>();
//    this.iReadOnlyCentralSystem = iReadOnlyCentralSystem;
//    this.controller = controller;
//    addMouseListener(new MouseAdapter() {
//      @Override
//      public void mouseClicked(MouseEvent e) {
//        super.mouseClicked(e);
//        int clickedDay = e.getX() / (getWidth() / 7);
//        int clickedHour = e.getY() / (getHeight() / 24);
//        openEventFrameFor(clickedDay, clickedHour);
//      }
//    });
//  }
//
//  private double convertTimeToDouble(int time) {
//    int hours = time / 100;
//    int minutes = time % 100;
//    return hours + minutes / 60.0;
//  }
//
//  private void openEventFrameFor(int clickedDay, int clickedHour) {
//    int hourHeight = getHeight() / 24;
//    double clickedTime = clickedHour + (double) clickedHour / hourHeight;
//    IEvent clickedEvent = findEventForDayAndHour(clickedDay, clickedTime);
//    if (clickedEvent != null) {
//      EventFrame eventFrame = new EventFrame(iReadOnlyCentralSystem, clickedEvent,
//              userId, controller);
//      eventFrame.prepareGUI();
//      eventFrame.makeVisible();
//    }
//  }
//
//  private IEvent findEventForDayAndHour(int day, double hour) {
//    for (IEvent event : iReadOnlyCentralSystem.getEvents(userId)) {
//      if (event.getStartDay().getDayInInteger() == day) {
//        if (hour >= this.convertTimeToDouble(event.getStartTime())
//                && hour <= this.convertTimeToDouble(event.getEndTime())) {
//          return event;
//        }
//      }
//    }
//    return null;
//  }
//
//  @Override
//  protected void paintComponent(Graphics g) {
//    super.paintComponent(g);
//    Graphics2D g2d = (Graphics2D) g;
//
//    // Setup for drawing
//    int width = getWidth();
//    int height = getHeight();
//    int dayWidth = width / 7;
//    int hourHeight = height / 24;
//
//    g2d.setColor(Color.RED);
//    for (Map.Entry<Integer, Map<Double, Double>> dayEntry : duration.entrySet()) {
//      int day = dayEntry.getKey();
//      for (Map.Entry<Double, Double> eventEntry : dayEntry.getValue().entrySet()) {
//        double startHour = eventEntry.getKey();
//        double endHour = eventEntry.getValue();
//        int startY = (int) (hourHeight * startHour);
//        int endY = (int) (hourHeight * endHour);
//        g2d.fillRect(dayWidth * (day), startY, dayWidth, endY - startY);
//      }
//    }
//
//    // Draw day separators
//    g2d.setColor(Color.BLACK);
//    for (int i = 1; i < 7; i++) {
//      int x = i * dayWidth;
//      g2d.drawLine(x, 0, x, height);
//    }
//
//    // Draw hour lines
//    for (int i = 1; i < 24; i++) {
//      int y = i * hourHeight;
//      if (i % 4 == 0) {
//        g2d.setStroke(new BasicStroke(2));
//      } else {
//        g2d.setStroke(new BasicStroke(1));
//      }
//      g2d.drawLine(0, y, width, y);
//    }
//  }
//
//  @Override
//  public void addEventEntry(DayOfWeek day, Double startHour, Double endHour, String selectedId) {
//    if (!this.duration.containsKey(day.getDayInInteger())) {
//      this.duration.put(day.getDayInInteger(), new HashMap<>());
//      this.duration.get(day.getDayInInteger()).put(startHour, endHour);
//      this.userId = selectedId;
//    }
//    repaint();
//  }
//
//  public void clearSchedule() {
//    this.duration.clear();
//    this.repaint();
//  }
//}

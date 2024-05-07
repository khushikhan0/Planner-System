package original.view;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.*;

import original.model.EventInterface;
import original.model.ReadOnlyPlannerModel;
import original.model.ScheduleInterface;

public class PlannerSystemDecorator extends PlannerSystemPanel {
  private final ReadOnlyPlannerModel model;
  private ScheduleInterface schdeuleDisplayed;
  private String currentUser;
  private final List<ViewFeatures> featuresListeners;

  /**
   * Constructs the panel holding interactive components for the PlannerSystem view.
   *
   * @param model the model to get information about the system from.
   */
  public PlannerSystemDecorator(ReadOnlyPlannerModel model, ScheduleInterface schdeuleDisplayed,
                                List<ViewFeatures> featuresListeners) {
    super(model);
    this.model = Objects.requireNonNull(model);
    this.schdeuleDisplayed = Objects.requireNonNull(schdeuleDisplayed);
    this.featuresListeners = new ArrayList<>();

    this.setBackground(new Color(0,0,0,0));
//    this.setOpaque(false);
    this.setFocusable(true);
    this.setVisible(true);
  }

  public void drawEvent(Graphics2D g) {
    for (EventInterface event : schdeuleDisplayed.getEvents()) {
      double minToHour = ((double) event.getDurationInMinutes() / 60)
              * ((double) this.getHeight() / 24);
      int length = (int) Math.round(minToHour);

      int span = event.getSpan();
      int xStart = dayToX(event.getAStartDay().getOrder());
      int yStart = timeToY(event.getAStartTime().getHours(), event.getAStartTime().getMinutes());

      g.setColor(new Color(0x9EFF4646, true));

      System.out.println(event.getAName());

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
  }
}

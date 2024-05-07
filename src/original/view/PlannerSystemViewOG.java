package original.view;

import java.util.ArrayList;
import java.util.Objects;

import original.model.EventInterface;
import original.model.Schedule;

/**
 * A View of the PlannerSystem that renders a textual view.
 */
public class PlannerSystemViewOG implements PlannerView {
  private final Schedule schedule;

  /**
   * Create a text view of the planner given a model.
   *
   * @param schedule a planner.
   */
  public PlannerSystemViewOG(Schedule schedule) {
    this.schedule = Objects.requireNonNull(schedule);
  }

  /**
   * Renders a text view of the planner system.
   *
   * @return a string representation of the planner system
   */
  @Override
  public String render() {
    String formatted = "User: " + this.schedule.getUser() + "\n";
    formatted = formatted + "Sunday:" + getAnEvent("Sunday") + "\n"
            + "Monday:" + getAnEvent("Monday") + "\n"
            + "Tuesday:" + getAnEvent("Tuesday") + "\n"
            + "Wednesday:" + getAnEvent("Wednesday") + "\n"
            + "Thursday:" + getAnEvent("Thursday") + "\n"
            + "Friday:" + getAnEvent("Friday") + "\n"
            + "Saturday:" + getAnEvent("Saturday");

    return formatted;
  }

  /**
   * Retrieves all the events on the given day and turns it into a string representation.
   *
   * @param day a day that you'd like a string representation of events from
   * @return string representation of all the events on a given day
   */
  private String getAnEvent(String day) {
    String endString = "";

    ArrayList<EventInterface> events = this.schedule.getEvents();
    for (EventInterface event : events) {
      if (event.getAStartDay().enumToString().equals(day)) {
        endString = endString + this.parseEvent(event);
      } else {
        endString = endString + "";
      }
    }

    return endString;
  }

  /**
   * Parses an event to the correct string format.
   *
   * @param event an event to parse into a string.
   * @return a string representation of an event
   */
  private String parseEvent(EventInterface event) {
    String formattedEvent = "\n" +
            "\t\tname: " + event.getAName() + "\n" +
            "\t\ttime: " + event.getAStartDay().enumToString() + ": " +
            event.getAStartTime().timeToString() +
            " -> " + event.getAEndDay().enumToString() + ": " +
            event.getAEndTime().timeToString() + "\n" +
            "\t\tlocation: " + event.getALocation() + "\n" +
            "\t\tonline: " + event.getIsOnline() + "\n" +
            "\t\tinvitees: " + event.inviteesToString();

    return formattedEvent;
  }
}

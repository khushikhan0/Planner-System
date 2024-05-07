package original.strategy;

import java.util.List;

import original.model.EventInterface;
import original.model.PlannerModel;

/**
 * Represents a type of strategy that can be used to auto schedule events in the planner system.
 */
public interface PlannerStrategy {

  /**
   * Method to search for next slot to schedule.
   * @param model model to get information from
   * @param duration duration of event in minutes
   * @param invitees list of invitees to check across
   * @return a list of available slots.
   */
  List<String> searchForAvailableTime(PlannerModel model, int duration, List<String> invitees);

  /**
   * To sort the events into a list based chronologically.
   * @param eventsList unsorted event list
   * @return sorted event list
   */
  List<EventInterface> sortEvents(List<EventInterface> eventsList);
}

package original.strategy;

import java.util.Comparator;

import original.model.EventInterface;

/**
 * compares events in order of time.
 */
public class EventComparator implements Comparator<EventInterface> {
  @Override
  public int compare(EventInterface event1, EventInterface event2) {
    int compareDays = event1.getAStartDay().compareTo(event2.getAStartDay());

    if (compareDays != 0) {
      return compareDays;
    }
    else {
      int compareHours = Integer.compare(event1.getAStartTime().getHours(),
              event2.getAStartTime().getHours());

      if (compareHours != 0) {
        return compareHours;
      }
      else {
        return Integer.compare(event1.getAStartTime().getMinutes(),
                event2.getAStartTime().getMinutes());
      }
    }
  }
}

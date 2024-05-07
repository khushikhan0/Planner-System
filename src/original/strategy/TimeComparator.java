package original.strategy;

import java.util.Comparator;

import original.model.Time;

/**
 * compare times of event from model.
 */
public class TimeComparator implements Comparator<Time> {
  @Override
  public int compare(Time time1, Time time2) {
    int hoursComparison = Integer.compare(time1.getHours(), time2.getHours());

    if (hoursComparison != 0) {
      return hoursComparison;
    }
    else {
      return Integer.compare(time1.getMinutes(), time2.getMinutes());
    }
  }
}

package original.strategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import original.model.Day;
import original.model.EventInterface;
import original.model.PlannerModel;
import original.model.Time;

/**
 * A strategy to find any time in which all the users in the invitees list are all available.
 */
public class AnytimeStrategy implements PlannerStrategy {
  @Override
  public List<String> searchForAvailableTime(PlannerModel model, int duration,
                                             List<String> invitees) {
    List<EventInterface> events = new ArrayList<>();

    //getting everyone's events
    for (String user : invitees) {
      events.addAll(model.getTheEvents(user));
    }

    //sort events list in order by time. the earliest event will go first
    events = this.sortEvents(events);

    //set a start time to compare to
    Time startTime = new Time(0, 0);
    Time endTime = new Time(23, 59);
    Day day = Day.SUN;

    //compare times with sunday at midnight
    for (int event = 0; event < events.size(); event++) {

      if (this.timeDifference(startTime, events.get(event).getAStartTime()) != duration) {
        startTime = events.get(event).getAEndTime();

        this.timeDifference(startTime, events.get(event).getAStartTime());
        day = events.get(event).getAStartDay();
        endTime = this.addDurationToTime(startTime, duration);
      }
      else {
        endTime = this.addDurationToTime(startTime, duration);
        System.out.println(endTime.timeToString());
        day = events.get(event).getAStartDay();
      }
    }

    TimeComparator comparator = new TimeComparator();

    return Arrays.asList(startTime.timeToString(), endTime.timeToString(), day.enumToString());
  }

  private Time addDurationToTime(Time startTime, int duration) {
    int hours = 0;
    int minutes;

    if (duration > 59) {
      hours = duration / 60;
    }
    minutes = duration % 60;

    int newHours = startTime.getHours() + hours;
    int newMinutes = startTime.getMinutes() + minutes;

    if (newMinutes > 59) {
      newHours = newMinutes / 60 + newHours;
    }
    minutes = newMinutes % 60;


    return new Time(newHours, minutes);
  }

  private int timeDifference(Time startTime, Time eventTime) {
    int hoursDiff = eventTime.getHours() - startTime.getHours();
    int minsDiff = eventTime.getMinutes() - startTime.getMinutes();

    return (hoursDiff * 60) + minsDiff;
  }

  @Override
  public List<EventInterface> sortEvents(List<EventInterface> eventsList) {
    EventComparator comparator = new EventComparator();

    //sorts the events list
    eventsList.sort(comparator);

    //initalizes empty list
    List<EventInterface> reversedList = new ArrayList<>();

    for (int event = eventsList.size() - 1; event >= 0; event--) {
      reversedList.add(eventsList.get(event));
    }

    return reversedList;
  }

}

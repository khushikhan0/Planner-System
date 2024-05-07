import java.util.ArrayList;
import java.util.Arrays;

import original.controller.Controller;
import original.model.Day;
import original.model.Event;
import original.model.EventInterface;
import original.model.PlannerModel;
import original.model.PlannerSystem;
import original.model.Schedule;
import original.model.Time;
import original.view.PlannerSystemView;
import original.view.PlannerViewGUI;
import original.view.ViewFeatures;

/**
 * Class to run the program from.
 */
public class PlannerMain {

  /**
   * the main method that runs the program.
   * @param args the arguments to run the program.
   */
  public static void main(String[] args) {
    PlannerModel model;
    PlannerViewGUI view;
    ViewFeatures controller;

    if (args.length >= 1 ) {
      if (args[0].equals("workday")) {
        // set strategy
      } else {
        // set strategy
      }
    }

    model = new PlannerSystem();
    view = new PlannerSystemView(model);
    controller = new Controller(model, view);

    //    if (args.length > 1 ) {
    //      if (args[1].equals("2")) {
    //        System.out.print("Used provider view");
    //        ICentralSystem model2 = new PlannerSystemAdapter(model);
    //        MainFrame view2 = new MainFrame(model2);
    //        view = new MainPlannerAdapter(view2);
    //        controller = new Controller(model, view);
    //        Features controller2 = new ControllerAdapter(controller);
    //        view2.setController(controller2);
    //      }
    //    }
    controller.startGame();
  }

  /**
   * Just for easy testing purposes to be deleted.
   * @return a list of schedules to put in test model.
   */
  private static ArrayList<Schedule> exModel() {
    ArrayList<Schedule> list = new ArrayList<>();
    Event e1 = new Event("Name", "Place", false,
            Day.TUES, new Time(10, 30), Day.TUES, new Time(11,00),
            new ArrayList<String>(Arrays.asList("User1", "User2")));
    Event e2 = new Event("E2", "Place2", true,
            Day.WED, new Time(10, 30), Day.THURS, new Time(11,00),
            new ArrayList<String>(Arrays.asList("User1")));
    Event e3 = new Event("Name3", "Place3", false,
            Day.FRI, new Time(10, 30), Day.TUES, new Time(9,00),
            new ArrayList<String>(Arrays.asList("User2")));

    list.add(new Schedule("User1", new ArrayList<EventInterface>(Arrays.asList(e1, e2))));
    list.add(new Schedule("User2", new ArrayList<EventInterface>(Arrays.asList(e1, e3))));

    return list;
  }
}

# README for Weekly Planner

## Overview:  
This codebase is creating a planner system to help make it easier to keep track all the
events in people's lives. A planner system contains multiple users, each with their own
schedule. In this schedule, people can add the events they have to attend in the week.
This codebase assumes that the implementer can read Java to an intermediate degree.

## Key Components:
There are four main components to this planner:
        1. Model: The model handles the schedule's data by allowing you to add events to
                schedules, remove events from schedules, modify events, etc. It makes it easier
                to move around your events as schedules should be flexible enough to do that.
        2. View: The view, given a schedule, creates a textual view of the schedule to make it
                easier to visualize your schedule.
        3. Filehandler: The filehandler allows you to convert your schedule into a file and
                vice versa.*
        4. Controller: The controller is not implemented yet, but it will allow you to control
                the GUI (graphical user interface) of the planner system or the text view .

* FileHandler is made a separate component from the model because the reading and writing xml files
doesn't effect the behavior of the system. In order to keep it separate, the system's convert to
file and upload file methods allow for a handler to be taken in-- in which in the future a different
handler could allow conversion between different file types etc. This helps with encapsulation
and reusability later.

## Key Subcomponents:
Within the key components of this planner:
        1. Model: In the model, a PlannerSystem has users and schedules. Each user has an
                individual Schedule. A Schedule has the user and a list of events. An Event has a
                Day (start day and end day) and a Time (start time and end time).
        2. View: In the view, a PlannerSystemView takes in a Schedule that you'd like to convert
                into textual view.
        3. Filehandler: The filehandler has a XMLFileHandler which can read from files and write to
                files. It also allows you to convert a Schedule to an XML file and vice versa.

## Source Organization:
    planner_system
        src (contains 4 main components)
            controller
            filehandler
                FileHandler (interface)
                XMLFileHandler (class that implements FileHandler)
            model
                Day (enum for days of the week)
                Event (class that represents a single Event)
                PlannerModel (an interface with all of the necessary methods for planners)
                PlannerSystem (class that extends PlannerModel)
                Schedule (class that represents a single Schedule)
                Time (class that represents a Time)
            view
                PlannerSystemView (a class that extends PlannerView)
                PlannerView (an interface with all necessary methods for viewing a planner)
        test (contains tests for 4 main components and all the subcomponents)
            filehandler
                test for XMLFileHandler
            model
                tests for day, event, plannersystem, schedule, and time
            view
                test for PlannerSystemView

## Adapters:
- Model:
        - We created an Adapter for all of the model interfaces
        - MainSystem:
            - PlannerSystemAdapter extends PlannerSystem and implements ICentralSystem
            - When their view takes in/uses an ICentralSystem it will take in this adapter and
            the adapter will call super on its methods to delegate to our model implementation
        - Events:
            - EventAdapter implements their event interface (IEvent) and has-a Event
            (from our model)
            - When their view calls on an IEvent, it would call our adapter and delegate to an
            instance of our model's event
        - Schedule:
            - ScheduleAdapter implements their event interface (ISchedule) and has-a Event
            (from our model)
            - When their view calls on an ISchedule, it would call our adapter and delegate to
             an instance of our model's schedule
        - Schedule:
    - View:
        - Main System:
            - MainPlannerAdapter extends PlannerSystemViewGUI and has-a MainFrame from the provider
            code
            - When our adapted view is called it delegates to their view
    - Controller:
        - Controller adapter has our controller but is a Features so when calls are made to it,
        it delegates to our controller.

 - Commands:
    - No args: OG view and anytime strategy
    - First arg: "workday" - workday Strategy, OG view; anything else will give anytime strat
    - Second arg: 2 - other view; anything else will give og view


package original.controller;//package original.controller;
//
//import cs3500.plannersystem.controller.Features;
//import cs3500.plannersystem.model.ICentralSystem;
//import cs3500.plannersystem.model.IEvent;
//import cs3500.plannersystem.view.IMainFrame;
//import original.model.Event;
//import original.model.EventInterface;
//import original.view.ViewFeatures;
//
///**
// * Allows for adapting a ViewFeatures controller to a Features controller of the provider code.
// */
//public class ControllerAdapter implements Features {
//  ViewFeatures features;
//
//
//  /**
//   * Constructs the adapter.
//   * @param features the view features to delegate to.
//   */
//  public ControllerAdapter(ViewFeatures features) {
//    this.features = features;
//  }
//
//  @Override
//  public void createEvent(IEvent event) {
//    EventInterface e = getEventInterface(event);
//    features.createEvent(e.getName(), e.getLocation(), e.getIsOnline(),
//            e.getStartDay().enumToString(), e.getStartTime().timeToString(),
//            e.getEndDay().enumToString(), e.getEndTime().timeToString(), e.getInvitees());
//  }
//
//  @Override
//  public void modifyEvent(IEvent oldEvent, IEvent newEvent) {
//    Event e = getEventInterface(oldEvent);
//    Event e1 = getEventInterface(newEvent);
//    features.modifyEvent(e1.getStartTime(),e1.getStartDay(),
//            e.getName(), e.getLocation(), e.getIsOnline(),
//            e.getStartDay().enumToString(), e.getStartTime().timeToString(),
//            e.getEndDay().enumToString(), e.getEndTime().timeToString(), e.getInvitees());
//  }
//
//  @Override
//  public void removeEvent(IEvent event) {
//    Event e = getEventInterface(event);
//    features.removeEvent(e.getStartTime(), e.getStartDay());
//  }
//
//  @Override
//  public ICentralSystem getCentralSystem() {
//    return null;
//    // this feature is not possible to implement because it returns an ICentralSystem
//  }
//
//  @Override
//  public IMainFrame getMainFrame() {
//    return null;
//  }
//}

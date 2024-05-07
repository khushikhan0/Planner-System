package cs3500.plannersystem.model;

public class UserExample implements IUser {
  private final String name;
  private final String uid;
  private ISchedule schedule;

  public UserExample(String name, String uid, ISchedule schedule) {
    this.name = name;
    this.uid = uid;
    this.schedule = schedule;
  }


  @Override
  public String getUid() {
    return uid;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public ISchedule getSchedule() {
    return schedule;
  }

  @Override
  public void setSchedule(ISchedule schedule) {
    this.schedule = schedule;
  }
}

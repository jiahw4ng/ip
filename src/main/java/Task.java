public class Task {
  protected String description;
  protected boolean isDone = false;

  public Task(String desc) {
    this.description = desc;
  }

  @Override
  public String toString() {
    return String.format("[%s] %s", this.isDone ? "X" : " ", this.description);
  }

  public void toggleIsDone() {
    this.isDone = !this.isDone;
  }
}

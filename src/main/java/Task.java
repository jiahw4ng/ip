import java.util.List;

/** Represents a task that can be completed or left incomplete. */
public class Task {
  private final String description;
  private boolean isDone = false;

  private Task(String desc) {
    this.description = desc;
  }

  @Override
  public String toString() {
    return String.format("[%s] %s", this.isDone ? "X" : " ", this.description);
  }

  /** Marks this task as completed. */
  public void markAsDone() {
    isDone = true;
  }

  /** Marks this task as incomplete. */
  public void markAsNotDone() {
    isDone = false;
  }

  /** Creates a task with the given description. */
  public static Task of(String desc) {
    return new Task(desc);
  }

  /** Prints the tasks in the order that the user entered them. */
  public static void printTasks(List<Task> tasks) {
    for (int i = 0; i < tasks.size(); i++) {
      System.out.println((i + 1) + ". " + tasks.get(i));
    }
  }
}

import java.util.List;

/** Represents a task that can be completed or left incomplete. */
public abstract class Task {
  protected final String description;
  protected boolean isDone = false;

  public Task(String desc) {
    this.description = desc;
  }

  /**
   * Creates the task specified by a task-creation command.
   *
   * @param input the complete command entered by the user
   * @return the corresponding todo, deadline, or event
   * @throws IllegalArgumentException if the command is unknown or is missing a
   *                                  required value
   */
  public static Task of(String input) {
    if (input.startsWith("todo")) {
      return Todo.createTodo(input);
    }
    if (input.startsWith("deadline")) {
      return Deadline.createDeadline(input);
    }
    if (input.startsWith("event")) {
      return Event.createEvent(input);
    }
    throw new IllegalArgumentException("I'm sorry, I don't know what that means!");
  }

  /**
   * Returns a trimmed command value, rejecting an empty value with a clear
   * message.
   */
  public static String requireValue(String value, String errorMsg) {
    String trimmedValue = value.trim();
    if (trimmedValue.isEmpty()) {
      throw new IllegalArgumentException(errorMsg);
    }
    return trimmedValue;
  }

  /**
   * Returns the index of a substring, rejecting a missing substring with a clear
   * message.
   */
  public static int requireIndex(String str, String substr, String errorMsg) {
    int index = str.indexOf(substr);
    if (index < 0) {
      throw new IllegalArgumentException(errorMsg);
    }
    return index;
  }

  @Override
  public String toString() {
    return String.format("[%s] %s", this.getDoneString(), this.description);
  }

  /** Marks this task as completed. */
  public void markAsDone() {
    isDone = true;
  }

  /** Marks this task as incomplete. */
  public void markAsNotDone() {
    isDone = false;
  }

  public String getDoneString() {
    return this.isDone ? "X" : " ";
  }

  /** Prints the tasks in the order that the user entered them. */
  public static void printTasks(List<Task> tasks) {
    for (int i = 0; i < tasks.size(); i++) {
      System.out.println((i + 1) + ". " + tasks.get(i));
    }
  }
}

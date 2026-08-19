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
   * @throws IllegalArgumentException if the command is unknown or is missing a required value
   */
  public static Task of(String input) {
    if (input.equals("todo") || input.startsWith("todo ")) {
      return new Todo(requireValue(input.substring("todo".length()), "a description"));
    }
    if (input.equals("deadline") || input.startsWith("deadline ")) {
      return createDeadline(input);
    }
    if (input.equals("event") || input.startsWith("event ")) {
      return createEvent(input);
    }
    throw new IllegalArgumentException("I don't know that command.");
  }

  /** Creates a deadline from a command containing a {@code /by} delimiter. */
  private static Deadline createDeadline(String input) {
    String details = input.substring("deadline".length()).trim();
    int byIndex = details.indexOf(" /by ");
    if (byIndex < 0) {
      throw new IllegalArgumentException("A deadline needs a /by date.");
    }
    String description = requireValue(details.substring(0, byIndex), "a description");
    String by = requireValue(details.substring(byIndex + " /by ".length()), "a /by date");
    return new Deadline(description, by);
  }

  /** Creates an event from a command containing {@code /from} and {@code /to} delimiters. */
  private static Event createEvent(String input) {
    String details = input.substring("event".length()).trim();
    int fromIndex = details.indexOf(" /from ");
    int toIndex = details.indexOf(" /to ");
    if (fromIndex < 0 || toIndex < 0 || toIndex <= fromIndex) {
      throw new IllegalArgumentException("An event needs /from and /to dates.");
    }
    String description = requireValue(details.substring(0, fromIndex), "a description");
    String from = requireValue(details.substring(fromIndex + " /from ".length(), toIndex), "a /from date");
    String to = requireValue(details.substring(toIndex + " /to ".length()), "a /to date");
    return new Event(description, from, to);
  }

  /** Returns a trimmed command value, rejecting an empty value with a clear message. */
  private static String requireValue(String value, String name) {
    String trimmedValue = value.trim();
    if (trimmedValue.isEmpty()) {
      throw new IllegalArgumentException("A task needs " + name + ".");
    }
    return trimmedValue;
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

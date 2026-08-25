import java.util.List;

/** Represents a task that can be completed or left incomplete. */
public abstract class Task {
  protected final String description;
  protected boolean isDone = false;

  /**
   * Constructs a {@code Task} with the specified description.
   *
   * @param desc the description of the task
   */
  public Task(String desc) {
    this.description = desc;
  }

  /**
   * Creates the task specified by a task-creation command.
   *
   * @param input the complete command entered by the user
   * @return the corresponding todo, deadline, or event
   * @throws IllegalCommandException if the command is unknown or is missing a
   *                                 required value
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
    throw new IllegalCommandException("I'm sorry, I don't know what that means!");
  }

  /**
   * Returns a trimmed command value, rejecting an empty value with a clear
   * message.
   *
   * @param value    the string to trim and validate
   * @param errorMsg the error message to throw if the value is empty
   * @return the trimmed non-empty string
   * @throws IllegalCommandException if the trimmed string is empty
   */
  public static String requireValue(String value, String errorMsg) {
    String trimmedValue = value.trim();
    if (trimmedValue.isEmpty()) {
      throw new IllegalCommandException(errorMsg);
    }
    return trimmedValue;
  }

  /**
   * Returns the index of a substring, rejecting a missing substring with a clear
   * message.
   *
   * @param str      the string to search within
   * @param substr   the delimiter or substring to search for
   * @param errorMsg the error message to throw if the substring is not found
   * @return the index of the substring
   * @throws IllegalCommandException if the substring is not found
   */
  public static int requireIndex(String str, String substr, String errorMsg) {
    int index = str.indexOf(substr);
    if (index < 0) {
      throw new IllegalCommandException(errorMsg);
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

  /**
   * Returns the status icon representing completion status.
   *
   * @return "X" if the task is done, or " " if not done
   */
  public String getDoneString() {
    return this.isDone ? "X" : " ";
  }

  /**
   * Prints the tasks in the order that the user entered them.
   *
   * @param tasks the list of tasks to print
   */
  public static void printTasks(List<Task> tasks) {
    for (int i = 0; i < tasks.size(); i++) {
      System.out.println((i + 1) + ". " + tasks.get(i));
    }
  }

  /**
   * Returns the formatted string representation of this task for persistent storage.
   *
   * @return the string formatted for file storage
   */
  public abstract String toDataFormat();

  /**
   * Creates a {@code Task} instance by decoding a line from the storage file.
   *
   * @param line the line of text from the storage file
   * @return the reconstructed {@code Task} instance
   * @throws IllegalArgumentException if the stored line format is invalid or corrupted
   */
  public static Task fromDataFormat(String line) {
    String[] parts = line.split(" \\| ");
    if (parts.length < 3) {
      throw new IllegalArgumentException("Invalid task format in storage file: " + line);
    }
    String type = parts[0];
    boolean isDone = parts[1].equals("1");
    String description = parts[2];
    Task task;
    switch (type) {
    case "T" -> task = new Todo(description);
    case "D" -> {
      if (parts.length < 4) {
        throw new IllegalArgumentException("Invalid deadline format in storage file: " + line);
      }
      task = new Deadline(description, DateTimeUtil.parse(parts[3]));
    }
    case "E" -> {
      if (parts.length < 5) {
        throw new IllegalArgumentException("Invalid event format in storage file: " + line);
      }
      task = new Event(description, DateTimeUtil.parse(parts[3]), DateTimeUtil.parse(parts[4]));
    }
    default -> throw new IllegalArgumentException("Unknown task type in storage file: " + type);
    }
    if (isDone) {
      task.markAsDone();
    }
    return task;
  }
}

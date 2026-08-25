/**
 * Represents a task without a date or time associated with it.
 */
public class Todo extends Task {

  /**
   * Constructs a {@code Todo} task with the specified description.
   *
   * @param desc the description of the task
   */
  public Todo(String desc) {
    super(desc);
  }

  @Override
  public String toString() {
    return "[T]" + super.toString();
  }

  /**
   * Creates a {@code Todo} task from a command input string.
   *
   * @param input the command string entered by the user
   * @return the created {@code Todo} task
   * @throws IllegalCommandException if the description is missing
   */
  public static Todo createTodo(String input) {
    String details = input.substring("todo".length()).trim();
    String description = requireValue(details, "A todo needs a non-empty description.");
    return new Todo(description);
  }

  @Override
  public String toDataFormat() {
    return String.format("T | %d | %s", this.isDone ? 1 : 0, this.description);
  }
}

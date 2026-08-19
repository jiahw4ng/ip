/** A task without a date or time associated with it. */
public class Todo extends Task {

  public Todo(String desc) {
    super(desc);
  }

  @Override
  public String toString() {
    return "[T]" + super.toString();
  }

  public static Todo createTodo(String input) {
    String details = input.substring("todo".length()).trim();
    String description = requireValue(details, "A todo needs a non-empty description.");
    return new Todo(description);
  }
}

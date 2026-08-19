/** A task without a date or time associated with it. */
public class Todo extends Task {

  public Todo(String desc) {
    super(desc);
  }

  @Override
  public String toString() {
    return "[T]" + super.toString();
  }
}

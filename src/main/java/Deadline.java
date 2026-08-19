/** A task that must be completed by a specified date or time. */
public class Deadline extends Task {
  protected final String by;

  public Deadline(String desc, String by) {
    super(desc);
    this.by = by;
  }

  @Override
  public String toString() {
    return String.format("[D]%s (by: %s)", super.toString(), this.by);
  }

}

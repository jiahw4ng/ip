/** A task that must be completed by a specified date or time. */
public class Deadline extends Task {
  protected final String by;
  public static final String BY_DELIMITER = "/by";

  public Deadline(String desc, String by) {
    super(desc);
    this.by = by;
  }

  @Override
  public String toString() {
    return String.format("[D]%s (by: %s)", super.toString(), this.by);
  }

  /** Creates a deadline from a command containing a {@code /by} delimiter. */
  public static Deadline createDeadline(String input) {
    String details = input.substring("deadline".length()).trim();
    int byIndex = requireIndex(details, BY_DELIMITER, "A deadline needs a non-empty /by date.");
    String description = requireValue(details.substring(0, byIndex), "A deadline needs a non-empty description.");
    String by = requireValue(details.substring(byIndex + BY_DELIMITER.length()), "A deadline needs a non-empty /by date.");
    return new Deadline(description, by);
  }

}

/**
 * Represents a task occurring between specified start and end dates or times.
 */
public class Event extends Task {
  protected final String from;
  protected final String to;
  public static final String FROM_DELIMITER = "/from";
  public static final String TO_DELIMITER = "/to";

  /**
   * Constructs an {@code Event} task with a description, start time, and end
   * time.
   *
   * @param desc the description of the event
   * @param from the starting date or time of the event
   * @param to   the ending date or time of the event
   */
  public Event(String desc, String from, String to) {
    super(desc);
    this.from = from;
    this.to = to;
  }

  @Override
  public String toString() {
    return String.format("[E]%s (from: %s to: %s)", super.toString(), this.from, this.to);
  }

  /**
   * Creates an event from a command containing {@code /from} and {@code /to}
   * delimiters.
   *
   * @param input the command string entered by the user
   * @return the created {@code Event} task
   * @throws IllegalCommandException if the description, start date, or end date
   *                                 is missing or invalid
   */
  public static Event createEvent(String input) {
    String details = input.substring("event".length()).trim();
    int fromIndex = requireIndex(details, FROM_DELIMITER, "An event needs a non-empty /from date.");
    int toIndex = requireIndex(details, TO_DELIMITER, "An event needs a non-empty /to date.");
    if (toIndex <= fromIndex) {
      throw new IllegalCommandException("An event needs a non-empty /to date that comes after the /from date.");
    }
    String description = requireValue(details.substring(0, fromIndex), "An event needs a non-empty description.");
    String from = requireValue(details.substring(fromIndex + FROM_DELIMITER.length(), toIndex),
        "An event needs a non-empty /from date.");
    String to = requireValue(details.substring(toIndex + TO_DELIMITER.length()),
        "An event needs a non-empty /to date.");
    return new Event(description, from, to);
  }
}

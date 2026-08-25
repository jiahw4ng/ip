import java.time.LocalDateTime;

/**
 * Represents a task occurring between specified start and end dates or times.
 */
public class Event extends Task {
  protected final LocalDateTime from;
  protected final LocalDateTime to;
  public static final String FROM_DELIMITER = "/from";
  public static final String TO_DELIMITER = "/to";

  /**
   * Constructs an {@code Event} task with a description, start time, and end
   * time.
   *
   * @param desc the description of the event
   * @param from the starting date and time of the event
   * @param to   the ending date and time of the event
   * @throws IllegalCommandException if {@code to} is before {@code from}
   */
  public Event(String desc, LocalDateTime from, LocalDateTime to) {
    super(desc);
    if (to.isBefore(from)) {
      throw new IllegalCommandException("The event end date (/to) cannot be before the start date (/from).");
    }
    this.from = from;
    this.to = to;
  }

  @Override
  public String toString() {
    return String.format("[E]%s (from: %s to: %s)", super.toString(),
        DateTimeUtil.formatDisplay(this.from), DateTimeUtil.formatDisplay(this.to));
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
      throw new IllegalCommandException("An event needs a /from date that comes before the /to date.");
    }
    String description = requireValue(details.substring(0, fromIndex), "An event needs a non-empty description.");
    String fromString = requireValue(details.substring(fromIndex + FROM_DELIMITER.length(), toIndex),
        "An event needs a non-empty /from date.");
    String toString = requireValue(details.substring(toIndex + TO_DELIMITER.length()),
        "An event needs a non-empty /to date.");
    LocalDateTime from = DateTimeUtil.parse(fromString);
    LocalDateTime to = DateTimeUtil.parse(toString);
    return new Event(description, from, to);
  }

  @Override
  public String toDataFormat() {
    return String.format("E | %d | %s | %s | %s", this.isDone ? 1 : 0, this.description,
        DateTimeUtil.formatStorage(this.from), DateTimeUtil.formatStorage(this.to));
  }
}

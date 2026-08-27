package martin.task;

import java.time.LocalDateTime;

import martin.exception.IllegalCommandException;
import martin.util.DateTimeUtil;

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
     * @param description The description of the event.
     * @param startDateTime The starting date and time of the event.
     * @param endDateTime The ending date and time of the event.
     * @throws IllegalCommandException If {@code endDateTime} is before {@code startDateTime}.
     */
    public Event(String description, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        super(description);
        if (endDateTime.isBefore(startDateTime)) {
            throw new IllegalCommandException("The event end date (/to) cannot be before the start date (/from).");
        }
        this.from = startDateTime;
        this.to = endDateTime;
    }

    /**
     * Returns this event with its completion status and formatted time range.
     *
     * @return The display representation of this event.
     */
    @Override
    public String toString() {
        return String.format("[E]%s (from: %s to: %s)", super.toString(),
                DateTimeUtil.formatDisplay(this.from), DateTimeUtil.formatDisplay(this.to));
    }

    /**
     * Creates an event from a command containing {@code /from} and {@code /to}
     * delimiters.
     *
     * @param input The command string entered by the user.
     * @return The created {@code Event} task.
     * @throws IllegalCommandException If the description, start date, or end date is missing or invalid.
     */
    public static Event createEvent(String input) {
        String details = input.substring("event".length()).trim();
        int fromIndex = requireIndex(details, FROM_DELIMITER, "An event needs a non-empty /from date.");
        int toIndex = requireIndex(details, TO_DELIMITER, "An event needs a non-empty /to date.");
        if (toIndex <= fromIndex) {
            throw new IllegalCommandException("An event needs a /from date that comes before the /to date.");
        }
        String description = requireValue(details.substring(0, fromIndex), "An event needs a non-empty description.");
        String startDateTimeText = requireValue(details.substring(fromIndex + FROM_DELIMITER.length(), toIndex),
                "An event needs a non-empty /from date.");
        String endDateTimeText = requireValue(details.substring(toIndex + TO_DELIMITER.length()),
                "An event needs a non-empty /to date.");
        LocalDateTime startDateTime = DateTimeUtil.parse(startDateTimeText);
        LocalDateTime endDateTime = DateTimeUtil.parse(endDateTimeText);
        return new Event(description, startDateTime, endDateTime);
    }

    /**
     * Returns this event in the persistent storage format.
     *
     * @return The storage representation of this event.
     */
    @Override
    public String toDataFormat() {
        return String.format("E | %d | %s | %s | %s", this.isDone ? 1 : 0, this.description,
                DateTimeUtil.formatStorage(this.from), DateTimeUtil.formatStorage(this.to));
    }
}

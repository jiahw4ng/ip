package martin.task;

import java.time.LocalDateTime;

import martin.exception.IllegalCommandException;
import martin.util.DateTimeUtil;

/**
 * Represents a task that must be completed by a specified date or time.
 */
public class Deadline extends Task {
    protected final LocalDateTime by;
    public static final String BY_DELIMITER = "/by";

    /**
     * Constructs a {@code Deadline} task with a description and a deadline
     * date/time.
     *
     * @param description The description of the task.
     * @param deadline The date and time by which the task should be completed.
     */
    public Deadline(String description, LocalDateTime deadline) {
        super(description);
        this.by = deadline;
    }

    /**
     * Returns this deadline with its completion status and formatted due date.
     *
     * @return The display representation of this deadline.
     */
    @Override
    public String toString() {
        return String.format("[D]%s (by: %s)", super.toString(), DateTimeUtil.formatDisplay(this.by));
    }

    /**
     * Creates a {@code Deadline} from a command containing a {@code /by} delimiter.
     *
     * @param input The command string entered by the user.
     * @return The created {@code Deadline} task.
     * @throws IllegalCommandException If the description or date is missing or invalid.
     */
    public static Deadline createDeadline(String input) {
        String details = input.substring("deadline".length()).trim();
        int byIndex = requireIndex(details, BY_DELIMITER, "A deadline needs a non-empty /by date.");
        String description = requireValue(details.substring(0, byIndex), "A deadline needs a non-empty description.");
        String deadlineText = requireValue(details.substring(byIndex + BY_DELIMITER.length()),
                "A deadline needs a non-empty /by date.");
        LocalDateTime deadline = DateTimeUtil.parse(deadlineText);
        return new Deadline(description, deadline);
    }

    /**
     * Returns this deadline in the persistent storage format.
     *
     * @return The storage representation of this deadline.
     */
    @Override
    public String toDataFormat() {
        return String.format("D | %d | %s | %s", this.isDone ? 1 : 0, this.description,
                DateTimeUtil.formatStorage(this.by));
    }

}

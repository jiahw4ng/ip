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
     * @param desc the description of the task
     * @param by   the date and time by which the task should be completed
     */
    public Deadline(String desc, LocalDateTime by) {
        super(desc);
        this.by = by;
    }

    @Override
    public String toString() {
        return String.format("[D]%s (by: %s)", super.toString(), DateTimeUtil.formatDisplay(this.by));
    }

    /**
     * Creates a {@code Deadline} from a command containing a {@code /by} delimiter.
     *
     * @param input the command string entered by the user
     * @return the created {@code Deadline} task
     * @throws IllegalCommandException if the description or date is missing or
     *                                 invalid
     */
    public static Deadline createDeadline(String input) {
        String details = input.substring("deadline".length()).trim();
        int byIndex = requireIndex(details, BY_DELIMITER, "A deadline needs a non-empty /by date.");
        String description = requireValue(details.substring(0, byIndex), "A deadline needs a non-empty description.");
        String byString = requireValue(details.substring(byIndex + BY_DELIMITER.length()),
                "A deadline needs a non-empty /by date.");
        LocalDateTime by = DateTimeUtil.parse(byString);
        return new Deadline(description, by);
    }

    @Override
    public String toDataFormat() {
        return String.format("D | %d | %s | %s", this.isDone ? 1 : 0, this.description,
                DateTimeUtil.formatStorage(this.by));
    }

}

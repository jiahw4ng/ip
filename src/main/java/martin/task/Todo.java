package martin.task;

import martin.exception.IllegalCommandException;

/**
 * Represents a task without a date or time associated with it.
 */
public class Todo extends Task {

    /**
     * Constructs a {@code Todo} task with the specified description.
     *
     * @param description The description of the task.
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Returns this todo with its completion status and description.
     *
     * @return The display representation of this todo.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    /**
     * Creates a {@code Todo} task from a command input string.
     *
     * @param input The command string entered by the user.
     * @return The created {@code Todo} task.
     * @throws IllegalCommandException If the description is missing.
     */
    public static Todo createTodo(String input) {
        String details = input.substring("todo".length()).trim();
        String description = requireValue(details, "A todo needs a non-empty description.");
        return new Todo(description);
    }

    /**
     * Returns this todo in the persistent storage format.
     *
     * @return The storage representation of this todo.
     */
    @Override
    public String toDataFormat() {
        return String.format("T | %d | %s", this.isDone ? 1 : 0, this.description);
    }
}

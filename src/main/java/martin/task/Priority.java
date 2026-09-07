package martin.task;

import java.util.Locale;

import martin.exception.IllegalCommandException;

/** Represents the priority levels that can be assigned to a task. */
public enum Priority {
    HIGH("H"),
    MEDIUM("M"),
    LOW("L");

    private final String storageCode;

    /**
     * Constructs a priority with the specified storage code.
     *
     * @param storageCode The code used to represent this priority in storage.
     */
    Priority(String storageCode) {
        this.storageCode = storageCode;
    }

    /**
     * Returns the code used to represent this priority in storage.
     *
     * @return The storage code for this priority.
     */
    public String getStorageCode() {
        return this.storageCode;
    }

    /**
     * Creates a priority from a user-provided priority name.
     *
     * @param priorityName The priority name entered by the user.
     * @return The corresponding priority.
     * @throws IllegalCommandException If the name is not high, medium, or low.
     */
    public static Priority fromUserInput(String priorityName) {
        try {
            return valueOf(priorityName.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException exception) {
            throw new IllegalCommandException("Priority must be high, medium, or low.");
        }
    }

    /**
     * Creates a priority from a stored priority code.
     *
     * @param storageCode The priority code read from the storage file.
     * @return The corresponding priority.
     * @throws IllegalArgumentException If the storage code is unknown.
     */
    public static Priority fromStorageCode(String storageCode) {
        for (Priority priority : values()) {
            if (priority.storageCode.equals(storageCode)) {
                return priority;
            }
        }
        throw new IllegalArgumentException("Unknown priority in storage file: " + storageCode);
    }
}

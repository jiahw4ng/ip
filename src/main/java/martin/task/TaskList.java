package martin.task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents the list of tasks and encapsulates operations to manage and
 * manipulate tasks.
 */
public class TaskList {
    private final List<Task> tasks;

    /**
     * Constructs an empty {@code TaskList}.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructs a {@code TaskList} initialized with an existing list of tasks.
     *
     * @param tasks the initial list of tasks
     */
    public TaskList(List<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }

    /**
     * Adds a task to the list.
     *
     * @param task the task to add
     */
    public void add(Task task) {
        this.tasks.add(task);
    }

    /**
     * Removes the specified task from the list.
     *
     * @param task the task to remove
     * @return {@code true} if the task was found and removed, {@code false}
     *         otherwise
     */
    public boolean remove(Task task) {
        return this.tasks.remove(task);
    }

    /**
     * Removes and returns the task at the specified zero-based index.
     *
     * @param index the zero-based index of the task to remove
     * @return the removed task
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public Task remove(int index) {
        return this.tasks.remove(index);
    }

    /**
     * Retrieves the task at the specified zero-based index.
     *
     * @param index the zero-based index of the task
     * @return the task at the specified index
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public Task get(int index) {
        return this.tasks.get(index);
    }

    /**
     * Returns the task selected by a one-based user index, or {@code null} if the
     * index is invalid.
     *
     * @param oneBasedIndex the one-based index of the task
     * @return the selected {@code Task}, or {@code null} if invalid
     */
    public Task getByOneBasedIndex(int oneBasedIndex) {
        if (oneBasedIndex < 1 || oneBasedIndex > this.tasks.size()) {
            return null;
        }
        return this.tasks.get(oneBasedIndex - 1);
    }

    /**
     * Returns the total number of tasks in the list.
     *
     * @return the number of tasks
     */
    public int size() {
        return this.tasks.size();
    }

    /**
     * Returns whether the task list is empty.
     *
     * @return {@code true} if the task list is empty, {@code false} otherwise
     */
    public boolean isEmpty() {
        return this.tasks.isEmpty();
    }

    /**
     * Returns an unmodifiable view of all tasks in the list.
     *
     * @return an unmodifiable list of tasks
     */
    public List<Task> getAllTasks() {
        return Collections.unmodifiableList(this.tasks);
    }
}

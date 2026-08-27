package martin;

import martin.command.Command;
import martin.exception.IllegalCommandException;
import martin.storage.TasksStorage;
import martin.task.Task;
import martin.task.TaskList;
import martin.ui.Ui;

/**
 * Chatbot application that coordinates task management, storage, and user
 * interactions.
 */
public class Martin {
    private final TasksStorage storage;
    private final TaskList tasks;
    private final Ui ui;

    /**
     * Constructs a new {@code Martin} chatbot instance with the specified data file
     * path.
     *
     * @param filePath the file path used to load and persist task data
     */
    public Martin(String filePath) {
        this.ui = new Ui();
        this.storage = new TasksStorage(filePath);
        this.tasks = new TaskList(this.storage.load());
    }

    /**
     * Constructs a new {@code Martin} chatbot instance with the default data file
     * path.
     */
    public Martin() {
        this("./data/martin.txt");
    }

    /**
     * Starts and runs the main execution loop for the Martin chatbot.
     */
    public void run() {
        this.ui.showWelcome();

        boolean isRunning = true;
        while (isRunning) {
            String input = this.ui.readCommand();
            if (input == null) {
                break;
            }

            try {
                if (input.trim().isEmpty()) {
                    throw new IllegalCommandException("I'm sorry, I don't know what that means.");
                }
                Command command = Command.from(input);
                switch (command) {
                    case LIST -> this.ui.showTaskList(this.tasks);
                    case MARK -> handleMarkTask(input, true);
                    case UNMARK -> handleMarkTask(input, false);
                    case DELETE -> handleDeleteTask(input);
                    case TODO, DEADLINE, EVENT -> handleAddTask(input);
                    case BYE -> isRunning = false;
                }
            } catch (IllegalCommandException exception) {
                this.ui.showError(exception.getMessage());
            }
            this.ui.showLine();
        }
        this.ui.showGoodbye();
    }

    /**
     * Returns the task selected by a one-based command index, or {@code null} when
     * it is invalid.
     *
     * @param input the user input containing the task number
     * @return the selected {@code Task}, or {@code null} if the index is invalid
     */
    private Task findTaskFromInput(String input) {
        try {
            int taskNumber = Integer.parseInt(input.substring(input.indexOf(' ') + 1));
            return this.tasks.getByOneBasedIndex(taskNumber);
        } catch (NumberFormatException exception) {
            return null;
        }
    }

    /**
     * Deletes the specified task from the list based on user input.
     *
     * @param input the user input containing the task index to delete
     */
    private void handleDeleteTask(String input) {
        Task task = this.findTaskFromInput(input);
        if (task == null) {
            this.ui.showError("I can't find that task. Use a number shown by list.");
        } else {
            this.tasks.remove(task);
            this.storage.save(this.tasks);
            this.ui.showTaskDeleted(task, this.tasks.size());
        }
    }

    /**
     * Adds the task described by a valid task-creation command.
     *
     * @param input the task command input string
     */
    private void handleAddTask(String input) {
        Task newTask = Task.of(input);
        this.tasks.add(newTask);
        this.storage.save(this.tasks);
        this.ui.showTaskAdded(newTask, this.tasks.size());
    }

    /**
     * Marks or unmarks the task selected by the command's one-based index.
     *
     * @param input            the user input containing the task index
     * @param shouldMarkAsDone {@code true} to mark as done, {@code false} to mark
     *                         as not done
     */
    private void handleMarkTask(String input, boolean shouldMarkAsDone) {
        Task task = this.findTaskFromInput(input);
        if (task == null) {
            this.ui.showError("I can't find that task. Use a number shown by list.");
        } else if (shouldMarkAsDone) {
            task.markAsDone();
            this.storage.save(this.tasks);
            this.ui.showTaskMarked(task);
        } else {
            task.markAsNotDone();
            this.storage.save(this.tasks);
            this.ui.showTaskUnmarked(task);
        }
    }

}

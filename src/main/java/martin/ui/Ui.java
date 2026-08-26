package martin.ui;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import martin.task.Task;
import martin.task.TaskList;

/**
 * Handles all user interface interactions for the Martin chatbot, including
 * reading inputs, formatting outputs, and printing messages.
 */
public class Ui {
    private static final String GRAY = "\u001B[90m";
    private static final String RESET = "\u001B[0m";

    public static final String MARTIN_BANNER = """
            M   M   A   RRR  TTTTT I N   N
            MM MM  A A  R  R   T   I NN  N
            M M M AAAAA RRR    T   I N N N
            M   M A   A R R    T   I N  NN
            M   M A   A R  R   T   I N   N
            """;

    public static final String HORIZ_LINE = "_____________________________________________________";
    public static final String MARTIN_GREETING = "Hello! I'm Martin.\nWhat can I do for you?";
    public static final String MARTIN_GOODBYE = "Bye. Hope to see you again soon!";

    private final Scanner scanner;

    /**
     * Constructs a new {@code Ui} instance reading from standard input.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Prints the horizontal divider line.
     */
    public void showLine() {
        System.out.println(HORIZ_LINE);
    }

    /**
     * Displays the initial greeting message and banner.
     */
    public void showWelcome() {
        this.showLine();
        System.out.println(MARTIN_BANNER);
        System.out.println(MARTIN_GREETING);
        this.showLine();
    }

    /**
     * Displays the goodbye message.
     */
    public void showGoodbye() {
        System.out.println(MARTIN_GOODBYE);
        this.showLine();
    }

    /**
     * Reads a line of command input from the user.
     * Returns {@code null} if the input stream has reached EOF or was interrupted.
     *
     * @return the raw command string entered by the user, or {@code null} on EOF
     */
    public String readCommand() {
        System.out.print("> " + GRAY);
        if (!this.scanner.hasNextLine()) {
            System.out.print(RESET);
            System.out.println();
            return null;
        }

        String input;
        try {
            input = this.scanner.nextLine();
        } catch (NoSuchElementException | IllegalStateException exception) {
            System.out.print(RESET);
            System.out.println();
            return null;
        }

        System.out.print(RESET);
        this.showLine();
        return input;
    }

    /**
     * Displays an error message to the user.
     *
     * @param message the error message to display
     */
    public void showError(String message) {
        System.out.println(message);
    }

    /**
     * Displays the list of tasks from a {@link TaskList}.
     *
     * @param taskList the task list to display
     */
    public void showTaskList(TaskList taskList) {
        this.showTaskList(taskList.getAllTasks());
    }

    /**
     * Displays the list of tasks.
     *
     * @param tasks the list of tasks to display
     */
    public void showTaskList(List<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("You have no tasks in your list!");
            return;
        }

        System.out.println("Here are the tasks in your list:");
        Task.printTasks(tasks);
    }

    /**
     * Displays confirmation that a task has been added.
     *
     * @param task the added task
     * @param totalTasks the new total number of tasks
     */
    public void showTaskAdded(Task task, int totalTasks) {
        System.out.println("Got it. I've added this task:");
        System.out.println(task);
        System.out.printf("Now you have %d tasks in the list.%n", totalTasks);
    }

    /**
     * Displays confirmation that a task has been deleted.
     *
     * @param task the removed task
     * @param totalTasks the remaining number of tasks
     */
    public void showTaskDeleted(Task task, int totalTasks) {
        System.out.println("Noted. I've removed this task:");
        System.out.println(task);
        System.out.printf("Now you have %d tasks in the list.%n", totalTasks);
    }

    /**
     * Displays confirmation that a task was marked as completed.
     *
     * @param task the marked task
     */
    public void showTaskMarked(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(task);
    }

    /**
     * Displays confirmation that a task was marked as not completed.
     *
     * @param task the unmarked task
     */
    public void showTaskUnmarked(Task task) {
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println(task);
    }
}

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Main class for the Martin task management chatbot application.
 */
public class Martin {
    // ANSI escape codes used to colour the echoed user input in supported
    // terminals.
    private static final String GRAY = "\u001B[90m";
    private static final String RESET = "\u001B[0m";

    public static final String BANNER = """
            M   M   A   RRR  TTTTT I N   N
            MM MM  A A  R  R   T   I NN  N
            M M M AAAAA RRR    T   I N N N
            M   M A   A R R    T   I N  NN
            M   M A   A R  R   T   I N   N
            """;

    public static final String HORIZ_LINE = "_____________________________________________________";
    public static final String GREETING = "Hello! I'm Martin.\nWhat can I do for you?";
    public static final String GOODBYE = "Bye. Hope to see you again soon!";

    /**
     * Entry point of the Martin application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        start();

        try (Scanner scanner = new Scanner(System.in)) {
            List<Task> tasks = new ArrayList<>();
            boolean isRunning = true;
            while (isRunning) {
                System.out.print("> " + GRAY);
                String input = scanner.nextLine();
                System.out.print(RESET);
                System.out.println(HORIZ_LINE);

                try {
                    if (input.trim().isEmpty()) {
                        throw new IllegalCommandException("I'm sorry, I don't know what that means.");
                    }
                    Command command = Command.from(input);
                    switch (command) {
                        case LIST -> printList(tasks);
                        case MARK -> handleMarkTask(tasks, input, true);
                        case UNMARK -> handleMarkTask(tasks, input, false);
                        case DELETE -> handleDeleteTask(tasks, input);
                        case TODO, DEADLINE, EVENT -> handleAddTask(tasks, input);
                        case BYE -> isRunning = false;
                    }
                } catch (IllegalCommandException exception) {
                    System.out.println(exception.getMessage());
                }
                System.out.println(HORIZ_LINE);
            }
        }
        goodbye();
    }

    /**
     * Prints the initial greeting message and banner.
     */
    private static void start() {
        System.out.println(HORIZ_LINE);
        System.out.println(BANNER);
        System.out.println(GREETING);
        System.out.println(HORIZ_LINE);
    }

    /**
     * Prints the goodbye message.
     */
    private static void goodbye() {
        System.out.println(GOODBYE);
        System.out.println(HORIZ_LINE);
    }

    /**
     * Prints all tasks currently in the task list.
     *
     * @param tasks the list of tasks to display
     */
    private static void printList(List<Task> tasks) {

        if (tasks.isEmpty()) {
            System.out.println("You have no tasks in your list!");
            return;
        }

        System.out.println("Here are the tasks in your list:");
        Task.printTasks(tasks);
    }

    /**
     * Returns the task selected by a one-based command index, or {@code null} when
     * it is invalid.
     *
     * @param tasks the list of tasks to search
     * @param input the user input containing the task number
     * @return the selected {@code Task}, or {@code null} if the index is invalid
     */
    private static Task findTask(List<Task> tasks, String input) {
        try {
            int taskNumber = Integer.parseInt(input.substring(input.indexOf(' ') + 1));
            if (taskNumber < 1 || taskNumber > tasks.size()) {
                return null;
            }
            return tasks.get(taskNumber - 1);
        } catch (NumberFormatException exception) {
            return null;
        }
    }

    /**
     * Deletes the specified task from the list based on user input.
     *
     * @param tasks the list of tasks to remove from
     * @param input the user input containing the task index to delete
     */
    private static void handleDeleteTask(List<Task> tasks, String input) {
        Task task = findTask(tasks, input);
        if (task == null) {
            System.out.println("I can't find that task. Use a number shown by list.");
        } else {
            tasks.remove(task);
            System.out.println("Noted. I've removed this task:");
            System.out.println(task);
            System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        }
    }

    /**
     * Adds the task described by a valid task-creation command.
     *
     * @param tasks the list of tasks to add to
     * @param input the task command input string
     */
    private static void handleAddTask(List<Task> tasks, String input) {
        Task newTask = Task.of(input);
        tasks.add(newTask);
        System.out.println("Got it. I've added this task:");
        System.out.println(newTask);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
    }

    /**
     * Marks or unmarks the task selected by the command's one-based index.
     *
     * @param tasks            the list of tasks
     * @param input            the user input containing the task index
     * @param shouldMarkAsDone {@code true} to mark as done, {@code false} to mark
     *                         as not done
     */
    private static void handleMarkTask(List<Task> tasks, String input, boolean shouldMarkAsDone) {
        Task task = findTask(tasks, input);
        if (task == null) {
            System.out.println("I can't find that task. Use a number shown by list.");
        } else if (shouldMarkAsDone) {
            handleMarkTaskAsDone(task);
        } else {
            handleMarkTaskAsNotDone(task);
        }
    }

    /**
     * Marks the specified task as done and prints a confirmation message.
     *
     * @param task the task to mark as done
     */
    private static void handleMarkTaskAsDone(Task task) {
        task.markAsDone();
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(task);
    }

    /**
     * Marks the specified task as not done and prints a confirmation message.
     *
     * @param task the task to mark as not done
     */
    private static void handleMarkTaskAsNotDone(Task task) {
        task.markAsNotDone();
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println(task);
    }
}

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Chatbot application that manages and tracks user tasks.
 */
public class Martin {
    // ANSI escape codes used to colour the echoed user input in supported
    // terminals.
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

    private final List<Task> tasks;

    /**
     * Constructs a new {@code Martin} chatbot instance with an empty task list.
     */
    public Martin() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Starts and runs the main execution loop for the Martin chatbot.
     */
    public void run() {
        this.start();

        try (Scanner scanner = new Scanner(System.in)) {
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
                        case LIST -> printList();
                        case MARK -> handleMarkTask(input, true);
                        case UNMARK -> handleMarkTask(input, false);
                        case DELETE -> handleDeleteTask(input);
                        case TODO, DEADLINE, EVENT -> handleAddTask(input);
                        case BYE -> isRunning = false;
                    }
                } catch (IllegalCommandException | IllegalArgumentException exception) {
                    System.out.println(exception.getMessage());
                }
                System.out.println(HORIZ_LINE);
            }
        }
        this.goodbye();
    }

    /**
     * Prints the initial greeting message and banner.
     */
    private void start() {
        System.out.println(HORIZ_LINE);
        System.out.println(MARTIN_BANNER);
        System.out.println(MARTIN_GREETING);
        System.out.println(HORIZ_LINE);
    }

    /**
     * Prints the goodbye message.
     */
    private void goodbye() {
        System.out.println(MARTIN_GOODBYE);
        System.out.println(HORIZ_LINE);
    }

    /**
     * Prints all tasks currently in the task list.
     */
    private void printList() {
        if (this.tasks.isEmpty()) {
            System.out.println("You have no tasks in your list!");
            return;
        }

        System.out.println("Here are the tasks in your list:");
        Task.printTasks(this.tasks);
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
            if (taskNumber < 1 || taskNumber > this.tasks.size()) {
                return null;
            }
            return this.tasks.get(taskNumber - 1);
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
            System.out.println("I can't find that task. Use a number shown by list.");
        } else {
            this.tasks.remove(task);
            System.out.println("Noted. I've removed this task:");
            System.out.println(task);
            System.out.printf("Now you have %d tasks in the list.%n", this.tasks.size());
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
        System.out.println("Got it. I've added this task:");
        System.out.println(newTask);
        System.out.printf("Now you have %d tasks in the list.%n", this.tasks.size());
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
            System.out.println("I can't find that task. Use a number shown by list.");
        } else if (shouldMarkAsDone) {
            task.markAsDone();
            System.out.println("Nice! I've marked this task as done:");
        } else {
            task.markAsNotDone();
            System.out.println("OK, I've marked this task as not done yet:");
        }
        System.out.println(task);
    }

}

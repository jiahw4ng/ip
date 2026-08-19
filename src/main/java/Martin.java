import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

    public static void main(String[] args) {
        start();

        try (Scanner scanner = new Scanner(System.in)) {
            List<Task> tasks = new ArrayList<>();
            String input = "";
            while (!input.equals("bye")) {
                System.out.print("> " + GRAY);
                input = scanner.nextLine();
                System.out.print(RESET);
                System.out.println(HORIZ_LINE);

                if (input.equals("list")) {
                    // print the list of tasks, or a message if there are none
                    printList(tasks);
                } else if (input.startsWith("mark ")) {
                    // mark the task as done, or print an error message if the task number is invalid
                    Task task = findTask(tasks, input);
                    if (task == null) {
                        System.out.println("I can't find that task. Use a number shown by list.");
                    } else {
                        handleMarkTaskAsDone(task);
                    }
                } else if (input.startsWith("unmark ")) {
                    // unmark the task as done, or print an error message if the task number is invalid
                    Task task = findTask(tasks, input);
                    if (task == null) {
                        System.out.println("I can't find that task. Use a number shown by list.");
                    } else {
                        handleMarkTaskAsNotDone(task);
                    }
                } else if (!input.equals("bye")) {
                    // create a new task, or print an error message if the command is invalid
                    try {
                        Task newTask = Task.of(input);
                        tasks.add(newTask);
                        System.out.println("Got it. I've added this task:");
                        System.out.println(newTask);
                        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                    } catch (IllegalArgumentException exception) {
                        System.out.println(exception.getMessage());
                    }
                }
                System.out.println(HORIZ_LINE);
            }
        }
        goodbye();
    }

    private static void start() {
        System.out.println(HORIZ_LINE);
        System.out.println(BANNER);
        System.out.println(GREETING);
        System.out.println(HORIZ_LINE);
    }

    private static void goodbye() {
        System.out.println(GOODBYE);
        System.out.println(HORIZ_LINE);
    }

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

    private static void handleMarkTaskAsDone(Task task) {
        task.markAsDone();
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(task);
    }

    private static void handleMarkTaskAsNotDone(Task task) {
        task.markAsNotDone();
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println(task);
    }
}

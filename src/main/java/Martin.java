import java.util.ArrayList;
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
            ArrayList<String> tasks = new ArrayList<>();
            String input = "";
            while (!input.equals("bye")) {
                System.out.print("> " + GRAY);
                input = scanner.nextLine();
                System.out.print(RESET);
                System.out.println(HORIZ_LINE);

                if (input.equals("list")) {
                    printTasks(tasks);
                    System.out.println(HORIZ_LINE);
                } else if (!input.equals("bye")) {
                    tasks.add(input);
                    System.out.println("added: " + input);
                    System.out.println(HORIZ_LINE);
                }
            }
        }
        goodbye();
    }

    public static void start() {
        System.out.println(HORIZ_LINE);
        System.out.println(BANNER);
        System.out.println(GREETING);
        System.out.println(HORIZ_LINE);
    }

    public static void goodbye() {
        System.out.println(GOODBYE);
        System.out.println(HORIZ_LINE);
    }

    /** Prints the tasks in the order that the user entered them. */
    private static void printTasks(ArrayList<String> tasks) {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }
}

import java.util.Scanner;

public class Martin {
    // ANSI escape codes used to colour the echoed user input in supported
    // terminals.
    private static final String GRAY = "\u001B[90m";
    private static final String RESET = "\u001B[0m";

    public static final String banner = "M   M   A   RRR  TTTTT I N   N\n"
            + "MM MM  A A  R  R   T   I NN  N\n"
            + "M M M AAAAA RRR    T   I N N N\n"
            + "M   M A   A R R    T   I N  NN\n"
            + "M   M A   A R  R   T   I N   N\n";

    public static final String horizontalLine = "_____________________________________________________";
    public static final String greeting = "Hello! I'm Martin.\nWhat can I do for you?";
    public static final String bye = "Bye. Hope to see you again soon!";

    public static void main(String[] args) {
        start();

        Scanner scanner = new Scanner(System.in);
        String input = "";
        while (!input.equals("bye")) {
            System.out.print("> ");
            input = scanner.nextLine();
            System.out.println(horizontalLine);

            if (!input.equals("bye")) {
                System.out.println(GRAY + input + RESET);
                System.out.println(horizontalLine);
            }
        }

        goodbye();
    }

    public static void start() {
        System.out.println(horizontalLine);
        System.out.println(banner);
        System.out.println(greeting);
        System.out.println(horizontalLine);
    }

    public static void goodbye() {
        System.out.println(GRAY + bye + RESET);
        System.out.println(horizontalLine);
    }
}

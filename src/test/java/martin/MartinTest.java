package martin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class MartinTest {
    @Test
    public void dummyTest() {
        assertEquals(2, 2);
    }

    @Test
    public void anotherDummyTest() {
        assertEquals(4, 4);
    }

    @Test
    public void findCommand_matchingTasks_displaysMatches(@TempDir Path tempDir) {
        String input = String.join(System.lineSeparator(),
                "todo read book",
                "deadline return book /by 2026/08/26 1830",
                "find BOOK",
                "bye",
                "");
        java.io.InputStream originalInput = System.in;
        PrintStream originalOutput = System.out;
        ByteArrayOutputStream capturedOutput = new ByteArrayOutputStream();

        try {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(capturedOutput, true, StandardCharsets.UTF_8));
            new Martin(tempDir.resolve("martin.txt").toString()).run();
        } finally {
            System.setIn(originalInput);
            System.setOut(originalOutput);
        }

        String output = capturedOutput.toString(StandardCharsets.UTF_8);
        assertTrue(output.contains("Here are the matching tasks in your list:"));
        assertTrue(output.contains("1. [T][ ] read book"));
        assertTrue(output.contains("2. [D][ ] return book"));
    }

    @Test
    public void findCommand_missingKeyword_displaysError(@TempDir Path tempDir) {
        String input = String.join(System.lineSeparator(), "find", "bye", "");
        java.io.InputStream originalInput = System.in;
        PrintStream originalOutput = System.out;
        ByteArrayOutputStream capturedOutput = new ByteArrayOutputStream();

        try {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(capturedOutput, true, StandardCharsets.UTF_8));
            new Martin(tempDir.resolve("martin.txt").toString()).run();
        } finally {
            System.setIn(originalInput);
            System.setOut(originalOutput);
        }

        assertTrue(capturedOutput.toString(StandardCharsets.UTF_8)
                .contains("A find command needs a non-empty keyword."));
    }
}

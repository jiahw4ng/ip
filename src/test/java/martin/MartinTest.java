package martin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import martin.core.Martin;

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
        assertTrue(output.contains("The following tasks match your request:"));
        assertTrue(output.contains("1. [T][ ][LOW] read book"));
        assertTrue(output.contains("2. [D][ ][LOW] return book"));
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
                .contains("A find command requires a non-empty keyword."));
    }

    @Test
    public void executeCommand_invalidCommand_returnsError(@TempDir Path tempDir) {
        Martin martin = new Martin(tempDir.resolve("martin.txt").toString());

        assertEquals("I regret that I do not recognise that command.", martin.executeCommand("unknown"));
    }

    @Test
    public void executeCommand_bye_stopsMartinAndReturnsGoodbye(@TempDir Path tempDir) {
        Martin martin = new Martin(tempDir.resolve("martin.txt").toString());

        assertEquals("I shall take my leave. Until we meet again.", martin.executeCommand("bye"));
        assertFalse(martin.isRunning());
    }

    @Test
    public void run_endOfInput_stopsMartinAndShowsGoodbye(@TempDir Path tempDir) {
        java.io.InputStream originalInput = System.in;
        PrintStream originalOutput = System.out;
        ByteArrayOutputStream capturedOutput = new ByteArrayOutputStream();

        try {
            System.setIn(new ByteArrayInputStream(new byte[0]));
            System.setOut(new PrintStream(capturedOutput, true, StandardCharsets.UTF_8));
            new Martin(tempDir.resolve("martin.txt").toString()).run();
        } finally {
            System.setIn(originalInput);
            System.setOut(originalOutput);
        }

        String output = capturedOutput.toString(StandardCharsets.UTF_8);
        assertTrue(output.contains("I shall take my leave. Until we meet again."));
    }

    @Test
    public void executeCommand_todoWithPriority_displaysPriority(@TempDir Path tempDir) {
        Martin martin = new Martin(tempDir.resolve("martin.txt").toString());

        String response = martin.executeCommand("todo submit report /p high");

        assertTrue(response.contains("[T][ ][HIGH] submit report"));
    }
}

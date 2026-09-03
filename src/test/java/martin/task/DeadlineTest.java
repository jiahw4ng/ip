package martin.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import martin.exception.IllegalCommandException;

/**
 * Unit tests for {@link Deadline}.
 */
public class DeadlineTest {

    @Test
    public void createDeadline_validCommand_success() {
        Deadline deadline = Deadline.deadlineFromInputString("deadline return book /by 2026/08/26 1830");
        assertEquals("[D][ ] return book (by: Aug 26 2026, 18:30)", deadline.toString());
        assertEquals("D | 0 | return book | 2026/08/26 1830", deadline.toDataFormat());
    }

    @Test
    public void createDeadline_missingByDelimiter_throwsIllegalCommandException() {
        assertThrows(IllegalCommandException.class, () ->
                Deadline.deadlineFromInputString("deadline return book 2026/08/26 1830"));
    }

    @Test
    public void createDeadline_emptyDescription_throwsIllegalCommandException() {
        assertThrows(IllegalCommandException.class, () ->
                Deadline.deadlineFromInputString("deadline /by 2026/08/26 1830"));
    }
}

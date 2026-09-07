package martin.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/** Unit tests for event priority parsing. */
public class EventTest {

    @Test
    public void parseEventFromInputString_priorityParameter_createsEventWithPriority() {
        Event event = Event.parseEventFromInputString(
                "event project meeting /from 2026/08/26 1400 /to 2026/08/26 1500 /p high");

        assertEquals(Priority.HIGH, event.getPriority());
        assertEquals("[E][ ][HIGH] project meeting (from: Aug 26 2026, 14:00 to: Aug 26 2026, 15:00)",
                event.toString());
    }
}

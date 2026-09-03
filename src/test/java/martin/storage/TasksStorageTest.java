package martin.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import martin.exception.StorageException;
import martin.task.Task;
import martin.task.Todo;

/**
 * Tests loading and saving tasks through local storage.
 */
public class TasksStorageTest {

    @Test
    public void load_corruptedLine_skipsLineAndLoadsValidTasks(@TempDir Path tempDir) throws Exception {
        Path filePath = tempDir.resolve("martin.txt");
        Files.write(filePath, List.of(
                "T | 0 | read book",
                "corrupted task line"));

        List<Task> tasks = new TasksStorage(filePath.toString()).load();

        assertEquals(1, tasks.size());
        assertEquals("[T][ ] read book", tasks.get(0).toString());
    }

    @Test
    public void load_directoryPath_throwsStorageException(@TempDir Path tempDir) {
        StorageException exception = assertThrows(StorageException.class, () ->
                new TasksStorage(tempDir.toString()).load());

        assertEquals("Unable to load tasks from " + tempDir + ".", exception.getMessage());
        assertInstanceOf(IOException.class, exception.getCause());
    }

    @Test
    public void save_directoryPath_throwsStorageException(@TempDir Path tempDir) {
        StorageException exception = assertThrows(StorageException.class, () ->
                new TasksStorage(tempDir.toString()).save(List.of(new Todo("read book"))));

        assertEquals("Unable to save tasks to " + tempDir + ".", exception.getMessage());
        assertInstanceOf(IOException.class, exception.getCause());
    }
}

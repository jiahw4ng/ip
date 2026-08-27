package martin.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import martin.exception.IllegalCommandException;
import martin.task.Task;
import martin.task.TaskList;

/**
 * Handles the loading and saving of tasks to and from the local storage file.
 */
public class TasksStorage {
    private final Path filePath;

    /**
     * Constructs a {@code TasksStorage} object with the specified file path.
     *
     * @param filePath The relative or absolute path to the data file.
     */
    public TasksStorage(String filePath) {
        this.filePath = Paths.get(filePath);
    }

    /**
     * Loads tasks from the data file. If the file or parent directory does not
     * exist,
     * it creates them and returns an empty list.
     *
     * @return The list of loaded tasks.
     */
    public List<Task> load() {
        List<Task> tasks = new ArrayList<>();
        try {
            if (!Files.exists(this.filePath)) {
                if (this.filePath.getParent() != null) {
                    Files.createDirectories(this.filePath.getParent());
                }
                Files.createFile(this.filePath);
                return tasks;
            }

            List<String> lines = Files.readAllLines(this.filePath);
            for (String line : lines) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                try {
                    tasks.add(Task.fromDataFormat(line));
                } catch (IllegalCommandException | IllegalArgumentException exception) {
                    System.out.println("Skipping corrupted or outdated task line: " + line);
                }
            }
        } catch (IOException exception) {
            System.out.println("Error loading tasks from file: " + exception.getMessage());
        }
        if (!tasks.isEmpty()) {
            System.out.println("Loaded " + tasks.size() + " task(s) from the data file!");
        }
        return tasks;
    }

    /**
     * Saves the given task list to the data file.
     *
     * @param taskList The {@link TaskList} to save.
     */
    public void save(TaskList taskList) {
        this.save(taskList.getAllTasks());
    }

    /**
     * Saves the given list of tasks to the data file.
     *
     * @param tasks The list of tasks to save.
     */
    public void save(List<Task> tasks) {
        try {
            if (this.filePath.getParent() != null && !Files.exists(this.filePath.getParent())) {
                Files.createDirectories(this.filePath.getParent());
            }

            List<String> lines = new ArrayList<>();
            for (Task task : tasks) {
                lines.add(task.toDataFormat());
            }
            Files.write(this.filePath, lines);
        } catch (IOException exception) {
            System.out.println("Error saving tasks to file: " + exception.getMessage());
        }
    }
}

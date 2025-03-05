package mimon;

import tasks.Task;
import tasks.Deadline;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles loading and saving of tasks to a file.
 */
public class Storage {
    private final String filePath;

    /**
     * Constructs a Storage object with the specified file path.
     *
     * @param filePath The file path where tasks are stored.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the storage file.
     *
     * @return A list of tasks loaded from the file.
     * @throws MimonException If there is an error reading the file.
     */
    public ArrayList<Task> load() throws MimonException {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            return tasks;
        }

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                try {
                    Task task = Task.fromFileFormat(line);
                    if (task != null) {
                        tasks.add(task);
                    }
                } catch (Deadline.DeadlineParseException e) {
                    // Log the error or handle it as needed
                    // For now, we'll skip the task that can't be parsed
                    System.err.println("Skipping invalid task: " + line + " - " + e.getMessage());
                }
            }
            return tasks;
        } catch (IOException e) {
            throw new MimonException("Error loading tasks from file: " + e.getMessage());
        }
    }
    /**
     * Saves the list of tasks to the storage file.
     *
     * @param tasks The list of tasks to be saved.
     * @throws MimonException If there is an error writing to the file.
     */
    public void saveTasks(ArrayList<Task> tasks) throws MimonException {
        File file = new File(filePath);

        try {
            // Ensure directory exists
            File parentDir = file.getParentFile();
            if (parentDir != null) {
                parentDir.mkdirs();
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            for (Task task : tasks) {
                String taskLine = task.toFileFormat();
                writer.write(taskLine);
                writer.newLine();
            }

            writer.close();
        } catch (IOException e) {
            throw new MimonException("Error saving tasks to file: " + e.getMessage());
        }
    }
}
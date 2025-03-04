package mimon;

import tasks.Task;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public ArrayList<Task> load() throws MimonException {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            return tasks;
        }

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                Task task = Task.fromFileFormat(line);
                if (task != null) {
                    tasks.add(task);
                }
            }
            return tasks;
        } catch (IOException e) {
            throw new MimonException("Error loading tasks from file: " + e.getMessage());
        }
    }

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


package mimon;
import tasks.Task;
import tasks.Todo;
import tasks.Deadline;
import tasks.Event;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Mimon {
    private static final String FILE_PATH = "./data/mimon.txt";
    public static final String DASHED_LINE = "____________________________________________________________";
    private static ArrayList<Task> tasks = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        loadTasksFromFile();
        printWelcomeMessage();

        while (true) {
            String userInput = scanner.nextLine().trim();

            if (userInput.equalsIgnoreCase("bye")) {
                printExitMessage();
                break;
            }

            try {
                processUserCommand(userInput);
                saveTasksToFile();
            } catch (MimonException e) {
                System.out.println(DASHED_LINE);
                System.out.println("Hold on! " + e.getMessage());
                System.out.println(DASHED_LINE);
            }
        }
    }

    private static void processUserCommand(String userInput) throws MimonException {
        if (userInput.equalsIgnoreCase("list")) {
            listTasks();
        } else if (userInput.startsWith("mark ")) {
            markTask(userInput, true);
        } else if (userInput.startsWith("unmark ")) {
            markTask(userInput, false);
        } else if (userInput.startsWith("delete ")) {
            deleteTask(userInput);
        } else if (userInput.startsWith("todo")) {
            if (userInput.trim().equals("todo")) {
                throw new MimonException("The description of a todo must have some information.");
            }
            addTodoTask(userInput.substring(5).trim());
        } else if (userInput.startsWith("deadline")) {
            if (userInput.trim().equals("deadline")) {
                throw new MimonException("The description of a deadline should be meaningful.");
            }
            addDeadlineTask(userInput.substring(9).trim());
        } else if (userInput.startsWith("event")) {
            if (userInput.trim().equals("event")) {
                throw new MimonException("The description of an event should include the date!.");
            }
            addEventTask(userInput.substring(6).trim());
        } else {
            throw new MimonException("I don't recognize that command. Please enter a valid one.");
        }
    }

    private static void printWelcomeMessage() {
        System.out.println(DASHED_LINE);
        System.out.println("Hello! I'm Mimon");
        System.out.println("What can I do for you?");
        System.out.println(DASHED_LINE);
    }

    private static void printExitMessage() {
        System.out.println(DASHED_LINE);
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(DASHED_LINE);
    }

    private static void listTasks() {
        System.out.println(DASHED_LINE);
        System.out.println("Here are the tasks in your list:");
        if (tasks.isEmpty()) {
            System.out.println("No tasks added yet.");
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ". " + tasks.get(i));
            }
        }
        System.out.println(DASHED_LINE);
    }

    private static void markTask(String userInput, boolean isDone) throws MimonException {
        try {
            int taskIndex = Integer.parseInt(userInput.split(" ")[1]) - 1;
            if (taskIndex >= 0 && taskIndex < tasks.size()) {
                if (isDone) {
                    tasks.get(taskIndex).markAsDone();
                    System.out.println(DASHED_LINE);
                    System.out.println("Nice! I've marked this task as done:");
                } else {
                    tasks.get(taskIndex).markAsNotDone();
                    System.out.println(DASHED_LINE);
                    System.out.println("OK, I've marked this task as not done yet:");
                }
                System.out.println("  " + tasks.get(taskIndex));
                System.out.println(DASHED_LINE);
            } else {
                throw new MimonException("Invalid task number. Please enter a valid task number to mark or unmark.");
            }
        } catch (NumberFormatException e) {
            throw new MimonException("Invalid command format. Use: mark/unmark <task_number>");
        }
    }

    private static void deleteTask(String userInput) throws MimonException {
        try {
            int taskIndex = Integer.parseInt(userInput.split(" ")[1]) - 1;
            if (taskIndex >= 0 && taskIndex < tasks.size()) {
                Task removedTask = tasks.remove(taskIndex);
                System.out.println(DASHED_LINE);
                System.out.println("Noted. I've removed this task:");
                System.out.println("  " + removedTask);
                System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                System.out.println(DASHED_LINE);
            } else {
                throw new MimonException("Invalid task number. Please enter a valid task number to delete.");
            }
        } catch (NumberFormatException e) {
            throw new MimonException("Invalid command format. Use: delete <task_number>");
        }
    }

    private static void addTodoTask(String description) {
        addTask(new Todo(description));
    }

    private static void addDeadlineTask(String input) throws MimonException {
        String[] parts = input.split(" /by ", 2);
        if (parts.length < 2) {
            throw new MimonException("Invalid deadline format. Use: deadline description /by DATE");
        }
        addTask(new Deadline(parts[0], parts[1]));
    }

    private static void addEventTask(String input) throws MimonException {
        String[] parts = input.split(" /from | /to ", 3);
        if (parts.length < 3) {
            throw new MimonException("Invalid event format. Use: event description /from START /to END");
        }
        addTask(new Event(parts[0], parts[1], parts[2]));
    }

    private static void addTask(Task task) {
        tasks.add(task);
        System.out.println(DASHED_LINE);
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        System.out.println(DASHED_LINE);
    }

    private static void saveTasksToFile() {
        File file = new File(FILE_PATH);

        try {
            // Ensure directory exists
            // file.getParentFile().mkdirs();

            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            for (Task task : tasks) {
                String taskLine = task.toFileFormat();
                System.out.println("Saving: " + taskLine); // Debug print
                writer.write(taskLine);
                writer.newLine();
            }

            writer.close();
            System.out.println("Tasks successfully saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving tasks to file: " + e.getMessage());
        }
    }

    private static void loadTasksFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return;
        }
        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                Task task = Task.fromFileFormat(line);
                if (task != null) {
                    tasks.add(task);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading tasks from file.");
        }
    }
}

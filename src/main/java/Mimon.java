import java.util.Scanner;

public class Mimon {
    private static final int MAX_TASKS = 100;
    public static final String DASHED_LINE = "____________________________________________________________";
    private static Task[] tasks = new Task[MAX_TASKS];
    private static int taskCount = 0;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        printWelcomeMessage();

        while (true) {
            String userInput = scanner.nextLine().trim();

            if (userInput.equalsIgnoreCase("bye")) {
                printExitMessage();
                break;
            }

            processUserCommand(userInput);
        }
    }

    /**
     * Processes user input and executes corresponding commands.
     */
    private static void processUserCommand(String userInput) {
        if (userInput.equalsIgnoreCase("list")) {
            listTasks();
        } else if (userInput.startsWith("mark ")) {
            markTask(userInput, true);
        } else if (userInput.startsWith("unmark ")) {
            markTask(userInput, false);
        } else if (userInput.startsWith("todo ")) {
            addTodoTask(userInput.substring(5));
        } else if (userInput.startsWith("deadline ")) {
            addDeadlineTask(userInput.substring(9));
        } else if (userInput.startsWith("event ")) {
            addEventTask(userInput.substring(6));
        } else {
            System.out.println("Invalid command. Try again.");
        }
    }

    /**
     * Prints the welcome message when the application starts.
     */
    private static void printWelcomeMessage() {
        System.out.println(DASHED_LINE);
        System.out.println("Hello! I'm Mimon");
        System.out.println("What can I do for you?");
        System.out.println(DASHED_LINE);
    }

    /**
     * Prints the exit message when the user quits the application.
     */
    private static void printExitMessage() {
        System.out.println(DASHED_LINE);
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(DASHED_LINE);
    }

    /**
     * Lists all tasks stored in the task array.
     */
    private static void listTasks() {
        System.out.println(DASHED_LINE);
        System.out.println("Here are the tasks in your list:");
        if (taskCount == 0) {
            System.out.println("No tasks added yet.");
        } else {
            for (int i = 0; i < taskCount; i++) {
                System.out.println((i + 1) + ". " + tasks[i]);
            }
        }
        System.out.println(DASHED_LINE);
    }

    /**
     * Marks or unmarks a task as done or not done.
     * @param userInput The user command specifying which task to mark/unmark.
     * @param isDone Boolean flag indicating whether to mark or unmark the task.
     */
    private static void markTask(String userInput, boolean isDone) {
        try {
            int taskIndex = Integer.parseInt(userInput.split(" ")[1]) - 1;
            if (taskIndex >= 0 && taskIndex < taskCount) {
                if (isDone) {
                    tasks[taskIndex].markAsDone();
                    System.out.println(DASHED_LINE);
                    System.out.println("Nice! I've marked this task as done:");
                } else {
                    tasks[taskIndex].markAsNotDone();
                    System.out.println(DASHED_LINE);
                    System.out.println("OK, I've marked this task as not done yet:");
                }
                System.out.println("  " + tasks[taskIndex]);
                System.out.println(DASHED_LINE);
            } else {
                System.out.println("Invalid task number.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid command format. Use: mark/unmark <task_number>");
        }
    }

    /**
     * Adds a new To-Do task to the task list.
     * @param description The description of the to-do task.
     */
    private static void addTodoTask(String description) {
        addTask(new Todo(description));
    }

    /**
     * Adds a new Deadline task to the task list.
     * @param input The user input containing the task description and due date.
     */
    private static void addDeadlineTask(String input) {
        String[] parts = input.split(" /by ", 2);
        if (parts.length < 2) {
            System.out.println("Invalid deadline format. Use: deadline description /by DATE");
            return;
        }
        addTask(new Deadline(parts[0], parts[1]));
    }

    /**
     * Adds a new Event task to the task list.
     * @param input The user input containing the event description, start, and end times.
     */
    private static void addEventTask(String input) {
        String[] parts = input.split(" /from | /to ", 3);
        if (parts.length < 3) {
            System.out.println("Invalid event format. Use: event description /from START /to END");
            return;
        }
        addTask(new Event(parts[0], parts[1], parts[2]));
    }

    /**
     * Adds a new task to the task list.
     * @param task The task object to be added.
     */
    private static void addTask(Task task) {
        if (taskCount >= MAX_TASKS) {
            System.out.println("Task list is full! Cannot add more tasks.");
            return;
        }
        tasks[taskCount++] = task;
        System.out.println(DASHED_LINE);
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
        System.out.println(DASHED_LINE);
    }
}

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

            try {
                processUserCommand(userInput);
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
        if (taskCount == 0) {
            System.out.println("No tasks added yet.");
        } else {
            for (int i = 0; i < taskCount; i++) {
                System.out.println((i + 1) + ". " + tasks[i]);
            }
        }
        System.out.println(DASHED_LINE);
    }

    private static void markTask(String userInput, boolean isDone) throws MimonException {
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
                throw new MimonException("Invalid task number. Please enter a valid task number to mark or unmark.");
            }
        } catch (NumberFormatException e) {
            throw new MimonException("Invalid command format. Use: mark/unmark <task_number>");
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

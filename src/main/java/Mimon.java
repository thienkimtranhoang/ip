import java.util.Scanner;

public class Mimon {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Task[] tasks = new Task[100];
        int taskCount = 0;

        System.out.println("____________________________________________________________");
        System.out.println("Hello! I'm Mimon");
        System.out.println("What can I do for you?");
        System.out.println("____________________________________________________________");

        while (true) {
            String userInput = scanner.nextLine();

            if (userInput.equalsIgnoreCase("bye")) {
                System.out.println("____________________________________________________________");
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println("____________________________________________________________");
                break;
            }

            if (userInput.equalsIgnoreCase("list")) {
                System.out.println("____________________________________________________________");
                System.out.println("Here are the tasks in your list:");
                if (taskCount == 0) {
                    System.out.println("No tasks added yet.");
                } else {
                    for (int i = 0; i < taskCount; i++) {
                        System.out.println((i + 1) + ". " + tasks[i]);
                    }
                }
                System.out.println("____________________________________________________________");
                continue;
            }

            if (userInput.startsWith("mark ")) {
                int taskIndex = Integer.parseInt(userInput.substring(5)) - 1;
                if (taskIndex >= 0 && taskIndex < taskCount) {
                    tasks[taskIndex].markAsDone();
                    System.out.println("____________________________________________________________");
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println("  " + tasks[taskIndex]);
                    System.out.println("____________________________________________________________");
                }
                continue;
            }

            if (userInput.startsWith("unmark ")) {
                int taskIndex = Integer.parseInt(userInput.substring(7)) - 1;
                if (taskIndex >= 0 && taskIndex < taskCount) {
                    tasks[taskIndex].markAsNotDone();
                    System.out.println("____________________________________________________________");
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println("  " + tasks[taskIndex]);
                    System.out.println("____________________________________________________________");
                }
                continue;
            }

            // Adding tasks based on type
            if (userInput.startsWith("todo ")) {
                tasks[taskCount] = new Todo(userInput.substring(5));
            } else if (userInput.startsWith("deadline ")) {
                String[] parts = userInput.substring(9).split(" /by ", 2);
                String by = (parts.length > 1) ? parts[1] : "Unknown deadline";
                tasks[taskCount] = new Deadline(parts[0], by);
            } else if (userInput.startsWith("event ")) {
                String[] parts = userInput.substring(6).split(" /from | /to ", 3);
                if (parts.length > 2) {
                    tasks[taskCount] = new Event(parts[0], parts[1], parts[2]);
                } else {
                    System.out.println("Invalid event format. Use: event description /from START /to END");
                    continue;
                }
            } else {
                System.out.println("Invalid command. Try again.");
                continue;
            }

            System.out.println("____________________________________________________________");
            System.out.println("Got it. I've added this task:");
            System.out.println("  " + tasks[taskCount]);
            taskCount++;
            System.out.println("Now you have " + taskCount + " tasks in the list.");
            System.out.println("____________________________________________________________");
        }
    }
}

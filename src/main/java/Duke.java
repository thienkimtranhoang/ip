import java.util.Scanner;

public class Duke {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Initialize the task list
        Task[] tasks = new Task[100];
        int taskCount = 0;
        
        // Display initial greeting
        System.out.println("____________________________________________________________");
        System.out.println("Hello! I'm Mimon");
        System.out.println("What can I do for you?");
        System.out.println("____________________________________________________________");

        // Loop to handle user input
        while (true) {
            // Read user input
            String userInput = scanner.nextLine();

            // If user types "bye", exit the loop
            if (userInput.equalsIgnoreCase("bye")) {
                System.out.println("____________________________________________________________");
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println("____________________________________________________________");
                break;
            }

            // If user types "list", display all tasks
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

            // If user types "mark", mark the task as done
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

            // If user types "unmark", mark the task as not done
            if (userInput.startsWith("unmark ")) {
                int taskIndex = Integer.parseInt(userInput.substring(7)) - 1;  // Fixed substring index
                if (taskIndex >= 0 && taskIndex < taskCount) {
                    tasks[taskIndex].markAsNotDone();
                    System.out.println("____________________________________________________________");
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println("  " + tasks[taskIndex]);
                    System.out.println("____________________________________________________________");
                }
                continue;
            }

            // Add the task to the list
            tasks[taskCount] = new Task(userInput);
            taskCount++;

            // Echo the input with confirmation
            System.out.println("____________________________________________________________");
            System.out.println("added: " + userInput);
            System.out.println("____________________________________________________________");
        }
    }
}

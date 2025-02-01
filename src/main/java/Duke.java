import java.util.Scanner;

public class Duke {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Initialize the task list
        String[] tasks = new String[100];  // Store task descriptions
        boolean[] isDone = new boolean[100]; // Track completion status
        int taskCount = 0;

        // Display initial greeting
        System.out.println("____________________________________________________________");
        System.out.println("Hello! I'm Mimon");
        System.out.println("What can I do for you?");
        System.out.println("____________________________________________________________");

        // Loop to handle user input
        while (true) {
            // Read user input
            String userInput = scanner.nextLine().trim();

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
                        String status = isDone[i] ? "[X]" : "[ ]";
                        System.out.println((i + 1) + ". " + status + " " + tasks[i]);
                    }
                }
                System.out.println("____________________________________________________________");
                continue;
            }

            // If user types "mark", mark the task as done
            if (userInput.startsWith("mark ")) {
                try {
                    int taskIndex = Integer.parseInt(userInput.substring(5)) - 1;
                    if (taskIndex >= 0 && taskIndex < taskCount) {
                        isDone[taskIndex] = true;
                        System.out.println("____________________________________________________________");
                        System.out.println("Nice! I've marked this task as done:");
                        System.out.println("  [X] " + tasks[taskIndex]);
                        System.out.println("____________________________________________________________");
                    } else {
                        System.out.println("Invalid task number.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid task number.");
                }
                continue;
            }

            // If user types "unmark", mark the task as not done
            if (userInput.startsWith("unmark ")) {
                try {
                    int taskIndex = Integer.parseInt(userInput.substring(7)) - 1;
                    if (taskIndex >= 0 && taskIndex < taskCount) {
                        isDone[taskIndex] = false;
                        System.out.println("____________________________________________________________");
                        System.out.println("OK, I've marked this task as not done yet:");
                        System.out.println("  [ ] " + tasks[taskIndex]);
                        System.out.println("____________________________________________________________");
                    } else {
                        System.out.println("Invalid task number.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid task number.");
                }
                continue;
            }

            // Add the task to the list
            tasks[taskCount] = userInput;
            isDone[taskCount] = false; // New tasks are not done by default
            taskCount++;

            // Echo the input with confirmation
            System.out.println("____________________________________________________________");
            System.out.println("Added: " + userInput);
            System.out.println("____________________________________________________________");
        }
    }
}

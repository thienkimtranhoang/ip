import java.util.Scanner;

public class Duke {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Initialize the tasks array
        String[] tasks = new String[100];
        int taskCount = 0;

        // Display initial greeting
        System.out.println();
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

            // Add the input to the tasks array
            tasks[taskCount] = userInput;
            taskCount++;

            // Echo the input with confirmation
            System.out.println("____________________________________________________________");
            System.out.println("added: " + userInput);
            System.out.println("____________________________________________________________");
        }
    }
}

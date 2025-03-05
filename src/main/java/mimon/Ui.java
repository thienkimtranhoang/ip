package mimon;

import tasks.Task;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles user interaction, including input processing and output display.
 */
public class Ui {
    public static final String DASHED_LINE = "____________________________________________________________";
    private Scanner scanner;

    /**
     * Constructs a Ui object and initializes the scanner.
     */
    public Ui() {
        scanner = new Scanner(System.in);
    }

    /**
     * Reads user input from the console.
     *
     * @return The trimmed user input.
     */
    public String readUserInput() {
        return scanner.nextLine().trim();
    }
    /**
     * Prints the welcome message when the application starts.
     */
    public void printWelcomeMessage() {
        printDashedLine();
        System.out.println("Hello! I'm Mimon");
        System.out.println("What can I do for you?");
        printDashedLine();
    }
    /**
     * Prints the exit message when the application terminates.
     */
    public void printExitMessage() {
        printDashedLine();
        System.out.println("Bye. Hope to see you again soon!");
        printDashedLine();
    }

    /**
     * Displays the current list of tasks.
     *
     * @param tasks The list of tasks to display.
     */
    public void displayTaskList(ArrayList<Task> tasks) {
        printDashedLine();
        System.out.println("Here are the tasks in your list:");
        if (tasks.isEmpty()) {
            System.out.println("No tasks added yet.");
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ". " + tasks.get(i));
            }
        }
        printDashedLine();
    }
    /**
     * Displays a message when a task is marked as done.
     *
     * @param task The task that was marked as done.
     */
    public void displayTaskMarkedDone(Task task) {
        printDashedLine();
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + task);
        printDashedLine();
    }
    /**
     * Displays a message when a task is marked as not done.
     *
     * @param task The task that was marked as not done.
     */
    public void displayTaskMarkedNotDone(Task task) {
        printDashedLine();
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  " + task);
        printDashedLine();
    }

    /**
     * Displays a message when a task is deleted.
     *
     * @param task The task that was deleted.
     * @param remainingTasks The number of remaining tasks after deletion.
     */
    public void displayTaskDeleted(Task task, int remainingTasks) {
        printDashedLine();
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + remainingTasks + " tasks in the list.");
        printDashedLine();
    }

    /**
     * Displays a message when a new task is added.
     *
     * @param task The task that was added.
     * @param totalTasks The total number of tasks in the list after addition.
     */
    public void displayTaskAdded(Task task, int totalTasks) {
        printDashedLine();
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + totalTasks + " tasks in the list.");
        printDashedLine();
    }

    /**
     * Displays an error message to the user.
     *
     * @param message The error message to display.
     */
    public void showErrorMessage(String message) {
        printDashedLine();
        System.out.println("Hold on! " + message);
        printDashedLine();
    }

    /**
     * Displays a generic message to the user.
     *
     * @param message The message to display.
     */
    public void showMessage(String message) {
        printDashedLine();
        System.out.println(message);
        printDashedLine();
    }
    /**
     * Prints a dashed line for formatting purposes.
     */
    public void printDashedLine() {
        System.out.println(DASHED_LINE);
    }
}

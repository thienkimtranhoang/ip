package mimon;

import tasks.Task;
import java.util.ArrayList;
import java.util.Scanner;

public class Ui {
    public static final String DASHED_LINE = "____________________________________________________________";
    private Scanner scanner;

    public Ui() {
        scanner = new Scanner(System.in);
    }

    public String readUserInput() {
        return scanner.nextLine().trim();
    }

    public void printWelcomeMessage() {
        printDashedLine();
        System.out.println("Hello! I'm Mimon");
        System.out.println("What can I do for you?");
        printDashedLine();
    }

    public void printExitMessage() {
        printDashedLine();
        System.out.println("Bye. Hope to see you again soon!");
        printDashedLine();
    }

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

    public void displayTaskMarkedDone(Task task) {
        printDashedLine();
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + task);
        printDashedLine();
    }

    public void displayTaskMarkedNotDone(Task task) {
        printDashedLine();
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  " + task);
        printDashedLine();
    }

    public void displayTaskDeleted(Task task, int remainingTasks) {
        printDashedLine();
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + remainingTasks + " tasks in the list.");
        printDashedLine();
    }

    public void displayTaskAdded(Task task, int totalTasks) {
        printDashedLine();
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + totalTasks + " tasks in the list.");
        printDashedLine();
    }

    public void showErrorMessage(String message) {
        printDashedLine();
        System.out.println("Hold on! " + message);
        printDashedLine();
    }

    public void printDashedLine() {
        System.out.println(DASHED_LINE);
    }
}
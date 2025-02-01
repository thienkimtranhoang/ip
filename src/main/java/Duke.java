import java.util.Scanner;

public class Duke {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Display initial greeting
        System.out.println("____________________________________________________________");
        System.out.println("Hello! I'm Mimon");
        System.out.println("What can I do for you?");
        System.out.println("____________________________________________________________");

        // Wait for user input (to simulate interaction)
        scanner.nextLine();

        // Exit message
        System.out.println("____________________________________________________________");
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println("____________________________________________________________");

        scanner.close();
    }
}

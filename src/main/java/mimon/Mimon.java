package mimon;

import command.Command;

/**
 * The main class for the Mimon task management application.
 * It initializes the necessary components, handles user interactions,
 * and processes commands.
 */
public class Mimon {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Constructs a Mimon object and initializes the UI, storage, and task list.
     *
     * @param filePath The file path for storing task data.
     */
    public Mimon(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (MimonException e) {
            ui.showErrorMessage("Error loading tasks: " + e.getMessage());
            tasks = new TaskList();
        }
    }
    /**
     * Runs the Mimon application, displaying a welcome message and processing user commands.
     */
    public void run() {
        ui.printWelcomeMessage();
        boolean isExit = false;

        while (!isExit) {
            try {
                String fullCommand = ui.readUserInput();
                ui.printDashedLine(); // show the divider line

                Command c = Parser.parse(fullCommand);
                c.execute(tasks, ui, storage);
                isExit = c.isExit();
            } catch (MimonException e) {
                ui.showErrorMessage(e.getMessage());
            } finally {
                ui.printDashedLine();
            }
        }
    }
    /**
     * The entry point of the Mimon application.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        new Mimon("./data/mimon.txt").run();
    }
}
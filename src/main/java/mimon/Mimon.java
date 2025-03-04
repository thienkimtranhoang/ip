package mimon;

import command.Command;

public class Mimon {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

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

    public static void main(String[] args) {
        new Mimon("./data/mimon.txt").run();
    }
}
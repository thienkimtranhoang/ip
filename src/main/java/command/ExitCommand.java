package command;

import mimon.Storage;
import mimon.TaskList;
import mimon.Ui;

public class ExitCommand extends Command {
    public ExitCommand() {
        this.isExit = true;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.printExitMessage();
    }
}

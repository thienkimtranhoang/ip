package command;

import mimon.Storage;
import mimon.TaskList;
import mimon.Ui;

public class ListCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.displayTaskList(tasks.getTasks());
    }
}

package command;

import mimon.MimonException;
import mimon.Storage;
import mimon.TaskList;
import mimon.Ui;
import tasks.Task;

public class DeleteCommand extends Command {
    private int taskIndex;

    public DeleteCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws MimonException {
        Task removedTask = tasks.deleteTask(taskIndex);
        ui.displayTaskDeleted(removedTask, tasks.size());
        storage.saveTasks(tasks.getTasks());
    }
}

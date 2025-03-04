package command;

import mimon.MimonException;
import mimon.Storage;
import mimon.TaskList;
import mimon.Ui;
import tasks.Task;

public class MarkCommand extends Command {
    private int taskIndex;
    private boolean isDone;

    public MarkCommand(int taskIndex, boolean isDone) {
        this.taskIndex = taskIndex;
        this.isDone = isDone;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws MimonException {
        Task task = tasks.getTask(taskIndex);

        if (isDone) {
            tasks.markTaskAsDone(taskIndex);
            ui.displayTaskMarkedDone(task);
        } else {
            tasks.markTaskAsNotDone(taskIndex);
            ui.displayTaskMarkedNotDone(task);
        }
        storage.saveTasks(tasks.getTasks());
    }
}

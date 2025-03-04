package command;

import mimon.MimonException;
import mimon.Storage;
import mimon.TaskList;
import mimon.Ui;
import tasks.Task;
import tasks.Todo;

public class AddToDoCommand extends Command {
    private String description;

    public AddToDoCommand(String description) {
        this.description = description;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws MimonException {
        Task newTask = new Todo(description);
        tasks.addTask(newTask);
        ui.displayTaskAdded(newTask, tasks.size());
        storage.saveTasks(tasks.getTasks());
    }
}

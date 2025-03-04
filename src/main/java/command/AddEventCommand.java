package command;

import mimon.MimonException;
import mimon.Storage;
import mimon.TaskList;
import mimon.Ui;
import tasks.Task;
import tasks.Event;

public class AddEventCommand extends Command {
    private String description;
    private String start;
    private String end;

    public AddEventCommand(String description, String start, String end) {
        this.description = description;
        this.start = start;
        this.end = end;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws MimonException {
        Task newTask = new Event(description, start, end);
        tasks.addTask(newTask);
        ui.displayTaskAdded(newTask, tasks.size());
        storage.saveTasks(tasks.getTasks());
    }
}

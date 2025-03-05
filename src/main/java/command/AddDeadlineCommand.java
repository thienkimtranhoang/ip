package command;

import mimon.MimonException;
import mimon.Storage;
import mimon.TaskList;
import mimon.Ui;
import tasks.Task;
import tasks.Deadline;

public class AddDeadlineCommand extends Command {
    private String description;
    private String deadline;

    public AddDeadlineCommand(String description, String deadline) {
        this.description = description;
        this.deadline = deadline;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws MimonException {
        try {
            Task newTask = new Deadline(description, deadline);
            tasks.addTask(newTask);
            ui.displayTaskAdded(newTask, tasks.size());
            storage.saveTasks(tasks.getTasks());
        } catch (Deadline.DeadlineParseException e) {
            throw new MimonException(e.getMessage());
        }
    }
}
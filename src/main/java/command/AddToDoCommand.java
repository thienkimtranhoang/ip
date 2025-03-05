package command;

import mimon.MimonException;
import mimon.Storage;
import mimon.TaskList;
import mimon.Ui;
import tasks.Task;
import tasks.Todo;

/**
 * Represents a command to add a todo task to the task list.
 * This command creates a new Todo task with a description,
 * adds it to the task list, and saves it to storage.
 */
public class AddToDoCommand extends Command {
    private String description;

    /**
     * Constructs an AddToDoCommand with a task description.
     *
     * @param description The description of the todo task
     */
    public AddToDoCommand(String description) {
        this.description = description;
    }

    /**
     * Executes the command to add a todo task.
     *
     * Creates a new Todo task, adds it to the task list,
     * displays a confirmation message, and saves the updated task list.
     *
     * @param tasks The current task list
     * @param ui The user interface for displaying messages
     * @param storage The storage mechanism for saving tasks
     * @throws MimonException If there is an error adding the task
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws MimonException {
        Task newTask = new Todo(description);
        tasks.addTask(newTask);
        ui.displayTaskAdded(newTask, tasks.size());
        storage.saveTasks(tasks.getTasks());
    }
}

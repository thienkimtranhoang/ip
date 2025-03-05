package command;

import mimon.MimonException;
import mimon.Storage;
import mimon.TaskList;
import mimon.Ui;
import tasks.Task;

/**
 * Represents a command to mark a task as done or not done.
 * This command updates the status of a specific task in the task list,
 * providing flexibility to mark tasks as completed or pending.
 */
public class MarkCommand extends Command {
    private int taskIndex;
    private boolean isDone;

    public MarkCommand(int taskIndex, boolean isDone) {
        this.taskIndex = taskIndex;
        this.isDone = isDone;
    }
    /**
     * Executes the command to mark a task as done or not done.
     *
     * Retrieves the task at the specified index, updates its status,
     * displays a confirmation message, and saves the updated task list.
     *
     * @param tasks The current task list containing the task to mark
     * @param ui The user interface for displaying confirmation messages
     * @param storage The storage mechanism to save the updated task list
     * @throws MimonException If there is an error retrieving or marking the task
     */
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

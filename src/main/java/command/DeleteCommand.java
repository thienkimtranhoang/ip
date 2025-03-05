package command;

import mimon.MimonException;
import mimon.Storage;
import mimon.TaskList;
import mimon.Ui;
import tasks.Task;

/**
 * Represents a command to delete a task from the task list.
 * This command removes a specific task at a given index from the task list
 * and updates the storage accordingly.
 */
public class DeleteCommand extends Command {
    private int taskIndex;

    /**
     * Constructs a DeleteCommand with the index of the task to be deleted.
     *
     * @param taskIndex The index of the task to remove from the task list
     */
    public DeleteCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    /**
     * Executes the command to delete a task.
     *
     * Removes the task at the specified index, displays a deletion confirmation,
     * and saves the updated task list to storage.
     *
     * @param tasks The current task list
     * @param ui The user interface for displaying messages
     * @param storage The storage mechanism for saving tasks
     * @throws MimonException If there is an error deleting the task (e.g., invalid index)
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws MimonException {
        Task removedTask = tasks.deleteTask(taskIndex);
        ui.displayTaskDeleted(removedTask, tasks.size());
        storage.saveTasks(tasks.getTasks());
    }
}

package command;

import mimon.Storage;
import mimon.TaskList;
import mimon.Ui;

/**
 * Represents a command to list all tasks in the task list.
 * This command retrieves all tasks from the task list and
 * displays them using the user interface.
 */
public class ListCommand extends Command {
    /**
     * Executes the command to display all tasks.
     *
     * Retrieves the complete list of tasks and displays them
     * through the user interface.
     *
     * @param tasks The current task list to display
     * @param ui The user interface for displaying the task list
     * @param storage The storage mechanism (unused in this command)
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.displayTaskList(tasks.getTasks());
    }
}

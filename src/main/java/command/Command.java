package command;

import mimon.MimonException;
import mimon.Storage;
import mimon.TaskList;
import mimon.Ui;

/**
 * Abstract base class for commands in the Mimon task management system.
 *
 * Defines the basic structure for all commands that can be executed
 * on the task list, providing a common interface for command operations.
 */
public abstract class Command {
    /**
     * Flag to indicate whether the command is an exit command.
     * Defaults to false for most commands.
     */
    protected boolean isExit = false;

    /**
     * Executes the specific command operation on the task list.
     *
     * Each concrete command implementation provides its own specific
     * execution logic for manipulating the task list.
     *
     * @param tasks The current task list to perform the command on
     * @param ui The user interface for displaying messages
     * @param storage The storage mechanism for saving tasks
     * @throws MimonException If an error occurs during command execution
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws MimonException;

    /**
     * Checks if the command is an exit command.
     *
     * @return true if the command is an exit command, false otherwise
     */
    public boolean isExit() {
        return isExit;
    }
}


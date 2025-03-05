package command;

import mimon.Storage;
import mimon.TaskList;
import mimon.Ui;

/**
 * Represents a command to exit the Mimon task management application.
 * This command sets the exit flag and prints an exit message.
 */
public class ExitCommand extends Command {
    /**
     * Constructs an ExitCommand.
     * Sets the isExit flag to true, indicating that the application
     * should terminate after this command is executed.
     */
    public ExitCommand() {
        this.isExit = true;
    }

    /**
     * Executes the exit command.
     *
     * Prints an exit message through the user interface.
     * No modifications are made to the task list or storage.
     *
     * @param tasks The current task list (unused in this command)
     * @param ui The user interface for displaying messages
     * @param storage The storage mechanism (unused in this command)
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.printExitMessage();
    }
}

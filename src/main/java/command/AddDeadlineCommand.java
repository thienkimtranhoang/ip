package command;

import mimon.MimonException;
import mimon.Storage;
import mimon.TaskList;
import mimon.Ui;
import tasks.Task;
import tasks.Deadline;

/**
 * Represents a command to add a deadline task to the task list.
 * This command creates a new Deadline task with a description and deadline,
 * adds it to the task list, and saves it to storage.
 */
public class AddDeadlineCommand extends Command {
    private String description;
    private String deadline;

    /**
     * Constructs an AddDeadlineCommand with a task description and deadline.
     *
     * @param description The description of the deadline task
     * @param deadline The deadline for the task
     */
    public AddDeadlineCommand(String description, String deadline) {
        this.description = description;
        this.deadline = deadline;
    }
    /**
     * Executes the command to add a deadline task.
     *
     * Creates a new Deadline task, adds it to the task list,
     * displays a confirmation message, and saves the updated task list.
     *
     * @param tasks The current task list
     * @param ui The user interface for displaying messages
     * @param storage The storage mechanism for saving tasks
     * @throws MimonException If there is an error parsing the deadline or adding the task
     */
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
package command;

import mimon.MimonException;
import mimon.Storage;
import mimon.TaskList;
import mimon.Ui;
import tasks.Task;
import tasks.Event;

/**
 * Represents a command to add an event task to the task list.
 * This command creates a new Event task with a description, start, and end time,
 * adds it to the task list, and saves it to storage.
 */
public class AddEventCommand extends Command {
    private String description;
    private String start;
    private String end;

    /**
     * Constructs an AddEventCommand with a task description, start, and end times.
     *
     * @param description The description of the event task
     * @param start The start time of the event
     * @param end The end time of the event
     */
    public AddEventCommand(String description, String start, String end) {
        this.description = description;
        this.start = start;
        this.end = end;
    }

    /**
     * Executes the command to add an event task.
     *
     * Creates a new Event task, adds it to the task list,
     * displays a confirmation message, and saves the updated task list.
     *
     * @param tasks The current task list
     * @param ui The user interface for displaying messages
     * @param storage The storage mechanism for saving tasks
     * @throws MimonException If there is an error parsing the event or adding the task
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws MimonException {
        try {
            Task newTask = new Event(description, start, end);
            tasks.addTask(newTask);
            ui.displayTaskAdded(newTask, tasks.size());
            storage.saveTasks(tasks.getTasks());
        } catch (Event.EventParseException e) {
            // Convert the specific Event parsing exception to a MimonException
            throw new MimonException("Invalid event format: " + e.getMessage());
        }
    }
}
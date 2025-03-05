package command;

import mimon.Storage;
import mimon.TaskList;
import mimon.Ui;
import mimon.MimonException;
import tasks.Task;
import tasks.Deadline;
import tasks.Event;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Represents a command to list tasks filtered by a specific date.
 * This command searches through the task list and displays tasks
 * that occur on the specified date, supporting both Deadline and Event tasks.
 * Supports multiple date input formats for flexibility.
 */
public class ListByDateCommand extends Command {
    private String dateString;

    public ListByDateCommand(String dateString) {
        this.dateString = dateString;
    }

    /**
     * Executes the command to list tasks on a specific date.
     *
     * Parses the input date, filters tasks occurring on that date,
     * and displays the matching tasks through the user interface.
     *
     * @param tasks The current task list to search
     * @param ui The user interface for displaying messages
     * @param storage The storage mechanism (unused in this command)
     * @throws MimonException If there is an error parsing the date
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws MimonException {
        try {
            // Parse the date string
            LocalDate targetDate = parseDate(dateString);

            // Filter tasks that are on the specified date
            ArrayList<Task> tasksOnDate = filterTasksByDate(tasks.getTasks(), targetDate);

            // Display the tasks
            if (tasksOnDate.isEmpty()) {
                ui.showMessage("No tasks found on " + targetDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy")));
            } else {
                ui.showMessage("Tasks on " + targetDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy")) + ":");
                for (Task task : tasksOnDate) {
                    ui.showMessage(task.toString());
                }
            }
        } catch (DateTimeParseException e) {
            throw new MimonException("Invalid date format. Please use YYYY-MM-DD format.");
        }
    }

    private LocalDate parseDate(String dateString) {
        // Support multiple date formats
        DateTimeFormatter[] formatters = {
                DateTimeFormatter.ofPattern("yyyy-MM-dd"),
                DateTimeFormatter.ofPattern("dd/MM/yyyy"),
                DateTimeFormatter.ofPattern("d/M/yyyy")
        };

        for (DateTimeFormatter formatter : formatters) {
            try {
                return LocalDate.parse(dateString, formatter);
            } catch (DateTimeParseException ignored) {}
        }

        throw new DateTimeParseException("Unable to parse date", dateString, 0);
    }

    private ArrayList<Task> filterTasksByDate(ArrayList<Task> allTasks, LocalDate date) {
        return allTasks.stream()
                .filter(task -> {
                    if (task instanceof Deadline) {
                        return ((Deadline) task).isOnDate(date);
                    }
                    if (task instanceof Event) {
                        return ((Event) task).isOnDate(date);
                    }
                    return false;
                })
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
package command;

import mimon.Storage;
import mimon.TaskList;
import mimon.Ui;
import mimon.MimonException;
import tasks.Task;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Represents a command to find tasks based on a keyword search.
 * This command searches through the task list for tasks whose description
 * contains the specified keyword (case-insensitive).
 */
public class FindCommand extends Command {
    private String keyword;

    /**
     * Constructs a FindCommand with a search keyword.
     *
     * @param keyword The keyword to search for in task descriptions
     */
    public FindCommand(String keyword) {
        this.keyword = keyword.toLowerCase(); // Case-insensitive search
    }

    /**
     * Executes the command to find tasks matching the keyword.
     *
     * Searches the task list for tasks containing the keyword,
     * then displays the matching tasks or a "no tasks found" message.
     *
     * @param tasks The current task list to search
     * @param ui The user interface for displaying messages
     * @param storage The storage mechanism (unused in this command)
     * @throws MimonException If there is an error during the find operation
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws MimonException {
        // Filter tasks that contain the keyword in their description
        ArrayList<Task> matchingTasks = findTasksByKeyword(tasks.getTasks(), keyword);

        // Display the results
        if (matchingTasks.isEmpty()) {
            ui.showMessage("No tasks found matching the keyword: " + keyword);
        } else {
            ui.showMessage("Here are the matching tasks in your list:");
            for (int i = 0; i < matchingTasks.size(); i++) {
                // Display the task with a number prefix
                ui.showMessage((i + 1) + ". " + matchingTasks.get(i).toString());
            }
        }
    }

    private ArrayList<Task> findTasksByKeyword(ArrayList<Task> allTasks, String keyword) {
        return allTasks.stream()
                .filter(task -> task.toString().toLowerCase().contains(keyword))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
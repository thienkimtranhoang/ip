
package mimon;

import tasks.Task;
import java.util.ArrayList;

/**
 * Represents a list of tasks in the Mimon task management system.
 * Provides methods to manage a collection of tasks, including adding,
 * retrieving, deleting, and marking tasks.
 */
public class TaskList {
    private ArrayList<Task> tasks;

    /**
     * Constructs an empty TaskList.
     * Initializes an empty ArrayList to store tasks.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructs a TaskList with a pre-existing list of tasks.
     *
     * @param tasks An ArrayList of tasks to initialize the TaskList
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a new task to the task list.
     *
     * @param task The Task to be added to the list
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Retrieves a task at the specified index.
     *
     * @param index The index of the task to retrieve
     * @return The Task at the specified index
     * @throws MimonException If the index is out of bounds
     */
    public Task getTask(int index) throws MimonException {
        if (index >= 0 && index < tasks.size()) {
            return tasks.get(index);
        } else {
            throw new MimonException("Invalid task number.");
        }
    }

    /**
     * Deletes a task at the specified index.
     *
     * @param index The index of the task to delete
     * @return The deleted Task
     * @throws MimonException If the index is out of bounds
     */
    public Task deleteTask(int index) throws MimonException {
        if (index >= 0 && index < tasks.size()) {
            return tasks.remove(index);
        } else {
            throw new MimonException("Invalid task number. Please enter a valid task number to delete.");
        }
    }

    /**
     * Marks a task as done at the specified index.
     *
     * @param index The index of the task to mark as done
     * @throws MimonException If the index is out of bounds
     */
    public void markTaskAsDone(int index) throws MimonException {
        if (index >= 0 && index < tasks.size()) {
            tasks.get(index).markAsDone();
        } else {
            throw new MimonException("Invalid task number. Please enter a valid task number to mark.");
        }
    }

    /**
     * Marks a task as not done at the specified index.
     *
     * @param index The index of the task to mark as not done
     * @throws MimonException If the index is out of bounds
     */
    public void markTaskAsNotDone(int index) throws MimonException {
        if (index >= 0 && index < tasks.size()) {
            tasks.get(index).markAsNotDone();
        } else {
            throw new MimonException("Invalid task number. Please enter a valid task number to unmark.");
        }
    }

    /**
     * Returns the list of tasks.
     *
     * @return An ArrayList containing all tasks
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return The size of the task list
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Checks if the task list is empty.
     *
     * @return true if the task list contains no tasks, false otherwise
     */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }
}
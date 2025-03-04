
package mimon;

import tasks.Task;
import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public Task getTask(int index) throws MimonException {
        if (index >= 0 && index < tasks.size()) {
            return tasks.get(index);
        } else {
            throw new MimonException("Invalid task number.");
        }
    }

    public Task deleteTask(int index) throws MimonException {
        if (index >= 0 && index < tasks.size()) {
            return tasks.remove(index);
        } else {
            throw new MimonException("Invalid task number. Please enter a valid task number to delete.");
        }
    }

    public void markTaskAsDone(int index) throws MimonException {
        if (index >= 0 && index < tasks.size()) {
            tasks.get(index).markAsDone();
        } else {
            throw new MimonException("Invalid task number. Please enter a valid task number to mark.");
        }
    }

    public void markTaskAsNotDone(int index) throws MimonException {
        if (index >= 0 && index < tasks.size()) {
            tasks.get(index).markAsNotDone();
        } else {
            throw new MimonException("Invalid task number. Please enter a valid task number to unmark.");
        }
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public int size() {
        return tasks.size();
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }
}
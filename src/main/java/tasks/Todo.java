package tasks;

/**
 * Represents a simple todo task in the task management system.
 * A todo task is a basic task with only a description and done status.
 */
public class Todo extends Task {
    /**
     * Constructs a Todo task with a description.
     *
     * @param description The description of the todo task
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Returns a string representation of the todo task.
     *
     * @return A formatted string showing the task type and status
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    /**
     * Converts the todo task to a file-compatible format.
     *
     * @return A string representation suitable for file storage
     */
    @Override
    public String toFileFormat() {
        return "T | " + (isDone ? "1" : "0") + " | " + description;
    }
}

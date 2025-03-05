package tasks;

/**
 * Abstract base class representing a generic task in the task management system.
 * Provides common functionality for different types of tasks such as Todo,
 * Deadline, and Event tasks.
 */
public class Task {
    /** The description of the task. */
    protected String description;
    /** Indicates whether the task is completed. */
    protected boolean isDone;

    /**
     * Constructs a Task with a description.
     * Initially sets the task as not done.
     *
     * @param description The description of the task
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Retrieves the status icon representing the task's completion state.
     *
     * @return "[X]" if the task is done, "[ ]" if the task is not done
     */
    public String getStatusIcon() {
        return (isDone ? "[X]" : "[ ]");
    }

    /**
     * Marks the task as completed.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as not completed.
     */
    public void markAsNotDone() {
        this.isDone = false;
    }

    /**
     * Returns a string representation of the task.
     *
     * @return A formatted string with status icon and description
     */
    @Override
    public String toString() {
        return getStatusIcon() + " " + description;
    }

    /**
     * Converts the task to a file-compatible format.
     *
     * @return A string representation suitable for file storage
     */
    public String toFileFormat() {
        return (this instanceof Todo ? "T" : this instanceof Deadline ? "D" : "E") +
                " | " + (isDone ? "1" : "0") + " | " + description;
    }

    /**
     * Reconstructs a Task from its file format representation.
     *
     * Supports recreating Todo, Deadline, and Event tasks from a stored string.
     *
     * @param line The string containing task details in file format
     * @return A Task object of the appropriate type
     * @throws Deadline.DeadlineParseException If there is an error parsing the task
     */
    public static Task fromFileFormat(String line) throws Deadline.DeadlineParseException {
        String[] parts = line.split(" \\| ");
        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        Task task = null;
        try {
            switch (type) {
            case "T":
                task = new Todo(description);
                break;
            case "D":
                task = new Deadline(description, parts[3]); // parts[3] is the deadline date
                break;
            case "E":
                task = new Event(description, parts[3], parts[4]); // parts[3] is start, parts[4] is end
                break;
            }
        } catch (Deadline.DeadlineParseException e) {
            // Rethrow the specific exception for Deadline parsing
            throw e;
        } catch (Event.EventParseException e) {
            // Convert Event parsing exception to DeadlineParseException
            // to maintain the existing exception handling
            throw new Deadline.DeadlineParseException("Invalid event format: " + e.getMessage());
        }

        if (task != null && isDone) {
            task.markAsDone();
        }
        return task;
    }
}
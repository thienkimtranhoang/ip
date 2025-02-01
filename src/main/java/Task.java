public class Task {
    protected String description;
    protected boolean isDone;

    // Constructor to initialize a task with a description
    public Task(String description) {
        this.description = description;
        this.isDone = false; // Default status is not done
    }

    // Method to get the status icon (X if done, space if not)
    public String getStatusIcon() {
        return (isDone ? "[X]" : "[ ]");
    }

    // Method to mark the task as done
    public void markAsDone() {
        this.isDone = true;
    }

    // Method to mark the task as not done
    public void markAsNotDone() {
        this.isDone = false;
    }

    // Method to print the task in the desired format
    public String toString() {
        return getStatusIcon() + " " + description;
    }
}

package tasks;

public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "[X]" : "[ ]");
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsNotDone() {
        this.isDone = false;
    }

    @Override
    public String toString() {
        return getStatusIcon() + " " + description;
    }

    public String toFileFormat() {
        return (this instanceof Todo ? "T" : this instanceof Deadline ? "D" : "E") +
                " | " + (isDone ? "1" : "0") + " | " + description;
    }

    public static Task fromFileFormat(String line) {
        String[] parts = line.split(" \\| ");
        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        Task task = null;
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
        if (task != null && isDone) {
            task.markAsDone();
        }
        return task;
    }
}

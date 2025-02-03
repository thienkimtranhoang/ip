public class Task {
    private String description;
    private boolean isDone;
    private String type;
    private String extraInfo;

    public Task(String type, String description, String extraInfo) {
        this.type = type;
        this.description = description;
        this.extraInfo = extraInfo;
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
        return "[" + type + "]" + getStatusIcon() + " " + description + (extraInfo.isEmpty() ? "" : " " + extraInfo);
    }
}

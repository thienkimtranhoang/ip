package tasks;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a deadline task in the task management system.
 * A deadline task has a description and a specific date and time by which
 * the task should be completed.
 *
 * Supports flexible date and time parsing with multiple input formats.
 */
public class Deadline extends Task {
    private LocalDateTime by;

    // Constructors and formatters
    private static final DateTimeFormatter[] PARSE_FORMATTERS = {
            DateTimeFormatter.ISO_LOCAL_DATE_TIME,  // Add this as the first formatter
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"),  // Explicit pattern for ISO format
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),
            DateTimeFormatter.ofPattern("d/M/yyyy HHmm"),
            DateTimeFormatter.ofPattern("d/M/yyyy H:mm"),
            DateTimeFormatter.ofPattern("d/M/yyyy")
    };

    private static final DateTimeFormatter OUTPUT_FORMATTER =
            DateTimeFormatter.ofPattern("MMM dd yyyy");

    /**
     * Custom exception thrown when there is an error parsing a deadline.
     * Provides detailed information about parsing failures.
     */
    public static class DeadlineParseException extends Exception {
        public DeadlineParseException(String message) {
            super(message);
        }
    }

    /**
     * Constructs a Deadline task with a description and deadline.
     *
     * @param description The description of the deadline task
     * @param byString The deadline date and time as a string
     * @throws DeadlineParseException If the deadline cannot be parsed
     */
    public Deadline(String description, String byString) throws DeadlineParseException {
        super(description);
        try {
            this.by = parseDateTime(byString);
        } catch (Exception e) {
            throw new DeadlineParseException("Invalid deadline format: " + byString);
        }
    }

    private LocalDateTime parseDateTime(String byString) throws Exception {
        // Remove any leading/trailing whitespace
        byString = byString.trim();

        // Try multiple parsing strategies
        for (int i = 0; i < PARSE_FORMATTERS.length; i++) {
            DateTimeFormatter formatter = PARSE_FORMATTERS[i];

            try {
                LocalDateTime parsed = LocalDateTime.parse(byString, formatter);
                // If no time is specified or time is midnight, default to end of day (23:59)
                if (parsed.toLocalTime().equals(LocalDateTime.MIN.toLocalTime())) {
                    parsed = parsed.withHour(23).withMinute(59);
                }

                return parsed;
            } catch (DateTimeParseException e) {
            }
        }

        // If still not parsed, try custom parsing for d/M/yyyy
        try {
            LocalDate parsedDate = LocalDate.parse(byString, DateTimeFormatter.ofPattern("d/M/yyyy"));
            LocalDateTime result = parsedDate.atTime(23, 59);
            System.out.println("Parsed as date with default time: " + result);
            return result;
        } catch (DateTimeParseException e) {
            System.out.println("Final parsing attempt failed");
            throw new Exception("Unable to parse date: " + byString);
        }
    }

    /**
     * Returns a string representation of the deadline task.
     *
     * @return A formatted string showing the task type, description, and deadline
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " +
                OUTPUT_FORMATTER.format(by) + ")";
    }

    /**
     * Converts the deadline task to a file-compatible format.
     *
     * @return A string representation suitable for file storage
     */
    @Override
    public String toFileFormat() {
        return "D | " + (isDone ? "1" : "0") + " | " +
                description + " | " + by.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    /**
     * Checks if the deadline is on a specific date.
     *
     * @param date The date to check
     * @return true if the deadline is on the specified date, false otherwise
     */
    public boolean isOnDate(LocalDate date) {
        return this.by.toLocalDate().isEqual(date);
    }

}
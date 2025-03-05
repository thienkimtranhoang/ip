package tasks;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents an event task in the task management system.
 * An event task has a description with a start and end date and time.
 *
 * Supports flexible date and time parsing with multiple input formats.
 */
public class Event extends Task {
    private LocalDateTime fromDateTime;
    private LocalDateTime toDateTime;

    // Constructors and formatters
    private static final DateTimeFormatter[] PARSE_FORMATTERS = {
            DateTimeFormatter.ISO_LOCAL_DATE_TIME,  // First formatter
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"),  // Explicit ISO format
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
     * Custom exception thrown when there is an error parsing an event.
     * Provides detailed information about parsing failures.
     */
    public static class EventParseException extends Exception {
        public EventParseException(String message) {
            super(message);
        }
    }

    /**
     * Constructs an Event task with a description, start, and end times.
     *
     * @param description The description of the event task
     * @param from The start date and time as a string
     * @param to The end date and time as a string
     * @throws EventParseException If the event details cannot be parsed
     */
    public Event(String description, String from, String to) throws EventParseException {
        super(description);
        try {
            this.fromDateTime = parseDateTime(from);
            this.toDateTime = parseDateTime(to);
        } catch (Exception e) {
            throw new EventParseException("Invalid event format: " + e.getMessage());
        }
    }

    private LocalDateTime parseDateTime(String dateTimeString) throws Exception {
        // Remove any leading/trailing whitespace
        dateTimeString = dateTimeString.trim();

        // Try multiple parsing strategies
        for (DateTimeFormatter formatter : PARSE_FORMATTERS) {
            try {
                LocalDateTime parsed = LocalDateTime.parse(dateTimeString, formatter);

                // If no time is specified or time is midnight, default to end of day (23:59)
                if (parsed.toLocalTime().equals(LocalDateTime.MIN.toLocalTime())) {
                    parsed = parsed.withHour(23).withMinute(59);
                }

                return parsed;
            } catch (DateTimeParseException e) {
                // Continue to next formatter
            }
        }

        // If still not parsed, try custom parsing for d/M/yyyy
        try {
            LocalDate parsedDate = LocalDate.parse(dateTimeString, DateTimeFormatter.ofPattern("d/M/yyyy"));
            return parsedDate.atTime(23, 59);
        } catch (DateTimeParseException e) {
            throw new Exception("Unable to parse date: " + dateTimeString);
        }
    }

    /**
     * Returns a string representation of the event task.
     *
     * @return A formatted string showing the task type, description, start, and end times
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() +
                " (from: " + OUTPUT_FORMATTER.format(fromDateTime) +
                " to: " + OUTPUT_FORMATTER.format(toDateTime) + ")";
    }

    /**
     * Converts the event task to a file-compatible format.
     *
     * @return A string representation suitable for file storage
     */
    @Override
    public String toFileFormat() {
        return "E | " + (isDone ? "1" : "0") + " | " +
                description + " | " +
                fromDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + " | " +
                toDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    /**
     * Checks if the event occurs on a specific date.
     *
     * @param date The date to check
     * @return true if the event is on or spans the specified date, false otherwise
     */
    public boolean isOnDate(LocalDate date) {
        return (fromDateTime.toLocalDate().isEqual(date) ||
                toDateTime.toLocalDate().isEqual(date) ||
                (date.isAfter(fromDateTime.toLocalDate()) &&
                        date.isBefore(toDateTime.toLocalDate())));
    }
}
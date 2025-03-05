package tasks;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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

    // Custom exception for event parsing
    public static class EventParseException extends Exception {
        public EventParseException(String message) {
            super(message);
        }
    }

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

    @Override
    public String toString() {
        return "[E]" + super.toString() +
                " (from: " + OUTPUT_FORMATTER.format(fromDateTime) +
                " to: " + OUTPUT_FORMATTER.format(toDateTime) + ")";
    }

    @Override
    public String toFileFormat() {
        return "E | " + (isDone ? "1" : "0") + " | " +
                description + " | " +
                fromDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + " | " +
                toDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public static Event fromFileFormat(String[] parts) throws EventParseException {
        try {
            // Handle cases where event details might be at different indices
            String fromString = parts.length > 3 ? parts[3] : parts[2];
            String toString = parts.length > 4 ? parts[4] : parts[3];

            Event event = new Event(parts[2], fromString, toString);
            if (parts[1].equals("1")) {
                event.markAsDone();
            }
            return event;
        } catch (Exception e) {
            e.printStackTrace();
            throw new EventParseException("Failed to parse event: " + e.getMessage());
        }
    }

    // Getters for date-related operations
    public LocalDateTime getFromDateTime() {
        return fromDateTime;
    }

    public LocalDateTime getToDateTime() {
        return toDateTime;
    }

    public boolean isOnDate(LocalDate date) {
        return (fromDateTime.toLocalDate().isEqual(date) ||
                toDateTime.toLocalDate().isEqual(date) ||
                (date.isAfter(fromDateTime.toLocalDate()) &&
                        date.isBefore(toDateTime.toLocalDate())));
    }
}
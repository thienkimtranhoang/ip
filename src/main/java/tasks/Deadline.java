package tasks;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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

    // Custom exception for deadline parsing
    public static class DeadlineParseException extends Exception {
        public DeadlineParseException(String message) {
            super(message);
        }
    }

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

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " +
                OUTPUT_FORMATTER.format(by) + ")";
    }

    @Override
    public String toFileFormat() {
        return "D | " + (isDone ? "1" : "0") + " | " +
                description + " | " + by.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public static Deadline fromFileFormat(String[] parts) throws DeadlineParseException {
        // Print out debugging information
        System.out.println("Debug: Parsing Deadline");
        System.out.println("Total parts: " + parts.length);
        for (int i = 0; i < parts.length; i++) {
            System.out.println("Part " + i + ": [" + parts[i] + "]");
        }

        try {
            // Handle cases where deadline might be at different indices
            String deadlineString = parts.length > 3 ? parts[3] : parts[2];

            System.out.println("Attempting to parse deadline: [" + deadlineString + "]");

            Deadline deadline = new Deadline(parts[2], deadlineString);
            if (parts[1].equals("1")) {
                deadline.markAsDone();
            }
            return deadline;
        } catch (Exception e) {
            // Print full stack trace for more detailed error information
            e.printStackTrace();
            throw new DeadlineParseException("Failed to parse deadline: " + e.getMessage());
        }
    }

    // Getters for date-related operations
    public LocalDateTime getDateTime() {
        return by;
    }
    // New method to check if the deadline is on a specific date
    public boolean isOnDate(LocalDate date) {
        return this.by.toLocalDate().isEqual(date);
    }

    // Method to get the date of the deadline
    public LocalDate getDate() {
        return this.by.toLocalDate();
    }
}
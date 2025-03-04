package mimon;

import command.*;

public class Parser {
    public static Command parse(String userInput) throws MimonException {
        String[] inputParts = userInput.trim().split(" ", 2);
        String commandType = inputParts[0].toLowerCase();
        String arguments = inputParts.length > 1 ? inputParts[1].trim() : "";

        try {
            switch (commandType) {
            case "bye":
                return new ExitCommand();
            case "list":
                return new ListCommand();
            case "todo":
                if (inputParts.length < 2) {
                    throw new MimonException("The description of a todo must have some information.");
                }
                return new AddToDoCommand(inputParts[1]);
            case "deadline":
                return parseDeadlineCommand(inputParts[1]);
            case "event":
                return parseEventCommand(inputParts[1]);
            case "mark":
                return parseMarkCommand(inputParts[1], true);
            case "unmark":
                return parseMarkCommand(inputParts[1], false);
            case "delete":
                return parseDeleteCommand(inputParts[1]);
            case "list-date":
                if (arguments.isEmpty()) {
                    throw new MimonException("Date is required for list-date command");
                }
                return new ListByDateCommand(arguments);
            case "find":
                if (inputParts.length < 2) {
                    throw new MimonException("Find command requires a keyword");
                }
                return new FindCommand(inputParts[1]);
            default:
                throw new MimonException("I don't recognize that command. Please enter a valid one.");
            }
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            throw new MimonException("Invalid command format.");
        }
    }

    private static Command parseDeadlineCommand(String input) throws MimonException {
        String[] parts = input.split(" /by ", 2);
        if (parts.length < 2) {
            throw new MimonException("Invalid deadline format. Use: deadline description /by DATE");
        }
        return new AddDeadlineCommand(parts[0], parts[1]);
    }

    private static Command parseEventCommand(String input) throws MimonException {
        String[] parts = input.split(" /from | /to ", 3);
        if (parts.length < 3) {
            throw new MimonException("Invalid event format. Use: event description /from START /to END");
        }
        return new AddEventCommand(parts[0], parts[1], parts[2]);
    }

    private static Command parseMarkCommand(String input, boolean isDone) throws MimonException {
        int taskIndex = Integer.parseInt(input) - 1;
        return new MarkCommand(taskIndex, isDone);
    }

    private static Command parseDeleteCommand(String input) throws MimonException {
        int taskIndex = Integer.parseInt(input) - 1;
        return new DeleteCommand(taskIndex);
    }
}
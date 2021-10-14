package seedu.address.logic.parser;
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_INTEGER_INPUT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOURSWORKED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OVERTIME;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.RemoveHoursWorkedCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.HoursWorked;
import seedu.address.model.person.Overtime;

/**
 * Parses input arguments and creates a new RemoveHoursWorkedCommand object.
 */
public class RemoveHoursWorkedCommandParser implements Parser<RemoveHoursWorkedCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RemoveHoursWorkedCommand
     * and returns a RemoveHoursWorkedCommand object for execution.
     *
     * @param args A string representing the user's input.
     * @return A new RemoveHoursWorkedCommand object.
     * @throws ParseException if the user input does not conform the expected format
     * or an invalid integer input for the number of hours is given.
     */
    public RemoveHoursWorkedCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_HOURSWORKED, PREFIX_OVERTIME);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RemoveHoursWorkedCommand.MESSAGE_USAGE), ive);
        }

        // If both prefixes are not present
        if (argMultimap.getValue(PREFIX_HOURSWORKED).isEmpty()
                && argMultimap.getValue(PREFIX_OVERTIME).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RemoveHoursWorkedCommand.MESSAGE_USAGE));
        }

        String hoursWorkedString = argMultimap.getValue(PREFIX_HOURSWORKED).orElse("0");
        String overtimeString = argMultimap.getValue(PREFIX_OVERTIME).orElse("0");

        int hoursWorked;
        int overtime;
        try {
            hoursWorked = Integer.parseInt(hoursWorkedString);
            overtime = Integer.parseInt(overtimeString);
        } catch (NumberFormatException nfe) {
            throw new ParseException(String.format(MESSAGE_INVALID_INTEGER_INPUT,
                    RemoveHoursWorkedCommand.MESSAGE_USAGE), nfe);
        }
        // At least one of the inputs must be a non-negative integer
        if (hoursWorked <= 0 && overtime <= 0) {
            throw new ParseException(String.format(MESSAGE_INVALID_INTEGER_INPUT,
                    RemoveHoursWorkedCommand.MESSAGE_USAGE));
        }

        return new RemoveHoursWorkedCommand(index, new HoursWorked(hoursWorkedString),
                new Overtime(overtimeString));
    }
}

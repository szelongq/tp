package seedu.address.logic.parser;
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_HOURS_WORKED_INPUT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_OVERTIME_INPUT;
import static seedu.address.commons.util.StringUtil.isNonNegativeInteger;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOURSWORKED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OVERTIME;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeductHoursWorkedCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.HoursWorked;
import seedu.address.model.person.Overtime;

/**
 * Parses input arguments and creates a new DeductHoursWorkedCommand object.
 */
public class DeductHoursWorkedCommandParser implements Parser<DeductHoursWorkedCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeductHoursWorkedCommand
     * and returns a DeductHoursWorkedCommand object for execution.
     *
     * @param args A string representing the user's input.
     * @return A new DeductHoursWorkedCommand object.
     * @throws ParseException if the user input does not conform the expected format
     * or an invalid integer input for the number of hours is given.
     */
    public DeductHoursWorkedCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_HOURSWORKED, PREFIX_OVERTIME);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeductHoursWorkedCommand.MESSAGE_USAGE), ive);
        }

        // If both prefixes are not present
        if (argMultimap.getValue(PREFIX_HOURSWORKED).isEmpty()
                && argMultimap.getValue(PREFIX_OVERTIME).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeductHoursWorkedCommand.MESSAGE_USAGE));
        }

        // If either prefix contains a 0, reject the input
        if (argMultimap.getValue(PREFIX_HOURSWORKED).isPresent()
                && argMultimap.getValue(PREFIX_HOURSWORKED).get().equals("0")) {
            throw new ParseException(String.format(MESSAGE_INVALID_HOURS_WORKED_INPUT,
                    DeductHoursWorkedCommand.MESSAGE_USAGE));
        }
        if (argMultimap.getValue(PREFIX_OVERTIME).isPresent()
                && argMultimap.getValue(PREFIX_OVERTIME).get().equals("0")) {
            throw new ParseException(String.format(MESSAGE_INVALID_OVERTIME_INPUT,
                    DeductHoursWorkedCommand.MESSAGE_USAGE));
        }

        String hoursWorkedString = argMultimap.getValue(PREFIX_HOURSWORKED).orElse("0");
        String overtimeString = argMultimap.getValue(PREFIX_OVERTIME).orElse("0");

        return new DeductHoursWorkedCommand(index, parseHoursWorkedString(hoursWorkedString),
                parseOvertimeString(overtimeString));
    }

    /**
     * Parses the given {@code String} that represents the number of hours worked into a
     * HoursWorked object that contains that number of hours worked.
     *
     * @param hoursWorkedString A string representing the number of hours worked.
     * @return A new HoursWorked object.
     * @throws ParseException if an invalid integer input for the hours worked is given.
     */
    private HoursWorked parseHoursWorkedString(String hoursWorkedString) throws ParseException {
        if (!isNonNegativeInteger(hoursWorkedString)) {
            throw new ParseException(String.format(MESSAGE_INVALID_HOURS_WORKED_INPUT,
                    DeductHoursWorkedCommand.MESSAGE_USAGE));
        }

        HoursWorked hoursWorked;
        try {
            hoursWorked = new HoursWorked(hoursWorkedString);
        } catch (IllegalArgumentException iae) {
            throw new ParseException(String.format(MESSAGE_INVALID_HOURS_WORKED_INPUT,
                    DeductHoursWorkedCommand.MESSAGE_USAGE));
        }
        return hoursWorked;
    }

    /**
     * Parses the given {@code String} that represents the number of overtime hours worked into a
     * Overtime object that contains that number of overtime hours worked.
     *
     * @param overtimeString A string representing the number of overtime hours worked.
     * @return A new Overtime object.
     * @throws ParseException if an invalid integer input for the overtime hours worked is given.
     */
    private Overtime parseOvertimeString(String overtimeString) throws ParseException {
        if (!isNonNegativeInteger(overtimeString)) {
            throw new ParseException(String.format(MESSAGE_INVALID_OVERTIME_INPUT,
                    DeductHoursWorkedCommand.MESSAGE_USAGE));
        }

        Overtime overtime;
        try {
            overtime = new Overtime(overtimeString);
        } catch (IllegalArgumentException iae) {
            throw new ParseException(String.format(MESSAGE_INVALID_OVERTIME_INPUT,
                    DeductHoursWorkedCommand.MESSAGE_USAGE));
        }
        return overtime;
    }
}

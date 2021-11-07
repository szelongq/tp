package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DATE_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AssignLeaveCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AssignLeaveCommand object.
 */
public class AssignLeaveCommandParser implements Parser<AssignLeaveCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AssignLeaveCommand
     * and returns a AssignLeaveCommand object for execution.
     *
     * @param args A string representing the user's input.
     * @return A new AssignLeaveCommand object.
     * @throws ParseException if the user input does not conform to the expected format
     * or an invalid date input is given.
     */
    public AssignLeaveCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_DATE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AssignLeaveCommand.MESSAGE_USAGE), ive);
        }

        String dateString = argMultimap.getValue(PREFIX_DATE).orElse("");
        // If prefix is missing
        if (dateString.equals("")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AssignLeaveCommand.MESSAGE_USAGE));
        }
        LocalDate date;
        try {
            date = LocalDate.parse(dateString);
        } catch (DateTimeParseException dtpe) {
            throw new ParseException(String.format(MESSAGE_INVALID_DATE_FORMAT,
                    AssignLeaveCommand.MESSAGE_USAGE), dtpe);
        }

        return new AssignLeaveCommand(index, date);
    }
}

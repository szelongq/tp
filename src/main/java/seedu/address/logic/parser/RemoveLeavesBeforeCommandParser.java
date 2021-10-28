package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DATE_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import seedu.address.logic.commands.RemoveLeavesBeforeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new RemoveLeavesBeforeCommand object.
 */
public class RemoveLeavesBeforeCommandParser implements Parser<RemoveLeavesBeforeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RemoveLeavesBeforeCommand
     * and returns a RemoveLeavesBeforeCommand object for execution.
     *
     * @param args A string representing the user's input.
     * @return A new RemoveLeavesBeforeCommand object.
     * @throws ParseException if the user input does not conform to the expected format
     *                        or an invalid date input is given.
     */
    public RemoveLeavesBeforeCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_DATE);

        String dateString = argMultimap.getValue(PREFIX_DATE).orElse("");
        // If prefix is missing
        if (dateString.equals("")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RemoveLeavesBeforeCommand.MESSAGE_USAGE));
        }

        LocalDate beforeDate;
        try {
            beforeDate = LocalDate.parse(dateString);
        } catch (DateTimeParseException dtpe) {
            throw new ParseException(String.format(MESSAGE_INVALID_DATE_FORMAT,
                    RemoveLeavesBeforeCommand.MESSAGE_USAGE), dtpe);
        }

        return new RemoveLeavesBeforeCommand(beforeDate);
    }
}

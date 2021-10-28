package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_INTEGER_INPUT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddLeaveBalanceCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.LeaveBalance;

/**
 * Parses input arguments and creates a new AddLeaveBalanceCommand object.
 */
public class AddLeaveBalanceCommandParser implements Parser<AddLeaveBalanceCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddLeaveBalanceCommand
     * and returns a AddLeaveBalanceCommand object for execution.
     *
     * @param args A string representing the user's input.
     * @return A new AddLeaveBalanceCommand object.
     * @throws ParseException if the user input does not conform the expected format
     * or an invalid integer input for the number of leaves is given.
     */
    public AddLeaveBalanceCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_LEAVE);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddLeaveBalanceCommand.MESSAGE_USAGE), ive);
        }

        String numberOfLeavesString = argMultimap.getValue(PREFIX_LEAVE).orElse("");
        // If prefix is missing
        if (numberOfLeavesString.equals("")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddLeaveBalanceCommand.MESSAGE_USAGE));
        }
        int numberOfLeaves;
        try {
            numberOfLeaves = Integer.parseInt(numberOfLeavesString);
        } catch (NumberFormatException nfe) {
            throw new ParseException(String.format(MESSAGE_INVALID_INTEGER_INPUT,
                    AddLeaveBalanceCommand.MESSAGE_USAGE), nfe);
        }
        // If a non-positive integer is given
        if (numberOfLeaves <= 0) {
            throw new ParseException(String.format(MESSAGE_INVALID_INTEGER_INPUT,
                    AddLeaveBalanceCommand.MESSAGE_USAGE));
        }

        return new AddLeaveBalanceCommand(index, new LeaveBalance(numberOfLeavesString));
    }

}

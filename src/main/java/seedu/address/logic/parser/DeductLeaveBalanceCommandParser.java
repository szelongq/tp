package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_LEAVES_INPUT;
import static seedu.address.commons.util.StringUtil.isNonZeroUnsignedInteger;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeductLeaveBalanceCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.LeaveBalance;

/**
 * Parses input arguments and creates a new DeductLeaveBalanceCommand object.
 */
public class DeductLeaveBalanceCommandParser implements Parser<DeductLeaveBalanceCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeductLeaveBalanceCommand
     * and returns a DeductLeaveBalanceCommand object for execution.
     *
     * @param args A string representing the user's input.
     * @return A new DeductLeaveBalanceCommand object.
     * @throws ParseException if the user input does not conform the expected format
     * or an invalid integer input for the number of leaves is given.
     */
    public DeductLeaveBalanceCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_LEAVE);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeductLeaveBalanceCommand.MESSAGE_USAGE), ive);
        }

        String numberOfLeavesString = argMultimap.getValue(PREFIX_LEAVE).orElse("");
        // If prefix is missing
        if (numberOfLeavesString.equals("")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeductLeaveBalanceCommand.MESSAGE_USAGE));
        }

        return new DeductLeaveBalanceCommand(index, parseLeaveString(numberOfLeavesString));
    }

    /**
     * Parses the given {@code String} that represents a number of leaves into a
     * LeaveBalance object that contains that number of leaves.
     *
     * @param numberOfLeavesString A string representing the number of leaves.
     * @return A new LeaveBalance object.
     * @throws ParseException if an invalid integer input for the number of leaves is given.
     */
    private LeaveBalance parseLeaveString(String numberOfLeavesString) throws ParseException {
        // If a non-positive integer is given, reject the input
        if (!isNonZeroUnsignedInteger(numberOfLeavesString)) {
            throw new ParseException(String.format(MESSAGE_INVALID_LEAVES_INPUT,
                    DeductLeaveBalanceCommand.MESSAGE_USAGE));
        }

        LeaveBalance leaveBalance;
        try {
            leaveBalance = new LeaveBalance(numberOfLeavesString);
        } catch (IllegalArgumentException iae) {
            throw new ParseException(String.format(MESSAGE_INVALID_LEAVES_INPUT,
                    DeductLeaveBalanceCommand.MESSAGE_USAGE), iae);
        }
        return leaveBalance;
    }
}

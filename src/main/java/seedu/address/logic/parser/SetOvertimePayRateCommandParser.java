package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.SetOvertimePayRateCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.OvertimePayRate;

/**
 * Parses input arguments and creates a new SetOvertimePayRateCommand object
 */
public class SetOvertimePayRateCommandParser implements Parser<SetOvertimePayRateCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SetOvertimePayRateCommand
     * and returns a SetOvertimePayRateCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public SetOvertimePayRateCommand parse(String args) throws ParseException {
        try {
            OvertimePayRate overtimePayRate = new OvertimePayRate(args);
            return new SetOvertimePayRateCommand(overtimePayRate);
        } catch (IllegalArgumentException iae) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            SetOvertimePayRateCommand.MESSAGE_USAGE), iae);
        }
    }

}

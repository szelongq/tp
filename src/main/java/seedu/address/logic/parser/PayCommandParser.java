package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.PayCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class PayCommandParser implements Parser<PayCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the PayCommand
     * and returns a PayCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public PayCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.equals(PayCommand.PAY_ALL_COMMAND_PHRASE)) {
            return new PayCommand();
        } else {
            try {
                Index index = ParserUtil.parseIndex(args);
                return new PayCommand(index);
            } catch (ParseException pe) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, PayCommand.MESSAGE_USAGE), pe);
            }
        }
    }
}

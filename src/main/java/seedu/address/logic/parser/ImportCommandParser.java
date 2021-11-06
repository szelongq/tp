package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ImportCommand object
 */
public class ImportCommandParser implements Parser<ImportCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ImportCommand
     * and returns an ImportCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ImportCommand parse(String args) throws ParseException {
        String[] input = args.split(" ");
        StringBuffer filepath;
        int inputLength = input.length;

        if (inputLength <= 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));
        } else {
            filepath = new StringBuffer(input[1]);
            for (int i = 2; i < inputLength; i++) {
                // StringBuffer.append has lower runtime than String.concat
                filepath = filepath.append(" ").append(input[i]);
            }
        }
        return new ImportCommand(filepath.toString());
    }
}

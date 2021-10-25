package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.parser.exceptions.ParseException;


public class ImportCommandParserTest {
    private static ImportCommandParser parser = new ImportCommandParser();
    private static final String TEST_FILEPATH_WITH_SPACE = "/Users/Owner/Desktop/New Folder/Employees.csv";

    // Displays error message if no file path is specified.
    @Test
    public void parse_missingFilePath_failure() {
        String input = ImportCommand.COMMAND_WORD;
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE);
        assertParseFailure(parser, input, expectedMessage);
    }

    // Able to read filepath with directories utilising space character.
    @Test
    public void parse_filepathWithSpace_success() {
        String input = ImportCommand.COMMAND_WORD + " " + TEST_FILEPATH_WITH_SPACE;
        try {
            ImportCommand command = parser.parse(input);
            assertTrue(command.getFilepathString().equals(TEST_FILEPATH_WITH_SPACE));
        } catch (ParseException e) {
            assertTrue(false);
        }


    }
}


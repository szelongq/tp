package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_INTEGER_INPUT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RemoveLeavesCommand;
import seedu.address.model.person.Leave;

public class RemoveLeaveCommandParserTest {
    private final RemoveLeavesCommandParser parser = new RemoveLeavesCommandParser();
    private final String validNumberOfLeaves = "3";

    @Test
    public void parse_indexSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_LEAVE + validNumberOfLeaves;
        RemoveLeavesCommand expectedCommand = new RemoveLeavesCommand(targetIndex, new Leave(validNumberOfLeaves));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveLeavesCommand.MESSAGE_USAGE);

        // No parameters
        assertParseFailure(parser, " ", expectedMessage);

        // No index
        assertParseFailure(parser, PREFIX_LEAVE + "" + validNumberOfLeaves, expectedMessage);

        // No number of leaves
        assertParseFailure(parser, String.valueOf(INDEX_FIRST_PERSON.getOneBased()), expectedMessage);

        // Missing prefix
        assertParseFailure(parser, INDEX_FIRST_PERSON.getOneBased() + " "
                + validNumberOfLeaves, expectedMessage);
    }

    @Test
    public void parse_invalidInteger_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_INTEGER_INPUT, RemoveLeavesCommand.MESSAGE_USAGE);
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_LEAVE;

        // Not an integer
        assertParseFailure(parser, userInput + "1.1", expectedMessage);

        // Not a positive integer
        assertParseFailure(parser, userInput + "0", expectedMessage);
        assertParseFailure(parser, userInput + "-1", expectedMessage);
    }
}
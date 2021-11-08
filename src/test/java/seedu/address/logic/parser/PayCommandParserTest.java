package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.PayCommand;

public class PayCommandParserTest {

    private PayCommandParser parser = new PayCommandParser();

    @Test
    public void parsePaySingle_validArgs_returnsPayCommand() {
        assertParseSuccess(parser, "1", new PayCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parsePayAll_validArgs_returnsPayCommand() {
        assertParseSuccess(parser, PayCommand.PAY_ALL_COMMAND_PHRASE, new PayCommand());
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, PayCommand.MESSAGE_USAGE));
    }
}

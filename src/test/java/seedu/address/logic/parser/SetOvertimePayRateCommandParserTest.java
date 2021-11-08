package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SetOvertimePayRateCommand;
import seedu.address.model.OvertimePayRate;

public class SetOvertimePayRateCommandParserTest {

    private SetOvertimePayRateCommandParser parser = new SetOvertimePayRateCommandParser();

    @Test
    public void parse_validArgs_returnsSetOvertimePayRateCommand() {
        assertParseSuccess(parser, "1.5", new SetOvertimePayRateCommand(
                new OvertimePayRate("1.5")));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SetOvertimePayRateCommand.MESSAGE_USAGE));
    }
}

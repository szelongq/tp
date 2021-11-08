package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DATE_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AssignLeaveCommand;

public class AssignLeaveCommandParserTest {

    private static AssignLeaveCommandParser parser = new AssignLeaveCommandParser();

    private static final String FIRST_INDEX = "1";
    private static final String INVALID_INDEX = "-1";

    private static final String INVALID_LEAP_YEAR_DATE = "2021-02-29"; // 2021 is not a leap year.
    private static final String INVALID_MONTH_DATE = "2020-50-29"; // 50 is not a valid month for a date
    private static final String INVALID_DAY_DATE = "2020-02-50"; // 50 is not a valid day for a date
    private static final String INVALID_THREE_DIGIT_YEAR_DATE = "999-02-50"; // Year must be in 4 digits
    private static final String INVALID_FIVE_DIGIT_YEAR_DATE = "20202-02-50"; // Year must be in 4 digits

    private static final String VALID_TYPICAL_DATE = "2020-02-20";
    private static final String VALID_LEAP_YEAR_DATE = "2020-02-29";

    private static final String INPUT_DATE_NOT_GIVEN = " " + FIRST_INDEX + " " + PREFIX_DATE;
    private static final String INPUT_INDEX_NOT_GIVEN = " " + PREFIX_DATE + VALID_TYPICAL_DATE;

    private static final String INPUT_INVALID_INDEX = " " + INVALID_INDEX + " " + PREFIX_DATE + VALID_TYPICAL_DATE;

    private static final String INPUT_INVALID_DAY = " " + FIRST_INDEX + " " + PREFIX_DATE + INVALID_DAY_DATE;
    private static final String INPUT_INVALID_LEAP_YEAR = " " + FIRST_INDEX + " " + PREFIX_DATE
            + INVALID_LEAP_YEAR_DATE;
    private static final String INPUT_INVALID_MONTH = " " + FIRST_INDEX + " " + PREFIX_DATE + INVALID_MONTH_DATE;
    private static final String INPUT_INVALID_THREE_DIGIT_YEAR = " " + FIRST_INDEX + " " + PREFIX_DATE
            + INVALID_THREE_DIGIT_YEAR_DATE;
    private static final String INPUT_INVALID_FIVE_DIGIT_YEAR = " " + FIRST_INDEX + " " + PREFIX_DATE
            + INVALID_FIVE_DIGIT_YEAR_DATE;

    private static final String INPUT_VALID_LEAP_YEAR = " " + FIRST_INDEX + " " + PREFIX_DATE + VALID_LEAP_YEAR_DATE;
    private static final String INPUT_VALID_TYPICAL_DATE = " " + FIRST_INDEX + " " + PREFIX_DATE + VALID_TYPICAL_DATE;

    // Missing Index -> Failure
    @Test
    public void parse_indexNotGiven_invalidCommandError() {
        String input = INPUT_INDEX_NOT_GIVEN;
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignLeaveCommand.MESSAGE_USAGE);
        assertParseFailure(parser, input, expectedMessage);
    }

    // Missing Date -> Failure
    @Test
    public void parse_dateNotGiven_invalidCommandError() {
        String input = INPUT_DATE_NOT_GIVEN;
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignLeaveCommand.MESSAGE_USAGE);
        assertParseFailure(parser, input, expectedMessage);
    }

    // Invalid Index -> Failure
    @Test
    public void parse_invalidIndexGiven_invalidCommandError() {
        String input = INPUT_INVALID_INDEX;
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignLeaveCommand.MESSAGE_USAGE);
        assertParseFailure(parser, input, expectedMessage);
    }

    // Invalid Month -> Failure
    @Test
    public void parse_givenDateMonthNotValid_invalidDateError() {
        String input = INPUT_INVALID_MONTH;
        String expectedMessage = String.format(MESSAGE_INVALID_DATE_FORMAT, AssignLeaveCommand.MESSAGE_USAGE);
        assertParseFailure(parser, input, expectedMessage);
    }

    // Invalid Day -> Failure
    @Test
    public void parse_givenDateDayNotValid_invalidDateError() {
        String input = INPUT_INVALID_DAY;
        String expectedMessage = String.format(MESSAGE_INVALID_DATE_FORMAT, AssignLeaveCommand.MESSAGE_USAGE);
        assertParseFailure(parser, input, expectedMessage);
    }

    // Non-existent Leap Date -> Failure
    @Test
    public void parse_invalidDateInLeapYear_failure() {
        String input = INPUT_INVALID_LEAP_YEAR;
        String expectedMessage = String.format(MESSAGE_INVALID_DATE_FORMAT, AssignLeaveCommand.MESSAGE_USAGE);
        assertParseFailure(parser, input, expectedMessage);
    }

    // Invalid Year -> Failure
    @Test
    public void parse_givenDateThreeDigitYearNotValid_invalidDateError() {
        String input = INPUT_INVALID_THREE_DIGIT_YEAR;
        String expectedMessage = String.format(MESSAGE_INVALID_DATE_FORMAT, AssignLeaveCommand.MESSAGE_USAGE);
        assertParseFailure(parser, input, expectedMessage);
    }

    // Invalid Year -> Failure
    @Test
    public void parse_givenDateFiveDigitYearNotValid_invalidDateError() {
        String input = INPUT_INVALID_FIVE_DIGIT_YEAR;
        String expectedMessage = String.format(MESSAGE_INVALID_DATE_FORMAT, AssignLeaveCommand.MESSAGE_USAGE);
        assertParseFailure(parser, input, expectedMessage);
    }

    // Valid and existing Leap Day -> Success
    @Test
    public void parse_validDateInLeapYear_success() {
        String input = INPUT_VALID_LEAP_YEAR;
        assertParseSuccess(parser, input, new AssignLeaveCommand(INDEX_FIRST_PERSON,
                LocalDate.parse(VALID_LEAP_YEAR_DATE)));
    }

    // Valid Date -> Success
    @Test
    public void parse_validTypicalDate_success() {
        String input = INPUT_VALID_TYPICAL_DATE;
        assertParseSuccess(parser, input, new AssignLeaveCommand(INDEX_FIRST_PERSON,
                LocalDate.parse(VALID_TYPICAL_DATE)));
    }

}

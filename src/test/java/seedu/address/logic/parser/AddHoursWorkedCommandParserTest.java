package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_HOURS_WORKED_INPUT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_OVERTIME_INPUT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOURSWORKED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OVERTIME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddHoursWorkedCommand;
import seedu.address.model.person.HoursWorked;
import seedu.address.model.person.Overtime;


public class AddHoursWorkedCommandParserTest {
    private final AddHoursWorkedCommandParser parser = new AddHoursWorkedCommandParser();
    private final String validHoursWorked = "10";
    private final String validOvertime = "5";

    @Test
    public void parse_indexSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        // Both HoursWorked and Overtime present
        String userInput = targetIndex.getOneBased() + " " + PREFIX_HOURSWORKED + validHoursWorked
                + " " + PREFIX_OVERTIME + validOvertime;
        AddHoursWorkedCommand expectedCommand =
                new AddHoursWorkedCommand(targetIndex, new HoursWorked(validHoursWorked),
                        new Overtime(validOvertime));
        assertParseSuccess(parser, userInput, expectedCommand);

        // Only HoursWorked present
        userInput = targetIndex.getOneBased() + " " + PREFIX_HOURSWORKED + validHoursWorked;
        expectedCommand =
                new AddHoursWorkedCommand(targetIndex, new HoursWorked(validHoursWorked),
                        new Overtime("0"));
        assertParseSuccess(parser, userInput, expectedCommand);

        // Only Overtime present
        userInput = targetIndex.getOneBased() + " " + PREFIX_OVERTIME + validOvertime;
        expectedCommand = new AddHoursWorkedCommand(targetIndex, new HoursWorked("0"),
                        new Overtime(validOvertime));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddHoursWorkedCommand.MESSAGE_USAGE);

        // No parameters
        assertParseFailure(parser, " ", expectedMessage);

        // No index
        assertParseFailure(parser, PREFIX_HOURSWORKED + validHoursWorked
                + " " + PREFIX_OVERTIME + validOvertime, expectedMessage);

        // No hours worked and overtime inputs
        assertParseFailure(parser, String.valueOf(INDEX_FIRST_PERSON.getOneBased()), expectedMessage);

        // Missing prefix
        assertParseFailure(parser, INDEX_FIRST_PERSON.getOneBased() + " "
                + validHoursWorked, expectedMessage);
    }

    @Test
    public void parse_invalidHoursWorked_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_HOURS_WORKED_INPUT, AddHoursWorkedCommand.MESSAGE_USAGE);
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_HOURSWORKED;

        // Not an integer
        assertParseFailure(parser, userInput + "1.1", expectedMessage);
        assertParseFailure(parser, userInput + "aaa", expectedMessage);
        assertParseFailure(parser, userInput + "1abc", expectedMessage);

        // Not a positive integer
        assertParseFailure(parser, userInput + "0", expectedMessage);
        assertParseFailure(parser, userInput + "-1", expectedMessage);

        // Exceeds max hours worked
        assertParseFailure(parser, userInput + "745", expectedMessage);
        assertParseFailure(parser, userInput + "1000", expectedMessage);

        // Invalid hours worked with valid overtime
        assertParseFailure(parser, userInput + "0"
                + " " + PREFIX_OVERTIME + "5", expectedMessage);
        assertParseFailure(parser, userInput + "745"
                + " " + PREFIX_OVERTIME + "5", expectedMessage);

        // Invalid hours worked with invalid overtime: will display error for hours worked
        assertParseFailure(parser, userInput + "0"
                + " " + PREFIX_OVERTIME + "0", expectedMessage);
        assertParseFailure(parser, userInput + "745"
                + " " + PREFIX_OVERTIME + "745", expectedMessage);
    }

    @Test
    public void parse_invalidOvertime_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_OVERTIME_INPUT, AddHoursWorkedCommand.MESSAGE_USAGE);
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_OVERTIME;

        // Not an integer
        assertParseFailure(parser, userInput + "1.1", expectedMessage);
        assertParseFailure(parser, userInput + "aaa", expectedMessage);
        assertParseFailure(parser, userInput + "1abc", expectedMessage);

        // Not a positive integer
        assertParseFailure(parser, userInput + "0", expectedMessage);
        assertParseFailure(parser, userInput + "-1", expectedMessage);

        // Exceeds max overtime
        assertParseFailure(parser, userInput + "745", expectedMessage);
        assertParseFailure(parser, userInput + "1000", expectedMessage);

        // Invalid overtime with valid hours worked
        assertParseFailure(parser, userInput + "0"
                + " " + PREFIX_HOURSWORKED + "5", expectedMessage);
        assertParseFailure(parser, userInput + "745"
                + " " + PREFIX_HOURSWORKED + "5", expectedMessage);

        // Invalid hours worked with invalid overtime: will display error for overtime
        // as the 0 input is caught before the 745 input in the code
        assertParseFailure(parser, userInput + "0"
                + " " + PREFIX_HOURSWORKED + "745", expectedMessage);
    }
}

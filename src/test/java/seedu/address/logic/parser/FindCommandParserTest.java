package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_FINDDATE_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HOURLYSALARY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HOURSWORKED_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEAVES_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_OVERTIME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOURLYSALARY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOURSWORKED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OVERTIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import javafx.util.Pair;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;
import seedu.address.model.person.predicates.AddressContainsKeywordsPredicate;
import seedu.address.model.person.predicates.EmailContainsKeywordsPredicate;
import seedu.address.model.person.predicates.HoursEqualPredicate;
import seedu.address.model.person.predicates.HoursLessThanPredicate;
import seedu.address.model.person.predicates.HoursMoreThanEqualPredicate;
import seedu.address.model.person.predicates.LeaveEqualPredicate;
import seedu.address.model.person.predicates.LeaveLessThanPredicate;
import seedu.address.model.person.predicates.LeaveMoreThanEqualPredicate;
import seedu.address.model.person.predicates.LeavesTakenContainsDatesPredicate;
import seedu.address.model.person.predicates.NameContainsKeywordsPredicate;
import seedu.address.model.person.predicates.OvertimeEqualPredicate;
import seedu.address.model.person.predicates.OvertimeLessThanEqualPredicate;
import seedu.address.model.person.predicates.OvertimeMoreThanPredicate;
import seedu.address.model.person.predicates.PersonIsPaidPredicate;
import seedu.address.model.person.predicates.PhoneNumberMatchesPredicate;
import seedu.address.model.person.predicates.RoleContainsKeywordsPredicate;
import seedu.address.model.person.predicates.SalaryIsEqualPredicate;
import seedu.address.model.person.predicates.SalaryIsLessThanEqualPredicate;
import seedu.address.model.person.predicates.SalaryIsMoreThanPredicate;
import seedu.address.model.person.predicates.TagContainsKeywordsPredicate;
import seedu.address.testutil.PersonBuilder;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    private static void assertPredicatesAreEqual(FindCommand expectedCommand, FindCommand testCommand, Person p) {
        assertEquals(expectedCommand.getPredicate().test(p), testCommand.getPredicate().test(p));
    }

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidPreamble_throwsParseException() {
        assertParseFailure(parser, " asdfpw", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_noComparisonOperatorSalaryArg_throwsParseException() {
        assertParseFailure(parser, " " + PREFIX_HOURLYSALARY + "5",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidComparisonOperatorSalaryArg_throwsParseException() {
        assertParseFailure(parser, " " + PREFIX_HOURLYSALARY + "><5",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidComparisonOperatorSalaryArg2_throwsParseException() {
        assertParseFailure(parser, " " + PREFIX_HOURLYSALARY + "=>5",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidSalaryTooManyArgs_throwsParseException() {
        assertParseFailure(parser, " " + PREFIX_HOURLYSALARY + ">5 <10",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidSingleDate_throwsParseException() {
        assertParseFailure(parser, " " + PREFIX_DATE + "0",
                String.format(MESSAGE_INVALID_FINDDATE_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidStartDates_throwsParseException() {
        assertParseFailure(parser, " " + PREFIX_DATE + "0:2021-09-12",
                String.format(MESSAGE_INVALID_FINDDATE_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidEndDates_throwsParseException() {
        assertParseFailure(parser, " " + PREFIX_DATE + "2021-09-12:0",
                String.format(MESSAGE_INVALID_FINDDATE_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidDateMonth_throwsParseException() {
        assertParseFailure(parser, " " + PREFIX_DATE + "2021-04-12:2021-21-12",
                String.format(MESSAGE_INVALID_FINDDATE_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidDateDay_throwsParseException() {
        assertParseFailure(parser, " " + PREFIX_DATE + "2021-04-12:2021-05-32",
                String.format(MESSAGE_INVALID_FINDDATE_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidDateRangeFormat_throwsParseException() {
        assertParseFailure(parser, " " + PREFIX_DATE + "2021-04-12-2021-21-12",
                String.format(MESSAGE_INVALID_FINDDATE_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_tooManyDates_throwsParseException() {
        assertParseFailure(parser, " " + PREFIX_DATE + "2021-01-01:2022-01-01:2022-01-01",
                String.format(MESSAGE_INVALID_FINDDATE_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    /**
     * Since FindCommandParser now combines all predicates together with Stream::reduce,
     * the FindCommands given cannot be directly tested for equality.
     * Current workaround: test both predicates of FindCommand with given
     * inputs and see if both predicates return the same result for the inputs.
     */
    @Test
    public void parse_validPreamble_returnsFindCommand() {
        FindCommand expectedFindCommand =
                new FindCommand(new PersonIsPaidPredicate());
        try {
            // parsedFindCommand returns true for unpaid person
            FindCommand parsedFindCommand = parser.parse("unpaid");
            Person unpaidPerson = new PersonBuilder().withCalculatedPay("100").build();
            Person paidPerson = new PersonBuilder().build();
            assertPredicatesAreEqual(expectedFindCommand, parsedFindCommand, unpaidPerson);
            assertPredicatesAreEqual(expectedFindCommand, parsedFindCommand, paidPerson);
            assertTrue(parsedFindCommand.getPredicate().test(unpaidPerson));
            assertFalse(parsedFindCommand.getPredicate().test(paidPerson));
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    @Test
    public void parse_validNameArgs_returnsFindCommand() {
        FindCommand expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("Amy", "Bryan")));
        try {
            FindCommand parsedFindCommand = parser.parse(" " + PREFIX_NAME + "Amy Bryan");
            // Amy's name contains Amy => true
            // Bob's name does not contain Amy or Bryan => false
            assertPredicatesAreEqual(expectedFindCommand, parsedFindCommand, AMY);
            assertPredicatesAreEqual(expectedFindCommand, parsedFindCommand, BOB);
            assertTrue(parsedFindCommand.getPredicate().test(AMY));
            assertFalse(parsedFindCommand.getPredicate().test(BOB));
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    @Test
    public void parse_validPhoneArgs_returnsFindCommand() {
        FindCommand expectedFindCommand =
                new FindCommand(new PhoneNumberMatchesPredicate(Arrays.asList(VALID_PHONE_AMY)));
        try {
            FindCommand parsedFindCommand = parser.parse(" " + PREFIX_PHONE + "11111111");
            // Amy's phone number = "11111111" => true
            assertPredicatesAreEqual(expectedFindCommand, parsedFindCommand, AMY);
            assertPredicatesAreEqual(expectedFindCommand, parsedFindCommand, BOB);
            assertTrue(parsedFindCommand.getPredicate().test(AMY));
            assertFalse(parsedFindCommand.getPredicate().test(BOB));
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    @Test
    public void parse_validRoleArgs_returnsFindCommand() {
        FindCommand expectedFindCommand =
                new FindCommand(new RoleContainsKeywordsPredicate(Arrays.asList("Mobile", "Front")));
        try {
            FindCommand parsedFindCommand = parser.parse(" " + PREFIX_ROLE + "Mobile Front");
            // Amy's Role is Mobile App Developer (contains "Mobile") => true
            // Bob's Role is Front-End Developer (contains "Front") => true
            assertPredicatesAreEqual(expectedFindCommand, parsedFindCommand, AMY);
            assertPredicatesAreEqual(expectedFindCommand, parsedFindCommand, BOB);
            assertTrue(parsedFindCommand.getPredicate().test(AMY));
            assertTrue(parsedFindCommand.getPredicate().test(BOB));
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    @Test
    public void parse_validSharedRoleArgs_returnsFindCommand() {
        FindCommand expectedFindCommand =
                new FindCommand(new RoleContainsKeywordsPredicate(Arrays.asList("Developer")));
        try {
            FindCommand parsedFindCommand = parser.parse(" " + PREFIX_ROLE + "Developer");
            // Amy's Role is Mobile App Developer => true
            // Bob's Role is Front End Developer => true
            assertPredicatesAreEqual(expectedFindCommand, parsedFindCommand, AMY);
            assertPredicatesAreEqual(expectedFindCommand, parsedFindCommand, BOB);
            assertTrue(parsedFindCommand.getPredicate().test(AMY));
            assertTrue(parsedFindCommand.getPredicate().test(BOB));
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    @Test
    public void parse_validRoleArgsAmyOnly_returnsFindCommand() {
        FindCommand expectedFindCommand =
                new FindCommand(new RoleContainsKeywordsPredicate(Arrays.asList("Mobile")));
        try {
            FindCommand parsedFindCommand = parser.parse(" " + PREFIX_ROLE + "Mobile");
            // Amy's Role is Mobile App Developer => true
            // Bob's Role is Front End Developer => false
            assertPredicatesAreEqual(expectedFindCommand, parsedFindCommand, AMY);
            assertPredicatesAreEqual(expectedFindCommand, parsedFindCommand, BOB);
            assertTrue(parsedFindCommand.getPredicate().test(AMY));
            assertFalse(parsedFindCommand.getPredicate().test(BOB));
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    @Test
    public void parse_validAddressArgs_returnsFindCommand() {
        FindCommand expectedFindCommand =
                new FindCommand(new AddressContainsKeywordsPredicate(Arrays.asList("Street")));
        try {
            FindCommand parsedFindCommand = parser.parse(" " + PREFIX_ADDRESS + "Street");
            // Amy's address contains "Street" => true
            // Bob's address contains "Street" => true
            assertPredicatesAreEqual(expectedFindCommand, parsedFindCommand, AMY);
            assertPredicatesAreEqual(expectedFindCommand, parsedFindCommand, BOB);
            assertTrue(parsedFindCommand.getPredicate().test(AMY));
            assertTrue(parsedFindCommand.getPredicate().test(BOB));
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    @Test
    public void parse_validAddressArgsAmyOnly_returnsFindCommand() {
        FindCommand expectedFindCommand =
                new FindCommand(new AddressContainsKeywordsPredicate(Arrays.asList("312")));
        try {
            FindCommand parsedFindCommand = parser.parse(" " + PREFIX_ADDRESS + "312");
            // Amy's address contains "312" => true
            // Bob's address does not contain "312" => false
            assertPredicatesAreEqual(expectedFindCommand, parsedFindCommand, AMY);
            assertPredicatesAreEqual(expectedFindCommand, parsedFindCommand, BOB);
            assertTrue(parsedFindCommand.getPredicate().test(AMY));
            assertFalse(parsedFindCommand.getPredicate().test(BOB));
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    @Test
    public void parse_validSalary_returnsFindCommand() {
        FindCommand expectedFindCommand =
                new FindCommand(new SalaryIsEqualPredicate(Double.parseDouble(VALID_HOURLYSALARY_AMY)));
        try {
            // Test equals comparison
            FindCommand parsedFindCommand = parser.parse(" " + PREFIX_HOURLYSALARY + "=" + VALID_HOURLYSALARY_AMY);
            assertPredicatesAreEqual(expectedFindCommand, parsedFindCommand, AMY);
            assertPredicatesAreEqual(expectedFindCommand, parsedFindCommand, BOB);
            assertTrue(parsedFindCommand.getPredicate().test(AMY));
            assertFalse(parsedFindCommand.getPredicate().test(BOB));

            // Test more than comparison
            parsedFindCommand = parser.parse(" " + PREFIX_HOURLYSALARY + ">" + VALID_HOURLYSALARY_AMY);
            expectedFindCommand =
                    new FindCommand(new SalaryIsMoreThanPredicate(Double.parseDouble(VALID_HOURLYSALARY_AMY)));
            assertPredicatesAreEqual(expectedFindCommand, parsedFindCommand, AMY);
            assertPredicatesAreEqual(expectedFindCommand, parsedFindCommand, BOB);
            assertFalse(parsedFindCommand.getPredicate().test(AMY));
            assertFalse(parsedFindCommand.getPredicate().test(BOB));

            // Test less than equal comparison
            parsedFindCommand = parser.parse(" " + PREFIX_HOURLYSALARY + "<=" + VALID_HOURLYSALARY_AMY);
            expectedFindCommand =
                    new FindCommand(new SalaryIsLessThanEqualPredicate(Double.parseDouble(VALID_HOURLYSALARY_AMY)));
            assertPredicatesAreEqual(expectedFindCommand, parsedFindCommand, AMY);
            assertPredicatesAreEqual(expectedFindCommand, parsedFindCommand, BOB);
            assertTrue(parsedFindCommand.getPredicate().test(AMY));
            assertTrue(parsedFindCommand.getPredicate().test(BOB));
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    @Test
    public void parse_validHoursWorked_returnsFindCommand() {
        FindCommand expectedFindCommand =
                new FindCommand(new HoursEqualPredicate(Integer.parseInt(VALID_HOURSWORKED_AMY)));
        try {
            // Test equals comparison
            FindCommand parsedFindCommand = parser.parse(" " + PREFIX_HOURSWORKED + "=" + VALID_HOURSWORKED_AMY);
            assertPredicatesAreEqual(expectedFindCommand, parsedFindCommand, AMY);
            assertPredicatesAreEqual(expectedFindCommand, parsedFindCommand, BOB);
            assertTrue(parsedFindCommand.getPredicate().test(AMY));
            assertFalse(parsedFindCommand.getPredicate().test(BOB));

            // Test more than equal comparison
            parsedFindCommand = parser.parse(" " + PREFIX_HOURSWORKED + ">=" + VALID_HOURSWORKED_AMY);
            expectedFindCommand =
                    new FindCommand(new HoursMoreThanEqualPredicate(Integer.parseInt(VALID_HOURSWORKED_AMY)));
            assertPredicatesAreEqual(expectedFindCommand, parsedFindCommand, AMY);
            assertPredicatesAreEqual(expectedFindCommand, parsedFindCommand, BOB);
            assertTrue(parsedFindCommand.getPredicate().test(AMY));
            assertFalse(parsedFindCommand.getPredicate().test(BOB));

            // Test less than comparison
            parsedFindCommand = parser.parse(" " + PREFIX_HOURSWORKED + "<" + VALID_HOURSWORKED_AMY);
            expectedFindCommand =
                    new FindCommand(new HoursLessThanPredicate(Integer.parseInt(VALID_HOURSWORKED_AMY)));
            assertPredicatesAreEqual(expectedFindCommand, parsedFindCommand, AMY);
            assertPredicatesAreEqual(expectedFindCommand, parsedFindCommand, BOB);
            assertFalse(parsedFindCommand.getPredicate().test(AMY));
            assertTrue(parsedFindCommand.getPredicate().test(BOB));
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    @Test
    public void parse_validLeavesLeft_returnsFindCommand() {
        FindCommand expectedFindCommand =
                new FindCommand(new LeaveEqualPredicate(Integer.parseInt(VALID_LEAVES_AMY)));
        try {
            // Test equals comparison
            FindCommand parsedFindCommand = parser.parse(" " + PREFIX_LEAVE + "=" + VALID_LEAVES_AMY);
            assertPredicatesAreEqual(expectedFindCommand, parsedFindCommand, AMY);
            assertPredicatesAreEqual(expectedFindCommand, parsedFindCommand, BOB);
            assertTrue(parsedFindCommand.getPredicate().test(AMY));
            assertFalse(parsedFindCommand.getPredicate().test(BOB));

            // Test more than equal comparison
            parsedFindCommand = parser.parse(" " + PREFIX_LEAVE + ">=" + VALID_LEAVES_AMY);
            expectedFindCommand =
                    new FindCommand(new LeaveMoreThanEqualPredicate(Integer.parseInt(VALID_LEAVES_AMY)));
            assertPredicatesAreEqual(expectedFindCommand, parsedFindCommand, AMY);
            assertPredicatesAreEqual(expectedFindCommand, parsedFindCommand, BOB);
            assertTrue(parsedFindCommand.getPredicate().test(AMY));
            assertFalse(parsedFindCommand.getPredicate().test(BOB));

            // Test less than comparison
            parsedFindCommand = parser.parse(" " + PREFIX_LEAVE + "<" + VALID_LEAVES_AMY);
            expectedFindCommand =
                    new FindCommand(new LeaveLessThanPredicate(Integer.parseInt(VALID_LEAVES_AMY)));
            assertPredicatesAreEqual(expectedFindCommand, parsedFindCommand, AMY);
            assertPredicatesAreEqual(expectedFindCommand, parsedFindCommand, BOB);
            assertFalse(parsedFindCommand.getPredicate().test(AMY));
            assertTrue(parsedFindCommand.getPredicate().test(BOB));
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    @Test
    public void parse_validOvertime_returnsFindCommand() {
        FindCommand expectedFindCommand =
                new FindCommand(new OvertimeEqualPredicate(Integer.parseInt(VALID_OVERTIME_AMY)));
        try {
            // Test equals comparison
            FindCommand parsedFindCommand = parser.parse(" " + PREFIX_OVERTIME + "=" + VALID_OVERTIME_AMY);
            assertPredicatesAreEqual(expectedFindCommand, parsedFindCommand, AMY);
            assertPredicatesAreEqual(expectedFindCommand, parsedFindCommand, BOB);
            assertTrue(parsedFindCommand.getPredicate().test(AMY));
            assertFalse(parsedFindCommand.getPredicate().test(BOB));

            // Test less than equal comparison
            parsedFindCommand = parser.parse(" " + PREFIX_OVERTIME + "<=" + VALID_OVERTIME_AMY);
            expectedFindCommand =
                    new FindCommand(new OvertimeLessThanEqualPredicate(Integer.parseInt(VALID_OVERTIME_AMY)));
            assertPredicatesAreEqual(expectedFindCommand, parsedFindCommand, AMY);
            assertPredicatesAreEqual(expectedFindCommand, parsedFindCommand, BOB);
            assertTrue(parsedFindCommand.getPredicate().test(AMY));
            assertTrue(parsedFindCommand.getPredicate().test(BOB));

            // Test more than comparison
            parsedFindCommand = parser.parse(" " + PREFIX_OVERTIME + ">" + VALID_OVERTIME_AMY);
            expectedFindCommand =
                    new FindCommand(new OvertimeMoreThanPredicate(Integer.parseInt(VALID_OVERTIME_AMY)));
            assertPredicatesAreEqual(expectedFindCommand, parsedFindCommand, AMY);
            assertPredicatesAreEqual(expectedFindCommand, parsedFindCommand, BOB);
            assertFalse(parsedFindCommand.getPredicate().test(AMY));
            assertFalse(parsedFindCommand.getPredicate().test(BOB));
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    @Test
    public void parse_validEmailArgs_returnsFindCommand() {
        FindCommand expectedFindCommand =
                new FindCommand(new EmailContainsKeywordsPredicate(Arrays.asList("amy", "bill")));
        try {
            FindCommand parsedFindCommand = parser.parse(" " + PREFIX_EMAIL + "amy bill");
            // Amy's email contains amy => true
            // Bob's email does not contain either amy or bill => false
            assertPredicatesAreEqual(expectedFindCommand, parsedFindCommand, AMY);
            assertPredicatesAreEqual(expectedFindCommand, parsedFindCommand, BOB);
            assertTrue(parsedFindCommand.getPredicate().test(AMY));
            assertFalse(parsedFindCommand.getPredicate().test(BOB));
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    @Test
    public void parse_validTagArgs_returnsFindCommand() {
        FindCommand expectedFindCommand =
                new FindCommand(new TagContainsKeywordsPredicate(Arrays.asList("husband")));
        try {
            FindCommand parsedFindCommand = parser.parse(" " + PREFIX_TAG + "husband");
            assertPredicatesAreEqual(expectedFindCommand, parsedFindCommand, AMY);
            assertPredicatesAreEqual(expectedFindCommand, parsedFindCommand, BOB);
            assertFalse(parsedFindCommand.getPredicate().test(AMY));
            assertTrue(parsedFindCommand.getPredicate().test(BOB));
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    @Test
    public void parse_validSingleDateLeave_returnsFindCommand() {
        String validDateString = "2021-02-28";

        ArrayList<LocalDate> testLeaveDates = new ArrayList<>();
        testLeaveDates.add(LocalDate.parse(validDateString));

        ArrayList<LocalDate> personLeaveDates = new ArrayList<>();
        personLeaveDates.add(LocalDate.parse(validDateString));

        FindCommand expectedFindCommand =
                new FindCommand(new LeavesTakenContainsDatesPredicate(testLeaveDates, new ArrayList<>()));
        Person personWithNoLeave = new PersonBuilder().build();
        Person personWithLeaveOnDate = new PersonBuilder().withLeavesTaken(personLeaveDates).build();
        try {
            FindCommand parsedFindCommand = parser.parse(" " + PREFIX_DATE + "2021-02-28");
            assertPredicatesAreEqual(expectedFindCommand, parsedFindCommand, personWithNoLeave);
            assertPredicatesAreEqual(expectedFindCommand, parsedFindCommand, personWithLeaveOnDate);
            assertTrue(parsedFindCommand.getPredicate().test(personWithLeaveOnDate));
            assertFalse(parsedFindCommand.getPredicate().test(personWithNoLeave));
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    @Test
    public void parse_validDateRangeLeave_returnsFindCommand() {
        String validDateInRange = "2021-03-19";
        String validDateOutsideRange = "2021-05-20";
        String validDateRangeString = "2021-02-28:2021-03-28";

        // Create expected find command
        ArrayList<Pair<LocalDate, LocalDate>> testLeaveDateRange = new ArrayList<>();
        testLeaveDateRange.add(new Pair<>(LocalDate.parse("2021-02-28"), LocalDate.parse("2021-03-28")));

        FindCommand expectedFindCommand =
                new FindCommand(new LeavesTakenContainsDatesPredicate(new ArrayList<>(), testLeaveDateRange));

        // Create test people
        ArrayList<LocalDate> personLeaveInRangeDates = new ArrayList<>();
        personLeaveInRangeDates.add(LocalDate.parse(validDateInRange));

        ArrayList<LocalDate> personLeaveOutsideRangeDates = new ArrayList<>();
        personLeaveOutsideRangeDates.add(LocalDate.parse(validDateOutsideRange));

        Person personWithNoLeave = new PersonBuilder().build();
        Person personWithLeaveInRange = new PersonBuilder().withLeavesTaken(personLeaveInRangeDates).build();
        Person personWithLeaveOutsideRange = new PersonBuilder().withLeavesTaken(personLeaveOutsideRangeDates).build();
        try {
            FindCommand parsedFindCommand = parser.parse(" " + PREFIX_DATE + validDateRangeString);
            assertPredicatesAreEqual(expectedFindCommand, parsedFindCommand, personWithNoLeave);
            assertPredicatesAreEqual(expectedFindCommand, parsedFindCommand, personWithLeaveInRange);
            assertPredicatesAreEqual(expectedFindCommand, parsedFindCommand, personWithLeaveOutsideRange);

            // no leave at all => false
            assertFalse(parsedFindCommand.getPredicate().test(personWithNoLeave));

            // leave in range => true
            assertTrue(parsedFindCommand.getPredicate().test(personWithLeaveInRange));

            // leave outside range => false
            assertFalse(parsedFindCommand.getPredicate().test(personWithLeaveOutsideRange));
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }
}

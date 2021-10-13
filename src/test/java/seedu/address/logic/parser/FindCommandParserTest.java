package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HOURLYSALARY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOURLYSALARY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.AddressContainsKeywordsPredicate;
import seedu.address.model.person.EmailContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.PhoneNumberMatchesPredicate;
import seedu.address.model.person.RoleContainsKeywordsPredicate;
import seedu.address.model.person.SalaryIsEqualPredicate;
import seedu.address.model.person.TagContainsKeywordsPredicate;

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

    /**
     * Since FindCommandParser now combines all predicates together with Stream::reduce,
     * the FindCommands given cannot be directly tested for equality.
     * Current workaround: test both predicates of FindCommand with given
     * inputs and see if both predicates return the same result for the inputs.
     */
    @Test
    public void parse_validNameArgs_returnsFindCommand() {
        FindCommand expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("Amy", "Bryan")));
        try {
            FindCommand parsedFindCommand = parser.parse(" " + PREFIX_NAME + "Amy Bryan");
            assertPredicatesAreEqual(expectedFindCommand, parsedFindCommand, AMY); // true for both
            assertPredicatesAreEqual(expectedFindCommand, parsedFindCommand, BOB); // false for both
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
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    @Test
    public void parse_validRoleArgs_returnsFindCommand() {
        FindCommand expectedFindCommand =
                new FindCommand(new RoleContainsKeywordsPredicate(Arrays.asList("Mobile", "Front-end")));
        try {
            FindCommand parsedFindCommand = parser.parse(" " + PREFIX_ROLE + "Mobile Front-end");
            // Amy's Role is Mobile App Developer => true
            // Bob's Role is Front End Developer => true
            assertPredicatesAreEqual(expectedFindCommand, parsedFindCommand, AMY);
            assertPredicatesAreEqual(expectedFindCommand, parsedFindCommand, BOB);
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
            // Bob's Role is Front End Developer => true
            assertPredicatesAreEqual(expectedFindCommand, parsedFindCommand, AMY);
            assertPredicatesAreEqual(expectedFindCommand, parsedFindCommand, BOB);
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
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    @Test
    public void parse_validSalary_returnsFindCommand() {
        FindCommand expectedFindCommand =
                new FindCommand(new SalaryIsEqualPredicate(Float.parseFloat(VALID_HOURLYSALARY_AMY)));
        try {
            FindCommand parsedFindCommand = parser.parse(" " + PREFIX_HOURLYSALARY + "=" + VALID_HOURLYSALARY_AMY);
            assertPredicatesAreEqual(expectedFindCommand, parsedFindCommand, AMY);
            assertPredicatesAreEqual(expectedFindCommand, parsedFindCommand, BOB);
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
            assertPredicatesAreEqual(expectedFindCommand, parsedFindCommand, AMY); // true for both
            assertPredicatesAreEqual(expectedFindCommand, parsedFindCommand, BOB); // false for both
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
            assertPredicatesAreEqual(expectedFindCommand, parsedFindCommand, AMY); // true for both
            assertPredicatesAreEqual(expectedFindCommand, parsedFindCommand, BOB); // false for both
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }
}

package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOURLYSALARY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOURSWORKED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.AddressContainsKeywordsPredicate;
import seedu.address.model.person.EmailContainsKeywordsPredicate;
import seedu.address.model.person.LeavesTakenContainsDatesPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.PhoneNumberMatchesPredicate;
import seedu.address.model.person.RoleContainsKeywordsPredicate;
import seedu.address.model.person.SalaryIsEqualPredicate;
import seedu.address.model.person.SalaryIsLessThanEqualPredicate;
import seedu.address.model.person.SalaryIsLessThanPredicate;
import seedu.address.model.person.SalaryIsMoreThanEqualPredicate;
import seedu.address.model.person.SalaryIsMoreThanPredicate;
import seedu.address.model.person.TagContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_ROLE,
                        PREFIX_LEAVE, PREFIX_DATE, PREFIX_HOURLYSALARY, PREFIX_HOURSWORKED, PREFIX_TAG);

        ArrayList<Predicate<Person>> filters = new ArrayList<>();
        // To modify in the future: Simply add a predicate for each relevant tag
        // Refer to EditCommandParser
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            String[] nameKeywords = argMultimap.getValue(PREFIX_NAME).get().split("\\s+");
            filters.add(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            String[] phoneKeyNumbers = argMultimap.getValue(PREFIX_PHONE).get().split("\\s+");
            filters.add(new PhoneNumberMatchesPredicate(Arrays.asList(phoneKeyNumbers)));
        }
        if (argMultimap.getValue(PREFIX_TAG).isPresent()) {
            String[] tagKeywords = argMultimap.getValue(PREFIX_TAG).get().split("\\s+");
            filters.add(new TagContainsKeywordsPredicate(Arrays.asList(tagKeywords)));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            String[] emailKeywords = argMultimap.getValue(PREFIX_EMAIL).get().split("\\s+");
            filters.add(new EmailContainsKeywordsPredicate(Arrays.asList(emailKeywords)));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            String[] addressKeywords = argMultimap.getValue(PREFIX_ADDRESS).get().split("\\s+");
            filters.add(new AddressContainsKeywordsPredicate(Arrays.asList(addressKeywords)));
        }
        if (argMultimap.getValue(PREFIX_ROLE).isPresent()) {
            String[] roleKeywords = argMultimap.getValue(PREFIX_ROLE).get().split("\\s+");
            filters.add(new RoleContainsKeywordsPredicate(Arrays.asList(roleKeywords)));
        }
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            String[] keyDates = argMultimap.getValue(PREFIX_DATE).get().split("\\s+");
            filters.add(new LeavesTakenContainsDatesPredicate(Arrays.asList(keyDates)));
        }
        if (argMultimap.getValue(PREFIX_HOURLYSALARY).isPresent()) {
            String keyValue = argMultimap.getValue(PREFIX_HOURLYSALARY).get();
            Predicate<Person> personPredicate = parseSalaryComparison(keyValue);
            filters.add(personPredicate);
        }

        return new FindCommand(combinePredicates(filters));
    }

    /**
     * Combines the list of predicates in a list to a single predicate for the FindCommand to take in
     *
     * @param predicateList The list of predicates to combine
     */
    private Predicate<Person> combinePredicates(List<Predicate<Person>> predicateList) {
        return predicateList.stream()
                .reduce(x -> true, (predicate1, predicate2) -> predicate1.and(predicate2));
    }

    /**
     * Used for parsing the input given by the user for finding with respect to salary
     * @param input A string describing the condition for the salary of the person.
     *              It should take the form of (comparator)(number), where the comparator is any of:
     *              ">", "<", ">=", "<=", "="
     *              Valid examples: >=5, <3.25, =6.00
     *              Invalid examples: =>5, ==4.50, 6
     *
     * @return A Predicate which checks if the person passes the given condition as described in the input.
     */
    private Predicate<Person> parseSalaryComparison(String input) throws ParseException {
        try {
            String comparator = input.substring(0, 2);
            if (comparator.equals(">=")) {
                float value = Float.parseFloat(input.substring(2));
                return new SalaryIsMoreThanEqualPredicate(value);
            } else if (comparator.equals("<=")) {
                float value = Float.parseFloat(input.substring(2));
                return new SalaryIsLessThanEqualPredicate(value);
            } else if (comparator.charAt(0) == '>') {
                float value = Float.parseFloat(input.substring(1));
                return new SalaryIsMoreThanPredicate(value);
            } else if (comparator.charAt(0) == '<') {
                float value = Float.parseFloat(input.substring(1));
                return new SalaryIsLessThanPredicate(value);
            } else if (comparator.charAt(0) == '=') {
                float value = Float.parseFloat(input.substring(1));
                return new SalaryIsEqualPredicate(value);
            } else {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
    }
}

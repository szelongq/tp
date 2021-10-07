package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.EmailContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.PhoneNumberMatchesPredicate;
import seedu.address.model.person.TagContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    List<Predicate<Person>> filters = new ArrayList<>();

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
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);

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

        return new FindCommand(this.combinePredicates(filters));
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
}

package seedu.address.model.person.predicates;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Tag} contains any of the keywords given.
 */
public class TagContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public TagContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    /**
     * Test if the person contains tags whose string value contains any of the keywords. Not case sensitive.
     * This allows find to search for people tagged with "friends" with the command "find t/friend"
     * This only works since tags are alphanumeric and contain no whitespaces unlike names.
     * This implementation allows queries like find t/e to return every person with a tag that contains the letter e.
     * May not be intended but will need to find a better method of testing.
     *
     * @param person The person whose tags are to be tested
     * @return True if any tags that the person has contains any of the queried keywords.
     */
    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> (person.getTags().stream()
                        .anyMatch(tag -> tag.tagName.toLowerCase().contains(keyword.toLowerCase()))));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((TagContainsKeywordsPredicate) other).keywords)); // state check
    }
}

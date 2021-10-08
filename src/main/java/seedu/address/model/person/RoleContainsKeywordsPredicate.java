package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

public class RoleContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public RoleContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    /**
     * Tests if the person given contains the given keywords in their address. Not case sensitive.
     * @param person The person whose tags are to be tested
     * @return True if any tags that the person has contains any of the queried keywords.
     */
    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> (person.getRole().value.toLowerCase().contains(keyword.toLowerCase())));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RoleContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((RoleContainsKeywordsPredicate) other).keywords)); // state check
    }
}

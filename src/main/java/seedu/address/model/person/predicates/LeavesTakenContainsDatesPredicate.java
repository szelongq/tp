package seedu.address.model.person.predicates;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code LeavesTaken} contains any of the dates given.
 * A match is found if the specified date is present.
 */
public class LeavesTakenContainsDatesPredicate implements Predicate<Person> {
    private final List<String> dates;

    public LeavesTakenContainsDatesPredicate(List<String> dates) {
        this.dates = dates;
    }

    /**
     * Tests if the person given contains the given dates in their assigned leaves.
     * @param person The person whose tags are to be tested
     * @return True if any tags that the person has contains any of the queried dates.
     */
    @Override
    public boolean test(Person person) {
        return dates.stream()
                .anyMatch(date -> StringUtil.containsWordIgnoreCase(person.getLeavesTaken().toString(), date));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LeavesTakenContainsDatesPredicate // instanceof handles nulls
                && dates.equals(((LeavesTakenContainsDatesPredicate) other).dates)); // state check
    }
}

package seedu.address.model.person.predicates;

import java.util.function.Predicate;

import seedu.address.model.person.Person;

public class LeaveMoreThanEqualPredicate implements Predicate<Person> {
    private final int value;

    public LeaveMoreThanEqualPredicate(int value) {
        this.value = value;
    }

    /**
     * Tests if the person given has a number of leaves more than or equal the given value.
     * @param person The person whose number of leaves is to be tested
     * @return true if the person's number of leaves is more than or equal the given value.
     */
    @Override
    public boolean test(Person person) {
        return person.getLeaveBalance().value >= this.value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LeaveMoreThanEqualPredicate // instanceof handles nulls
                && value == ((LeaveMoreThanEqualPredicate) other).value); // state check
    }
}

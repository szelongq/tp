package seedu.address.model.person.predicates;

import java.util.function.Predicate;

import seedu.address.model.person.Person;

public class LeaveEqualPredicate implements Predicate<Person> {
    private final int value;

    public LeaveEqualPredicate(int value) {
        this.value = value;
    }

    /**
     * Tests if the person given has overtime strictly less than the given value.
     * @param person The person whose overtime is to be tested
     * @return true if the person's overtime is strictly less than the given value.
     */
    @Override
    public boolean test(Person person) {
        return person.getLeaves().value == this.value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LeaveEqualPredicate // instanceof handles nulls
                && value == ((LeaveEqualPredicate) other).value); // state check
    }
}

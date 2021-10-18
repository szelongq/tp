package seedu.address.model.person.predicates;

import java.util.function.Predicate;

import seedu.address.model.person.Person;

public class LeaveMoreThanPredicate implements Predicate<Person> {
    private final int value;

    public LeaveMoreThanPredicate(int value) {
        this.value = value;
    }

    /**
     * Tests if the person given has overtime strictly more than the given value.
     * @param person The person whose overtime is to be tested
     * @return true if the person's overtime is strictly more than the given value.
     */
    @Override
    public boolean test(Person person) {
        return person.getLeaves().value > this.value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LeaveMoreThanPredicate // instanceof handles nulls
                && value == ((LeaveMoreThanPredicate) other).value); // state check
    }
}

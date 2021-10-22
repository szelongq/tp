package seedu.address.model.person.predicates;

import java.util.function.Predicate;

import seedu.address.model.person.Person;

public class HoursMoreThanEqualPredicate implements Predicate<Person> {
    private final int value;

    public HoursMoreThanEqualPredicate(int value) {
        this.value = value;
    }

    /**
     * Tests if the person given has worked a number of hours more than or equal the given value.
     * @param person The person whose hours worked is to be tested
     * @return true if the person's number of hours worked is more than or equal the given value.
     */
    @Override
    public boolean test(Person person) {
        return person.getHoursWorked().value >= this.value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof HoursMoreThanEqualPredicate // instanceof handles nulls
                && value == ((HoursMoreThanEqualPredicate) other).value); // state check
    }
}

package seedu.address.model.person.predicates;

import java.util.function.Predicate;

import seedu.address.model.person.Person;

public class HoursMoreThanPredicate implements Predicate<Person> {
    private final int value;

    public HoursMoreThanPredicate(int value) {
        this.value = value;
    }

    /**
     * Tests if the person given has worked a number of hours strictly more than the given value.
     * @param person The person whose hours worked is to be tested
     * @return true if the person's number of hours worked is strictly more than the given value.
     */
    @Override
    public boolean test(Person person) {
        return person.getHoursWorked().value > this.value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof HoursMoreThanPredicate // instanceof handles nulls
                && value == ((HoursMoreThanPredicate) other).value); // state check
    }
}

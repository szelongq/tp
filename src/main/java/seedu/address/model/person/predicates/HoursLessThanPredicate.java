package seedu.address.model.person.predicates;

import java.util.function.Predicate;

import seedu.address.model.person.Person;

public class HoursLessThanPredicate implements Predicate<Person> {
    private final int value;

    public HoursLessThanPredicate(int value) {
        this.value = value;
    }

    /**
     * Tests if the person given has worked a number of hours strictly less than the given value.
     * @param person The person whose hours worked is to be tested
     * @return true if the person's number of hours worked is strictly less than the given value.
     */
    @Override
    public boolean test(Person person) {
        return person.getHoursWorked().value < this.value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof HoursLessThanPredicate // instanceof handles nulls
                && value == ((HoursLessThanPredicate) other).value); // state check
    }
}

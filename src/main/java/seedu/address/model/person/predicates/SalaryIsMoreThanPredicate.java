package seedu.address.model.person.predicates;

import java.util.function.Predicate;

import seedu.address.model.person.Person;

public class SalaryIsMoreThanPredicate implements Predicate<Person> {
    private final double value;

    public SalaryIsMoreThanPredicate(double value) {
        this.value = value;
    }

    /**
     * Tests if the person given has a salary strictly more than the given value.
     * @param person The person whose salary is to be tested
     * @return true if the person's salary is strictly more than the given value.
     */
    @Override
    public boolean test(Person person) {
        return person.getSalary().value > this.value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SalaryIsMoreThanPredicate // instanceof handles nulls
                && value == ((SalaryIsMoreThanPredicate) other).value); // state check
    }
}

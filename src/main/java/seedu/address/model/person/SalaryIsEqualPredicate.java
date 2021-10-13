package seedu.address.model.person;

import java.util.function.Predicate;

public class SalaryIsEqualPredicate implements Predicate<Person> {
    private final float value;

    public SalaryIsEqualPredicate(float value) {
        this.value = value;
    }

    /**
     * Tests if the person given has a salary equal to the given value.
     * @param person The person whose salary is to be tested
     * @return true if the person's salary is equal to the given value.
     */
    @Override
    public boolean test(Person person) {
        return person.getSalary().value == this.value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SalaryIsEqualPredicate // instanceof handles nulls
                && value == ((SalaryIsEqualPredicate) other).value); // state check
    }
}

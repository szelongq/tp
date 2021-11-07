package seedu.address.model.person.predicates;

import java.util.function.Predicate;

import seedu.address.model.person.Person;

public class SalaryIsEqualPredicate implements Predicate<Person> {
    private final double value;

    private final double threshold = 0.01; // Salaries are 2 d.p

    public SalaryIsEqualPredicate(double value) {
        this.value = value;
    }

    /**
     * Tests if the person given has a salary equal to the given value.
     * @param person The person whose salary is to be tested
     * @return true if the person's salary is equal to the given value.
     */
    @Override
    public boolean test(Person person) {
        double salaryToCompare = person.getSalary().value;
        return (Math.abs(salaryToCompare - value) < threshold);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SalaryIsEqualPredicate // instanceof handles nulls
                && value == ((SalaryIsEqualPredicate) other).value); // state check
    }
}

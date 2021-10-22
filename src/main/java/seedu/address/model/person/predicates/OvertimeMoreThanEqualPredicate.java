package seedu.address.model.person.predicates;

import java.util.function.Predicate;

import seedu.address.model.person.Person;

public class OvertimeMoreThanEqualPredicate implements Predicate<Person> {
    private final int value;

    public OvertimeMoreThanEqualPredicate(int value) {
        this.value = value;
    }

    /**
     * Tests if the person given has overtime more than or equal the given value.
     * @param person The person whose overtime is to be tested
     * @return true if the person's overtime is more than or equal the given value.
     */
    @Override
    public boolean test(Person person) {
        return person.getOvertime().value >= this.value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof OvertimeMoreThanEqualPredicate // instanceof handles nulls
                && value == ((OvertimeMoreThanEqualPredicate) other).value); // state check
    }
}

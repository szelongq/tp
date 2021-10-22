package seedu.address.model.person.predicates;

import java.util.function.Predicate;

import seedu.address.model.person.Person;

public class OvertimeLessThanEqualPredicate implements Predicate<Person> {
    private final float value;

    public OvertimeLessThanEqualPredicate(float value) {
        this.value = value;
    }

    /**
     * Tests if the person given has a overtime less than or equal the given value.
     * @param person The person whose overtime is to be tested
     * @return true if the person's overtime is less than or equal the given value.
     */
    @Override
    public boolean test(Person person) {
        return person.getOvertime().value <= this.value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof OvertimeLessThanEqualPredicate // instanceof handles nulls
                && value == ((OvertimeLessThanEqualPredicate) other).value); // state check
    }
}

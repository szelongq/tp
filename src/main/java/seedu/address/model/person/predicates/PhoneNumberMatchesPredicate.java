package seedu.address.model.person.predicates;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Phone} matches the specified phone number.
 * Phones match if their values are exactly the same.
 */
public class PhoneNumberMatchesPredicate implements Predicate<Person> {
    private final List<String> keyNumbers;

    public PhoneNumberMatchesPredicate(List<String> keyNumbers) {
        this.keyNumbers = keyNumbers;
    }

    /**
     * Tests if the person given contains the given numbers in their Phone Number.
     * @param person The person whose tags are to be tested
     * @return True if any tags that the person has contains the exact value.
     */
    @Override
    public boolean test(Person person) {
        String numberToTest = person.getPhone().value;
        return keyNumbers.stream()
                .anyMatch(number -> numberToTest.equals(number));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PhoneNumberMatchesPredicate // instanceof handles nulls
                && keyNumbers.equals(((PhoneNumberMatchesPredicate) other).keyNumbers)); // state check
    }
}

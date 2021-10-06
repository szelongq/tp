package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

public class PhoneNumberMatchesPredicate implements Predicate<Person> {
    private final List<String> keyNumbers;

    public PhoneNumberMatchesPredicate(List<String> keyNumbers) {
        this.keyNumbers = keyNumbers;
    }

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

package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's worked hours in the employee book.
 * Guarantees: immutable; is valid as declared in {@link #isValidHours(String)}
 */
public class HoursWorked {

    public static final String MESSAGE_CONSTRAINTS =
            "HoursWorked should only contain positive integers, and it should not be blank";

    public final int value;

    /**
     * Constructs a {@code HoursWorked}.
     *
     * @param amount A valid value in hours.
     */
    public HoursWorked(String amount) {
        requireNonNull(amount);
        checkArgument(isValidHours(amount), MESSAGE_CONSTRAINTS);
        this.value = Integer.parseInt(amount);
    }

    /**
     * Returns true if a given string is a valid name for a role.
     */
    public static boolean isValidHours(String test) {
        try {
            int amount = Integer.parseInt(test);
            return amount > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Salary // instanceof handles nulls
                && value == ((HoursWorked) other).value); // state check
    }

}


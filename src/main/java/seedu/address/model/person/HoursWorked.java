package seedu.address.model.person;

import seedu.address.commons.util.StringUtil;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's worked hours in the employee book.
 * Guarantees: immutable; is valid as declared in {@link #isValidHoursWorked(String)}
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
        checkArgument(isValidHoursWorked(amount), MESSAGE_CONSTRAINTS);
        this.value = Integer.parseInt(amount);
    }

    /**
     * Returns true if a given numerical string is non-negative.
     */
    public static boolean isValidHoursWorked(String test) {
        requireNonNull(test);

        return StringUtil.isNonNegativeInteger(test);
    }

    /**
     * Returns an updated HoursWorked object with the specified number of hours worked added.
     *
     * @param hoursWorked The number of hours worked to be added.
     * @return An updated HoursWorked object.
     */
    public HoursWorked addHoursWorked(HoursWorked hoursWorked) {
        int updatedValue = value + hoursWorked.value;
        return new HoursWorked(String.valueOf(updatedValue));
    }

    /**
     * Returns an updated HoursWorked object with the specified number of hours worked removed.
     *
     * @param hoursWorked The number of hours worked to be removed.
     * @return An updated HoursWorked object.
     * @throws IllegalArgumentException if the number of hours worked to be removed
     * is greater than the current number of hours worked.
     */
    public HoursWorked removeHoursWorked(HoursWorked hoursWorked) {
        int updatedValue = value - hoursWorked.value;
        if (updatedValue < 0) {
            throw new IllegalArgumentException();
        }
        return new HoursWorked(String.valueOf(updatedValue));
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof HoursWorked // instanceof handles nulls
                && value == ((HoursWorked) other).value); // state check
    }
}

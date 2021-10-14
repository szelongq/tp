package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's overtime in the employee book.
 * Guarantees: immutable; is valid as declared in {@link #isValidOvertime(String)}
 */
public class Overtime {

    public static final String MESSAGE_CONSTRAINTS =
            "Overtime should only contain a non-negative integer, and it should not be blank";

    public final int value;

    /**
     * Constructs an {@code Overtime} object.
     *
     * @param amount A valid value in hours.
     */
    public Overtime(String amount) {
        requireNonNull(amount);
        checkArgument(isValidOvertime(amount), MESSAGE_CONSTRAINTS);
        this.value = Integer.parseInt(amount);
    }

    /**
     * Returns true if a given numerical string is non-negative.
     */
    public static boolean isValidOvertime(String test) {
        try {
            int amount = Integer.parseInt(test);
            return amount >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Returns an updated Overtime object with the specified number of overtime hours added.
     *
     * @param overtime The number of overtime hours to be added.
     * @return An updated Overtime object.
     */
    public Overtime addOvertime(Overtime overtime) {
        int updatedValue = value + overtime.value;
        return new Overtime(String.valueOf(updatedValue));
    }

    /**
     * Returns an updated Overtime object with the specified number of overtime hours removed.
     *
     * @param overtime The number of overtime hours to be removed.
     * @return An updated Overtime object.
     * @throws IllegalArgumentException if the number of overtime hours to be removed
     * is greater than the current number of overtime hours.
     */
    public Overtime removeOvertime(Overtime overtime) {
        int updatedValue = value - overtime.value;
        if (updatedValue < 0) {
            throw new IllegalArgumentException();
        }
        return new Overtime(String.valueOf(updatedValue));
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Overtime // instanceof handles nulls
                && value == ((Overtime) other).value); // state check
    }
}

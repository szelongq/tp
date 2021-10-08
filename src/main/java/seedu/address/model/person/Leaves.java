package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's remaining leaves in the employee book.
 * Guarantees: immutable; is valid as declared in {@link #isValidLeaves(String)}
 */
public class Leaves {

    public static final String MESSAGE_CONSTRAINTS =
            "Leaves should only contain non-negative integers, and it should not be blank";

    public final int value;

    /**
     * Constructs a {@code Leaves} object.
     *
     * @param amount A valid leave amount in days.
     */
    public Leaves(String amount) {
        requireNonNull(amount);
        checkArgument(isValidLeaves(amount), MESSAGE_CONSTRAINTS);
        this.value = Integer.parseInt(amount);
    }

    /**
     * Returns true if a given leave is a non-negative numerical value.
     *
     * @param test The string input that is to be parsed into an integer.
     * @return True if the string is a non-negative integer, false otherwise.
     */
    public static boolean isValidLeaves(String test) {
        requireNonNull(test);
        try {
            int amount = Integer.parseInt(test);
            return amount >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Returns an updated Leaves object with the specified number of leaves added.
     *
     * @param leaves The amount of leaves to be added.
     * @return An updated Leaves object.
     */
    public Leaves addLeaves(Leaves leaves) {
        int updatedValue = value + leaves.value;
        return new Leaves(String.valueOf(updatedValue));
    }

    /**
     * Returns an updated Leaves object with the specified number of leaves removed.
     *
     * @param leaves The amount of leaves to be removed.
     * @return An updated Leaves object.
     * @throws IllegalArgumentException if the amount of leaves to be removed
     * is greater than the current amount of leaves.
     */
    public Leaves removeLeaves(Leaves leaves) {
        int updatedValue = value - leaves.value;
        if (updatedValue < 0) {
            throw new IllegalArgumentException();
        }
        return new Leaves(String.valueOf(updatedValue));
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Leaves // instanceof handles nulls
                && value == ((Leaves) other).value); // state check
    }
}

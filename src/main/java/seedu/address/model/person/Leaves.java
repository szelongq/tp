package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's remaining leaves in the employee book.
 * Guarantees: immutable; is valid as declared in {@link #isValidLeaves(String)}
 */
public class Leaves {

    public static final String MESSAGE_CONSTRAINTS =
            "Leaves should only contain positive integers, and it should not be blank";

    public final int value;

    /**
     * Constructs a {@code Leave}.
     *
     * @param amount A valid leave amount in days.
     */
    public Leaves(String amount) {
        requireNonNull(amount);
        checkArgument(isValidLeaves(amount), MESSAGE_CONSTRAINTS);
        this.value = Integer.parseInt(amount);
    }

    /**
     * Returns true if a given leave is a positive numerical value.
     */
    public static boolean isValidLeaves(String test) {
        try {
            int amount = Integer.parseInt(test);
            return amount >= 0;
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
                && value == ((Leaves) other).value); // state check
    }

}


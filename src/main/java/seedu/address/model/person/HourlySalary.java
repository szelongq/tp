package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's Salary in the employee book.
 * Guarantees: immutable; is valid as declared in {@link #isValidSalary(String)}
 */
public class HourlySalary {

    public static final String MESSAGE_CONSTRAINTS =
            "Salary should only contain positive integers, and it should not be blank";

    public final double value;

    /**
     * Constructs a {@code Salary}.
     *
     * @param amount A valid salary amount.
     */
    public HourlySalary(String amount) {
        requireNonNull(amount);
        checkArgument(isValidSalary(amount), MESSAGE_CONSTRAINTS);
        this.value = Double.parseDouble(amount);
    }

    /**
     * Returns true if a given numerical string is non-negative.
     */
    public static boolean isValidSalary(String test) {
        requireNonNull(test);
        try {
            double amount = Double.parseDouble(test);
            return amount >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    @Override
    public String toString() {
        return String.format("%.2f", value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof HourlySalary // instanceof handles nulls
                && value == ((HourlySalary) other).value); // state check
    }

}


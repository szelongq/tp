package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.io.Serializable;

/**
 * Represents a pay rate for overtime used in the employee payroll calculations.
 * Guarantees: immutable; is valid as declared in {@link #isValidOvertimePayRate(String)}
 */
public class OvertimePayRate implements Serializable {

    public static final String MESSAGE_CONSTRAINTS =
            "Overtime pay rate should only be a number greater than or equals to 1,"
                    + " and it should not be blank.";

    public final double value;

    /**
     * Constructs a {@code OvertimePayRate} with a default overtime pay rate of 1.5.
     */
    public OvertimePayRate() {
        this.value = 1.5;
    }

    /**
     * Constructs a {@code OvertimePayRate}.
     *
     * @param rate A valid overtime pay rate.
     */
    public OvertimePayRate(String rate) {
        requireNonNull(rate);
        checkArgument(isValidOvertimePayRate(rate), MESSAGE_CONSTRAINTS);
        this.value = Double.parseDouble(rate);
    }

    /**
     * Returns true if a given string is a valid numerical string and
     * is greater than or equals to 1.
     */
    public static boolean isValidOvertimePayRate(String test) {
        requireNonNull(test);
        boolean isValidRate = true;

        try {
            double value = Double.parseDouble(test);
            if (value < 1 || test.startsWith("+")) {
                isValidRate = false;
            }
        } catch (NumberFormatException nfe) {
            isValidRate = false;
        }

        return isValidRate;
    }

    @Override
    public String toString() {
        return String.format("%f", value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof OvertimePayRate // instanceof handles nulls
                && value == ((OvertimePayRate) other).value); // state check
    }

}


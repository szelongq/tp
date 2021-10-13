package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class CalculatedPay {

    public static final String MESSAGE_CONSTRAINTS =
            "Calculated pay should only contain non-negative values.";

    public final double value;

    /**
     * Constructs a {@code CalculatedPay}.
     *
     * @param amount A valid calculated pay.
     */
    public CalculatedPay(String amount) {
        requireNonNull(amount);
        checkArgument(isValidCalculatedPay(amount), MESSAGE_CONSTRAINTS);
        this.value = Double.parseDouble(amount);
    }

    /**
     * Returns true if given numerical string is non-negative.
     */
    public static boolean isValidCalculatedPay(String test) {
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
                || (other instanceof CalculatedPay // instanceof handles nulls
                && value == ((CalculatedPay) other).value); // state check
    }
}

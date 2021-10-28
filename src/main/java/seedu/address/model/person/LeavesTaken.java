package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Represents a Person's taken leaves in the employee book.
 * Guarantees: immutable;
 */
public class LeavesTaken {

    public static final String MESSAGE_CONSTRAINTS =
            "Leaves should only contain non-negative integers, and it should not be blank";

    // Dates are sorted in ascending order
    public final PriorityQueue<LocalDate> value;


    /**
     * Constructs a {@code LeavesTaken} object.
     */
    public LeavesTaken() {
        this.value = new PriorityQueue<>(LocalDate::compareTo);
    }

    /**
     * Constructs a {@code LeavesTaken} object.
     *
     * @param dates A list representing a sequence of dates.
     */
    public LeavesTaken(List<LocalDate> dates) {
        requireNonNull(dates);
        this.value = new PriorityQueue<>(LocalDate::compareTo);
        value.addAll(dates);
    }

    /**
     * Returns an updated LeavesTaken object with the specified date added.
     *
     * @param date The date that is to be added.
     * @return An updated LeavesTaken object.
     */
    public LeavesTaken addDate(LocalDate date) {
        List<LocalDate> dates = new ArrayList<>(value);
        dates.add(date);
        return new LeavesTaken(dates);
    }

    /**
     * Returns an updated LeavesTaken object with the specified date removed.
     *
     * @param date The date that is to be removed.
     * @return An updated LeavesTaken object.
     * @throws IllegalArgumentException if the specified date is not present.
     */
    public LeavesTaken removeDate(LocalDate date) {
        List<LocalDate> dates = new ArrayList<>(value);
        boolean dateExists = dates.remove(date);
        if (!dateExists) {
            throw new IllegalArgumentException();
        }
        return new LeavesTaken(dates);
    }

    public List<LocalDate> toList() {
        return new ArrayList<>(value);
    }

    @Override
    public String toString() {
        List<LocalDate> dates = new ArrayList<>(value);
        StringBuilder datesString = new StringBuilder();
        for (LocalDate date : dates) {
            datesString.append(date.toString()).append(" ");
        }
        return datesString.toString().trim();
    }

    /**
     * Builds a string to list the dates of the leaves taken better formatted to display to the user.
     * Numbers the dates in order and adds newline per date.
     * @return String to be displayed
     */
    public String toDisplayString() {
        List<LocalDate> dates = new ArrayList<>(value);
        if (dates.isEmpty()) {
            return "No leaves assigned yet!";
        }

        StringBuilder displayString = new StringBuilder();
        displayString.append("Leave dates applied:\n");
        for (int index = 1; index <= dates.size(); index++) {
            displayString.append(index).append(". ");
            displayString.append(dates.get(index - 1).toString()).append("\n");
        }
        return displayString.toString().trim();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LeavesTaken) // instanceof handles nulls
                && Arrays.equals(value.toArray(), ((LeavesTaken) other).value.toArray()); // state check
    }
}

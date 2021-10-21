package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Date;
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
    public final PriorityQueue<Date> value;


    /**
     * Constructs a {@code LeavesTaken} object.
     */
    public LeavesTaken() {
        this.value = new PriorityQueue<>(Date::compareTo);
    }

    /**
     * Constructs a {@code LeavesTaken} object.
     *
     * @param dates A list representing a sequence of dates.
     */
    public LeavesTaken(List<Date> dates) {
        requireNonNull(dates);
        this.value = new PriorityQueue<>(Date::compareTo);
        value.addAll(dates);
    }

    /**
     * Returns an updated LeavesTaken object with the specified date added.
     *
     * @param date The date that is to be added.
     * @return An updated LeavesTaken object.
     */
    public LeavesTaken addDate(Date date) {
        List<Date> dates = new ArrayList<>(value);
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
    public LeavesTaken removeDate(Date date) {
        List<Date> dates = new ArrayList<>(value);
        boolean dateExists = dates.remove(date);
        if (!dateExists) {
            throw new IllegalArgumentException();
        }
        return new LeavesTaken(dates);
    }

    @Override
    public String toString() {
        List<Date> dates = new ArrayList<>(value);
        StringBuilder datesString = new StringBuilder();
        for (Date date : dates) {
            datesString.append(date.toString()).append(" ");
        }
        return datesString.toString().trim();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LeavesTaken // instanceof handles nulls
                && value == ((LeavesTaken) other).value); // state check
    }
}

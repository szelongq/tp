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
     * Returns an updated LeavesTaken object with all dates before
     * the input date removed, inclusive of the date itself.
     * E.g. if 2021-10-20 is the input, all dates before 2021-10-20
     * as well as 2021-10-20 itself will be removed.
     *
     * @param beforeDate The date that is to be used.
     * @return An updated LeavesTaken object.
     */
    public LeavesTaken removeDatesBefore(LocalDate beforeDate) {
        List<LocalDate> dates = new ArrayList<>(value);
        List<LocalDate> newDates = new ArrayList<>();
        for (LocalDate date : dates) {
            // Only keep dates that are greater than the input date
            if (date.compareTo(beforeDate) > 0) {
                newDates.add(date);
            }
        }
        return new LeavesTaken(newDates);
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

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LeavesTaken) // instanceof handles nulls
                && Arrays.equals(value.toArray(), ((LeavesTaken) other).value.toArray()); // state check
    }
}

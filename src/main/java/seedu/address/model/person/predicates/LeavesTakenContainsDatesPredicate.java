package seedu.address.model.person.predicates;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;

import javafx.util.Pair;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code LeavesTaken} contains any of the dates given,
 * as well as if the {@code LeavesTaken} object contains a date within the date ranges given.
 * A match is found if the specified date is present, or if the person has a
 * leave within the date range.
 */
public class LeavesTakenContainsDatesPredicate implements Predicate<Person> {
    private final List<LocalDate> dates;
    private final List<Pair<LocalDate, LocalDate>> dateRanges;

    /**
     * Creates a LeavesTakenContainsDatePredicate object.
     *
     * @param dates List of dates that are to be checked by the predicate.
     * @param dateRanges List of date ranges that are to be checked by the predicate.
     */
    public LeavesTakenContainsDatesPredicate(List<LocalDate> dates,
                                             List<Pair<LocalDate, LocalDate>> dateRanges) {
        this.dates = dates;
        this.dateRanges = dateRanges;
    }

    /**
     * Tests if the person given contains the given dates in their assigned leaves,
     * or the person contains a date that is within the specified date ranges.
     * @param person The person whose tags are to be tested
     * @return True if any tags that the person has contains any of the queried dates,
     * or the person contains a date that is within the specified date ranges.
     */
    @Override
    public boolean test(Person person) {
        boolean containsDate = dates.stream()
                .anyMatch(date -> person.getLeavesTaken().containsDate(date));
        boolean isWithinDateRange = dateRanges.stream()
                .anyMatch(datePair -> person.getLeavesTaken()
                        .containsDateRange(datePair.getKey(), datePair.getValue()));
        return containsDate || isWithinDateRange;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LeavesTakenContainsDatesPredicate // instanceof handles nulls
                && dates.equals(((LeavesTakenContainsDatesPredicate) other).dates)); // state check
    }
}

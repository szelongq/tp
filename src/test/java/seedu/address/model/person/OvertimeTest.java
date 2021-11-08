package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class OvertimeTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Overtime(null));
    }

    @Test
    public void constructor_negativeOvertime_throwsIllegalArgumentException() {
        String negativeOvertime = "-3.12";
        assertThrows(IllegalArgumentException.class, () -> new Overtime(negativeOvertime));
    }

    @Test
    public void constructor_negativeZeroOvertime_throwsIllegalArgumentException() {
        String negativeZeroOvertime = "-000";
        assertThrows(IllegalArgumentException.class, () -> new Overtime(negativeZeroOvertime));
    }

    @Test
    public void constructor_alphanumericOvertime_throwsIllegalArgumentException() {
        String alphanumericOvertime = "3h";
        assertThrows(IllegalArgumentException.class, () -> new Overtime(alphanumericOvertime));
    }

    @Test
    public void constructor_floatingPointOvertime_throwsIllegalArgumentException() {
        String floatingPointOvertime = "3.5";
        assertThrows(IllegalArgumentException.class,
                Overtime.MESSAGE_CONSTRAINTS, () -> new Overtime(floatingPointOvertime));
    }

    @Test
    public void isValidOvertime() {
        // null overtime
        assertThrows(NullPointerException.class, () -> Overtime.isValidOvertime(null));

        // invalid overtime (formatting)
        assertFalse(Overtime.isValidOvertime("")); // empty string
        assertFalse(Overtime.isValidOvertime(" ")); // spaces only
        assertFalse(Overtime.isValidOvertime("seven")); // contains non-numeric characters only
        assertFalse(Overtime.isValidOvertime("7h")); // contains alphanumeric characters
        assertFalse(Overtime.isValidOvertime("-0")); // contains negative 0
        assertFalse(Overtime.isValidOvertime("1.1")); // contains floating point values

        // invalid overtime (equivalence partitions: [INTEGER_MIN, -1], [745, INTEGER_MAX])
        assertFalse(Overtime.isValidOvertime("745")); // boundary value
        assertFalse(Overtime.isValidOvertime("-1")); // boundary value
        assertFalse(Overtime.isValidOvertime("12345")); // value inside equivalence partition
        assertFalse(Overtime.isValidOvertime("-12345")); // value inside equivalence partition

        // valid overtime (equivalence partition: [0, 744])
        assertTrue(Overtime.isValidOvertime("744")); // max boundary
        assertTrue(Overtime.isValidOvertime("0")); // min boundary
        assertTrue(Overtime.isValidOvertime("087")); // value inside equivalence partition
        assertTrue(Overtime.isValidOvertime("367")); // value inside equivalence partition
    }

    @Test
    public void addOvertime_addZeroOvertime_throwsAssertionError() {
        Overtime validOvertime = new Overtime("10");
        // Negative values are caught by isValidOvertime, so 0 is the only possible value to cause an assertion error
        Overtime noOvertime = new Overtime("0");
        assertThrows(AssertionError.class, () -> validOvertime.addOvertime(noOvertime));
    }

    @Test
    public void addOvertime_exceedMaxOvertime_throwsIllegalArgumentException() {
        Overtime validOvertime = new Overtime("10");
        Overtime validOtherOvertime = new Overtime("735");
        assertThrows(IllegalArgumentException.class, () -> validOvertime.addOvertime(validOtherOvertime));
    }

    @Test
    public void addOvertime_success() {
        Overtime validOvertime = new Overtime("10");
        Overtime validOtherOvertime = new Overtime("20");
        Overtime validTotalOvertime = new Overtime("30");
        assertEquals(validTotalOvertime, validOvertime.addOvertime(validOtherOvertime));
    }

    @Test
    public void removeOvertime_removeZeroOvertime_throwsAssertionError() {
        Overtime validOvertime = new Overtime("10");
        // Negative values are caught by isValidOvertime, so 0 is the only possible value to cause an assertion error
        Overtime noOvertime = new Overtime("0");
        assertThrows(AssertionError.class, () -> validOvertime.removeOvertime(noOvertime));
    }

    @Test
    public void removeOvertime_insufficientOvertime_throwsIllegalArgumentException() {
        Overtime validOvertime = new Overtime("10");
        Overtime validOtherOvertime = new Overtime("11");
        assertThrows(IllegalArgumentException.class, () -> validOvertime.removeOvertime(validOtherOvertime));
    }

    @Test
    public void removeOvertime_success() {
        Overtime validOvertime = new Overtime("30");
        Overtime validOtherOvertime = new Overtime("20");
        Overtime validFinalOvertime = new Overtime("10");
        assertEquals(validFinalOvertime, validOvertime.removeOvertime(validOtherOvertime));
    }

    @Test
    public void getRemainingOvertimeCapacity_success() {
        Overtime validOvertime = new Overtime("100");
        Overtime validOtherOvertime = new Overtime("744");
        Overtime overtimeCapacity = new Overtime("644");
        Overtime otherOvertimeCapacity = new Overtime("0");

        assertEquals(overtimeCapacity, validOvertime.getRemainingOvertimeCapacity());
        assertEquals(otherOvertimeCapacity, validOtherOvertime.getRemainingOvertimeCapacity());
    }

    @Test
    public void equals() {
        Overtime overtime = new Overtime("3");
        Overtime sameOvertime = overtime;

        assertTrue(overtime.equals(sameOvertime)); // Same object
        assertTrue(overtime.equals(new Overtime("3"))); // Same number of overtime hours

        assertFalse(overtime.equals(null)); // Null object
        assertFalse(overtime.equals(new HoursWorked("3"))); // Not an Overtime object
        assertFalse(overtime.equals(new Overtime("2"))); // Different number of overtime hours
    }
}

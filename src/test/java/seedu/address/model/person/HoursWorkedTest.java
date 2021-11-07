package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class HoursWorkedTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new HoursWorked(null));
    }

    @Test
    public void constructor_negativeHours_throwsIllegalArgumentException() {
        String negativeHours = "-3.12";
        assertThrows(IllegalArgumentException.class, () -> new HourlySalary(negativeHours));
    }

    @Test
    public void constructor_negativeZeroHours_throwsIllegalArgumentException() {
        String negativeZeroHours = "-000";
        assertThrows(IllegalArgumentException.class, () -> new HourlySalary(negativeZeroHours));
    }

    @Test
    public void constructor_alphanumericHours_throwsIllegalArgumentException() {
        String alphanumericHours = "3h";
        assertThrows(IllegalArgumentException.class, () -> new HourlySalary(alphanumericHours));
    }

    @Test
    public void constructor_floatingPointHours_throwsIllegalArgumentException() {
        String floatingPointHours = "3.5";
        assertThrows(IllegalArgumentException.class,
                LeaveBalance.MESSAGE_CONSTRAINTS, () -> new LeaveBalance(floatingPointHours));
    }

    @Test
    public void isValidHoursWorked() {
        // null hoursWorked
        assertThrows(NullPointerException.class, () -> HoursWorked.isValidHoursWorked(null));

        // invalid hoursWorked
        assertFalse(HoursWorked.isValidHoursWorked("")); // empty string
        assertFalse(HoursWorked.isValidHoursWorked(" ")); // spaces only
        assertFalse(HoursWorked.isValidHoursWorked("seven days")); // contains non-numeric characters only
        assertFalse(HoursWorked.isValidHoursWorked("7h")); // contains alphanumeric characters
        assertFalse(HoursWorked.isValidHoursWorked("-1")); // contains negative values
        assertFalse(HoursWorked.isValidHoursWorked("1.1")); // contains floating point values
        assertFalse(HoursWorked.isValidHoursWorked("12345")); // exceeds max bound

        // valid hoursWorked
        assertTrue(HoursWorked.isValidHoursWorked("365")); // max boundary
        assertTrue(HoursWorked.isValidHoursWorked("0")); // min boundary
        assertTrue(HoursWorked.isValidHoursWorked("100")); // value inside equivalence partition
        assertTrue(HoursWorked.isValidHoursWorked("201")); // value inside equivalence partition
    }
}

package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        assertThrows(IllegalArgumentException.class, () -> new HoursWorked(negativeHours));
    }

    @Test
    public void constructor_negativeZeroHours_throwsIllegalArgumentException() {
        String negativeZeroHours = "-000";
        assertThrows(IllegalArgumentException.class, () -> new HoursWorked(negativeZeroHours));
    }

    @Test
    public void constructor_alphanumericHours_throwsIllegalArgumentException() {
        String alphanumericHours = "3h";
        assertThrows(IllegalArgumentException.class, () -> new HoursWorked(alphanumericHours));
    }

    @Test
    public void constructor_floatingPointHours_throwsIllegalArgumentException() {
        String floatingPointHours = "3.5";
        assertThrows(IllegalArgumentException.class,
                HoursWorked.MESSAGE_CONSTRAINTS, () -> new HoursWorked(floatingPointHours));
    }

    @Test
    public void isValidHoursWorked() {
        // null hoursWorked
        assertThrows(NullPointerException.class, () -> HoursWorked.isValidHoursWorked(null));

        // invalid hoursWorked (formatting)
        assertFalse(HoursWorked.isValidHoursWorked("")); // empty string
        assertFalse(HoursWorked.isValidHoursWorked(" ")); // spaces only
        assertFalse(HoursWorked.isValidHoursWorked("seven")); // contains non-numeric characters only
        assertFalse(HoursWorked.isValidHoursWorked("7h")); // contains alphanumeric characters
        assertFalse(HoursWorked.isValidHoursWorked("-0")); // contains negative 0
        assertFalse(HoursWorked.isValidHoursWorked("1.1")); // contains floating point values

        // invalid hoursWorked (equivalence partitions: [INTEGER_MIN, -1], [745, INTEGER_MAX])
        assertFalse(HoursWorked.isValidHoursWorked("745")); // boundary value
        assertFalse(HoursWorked.isValidHoursWorked("-1")); // boundary value
        assertFalse(HoursWorked.isValidHoursWorked("12345")); // value inside equivalence partition
        assertFalse(HoursWorked.isValidHoursWorked("-12345")); // value inside equivalence partition

        // valid hoursWorked (equivalence partition: [0, 744])
        assertTrue(HoursWorked.isValidHoursWorked("744")); // max boundary
        assertTrue(HoursWorked.isValidHoursWorked("0")); // min boundary
        assertTrue(HoursWorked.isValidHoursWorked("087")); // value inside equivalence partition
        assertTrue(HoursWorked.isValidHoursWorked("367")); // value inside equivalence partition
    }

    @Test
    public void addHoursWorked_addZeroHoursWorked_throwsAssertionError() {
        HoursWorked validHoursWorked = new HoursWorked("10");
        // Negative values are caught by isValidHoursWorked, so 0 is the only possible value to cause an assertion error
        HoursWorked noHoursWorked = new HoursWorked("0");
        assertThrows(AssertionError.class, () -> validHoursWorked.addHoursWorked(noHoursWorked));
    }

    @Test
    public void addHoursWorked_exceedMaxHoursWorked_throwsIllegalArgumentException() {
        HoursWorked validHoursWorked = new HoursWorked("10");
        HoursWorked validOtherHoursWorked = new HoursWorked("735");
        assertThrows(IllegalArgumentException.class, () -> validHoursWorked.addHoursWorked(validOtherHoursWorked));
    }

    @Test
    public void addHoursWorked_success() {
        HoursWorked validHoursWorked = new HoursWorked("10");
        HoursWorked validOtherHoursWorked = new HoursWorked("20");
        HoursWorked validTotalHoursWorked = new HoursWorked("30");
        assertEquals(validTotalHoursWorked, validHoursWorked.addHoursWorked(validOtherHoursWorked));
    }

    @Test
    public void removeHoursWorked_removeZeroHoursWorked_throwsAssertionError() {
        HoursWorked validHoursWorked = new HoursWorked("10");
        // Negative values are caught by isValidHoursWorked, so 0 is the only possible value to cause an assertion error
        HoursWorked noHoursWorked = new HoursWorked("0");
        assertThrows(AssertionError.class, () -> validHoursWorked.removeHoursWorked(noHoursWorked));
    }

    @Test
    public void removeHoursWorked_insufficientHoursWorked_throwsIllegalArgumentException() {
        HoursWorked validHoursWorked = new HoursWorked("10");
        HoursWorked validOtherHoursWorked = new HoursWorked("11");
        assertThrows(IllegalArgumentException.class, () -> validHoursWorked.removeHoursWorked(validOtherHoursWorked));
    }

    @Test
    public void removeHoursWorked_success() {
        HoursWorked validHoursWorked = new HoursWorked("30");
        HoursWorked validOtherHoursWorked = new HoursWorked("20");
        HoursWorked validFinalHoursWorked = new HoursWorked("10");
        assertEquals(validFinalHoursWorked, validHoursWorked.removeHoursWorked(validOtherHoursWorked));
    }

    @Test
    public void getRemainingHoursWorkedCapacity_success() {
        HoursWorked validHoursWorked = new HoursWorked("100");
        HoursWorked validOtherHoursWorked = new HoursWorked("744");
        HoursWorked hoursWorkedCapacity = new HoursWorked("644");
        HoursWorked otherHoursWorkedCapacity = new HoursWorked("0");

        assertEquals(hoursWorkedCapacity, validHoursWorked.getRemainingHoursWorkedCapacity());
        assertEquals(otherHoursWorkedCapacity, validOtherHoursWorked.getRemainingHoursWorkedCapacity());
    }

    @Test
    public void equals() {
        HoursWorked hoursWorked = new HoursWorked("3");
        HoursWorked sameHoursWorked = hoursWorked;

        assertTrue(hoursWorked.equals(sameHoursWorked)); // Same object
        assertTrue(hoursWorked.equals(new HoursWorked("3"))); // Same number of hours worked

        assertFalse(hoursWorked.equals(null)); // Null object
        assertFalse(hoursWorked.equals(new Overtime("3"))); // Not an HoursWorked object
        assertFalse(hoursWorked.equals(new HoursWorked("2"))); // Different number of hours worked
    }
}

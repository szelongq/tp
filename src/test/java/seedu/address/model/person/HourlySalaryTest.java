package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class HourlySalaryTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new HourlySalary(null));
    }

    @Test
    public void constructor_negativeSalary_throwsIllegalArgumentException() {
        String negativeSalary = "-3.12342563";
        assertThrows(IllegalArgumentException.class, () -> new HourlySalary(negativeSalary));
    }

    @Test
    public void constructor_negativeZeroSalary_throwsIllegalArgumentException() {
        String negativeZeroSalary = "-000";
        assertThrows(IllegalArgumentException.class, () -> new HourlySalary(negativeZeroSalary));
    }

    @Test
    public void constructor_alphanumericSalary_throwsIllegalArgumentException() {
        String alphanumericSalary = "3.1k";
        assertThrows(IllegalArgumentException.class, () -> new HourlySalary(alphanumericSalary));
    }

    @Test
    public void isValidSalary() {
        // null hourlySalary
        assertThrows(NullPointerException.class, () -> HourlySalary.isValidSalary(null));

        // invalid hourlySalary
        assertFalse(HourlySalary.isValidSalary("")); // empty string
        assertFalse(HourlySalary.isValidSalary(" ")); // spaces only
        assertFalse(HourlySalary.isValidSalary("^")); // only non-alphanumeric characters
        assertFalse(HourlySalary.isValidSalary("power-ranger")); // contains non-numeric characters only
        assertFalse(HourlySalary.isValidSalary("1.123m")); // contains alphanumeric characters
        assertFalse(HourlySalary.isValidSalary("-1.10")); // contains negative values

        // valid hourlySalary
        assertTrue(HourlySalary.isValidSalary("12345")); // numeric characters as integers only
        assertTrue(HourlySalary.isValidSalary("9.99")); // numeric characters as floating values only
    }
}

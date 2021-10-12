package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class LeaveTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Leave(null));
    }

    @Test
    public void constructor_invalidLeaves_throwsIllegalArgumentException() {
        // Not an integer
        assertThrows(IllegalArgumentException.class, Leave.MESSAGE_CONSTRAINTS, () -> new Leave("."));
        assertThrows(IllegalArgumentException.class, Leave.MESSAGE_CONSTRAINTS, () -> new Leave("1.1"));

        // Negative integer
        assertThrows(IllegalArgumentException.class, Leave.MESSAGE_CONSTRAINTS, () -> new Leave("-1"));
    }

    @Test
    public void isValidLeaves() {
        // Null leaves
        assertThrows(NullPointerException.class, () -> Leave.isValidLeaves(null));

        // Invalid leaves
        assertFalse(Leave.isValidLeaves("")); // empty string
        assertFalse(Leave.isValidLeaves(" ")); // spaces only

        // Valid leaves
        assertTrue(Leave.isValidLeaves("0"));
        assertTrue(Leave.isValidLeaves("3")); // Non-zero integer
        assertTrue(Leave.isValidLeaves("03")); // Integer with redundant 0
    }

    @Test
    public void equals() {
        Leave leave = new Leave("3");
        Leave sameLeave = leave;

        assertTrue(leave.equals(sameLeave)); // Same object
        assertTrue(leave.equals(new Leave("3"))); // Same number of leaves

        assertFalse(leave.equals(null)); // Null object
        assertFalse(leave.equals(new Name("test"))); // Not a leaves object
        assertFalse(leave.equals(new Leave("2"))); // Different number of leaves
    }
}

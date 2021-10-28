package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class LeaveBalanceTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new LeaveBalance(null));
    }

    @Test
    public void constructor_invalidLeaves_throwsIllegalArgumentException() {
        // Not an integer
        assertThrows(IllegalArgumentException.class, LeaveBalance.MESSAGE_CONSTRAINTS, () -> new LeaveBalance("."));
        assertThrows(IllegalArgumentException.class, LeaveBalance.MESSAGE_CONSTRAINTS, () -> new LeaveBalance("1.1"));

        // Negative integer
        assertThrows(IllegalArgumentException.class, LeaveBalance.MESSAGE_CONSTRAINTS, () -> new LeaveBalance("-1"));
    }

    @Test
    public void isValidLeaves() {
        // Null leaves
        assertThrows(NullPointerException.class, () -> LeaveBalance.isValidLeaveBalance(null));

        // Invalid leaves
        assertFalse(LeaveBalance.isValidLeaveBalance("")); // empty string
        assertFalse(LeaveBalance.isValidLeaveBalance(" ")); // spaces only

        // Valid leaves
        assertTrue(LeaveBalance.isValidLeaveBalance("0"));
        assertTrue(LeaveBalance.isValidLeaveBalance("3")); // Non-zero integer
        assertTrue(LeaveBalance.isValidLeaveBalance("03")); // Integer with redundant 0
    }

    @Test
    public void equals() {
        LeaveBalance leaveBalance = new LeaveBalance("3");
        LeaveBalance sameLeaveBalance = leaveBalance;

        assertTrue(leaveBalance.equals(sameLeaveBalance)); // Same object
        assertTrue(leaveBalance.equals(new LeaveBalance("3"))); // Same number of leaves

        assertFalse(leaveBalance.equals(null)); // Null object
        assertFalse(leaveBalance.equals(new Name("test"))); // Not a leaves object
        assertFalse(leaveBalance.equals(new LeaveBalance("2"))); // Different number of leaves
    }
}

package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    public void constructor_invalidLeaveBalance_throwsIllegalArgumentException() {
        // Not an integer
        assertThrows(IllegalArgumentException.class, LeaveBalance.MESSAGE_CONSTRAINTS, () -> new LeaveBalance("."));
        assertThrows(IllegalArgumentException.class, LeaveBalance.MESSAGE_CONSTRAINTS, () -> new LeaveBalance("1.1"));

        // Negative integer
        assertThrows(IllegalArgumentException.class, LeaveBalance.MESSAGE_CONSTRAINTS, () -> new LeaveBalance("-1"));

        // Negative Zero
        assertThrows(IllegalArgumentException.class, LeaveBalance.MESSAGE_CONSTRAINTS, () -> new LeaveBalance("-0"));
    }

    @Test
    public void isValidLeaveBalance() {
        // null leaveBalance
        assertThrows(NullPointerException.class, () -> LeaveBalance.isValidLeaveBalance(null));

        // invalid leaveBalance (formatting)
        assertFalse(LeaveBalance.isValidLeaveBalance("")); // empty string
        assertFalse(LeaveBalance.isValidLeaveBalance(" ")); // spaces only
        assertFalse(LeaveBalance.isValidLeaveBalance("seven")); // contains non-numeric characters only
        assertFalse(LeaveBalance.isValidLeaveBalance("7h")); // contains alphanumeric characters
        assertFalse(LeaveBalance.isValidLeaveBalance("-0")); // contains negative 0
        assertFalse(LeaveBalance.isValidLeaveBalance("1.1")); // contains floating point values

        // invalid leaveBalance (equivalence partitions: [INTEGER_MIN, -1], [366, INTEGER_MAX])
        assertFalse(LeaveBalance.isValidLeaveBalance("366")); // boundary value
        assertFalse(LeaveBalance.isValidLeaveBalance("-1")); // boundary value
        assertFalse(LeaveBalance.isValidLeaveBalance("12345")); // value inside equivalence partition
        assertFalse(LeaveBalance.isValidLeaveBalance("-12345")); // value inside equivalence partition

        // valid leaveBalance (equivalence partition: [0, 365])
        assertTrue(LeaveBalance.isValidLeaveBalance("365")); // max boundary
        assertTrue(LeaveBalance.isValidLeaveBalance("0")); // min boundary
        assertTrue(LeaveBalance.isValidLeaveBalance("087")); // value inside equivalence partition
        assertTrue(LeaveBalance.isValidLeaveBalance("220")); // value inside equivalence partition
    }

    @Test
    public void addLeaves_addZeroLeaves_throwsAssertionError() {
        LeaveBalance validLeaveBalance = new LeaveBalance("10");
        // Negative values are caught by isValidLeaves, so 0 is the only possible value to cause an assertion error
        LeaveBalance noLeaveBalance = new LeaveBalance("0");
        assertThrows(AssertionError.class, () -> validLeaveBalance.addLeaves(noLeaveBalance));
    }

    @Test
    public void addLeaves_exceedMaxLeaveBalance_throwsIllegalArgumentException() {
        LeaveBalance validLeaveBalance = new LeaveBalance("10");
        LeaveBalance validOtherLeaveBalance = new LeaveBalance("356");
        assertThrows(IllegalArgumentException.class, () -> validLeaveBalance.addLeaves(validOtherLeaveBalance));
    }

    @Test
    public void addLeaves_success() {
        LeaveBalance validLeaveBalance = new LeaveBalance("10");
        LeaveBalance validOtherLeaveBalance = new LeaveBalance("20");
        LeaveBalance validTotalLeaveBalance = new LeaveBalance("30");
        assertEquals(validTotalLeaveBalance, validLeaveBalance.addLeaves(validOtherLeaveBalance));
    }

    @Test
    public void removeLeaves_removeZeroLeaves_throwsAssertionError() {
        LeaveBalance validLeaveBalance = new LeaveBalance("10");
        // Negative values are caught by isValidLeaves, so 0 is the only possible value to cause an assertion error
        LeaveBalance noLeaveBalance = new LeaveBalance("0");
        assertThrows(AssertionError.class, () -> validLeaveBalance.removeLeaves(noLeaveBalance));
    }

    @Test
    public void removeLeaves_insufficientLeaveBalance_throwsIllegalArgumentException() {
        LeaveBalance validLeaveBalance = new LeaveBalance("10");
        LeaveBalance validOtherLeaveBalance = new LeaveBalance("11");
        assertThrows(IllegalArgumentException.class, () -> validLeaveBalance.removeLeaves(validOtherLeaveBalance));
    }

    @Test
    public void removeLeaves_success() {
        LeaveBalance validLeaveBalance = new LeaveBalance("30");
        LeaveBalance validOtherLeaveBalance = new LeaveBalance("20");
        LeaveBalance validFinalLeaveBalance = new LeaveBalance("10");
        assertEquals(validFinalLeaveBalance, validLeaveBalance.removeLeaves(validOtherLeaveBalance));
    }

    @Test
    public void getRemainingLeaveCapacity_success() {
        LeaveBalance validLeaveBalance = new LeaveBalance("101");
        LeaveBalance validOtherLeaveBalance = new LeaveBalance("365");
        LeaveBalance leaveBalanceCapacity = new LeaveBalance("264");
        LeaveBalance otherLeaveBalanceCapacity = new LeaveBalance("0");

        assertEquals(leaveBalanceCapacity, validLeaveBalance.getRemainingLeaveCapacity());
        assertEquals(otherLeaveBalanceCapacity, validOtherLeaveBalance.getRemainingLeaveCapacity());
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

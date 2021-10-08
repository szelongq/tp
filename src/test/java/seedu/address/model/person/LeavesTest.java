package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class LeavesTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Leaves(null));
    }

    @Test
    public void constructor_invalidLeaves_throwsIllegalArgumentException() {
        // Not an integer
        assertThrows(IllegalArgumentException.class, Leaves.MESSAGE_CONSTRAINTS, () -> new Leaves("."));
        assertThrows(IllegalArgumentException.class, Leaves.MESSAGE_CONSTRAINTS, () -> new Leaves("1.1"));

        // Negative integer
        assertThrows(IllegalArgumentException.class, Leaves.MESSAGE_CONSTRAINTS, () -> new Leaves("-1"));
    }

    @Test
    public void isValidLeaves() {
        // Null leaves
        assertThrows(NullPointerException.class, () -> Leaves.isValidLeaves(null));

        // Invalid leaves
        assertFalse(Leaves.isValidLeaves("")); // empty string
        assertFalse(Leaves.isValidLeaves(" ")); // spaces only

        // Valid leaves
        assertTrue(Leaves.isValidLeaves("0"));
        assertTrue(Leaves.isValidLeaves("3")); // Non-zero integer
        assertTrue(Leaves.isValidLeaves("03")); // Integer with redundant 0
    }

    @Test
    public void equals() {
        Leaves leaves = new Leaves("3");
        Leaves sameLeaves = leaves;

        assertTrue(leaves.equals(sameLeaves)); // Same object
        assertTrue(leaves.equals(new Leaves("3"))); // Same number of leaves

        assertFalse(leaves.equals(null)); // Null object
        assertFalse(leaves.equals(new Name("test"))); // Not a leaves object
        assertFalse(leaves.equals(new Leaves("2"))); // Different number of leaves
    }
}

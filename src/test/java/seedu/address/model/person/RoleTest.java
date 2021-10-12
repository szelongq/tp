package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RoleTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Role(null));
    }

    @Test
    public void constructor_invalidRole_throwsIllegalArgumentException() {
        String invalidRole = "";
        assertThrows(IllegalArgumentException.class, () -> new Role(invalidRole));
    }

    @Test
    public void isValidRole() {
        // null role
        assertThrows(NullPointerException.class, () -> Role.isValidRole(null));

        // invalid role
        assertFalse(Role.isValidRole("")); // empty string
        assertFalse(Role.isValidRole(" ")); // spaces only

        // valid role
        assertTrue(Role.isValidRole("software engineer smol")); // alphabets only
        assertTrue(Role.isValidRole("12345")); // numbers only
        assertTrue(Role.isValidRole("Junior Developer Stage 2")); // alphanumeric characters
        assertTrue(Role.isValidRole("Software Engineer")); // with capital letters
        assertTrue(Role.isValidRole("Software Engineer with 10 years experience and 20 years knowledge")); // long names
        assertTrue(Role.isValidRole("power-ranger")); // contains non-alphanumeric characters
        assertTrue(Role.isValidRole("^")); // not whitespace, slightly dubious
    }
}

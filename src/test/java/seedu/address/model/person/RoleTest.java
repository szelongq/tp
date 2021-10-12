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
        assertFalse(Role.isValidRole("^")); // only non-alphanumeric characters
        assertFalse(Role.isValidRole("0pProgrammers"));
        assertFalse(Role.isValidRole(" pProgrammers")); // starts with whitespace
        assertFalse(Role.isValidRole("Apple slicer <YO>")); // contains unsupported character '>'

        // valid role
        assertTrue(Role.isValidRole("software engineer smol")); // alphabets only
        assertTrue(Role.isValidRole("UI/UX Designer")); // Supports '/'
        assertTrue(Role.isValidRole("Front-End Programmer")); // Supports '-'
        assertTrue(Role.isValidRole("R&D Team Manager")); // Supports '&'
        assertTrue(Role.isValidRole("Team Leader, Front-End Team (Git)")); // Supports ',' , '-', '(' and ')'
        assertTrue(Role.isValidRole("Junior Developer Stage 2")); // alphanumeric characters
        assertTrue(Role.isValidRole("Software Engineer")); // with capital letters
        assertTrue(Role.isValidRole("Software Engineer with 10 years experience and 20 years knowledge")); // long names
    }
}

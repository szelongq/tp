package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's Role in the employee book.
 * Guarantees: immutable; is valid as declared in {@link #isValidRole(String)}
 */
public class Role {

    public static final String MESSAGE_CONSTRAINTS =
            "Roles should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String value;

    /**
     * Constructs a {@code Role}.
     *
     * @param roleName A valid Role Name.
     */
    public Role(String roleName) {
        requireNonNull(roleName);
        checkArgument(isValidRole(roleName), MESSAGE_CONSTRAINTS);
        this.value = roleName;
    }

    /**
     * Returns true if a given string is a valid name for a role.
     */
    public static boolean isValidRole(String test) {
        requireNonNull(test);
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return this.value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Role // instanceof handles nulls
                && value.equals(((Role) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}

package seedu.address.model.person.exceptions;

/**
 * Signals that the operation will result in duplicate Persons (Persons are considered duplicates if they have the same
 * identity).
 */
public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException(int[] indices) {
        super(String.format("Duplicate emails detected in Rows %d and %d.", indices[0] + 1, indices[1] + 1));
    }
}

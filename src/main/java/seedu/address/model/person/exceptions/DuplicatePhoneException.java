package seedu.address.model.person.exceptions;

/**
 * Signals that the operation will result in duplicate Persons (Persons are considered duplicates if they have the same
 * identity).
 */
public class DuplicatePhoneException extends RuntimeException {
    public DuplicatePhoneException(int[] indices) {
        super(String.format("Duplicate phone numbers detected in Rows %d and %d.", indices[0] + 1, indices[1] + 1));
    }
}

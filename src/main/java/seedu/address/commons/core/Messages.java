package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_INVALID_INTEGER_INPUT =
            "Please input a valid positive integer when adding/removing hours worked! \n%1$s";
    public static final String MESSAGE_INVALID_LEAVES_INPUT =
            "Please input a positive integer value between 1 and 365 (both inclusive) "
                    + "when adding/removing leaves! \n%1$s";
    public static final String MESSAGE_INVALID_REMOVE_INPUT =
            "Employee has less than %1$s %2$s! (Currently has %3$s %4$s)";
    public static final String MESSAGE_INVALID_ADD_INPUT =
            "Employee cannot have more than %1$s %2$s! (Can add at most %3$s %4$s)";
    public static final String MESSAGE_INSUFFICIENT_LEAVES = "Employee %1$s does not have any more leaves! \n";
    public static final String MESSAGE_INVALID_DATE_FORMAT = "Invalid date format! (Correct format: YYYY-MM-DD) \n%1$s";
    public static final String MESSAGE_INVALID_FINDDATE_FORMAT =
            "Dates should be of the form YYYY-MM-DD or YYYY-MM-DD:YYYY-MM-DD (for ranges)! \n%1$s";
    public static final String MESSAGE_DATE_ALREADY_ASSIGNED =
            "Employee %1$s already has a leave assigned to the date %2$s! \n";
    public static final String MESSAGE_INVALID_COMPARATOR =
            "Invalid comparison operator! Use either '>', '<', '>=', '<=' or '='.";
}

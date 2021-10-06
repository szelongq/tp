package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * Calculates an employee's pay using it's displayed index from the address book.
 */
public class CalculatePayCommand extends Command {

    public static final String COMMAND_WORD = "calculate";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Calculates the pay of the employee identified by the index number "
            + "used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_CALCULATE_PAY_SUCCESS = "Calculated Pay: %1$s";

    private final Index targetIndex;

    public CalculatePayCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToCalculatePay = lastShownList.get(targetIndex.getZeroBased());
        // Temporarily replace calculated pay result with phone number
        Phone calculatedPay = personToCalculatePay.getPhone();
        return new CommandResult(String.format(MESSAGE_CALCULATE_PAY_SUCCESS, calculatedPay));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CalculatePayCommand // instanceof handles nulls
                && targetIndex.equals(((CalculatePayCommand) other).targetIndex)); // state check
    }
}

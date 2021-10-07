package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Removes some number of leaves from an employee in HeRon.
 */
public class RemoveLeavesCommand extends Command {

    public static final String COMMAND_WORD = "removeLeaves";
    public static final String MESSAGE_ARGUMENTS = "Index: %1$d, Number of leaves: %2$d";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes leaves from the employee identified "
            + "by the index number used in the last person listing. "
            + "Number of leaves removed cannot be greater than the amount of leaves "
            + "the employee currently has. \n"
            + "Parameters: INDEX (must be a positive integer) "
            + "l/NO_OF_DAYS (must be a positive integer) \n"
            + "Example: " + COMMAND_WORD + " 1 l/2";

    private final Index index;
    private final int numberOfLeaves;

    /**
     * Creates a RemoveLeavesCommand instance.
     * @param index of the person in the filtered employee list to remove leaves from
     * @param numberOfLeaves that are to be added to the employee
     */
    public RemoveLeavesCommand(Index index, int numberOfLeaves) {
        requireAllNonNull(index, numberOfLeaves);

        this.index = index;
        this.numberOfLeaves = numberOfLeaves;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException(
                String.format(MESSAGE_ARGUMENTS, index.getOneBased(), numberOfLeaves));
    }

    @Override
    public boolean equals(Object other) {
        // Short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RemoveLeavesCommand)) {
            return false;
        }

        // State check
        RemoveLeavesCommand e = (RemoveLeavesCommand) other;
        return index.equals(e.index)
                && numberOfLeaves == e.numberOfLeaves;
    }
}

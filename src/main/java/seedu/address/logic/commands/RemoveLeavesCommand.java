package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Leaves;
import seedu.address.model.person.Person;

/**
 * Removes some number of leaves from an employee in HeRon.
 */
public class RemoveLeavesCommand extends Command {

    public static final String COMMAND_WORD = "removeLeaves";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes leaves from the employee identified "
            + "by the index number used in the last person listing. "
            + "Number of leaves removed cannot be greater than the amount of leaves "
            + "the employee currently has. \n"
            + "Parameters: INDEX (must be a positive integer) "
            + "l/NO_OF_DAYS (must be a positive integer) \n"
            + "Example: " + COMMAND_WORD + " 1 l/2";
    public static final String MESSAGE_SUCCESS =
            "Leaves successfully removed, current leaves are: %1$s";

    private final Index index;
    private final Leaves leaves;

    /**
     * Creates a RemoveLeavesCommand instance.
     * @param index of the person in the filtered employee list to remove leaves from
     * @param leaves that are to be added to the employee
     */
    public RemoveLeavesCommand(Index index, Leaves leaves) {
        requireAllNonNull(index, leaves);

        this.index = index;
        this.leaves = leaves;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson;
        try {
            editedPerson = new Person(
                    personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(), personToEdit.getAddress(),
                    personToEdit.getLeaves().removeLeaves(leaves), personToEdit.getTags());
        } catch (IllegalArgumentException iae) {
            throw new CommandException(
                    String.format(Messages.MESSAGE_INVALID_REMOVELEAVES_INPUT, leaves));
        }

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_SUCCESS, editedPerson.getLeaves()));
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
                && leaves.equals(e.leaves);
    }
}

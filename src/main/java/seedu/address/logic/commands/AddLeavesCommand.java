package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Leave;
import seedu.address.model.person.Person;

/**
 * Adds some number of leaves to an employee in HeRon.
 */
public class AddLeavesCommand extends Command {

    public static final String COMMAND_WORD = "addLeaves";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds leaves to the employee identified "
            + "by the index number used in the last person listing. \n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_LEAVE + "NO_OF_DAYS (must be a positive integer) \n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_LEAVE + "2";
    public static final String MESSAGE_SUCCESS =
            "Leaves successfully added to person: %1$s";

    private final Index index;
    private final Leave leave;

    /**
     * Creates an AddLeavesCommand instance.
     * @param index of the person in the filtered employee list to add leaves to
     * @param leave that are to be added to the employee
     */
    public AddLeavesCommand(Index index, Leave leave) {
        requireAllNonNull(index, leave);

        this.index = index;
        this.leave = leave;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = new Person(
                personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(), personToEdit.getAddress(),
                personToEdit.getRole(), personToEdit.getLeaves().addLeaves(leave), personToEdit.getLeavesTaken(),
                personToEdit.getSalary(), personToEdit.getHoursWorked(), personToEdit.getOvertime(),
                personToEdit.getCalculatedPay(), personToEdit.getTags());

        model.setPerson(personToEdit, editedPerson);
        model.setViewingPerson(editedPerson);

        return new CommandResult(String.format(MESSAGE_SUCCESS, editedPerson.toString()));
    }

    @Override
    public boolean equals(Object other) {
        // Short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddLeavesCommand)) {
            return false;
        }

        // State check
        AddLeavesCommand e = (AddLeavesCommand) other;
        return index.equals(e.index)
                && leave.equals(e.leave);
    }
}

package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.LeaveBalance;
import seedu.address.model.person.Person;

/**
 * Adds some number of leaves to an employee in HeRon.
 */
public class AddLeaveBalanceCommand extends Command {

    public static final String COMMAND_WORD = "addLeaveBalance";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds leaves to the employee identified "
            + "by the index number used in the last employee listing. \n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_LEAVE + "NO_OF_LEAVES (must be a positive integer) \n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_LEAVE + "2";
    public static final String MESSAGE_SUCCESS =
            "Leaves successfully added to employee: %1$s (Employee now has %2$s leave%3$s)";

    private final Index index;
    private final LeaveBalance leaveBalance;

    /**
     * Creates an AddLeaveBalanceCommand instance.
     * @param index of the person in the filtered employee list to add leaves to
     * @param leaveBalance that are to be added to the employee
     */
    public AddLeaveBalanceCommand(Index index, LeaveBalance leaveBalance) {
        requireAllNonNull(index, leaveBalance);

        this.index = index;
        this.leaveBalance = leaveBalance;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = getUpdatedPerson(personToEdit);

        model.setPerson(personToEdit, editedPerson);
        model.setViewingPerson(editedPerson);

        return new CommandResult(String.format(MESSAGE_SUCCESS,
                editedPerson.getName().toString(),
                editedPerson.getLeaveBalance().toString(),
                editedPerson.getLeaveBalance().toString().equals("1") ? "" : "s"));
    }

    /**
     * Returns a {@code Person} object that is a copy of the input person,
     * except with an updated leave balance.
     *
     * @param personToEdit The person object that is to be edited.
     * @return An updated Person object.
     * @throws CommandException if the added amount of leaves causes the Person's
     * leave balance to exceed the maximum allowed amount
     */
    private Person getUpdatedPerson(Person personToEdit) throws CommandException {
        LeaveBalance newLeaveBalance;
        try {
            newLeaveBalance = personToEdit.getLeaveBalance().addLeaves(leaveBalance);
        } catch (IllegalArgumentException iae) {
            String leaveCapacityString =
                    personToEdit.getLeaveBalance().getRemainingLeaveCapacity().toString();
            throw new CommandException(
                    String.format(Messages.MESSAGE_INVALID_ADD_INPUT,
                            LeaveBalance.MAX_LEAVES,
                            "leaves", leaveCapacityString,
                            leaveCapacityString.equals("1") ? "leave" : "leaves"));
        }

        return new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getRole(), newLeaveBalance,
                personToEdit.getLeavesTaken(), personToEdit.getSalary(), personToEdit.getHoursWorked(),
                personToEdit.getOvertime(), personToEdit.getCalculatedPay(), personToEdit.getTags());
    }

    @Override
    public boolean equals(Object other) {
        // Short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddLeaveBalanceCommand)) {
            return false;
        }

        // State check
        AddLeaveBalanceCommand e = (AddLeaveBalanceCommand) other;
        return index.equals(e.index)
                && leaveBalance.equals(e.leaveBalance);
    }
}

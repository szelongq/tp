package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import java.time.LocalDate;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.LeaveBalance;
import seedu.address.model.person.LeavesTaken;
import seedu.address.model.person.Person;

/**
 * Assigns a leave with a date to an employee in HeRon.
 */
public class AssignLeaveCommand extends Command {

    public static final String COMMAND_WORD = "assignLeave";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Allocates a leave with a date to the employee identified "
            + "by the index number used in the last employee listing. \n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_DATE + "DATE (of the format YYYY-MM-DD) \n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_DATE + "2021-10-30";
    public static final String MESSAGE_SUCCESS =
            "Leave with date %1$s successfully assigned to employee: %2$s";

    private final Index index;
    private final LocalDate date;

    /**
     * Creates an AssignLeaveCommand instance.
     *
     * @param index of the person in the filtered employee list to assign a leave to
     * @param date that corresponds to the leave being allocated to the employee
     */
    public AssignLeaveCommand(Index index, LocalDate date) {
        requireAllNonNull(index, date);

        this.index = index;
        this.date = date;
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
                date.toString(),
                editedPerson.getName().toString()));
    }

    /**
     * Returns a {@code Person} object that is a copy of the input person, except with an updated leave balance and
     * leaves taken list.
     *
     * @param personToEdit The person object that is to be edited.
     * @return An updated Person object.
     * @throws CommandException if the employee does not have enough leaves
     * or a leave with the given date is already assigned to the employee
     */
    private Person getUpdatedPerson(Person personToEdit) throws CommandException {
        LeaveBalance newLeaveBalance;
        // Check that the employee has at least 1 leave
        try {
            newLeaveBalance = personToEdit.getLeaveBalance().removeLeaves(new LeaveBalance("1"));
        } catch (IllegalArgumentException iae) {
            throw new CommandException(
                    String.format(Messages.MESSAGE_INSUFFICIENT_LEAVES,
                            personToEdit.getName().toString()));
        }
        // Check that the input date is not a duplicate
        LeavesTaken newLeavesTaken;
        try {
            newLeavesTaken = personToEdit.getLeavesTaken().addDate(date);
        } catch (IllegalArgumentException iae) {
            throw new CommandException(
                    String.format(Messages.MESSAGE_DATE_ALREADY_ASSIGNED,
                            personToEdit.getName().toString(), date));
        }

        return new Person(
                personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(), personToEdit.getAddress(),
                personToEdit.getRole(), newLeaveBalance, newLeavesTaken, personToEdit.getSalary(),
                personToEdit.getHoursWorked(), personToEdit.getOvertime(),
                personToEdit.getCalculatedPay(), personToEdit.getTags());
    }

    @Override
    public boolean equals(Object other) {
        // Short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AssignLeaveCommand)) {
            return false;
        }

        // State check
        AssignLeaveCommand e = (AssignLeaveCommand) other;
        return index.equals(e.index)
                && date.equals(e.date);
    }
}

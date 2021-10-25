package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import java.time.LocalDate;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Leave;
import seedu.address.model.person.Person;

/**
 * Assigns a leave with a date to an employee in HeRon.
 */
public class AssignLeaveCommand extends Command {

    public static final String COMMAND_WORD = "assignLeave";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Allocates a leave with a date to the employee identified "
            + "by the index number used in the last person listing. \n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_DATE + "DATE (of the format YYYY-MM-DD) \n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_DATE + "2021-10-30";
    public static final String MESSAGE_SUCCESS =
            "Leave successfully assigned to person: %1$s";

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
        Leave newLeaveBalance;
        // Check that the employee has at least 1 leave
        try {
            newLeaveBalance = personToEdit.getLeaves().removeLeaves(new Leave("1"));
        } catch (IllegalArgumentException iae) {
            throw new CommandException(
                    String.format(Messages.MESSAGE_INSUFFICIENT_LEAVES,
                            personToEdit.getName().toString()));
        }

        Person editedPerson = new Person(
                personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(), personToEdit.getAddress(),
                personToEdit.getRole(), newLeaveBalance,
                personToEdit.getLeavesTaken().addDate(date), personToEdit.getSalary(), personToEdit.getHoursWorked(),
                personToEdit.getOvertime(), personToEdit.getCalculatedPay(), personToEdit.getTags());

        model.setPerson(personToEdit, editedPerson);

        return new CommandResult(String.format(MESSAGE_SUCCESS, editedPerson.toString()));
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

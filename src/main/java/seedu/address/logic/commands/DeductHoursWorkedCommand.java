package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.commons.util.StringUtil.isNonZeroUnsignedInteger;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOURSWORKED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OVERTIME;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.HoursWorked;
import seedu.address.model.person.Overtime;
import seedu.address.model.person.Person;

/**
 * Removes hours worked and overtime hours worked from an employee in HeRon.
 */
public class DeductHoursWorkedCommand extends Command {

    public static final String COMMAND_WORD = "deductHoursWorked";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deducts hours worked OR overtime from the employee identified "
            + "by the index number used in the last employee listing. \n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_HOURSWORKED + "NO_OF_HOURS_WORKED] (must be a positive integer) "
            + "[" + PREFIX_OVERTIME + "NO_OF_HOURS_WORKED_OVERTIME] (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_HOURSWORKED + "2";
    public static final String MESSAGE_SUCCESS =
            "Successfully removed hours worked from employee: %1$s "
                    + "(Employee now has %2$s hour%3$s worked and "
                    + "%4$s overtime hour%5$s worked)";

    private final Index index;
    private final HoursWorked hoursWorked;
    private final Overtime overtime;

    /**
     * Creates an DeductHoursWorkedCommand instance.
     * @param index of the person in the filtered employee list to remove hours worked from
     * @param hoursWorked Hours worked that are to be removed from the employee
     * @param overtime Overtime (in hours) that is to be removed from the employee
     */
    public DeductHoursWorkedCommand(Index index, HoursWorked hoursWorked, Overtime overtime) {
        requireAllNonNull(index, hoursWorked, overtime);

        this.index = index;
        this.hoursWorked = hoursWorked;
        this.overtime = overtime;
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
                        editedPerson.getHoursWorked().toString(),
                        editedPerson.getHoursWorked().toString().equals("1") ? "" : "s",
                        editedPerson.getOvertime().toString(),
                        editedPerson.getOvertime().toString().equals("1") ? "" : "s"));
    }

    /**
     * Returns a {@code Person} object that is a copy of the input person,
     * except with updated hours worked and overtime hours worked.
     *
     * @param personToEdit The person object that is to be edited.
     * @return An updated Person object.
     * @throws CommandException if the removed amount of hours worked/overtime causes the Person's
     * hours worked/overtime to exceed the maximum allowed amount
     */
    private Person getUpdatedPerson(Person personToEdit) throws CommandException {
        HoursWorked newHoursWorked = personToEdit.getHoursWorked();
        try {
            // Check if hours worked input is a positive integer before calling addHoursWorked
            if (isNonZeroUnsignedInteger(hoursWorked.toString())) {
                newHoursWorked = personToEdit.getHoursWorked().removeHoursWorked(hoursWorked);
            }
        } catch (IllegalArgumentException iae) {
            String hoursWorkedString = hoursWorked.toString();
            String personHoursWorkedString = personToEdit.getHoursWorked().toString();
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_REMOVE_INPUT,
                    hoursWorkedString,
                    hoursWorkedString.equals("1") ? "hour worked" : "hours worked",
                    personHoursWorkedString,
                    personHoursWorkedString.equals("1") ? "hour worked" : "hours worked"));
        }

        Overtime newOvertime = personToEdit.getOvertime();
        try {
            // Check if overtime input is a positive integer before calling addOvertime
            if (isNonZeroUnsignedInteger(overtime.toString())) {
                newOvertime = personToEdit.getOvertime().removeOvertime(overtime);
            }
        } catch (IllegalArgumentException iae) {
            String overtimeString = overtime.toString();
            String personOvertimeString = personToEdit.getOvertime().toString();
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_REMOVE_INPUT,
                    overtimeString,
                    overtimeString.equals("1") ? "overtime hour worked" : "overtime hours worked",
                    personOvertimeString,
                    personOvertimeString.equals("1") ? "overtime hour worked" : "overtime hours worked"));
        }

        return new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getRole(), personToEdit.getLeaveBalance(),
                personToEdit.getLeavesTaken(), personToEdit.getSalary(), newHoursWorked, newOvertime,
                personToEdit.getCalculatedPay(), personToEdit.getTags());
    }

    @Override
    public boolean equals(Object other) {
        // Short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeductHoursWorkedCommand)) {
            return false;
        }

        // State check
        DeductHoursWorkedCommand e = (DeductHoursWorkedCommand) other;
        return index.equals(e.index)
                && hoursWorked.equals(e.hoursWorked)
                && overtime.equals(e.overtime);
    }
}

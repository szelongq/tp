package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import java.time.LocalDate;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Removes all assigned leaves that occur before a given date
 * from all employees according to a filtered list in HeRon.
 */
public class RemoveLeavesBeforeCommand extends Command {

    public static final String COMMAND_WORD = "removeLeavesBefore";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes all assigned leaves that occur before a given date "
            + "from all employees according to a filtered list in HeRon. \n"
            + "Parameter: DATE (of the format YYYY-MM-DD) \n"
            + "Example: " + COMMAND_WORD
            + " " + PREFIX_DATE + "2021-10-30";
    public static final String MESSAGE_SUCCESS =
            "All assigned leaves with date before %1$s have been removed";

    private final LocalDate beforeDate;

    /**
     * Creates a RemoveLeavesBeforeCommand instance.
     *
     * @param beforeDate that corresponds to the assigned leaves that the user wants to remove
     */
    public RemoveLeavesBeforeCommand(LocalDate beforeDate) {
        requireAllNonNull(beforeDate);

        this.beforeDate = beforeDate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (model.getFilteredPersonList().size() == 0) {
            throw new CommandException(Messages.MESSAGE_EMPTY_FILTERED_LIST);
        }

        // Remove dates from all persons in the filtered list
        for (Person personToEdit : lastShownList) {
            Person personWithDatesRemoved = getUpdatedPerson(personToEdit);
            model.setPerson(personToEdit, personWithDatesRemoved);
        }

        // View the first person in the list
        Index indexOfFirstPerson = Index.fromZeroBased(0);
        Person personToView = lastShownList.get(indexOfFirstPerson.getZeroBased());

        model.setViewingPerson(personToView);

        return new CommandResult(String.format(MESSAGE_SUCCESS, beforeDate));
    }

    /**
     * Returns a {@code Person} object that is a copy of the input person, except with an updated leaves taken list.
     *
     * @param personToEdit The person object that is to be edited.
     * @return An updated Person object.
     */
    private Person getUpdatedPerson(Person personToEdit) {
        return new Person(
                personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(), personToEdit.getAddress(),
                personToEdit.getRole(), personToEdit.getLeaveBalance(),
                personToEdit.getLeavesTaken().removeDatesBefore(beforeDate), personToEdit.getSalary(),
                personToEdit.getHoursWorked(), personToEdit.getOvertime(), personToEdit.getCalculatedPay(),
                personToEdit.getTags());
    }

    @Override
    public boolean equals(Object other) {
        // Short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RemoveLeavesBeforeCommand)) {
            return false;
        }

        // State check
        RemoveLeavesBeforeCommand e = (RemoveLeavesBeforeCommand) other;
        return beforeDate.equals(e.beforeDate);
    }
}

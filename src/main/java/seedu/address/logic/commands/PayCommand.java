package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.CalculatedPay;
import seedu.address.model.person.Email;
import seedu.address.model.person.HourlySalary;
import seedu.address.model.person.HoursWorked;
import seedu.address.model.person.Leave;
import seedu.address.model.person.LeavesTaken;
import seedu.address.model.person.Name;
import seedu.address.model.person.Overtime;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.tag.Tag;

/**
 * Pays a person identified using it's displayed index from the address book.
 */
public class PayCommand extends Command {

    public static final String COMMAND_WORD = "pay";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Pays the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_PAY_PERSON_SUCCESS = "Successfully paid person: %1$s";

    private final Index targetIndex;

    public PayCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToPay = lastShownList.get(targetIndex.getZeroBased());
        Person paidPerson = createPaidPerson(personToPay);
        model.setPerson(personToPay, paidPerson);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        model.setViewingPerson(paidPerson);

        return new CommandResult(String.format(MESSAGE_PAY_PERSON_SUCCESS, paidPerson));
    }

    private static Person createPaidPerson(Person personToPay) {
        assert personToPay != null;

        Name name = personToPay.getName();
        Phone phone = personToPay.getPhone();
        Email email = personToPay.getEmail();
        Address address = personToPay.getAddress();
        Role role = personToPay.getRole();
        Leave leave = personToPay.getLeaves();
        LeavesTaken leavesTaken = personToPay.getLeavesTaken();
        HourlySalary hourlySalary = personToPay.getSalary();

        // reset hours worked and overtime back to zero after being paid
        HoursWorked newHours = new HoursWorked("0");
        Overtime newOvertime = new Overtime("0");

        // set calcPay to 0 to represent as paid
        CalculatedPay newCalcPay = new CalculatedPay("0.0");

        Set<Tag> tags = personToPay.getTags();

        return new Person(name, phone, email, address, role, leave, leavesTaken, hourlySalary,
                newHours, newOvertime, newCalcPay, tags);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PayCommand // instanceof handles nulls
                && targetIndex.equals(((PayCommand) other).targetIndex)); // state check
    }
}

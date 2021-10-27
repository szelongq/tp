package seedu.address.logic.commands;

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;
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
 * Pays a person identified using it's displayed index or all persons in the current list from the address book.
 */
public class PayCommand extends Command {

    public static final String COMMAND_WORD = "pay";

    public static final String SPECIAL_COMMAND_PHRASE = "all";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Pays the person identified by the index number used OR all persons in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer) OR \"all\"\n"
            + "Example 1: " + COMMAND_WORD + " 1\n"
            + "Example 2: " + COMMAND_WORD + " " + SPECIAL_COMMAND_PHRASE;

    public static final String MESSAGE_PAY_PERSON_SUCCESS = "Successfully paid person: %1$s";

    public static final String MESSAGE_PAY_ALL_SUCCESS = "Successfully paid all in the list.";

    // Target index of the person to pay
    // If empty, the command pays all persons in the current list
    private final Optional<Index> targetIndex;

    public PayCommand() {
        this.targetIndex = Optional.empty();
    }

    public PayCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = Optional.of(targetIndex);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        CommandResult finalResult;

        if (targetIndex.isEmpty()) {
            finalResult = executePayAll(model);
        }
        else {
            finalResult = executePayByIndex(model);
        }

        return finalResult;
    }

    private CommandResult executePayByIndex(Model model) throws CommandException{
        assert nonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.get().getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        // Pay the person of the specified index
        Person personToPay = lastShownList.get(targetIndex.get().getZeroBased());
        Person paidPerson = createPaidPerson(personToPay);
        model.setPerson(personToPay, paidPerson);

        // View the person that has been paid
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        model.setViewingPerson(paidPerson);

        return new CommandResult(String.format(MESSAGE_PAY_PERSON_SUCCESS, paidPerson));
    }

    private CommandResult executePayAll(Model model) {
        assert nonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        // Pay all persons in the list
        for (Person personToPay: lastShownList) {
            Person paidPerson = createPaidPerson(personToPay);
            model.setPerson(personToPay, paidPerson);
        }

        // View the first person in the list that has been paid
        Index indexOfFirstPersonToPay = Index.fromZeroBased(0);
        Person personToView = lastShownList.get(indexOfFirstPersonToPay.getZeroBased());

        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        model.setViewingPerson(personToView);

        return new CommandResult(MESSAGE_PAY_ALL_SUCCESS);
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

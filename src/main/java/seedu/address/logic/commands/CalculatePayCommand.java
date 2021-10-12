package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.HourlySalary;
import seedu.address.model.person.HoursWorked;
import seedu.address.model.person.Leave;
import seedu.address.model.person.Name;
import seedu.address.model.person.Overtime;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.tag.Tag;


/**
 * Calculates an employee's pay using it's displayed index from the address book
 * and then set the employee to be awaiting payment of the calculated pay.
 */
public class CalculatePayCommand extends Command {

    public static final String COMMAND_WORD = "calculate";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Calculates the pay of the employee identified by the index number "
            + "used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_NOT_PAID = "The employee still has payment due. "
            + "Please pay the employee first before calculating new pay.";

    public static final String MESSAGE_CALCULATE_PAY_SUCCESS = "Calculated Pay: %1$s";

    public static final double OVERTIME_RATE = 1.5;

    private final Index targetIndex;

    public CalculatePayCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToCalculatePay = lastShownList.get(targetIndex.getZeroBased());

        /*
         * An exception is thrown if the employee to be calculated for still
         * has a previous calculated pay that has not been paid yet.
         * Awaiting implementation of amountToPay attribute.
         */
        /*
        if (!personToCalculatePay.isPaid()) {
            throw new CommandException(MESSAGE_NOT_PAID);
        }*/

        HourlySalary salary = personToCalculatePay.getSalary();
        HoursWorked hoursWorked = personToCalculatePay.getHoursWorked();
        Overtime overtime = personToCalculatePay.getOvertime();
        double calculatedPay = calculatePay(salary, hoursWorked, overtime);

        Person personWithCalculatedPay = createPersonWithCalculatedPay(personToCalculatePay, calculatedPay);
        model.setPerson(personToCalculatePay, personWithCalculatedPay);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_CALCULATE_PAY_SUCCESS, calculatedPay));
    }


    private double calculatePay(HourlySalary salary, HoursWorked hoursWorked, Overtime overtime) {
        return (salary.value * hoursWorked.value) + (OVERTIME_RATE * salary.value * overtime.value);
    }

    private Person createPersonWithCalculatedPay(Person personWithCalculatedPay, double calculatedPay) {
        Name name = personWithCalculatedPay.getName();
        Phone phone = personWithCalculatedPay.getPhone();
        Email email = personWithCalculatedPay.getEmail();
        Address address = personWithCalculatedPay.getAddress();
        Role role = personWithCalculatedPay.getRole();
        Leave leaves = personWithCalculatedPay.getLeaves();

        /*
         * Set the new calculated pay to be paid to the employee.
         * Awaiting implementation of amountToPay attribute.
         */
        HourlySalary salary = personWithCalculatedPay.getSalary();

        HoursWorked hours = personWithCalculatedPay.getHoursWorked();
        Set<Tag> tags = personWithCalculatedPay.getTags();

        return new Person(name, phone, email, address, role, leaves, salary, hours, tags);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CalculatePayCommand // instanceof handles nulls
                && targetIndex.equals(((CalculatePayCommand) other).targetIndex)); // state check
    }
}

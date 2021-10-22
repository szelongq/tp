package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Set;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.OvertimePayRate;
import seedu.address.model.person.Address;
import seedu.address.model.person.CalculatedPay;
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
 * Calculates the payroll for all employees in the address book according to the
 * how much work has been done at the current time.
 * After that, marks all employees as awaiting payment of the calculated amount.
 */
public class StartPayrollCommand extends Command {

    public static final String COMMAND_WORD = "startPayroll";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Calculates the payroll for all employees.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_NOT_PAID = "There are employees who still have payment due: %1$s\n"
            + "Please pay all employees first before starting a new payroll.";

    public static final String MESSAGE_START_PAYROLL_SUCCESS = "Payroll done.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        List<Person> personList = model.getFilteredPersonList();

        // First, check if there are any unpaid employees
        for (Person personToCalculatePay: personList) {
            /*
             * An exception is thrown if the employee to be calculated for still
             * has a previous calculated pay that has not been paid yet.
             */
            if (!personToCalculatePay.isPaid()) {
                throw new CommandException(String.format(MESSAGE_NOT_PAID, personToCalculatePay));
            }
        }

        // Get the current set pay rate for overtime
        OvertimePayRate overtimePayRate = model.getOvertimePayRate();

        // If there are no unpaid employees, proceed with calculating payroll
        for (Person personToCalculatePay: personList) {
            HourlySalary salary = personToCalculatePay.getSalary();
            HoursWorked hoursWorked = personToCalculatePay.getHoursWorked();
            Overtime overtime = personToCalculatePay.getOvertime();
            CalculatedPay calculatedPay = calculatePay(salary, hoursWorked, overtime, overtimePayRate);

            // Set the employee to be owed the calculated pay
            Person personWithCalculatedPay = createPersonWithCalculatedPay(personToCalculatePay, calculatedPay);
            model.setPerson(personToCalculatePay, personWithCalculatedPay);
        }

        return new CommandResult(String.format(MESSAGE_START_PAYROLL_SUCCESS));
    }


    private CalculatedPay calculatePay(HourlySalary salary, HoursWorked hoursWorked, Overtime overtime,
                                       OvertimePayRate overtimePayRate) {
        double normalPay = salary.value * hoursWorked.value;
        double overtimePay = overtimePayRate.value * salary.value * overtime.value;
        // Ensure that the total pay is rounded to 2 decimal places.
        String totalRoundedPay = String.format("%.2f", normalPay + overtimePay);

        return new CalculatedPay(totalRoundedPay);
    }

    private Person createPersonWithCalculatedPay(Person personWithCalculatedPay, CalculatedPay newCalculatedPay) {
        Name name = personWithCalculatedPay.getName();
        Phone phone = personWithCalculatedPay.getPhone();
        Email email = personWithCalculatedPay.getEmail();
        Address address = personWithCalculatedPay.getAddress();
        Role role = personWithCalculatedPay.getRole();
        Leave leaves = personWithCalculatedPay.getLeaves();
        HourlySalary salary = personWithCalculatedPay.getSalary();
        HoursWorked hours = personWithCalculatedPay.getHoursWorked();
        Set<Tag> tags = personWithCalculatedPay.getTags();

        return new Person(name, phone, email, address, role, leaves, salary, hours, newCalculatedPay, tags);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StartPayrollCommand); // instanceof handles nulls;
    }
}

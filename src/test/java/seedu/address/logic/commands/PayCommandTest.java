package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Address;
import seedu.address.model.person.CalculatedPay;
import seedu.address.model.person.Email;
import seedu.address.model.person.HourlySalary;
import seedu.address.model.person.HoursWorked;
import seedu.address.model.person.LeaveBalance;
import seedu.address.model.person.LeavesTaken;
import seedu.address.model.person.Name;
import seedu.address.model.person.Overtime;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.tag.Tag;


public class PayCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    private Person createPersonWithCalculatedPay(Person person, CalculatedPay newCalculatedPay) {
        assert person != null;
        assert newCalculatedPay != null;

        Name name = person.getName();
        Phone phone = person.getPhone();
        Email email = person.getEmail();
        Address address = person.getAddress();
        Role role = person.getRole();
        LeaveBalance leaves = person.getLeaveBalance();
        LeavesTaken leavesTaken = person.getLeavesTaken();
        HourlySalary hourlySalary = person.getSalary();
        HoursWorked hoursWorked = person.getHoursWorked();
        Overtime overtime = person.getOvertime();
        // New calculatedPay taken from input parameter
        Set<Tag> tags = person.getTags();

        return new Person(name, phone, email, address, role, leaves, leavesTaken, hourlySalary,
                hoursWorked, overtime, newCalculatedPay, tags);
    }

    private static Person createPaidPerson(Person personToPay) {
        assert personToPay != null;

        Name name = personToPay.getName();
        Phone phone = personToPay.getPhone();
        Email email = personToPay.getEmail();
        Address address = personToPay.getAddress();
        Role role = personToPay.getRole();
        LeaveBalance leaveBalance = personToPay.getLeaveBalance();
        LeavesTaken leavesTaken = personToPay.getLeavesTaken();
        HourlySalary hourlySalary = personToPay.getSalary();
        HoursWorked hoursWorked = personToPay.getHoursWorked();
        Overtime overtime = personToPay.getOvertime();

        // set calcPay to 0 to represent as paid
        CalculatedPay newCalcPay = new CalculatedPay("0.0");

        Set<Tag> tags = personToPay.getTags();

        return new Person(name, phone, email, address, role, leaveBalance, leavesTaken, hourlySalary,
                hoursWorked, overtime, newCalcPay, tags);
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person person = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        CalculatedPay calculatedPay = new CalculatedPay("500.00");
        Person personToPay = createPersonWithCalculatedPay(person, calculatedPay);
        model.setPerson(person, personToPay);

        PayCommand payCommand = new PayCommand(INDEX_FIRST_PERSON);

        Person paidPerson = createPaidPerson(personToPay);

        String expectedMessage = String.format(PayCommand.MESSAGE_PAY_PERSON_SUCCESS,
                personToPay.getCalculatedPay().toString(),
                paidPerson.getName().toString());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToPay, paidPerson);

        assertCommandSuccess(payCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        PayCommand payCommand = new PayCommand(outOfBoundIndex);

        assertCommandFailure(payCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_payAll_success() {
        // Adds calculatedPay to all employees
        for (int i = 0; i < model.getFilteredPersonList().size(); i++) {
            Person person = model.getFilteredPersonList().get(i);
            Person personWithCalcPay = createPersonWithCalculatedPay(person, new CalculatedPay("500"));
            model.setPerson(person, personWithCalcPay);
        }

        // Sets all employees as paid
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        for (int i = 0; i < expectedModel.getFilteredPersonList().size(); i++) {
            Person person = expectedModel.getFilteredPersonList().get(i);
            Person paidPerson = createPaidPerson(person);
            expectedModel.setPerson(person, paidPerson);
        }

        PayCommand payAllCommand = new PayCommand();
        String expectedString = PayCommand.MESSAGE_PAY_ALL_SUCCESS;

        assertCommandSuccess(payAllCommand, model, expectedString, expectedModel);
    }

    @Test
    public void execute_payAllOnAlreadyPaidEmployees_throwsException() {
        PayCommand payAllCommand = new PayCommand();
        // Model initializes with everyone already paid
        assertCommandFailure(payAllCommand, model, PayCommand.MESSAGE_NO_ONE_TO_BE_PAID);
    }

    @Test
    public void equals() {
        PayCommand payFirstCommand = new PayCommand(INDEX_FIRST_PERSON);
        PayCommand paySecondCommand = new PayCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(payFirstCommand.equals(payFirstCommand));

        // same values -> returns true
        PayCommand payFirstCommandCopy = new PayCommand(INDEX_FIRST_PERSON);
        assertTrue(payFirstCommand.equals(payFirstCommandCopy));

        // different types -> returns false
        assertFalse(payFirstCommand.equals(1));

        // null -> returns false
        assertFalse(payFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(payFirstCommand.equals(paySecondCommand));
    }

}

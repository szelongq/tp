package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
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
import seedu.address.model.person.Leave;
import seedu.address.model.person.Name;
import seedu.address.model.person.Overtime;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.tag.Tag;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code CalculatePayCommand}.
 */
public class CalculatePayCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    private CalculatedPay calculatePay(HourlySalary salary, HoursWorked hoursWorked, Overtime overtime) {
        double normalPay = salary.value * hoursWorked.value;
        double overtimePay = CalculatePayCommand.OVERTIME_RATE * salary.value * overtime.value;

        return new CalculatedPay(Double.toString(normalPay + overtimePay));
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

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToCalculatePay = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        CalculatePayCommand calculatePayCommand = new CalculatePayCommand(INDEX_FIRST_PERSON);

        HourlySalary salary = personToCalculatePay.getSalary();
        HoursWorked hoursWorked = personToCalculatePay.getHoursWorked();
        Overtime overtime = personToCalculatePay.getOvertime();
        CalculatedPay calculatedPay = calculatePay(salary, hoursWorked, overtime);

        Person personWithCalculatedPay = createPersonWithCalculatedPay(personToCalculatePay, calculatedPay);

        String expectedMessage =
                String.format(CalculatePayCommand.MESSAGE_CALCULATE_PAY_SUCCESS, personWithCalculatedPay);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToCalculatePay, personWithCalculatedPay);

        assertCommandSuccess(calculatePayCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        CalculatePayCommand calculatePayCommand = new CalculatePayCommand(outOfBoundIndex);

        assertCommandFailure(calculatePayCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToCalculatePay = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        CalculatePayCommand calculatePayCommand = new CalculatePayCommand(INDEX_FIRST_PERSON);

        HourlySalary salary = personToCalculatePay.getSalary();
        HoursWorked hoursWorked = personToCalculatePay.getHoursWorked();
        Overtime overtime = personToCalculatePay.getOvertime();
        CalculatedPay calculatedPay = calculatePay(salary, hoursWorked, overtime);

        Person personWithCalculatedPay = createPersonWithCalculatedPay(personToCalculatePay, calculatedPay);

        String expectedMessage =
                String.format(CalculatePayCommand.MESSAGE_CALCULATE_PAY_SUCCESS, personWithCalculatedPay);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        showPersonAtIndex(expectedModel, INDEX_FIRST_PERSON);
        expectedModel.setPerson(personToCalculatePay, personWithCalculatedPay);


        assertCommandSuccess(calculatePayCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        CalculatePayCommand calculatePayCommand = new CalculatePayCommand(outOfBoundIndex);

        assertCommandFailure(calculatePayCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_personIsNotPaid_throwsCommandException() {
        Person personToCalculatePay = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        CalculatePayCommand calculatePayCommand = new CalculatePayCommand(INDEX_FIRST_PERSON);

        CalculatedPay calculatedPay = new CalculatedPay("500.00");
        Person personWithCalculatedPay = createPersonWithCalculatedPay(personToCalculatePay, calculatedPay);

        model.setPerson(personToCalculatePay, personWithCalculatedPay);

        String expectedMessage =
                String.format(CalculatePayCommand.MESSAGE_NOT_PAID);

        assertCommandFailure(calculatePayCommand, model, expectedMessage);
    }

    @Test
    public void equals() {
        CalculatePayCommand calculatePayFirstCommand = new CalculatePayCommand(INDEX_FIRST_PERSON);
        CalculatePayCommand calculatePaySecondCommand = new CalculatePayCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(calculatePayFirstCommand.equals(calculatePayFirstCommand));

        // same values -> returns true
        CalculatePayCommand calculatePayFirstCommandCopy = new CalculatePayCommand(INDEX_FIRST_PERSON);
        assertTrue(calculatePayFirstCommand.equals(calculatePayFirstCommandCopy));

        // different types -> returns false
        assertFalse(calculatePayFirstCommand.equals(1));

        // null -> returns false
        assertFalse(calculatePayFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(calculatePayFirstCommand.equals(calculatePaySecondCommand));
    }
}

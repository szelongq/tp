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
import seedu.address.model.person.Leave;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.tag.Tag;


public class PayCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    private static Person createPaidPerson(Person personToPay) {
        assert personToPay != null;

        Name name = personToPay.getName();
        Phone phone = personToPay.getPhone();
        Email email = personToPay.getEmail();
        Address address = personToPay.getAddress();
        Role role = personToPay.getRole();
        Leave leave = personToPay.getLeaves();
        HourlySalary hourlySalary = personToPay.getSalary();
        HoursWorked hours = personToPay.getHoursWorked();

        // set calcPay to 0 to represent as paid
        CalculatedPay calcPay = new CalculatedPay("0.0");

        Set<Tag> tags = personToPay.getTags();

        return new Person(name, phone, email, address, role, leave, hourlySalary, hours, calcPay, tags);
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToPay = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        PayCommand payCommand = new PayCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(PayCommand.MESSAGE_PAY_PERSON_SUCCESS, personToPay);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Person paidPerson = createPaidPerson(personToPay);
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

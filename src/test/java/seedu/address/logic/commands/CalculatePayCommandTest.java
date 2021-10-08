package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Salary;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code CalculatePayCommand}.
 */
public class CalculatePayCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToCalculatePay = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        CalculatePayCommand calculatePayCommand = new CalculatePayCommand(INDEX_FIRST_PERSON);

        Salary calculatedPay = personToCalculatePay.getSalary();
        String expectedMessage =
                String.format(CalculatePayCommand.MESSAGE_CALCULATE_PAY_SUCCESS, calculatedPay);

        assertCommandSuccess(calculatePayCommand, model, expectedMessage, model);
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

        Salary calculatedPay = personToCalculatePay.getSalary();
        String expectedMessage =
                String.format(CalculatePayCommand.MESSAGE_CALCULATE_PAY_SUCCESS, calculatedPay);

        assertCommandSuccess(calculatePayCommand, model, expectedMessage, model);
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

package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEAVES_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEAVES_BOB;
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
import seedu.address.model.person.LeaveBalance;
import seedu.address.model.person.LeavesTaken;
import seedu.address.model.person.Name;
import seedu.address.model.person.Overtime;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.tag.Tag;

public class DeductLeaveBalanceCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final LeaveBalance removedLeaves = new LeaveBalance("3");
    private final LeaveBalance invalidRemovedLeaves = new LeaveBalance("100");

    private Person createPersonWithRemovedLeaves(Person personToRemoveLeavesFrom, LeaveBalance removedLeaves) {
        Name name = personToRemoveLeavesFrom.getName();
        Phone phone = personToRemoveLeavesFrom.getPhone();
        Email email = personToRemoveLeavesFrom.getEmail();
        Address address = personToRemoveLeavesFrom.getAddress();
        Role role = personToRemoveLeavesFrom.getRole();
        LeavesTaken leavesTaken = personToRemoveLeavesFrom.getLeavesTaken();
        HourlySalary salary = personToRemoveLeavesFrom.getSalary();
        HoursWorked hours = personToRemoveLeavesFrom.getHoursWorked();
        Overtime overtime = personToRemoveLeavesFrom.getOvertime();
        CalculatedPay calculatedPay = personToRemoveLeavesFrom.getCalculatedPay();
        Set<Tag> tags = personToRemoveLeavesFrom.getTags();

        LeaveBalance newLeaves = personToRemoveLeavesFrom.getLeaveBalance().removeLeaves(removedLeaves);

        return new Person(name, phone, email, address, role, newLeaves, leavesTaken,
                salary, hours, overtime, calculatedPay, tags);
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToRemoveLeavesFrom = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeductLeaveBalanceCommand deductLeaveBalanceCommand =
                new DeductLeaveBalanceCommand(INDEX_FIRST_PERSON, removedLeaves);

        Person personWithRemovedLeaves = createPersonWithRemovedLeaves(personToRemoveLeavesFrom, removedLeaves);

        String expectedMessage = String.format(DeductLeaveBalanceCommand.MESSAGE_SUCCESS,
                personWithRemovedLeaves.getName().toString(),
                personWithRemovedLeaves.getLeaveBalance().toString(),
                personWithRemovedLeaves.getLeaveBalance().toString().equals("1") ? "" : "s");

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToRemoveLeavesFrom, personWithRemovedLeaves);

        assertCommandSuccess(deductLeaveBalanceCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeductLeaveBalanceCommand deductLeaveBalanceCommand =
                new DeductLeaveBalanceCommand(outOfBoundIndex, removedLeaves);

        assertCommandFailure(deductLeaveBalanceCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToRemoveLeavesFrom = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeductLeaveBalanceCommand deductLeaveBalanceCommand =
                new DeductLeaveBalanceCommand(INDEX_FIRST_PERSON, removedLeaves);

        Person personWithRemovedLeaves = createPersonWithRemovedLeaves(personToRemoveLeavesFrom, removedLeaves);

        String expectedMessage = String.format(DeductLeaveBalanceCommand.MESSAGE_SUCCESS,
                personWithRemovedLeaves.getName().toString(),
                personWithRemovedLeaves.getLeaveBalance().toString(),
                personWithRemovedLeaves.getLeaveBalance().toString().equals("1") ? "" : "s");

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        showPersonAtIndex(expectedModel, INDEX_FIRST_PERSON);
        expectedModel.setPerson(personToRemoveLeavesFrom, personWithRemovedLeaves);

        assertCommandSuccess(deductLeaveBalanceCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // Ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeductLeaveBalanceCommand deductLeaveBalanceCommand =
                new DeductLeaveBalanceCommand(outOfBoundIndex, removedLeaves);

        assertCommandFailure(deductLeaveBalanceCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_notEnoughLeaves_throwsCommandException() {
        DeductLeaveBalanceCommand deductLeaveBalanceCommand =
                new DeductLeaveBalanceCommand(INDEX_FIRST_PERSON, invalidRemovedLeaves);
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        assertCommandFailure(deductLeaveBalanceCommand, model,
                String.format(Messages.MESSAGE_INVALID_REMOVE_INPUT,
                        invalidRemovedLeaves.toString(),
                        invalidRemovedLeaves.toString().equals("1") ? "leave" : "leaves",
                        firstPerson.getLeaveBalance().toString(),
                        firstPerson.getLeaveBalance().toString().equals("1") ? "leave" : "leaves"));
    }

    @Test
    public void equals() {
        final DeductLeaveBalanceCommand standardCommand =
                new DeductLeaveBalanceCommand(INDEX_FIRST_PERSON, new LeaveBalance(VALID_LEAVES_AMY));

        // Same values -> returns true
        DeductLeaveBalanceCommand commandWithSameValues =
                new DeductLeaveBalanceCommand(INDEX_FIRST_PERSON, new LeaveBalance(VALID_LEAVES_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // Same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // Different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // Different index -> returns false
        assertFalse(standardCommand.equals(new DeductLeaveBalanceCommand(INDEX_SECOND_PERSON,
                new LeaveBalance(VALID_LEAVES_AMY))));

        // Different number of leaves -> returns false
        assertFalse(standardCommand.equals(new DeductLeaveBalanceCommand(INDEX_FIRST_PERSON,
                new LeaveBalance(VALID_LEAVES_BOB))));
    }
}

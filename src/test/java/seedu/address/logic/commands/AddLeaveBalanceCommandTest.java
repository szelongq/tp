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

public class AddLeaveBalanceCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final LeaveBalance addedLeaves = new LeaveBalance("3");

    private Person createPersonWithAddedLeaves(Person personToAddLeavesTo, LeaveBalance addedLeaves) {
        Name name = personToAddLeavesTo.getName();
        Phone phone = personToAddLeavesTo.getPhone();
        Email email = personToAddLeavesTo.getEmail();
        Address address = personToAddLeavesTo.getAddress();
        Role role = personToAddLeavesTo.getRole();
        LeavesTaken leavesTaken = personToAddLeavesTo.getLeavesTaken();
        HourlySalary salary = personToAddLeavesTo.getSalary();
        HoursWorked hours = personToAddLeavesTo.getHoursWorked();
        Overtime overtime = personToAddLeavesTo.getOvertime();
        CalculatedPay calculatedPay = personToAddLeavesTo.getCalculatedPay();
        Set<Tag> tags = personToAddLeavesTo.getTags();

        LeaveBalance newLeaves = personToAddLeavesTo.getLeaveBalance().addLeaves(addedLeaves);

        return new Person(name, phone, email, address, role, newLeaves, leavesTaken,
                salary, hours, overtime, calculatedPay, tags);
    }

    private Person createPersonWithMaxLeaveBalance(Person personToAddLeavesTo) {
        Name name = personToAddLeavesTo.getName();
        Phone phone = personToAddLeavesTo.getPhone();
        Email email = personToAddLeavesTo.getEmail();
        Address address = personToAddLeavesTo.getAddress();
        Role role = personToAddLeavesTo.getRole();
        LeavesTaken leavesTaken = personToAddLeavesTo.getLeavesTaken();
        HourlySalary salary = personToAddLeavesTo.getSalary();
        HoursWorked hours = personToAddLeavesTo.getHoursWorked();
        Overtime overtime = personToAddLeavesTo.getOvertime();
        CalculatedPay calculatedPay = personToAddLeavesTo.getCalculatedPay();
        Set<Tag> tags = personToAddLeavesTo.getTags();

        LeaveBalance newLeaves = new LeaveBalance(String.valueOf(LeaveBalance.MAX_LEAVES));

        return new Person(name, phone, email, address, role, newLeaves, leavesTaken,
                salary, hours, overtime, calculatedPay, tags);
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToAddLeavesTo = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        AddLeaveBalanceCommand addLeaveBalanceCommand = new AddLeaveBalanceCommand(INDEX_FIRST_PERSON, addedLeaves);

        Person personWithAddedLeaves = createPersonWithAddedLeaves(personToAddLeavesTo, addedLeaves);

        String expectedMessage = String.format(AddLeaveBalanceCommand.MESSAGE_SUCCESS,
                personWithAddedLeaves.getName().toString(),
                personWithAddedLeaves.getLeaveBalance().toString(),
                personWithAddedLeaves.getLeaveBalance().toString().equals("1") ? "" : "s");

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToAddLeavesTo, personWithAddedLeaves);

        assertCommandSuccess(addLeaveBalanceCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AddLeaveBalanceCommand addLeaveBalanceCommand = new AddLeaveBalanceCommand(outOfBoundIndex, addedLeaves);

        assertCommandFailure(addLeaveBalanceCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToAddLeavesTo = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        AddLeaveBalanceCommand addLeaveBalanceCommand = new AddLeaveBalanceCommand(INDEX_FIRST_PERSON, addedLeaves);

        Person personWithAddedLeaves = createPersonWithAddedLeaves(personToAddLeavesTo, addedLeaves);

        String expectedMessage = String.format(AddLeaveBalanceCommand.MESSAGE_SUCCESS,
                personWithAddedLeaves.getName().toString(),
                personWithAddedLeaves.getLeaveBalance().toString(),
                personWithAddedLeaves.getLeaveBalance().toString().equals("1") ? "" : "s");

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        showPersonAtIndex(expectedModel, INDEX_FIRST_PERSON);
        expectedModel.setPerson(personToAddLeavesTo, personWithAddedLeaves);

        assertCommandSuccess(addLeaveBalanceCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // Ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        AddLeaveBalanceCommand addLeaveBalanceCommand = new AddLeaveBalanceCommand(outOfBoundIndex, addedLeaves);

        assertCommandFailure(addLeaveBalanceCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_exceedsMaxLeaveBalance_throwsCommandException() {
        Person personToAddLeavesTo = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person personWithMaxLeaveBalance = createPersonWithMaxLeaveBalance(personToAddLeavesTo);
        model.setPerson(personToAddLeavesTo, personWithMaxLeaveBalance);

        AddLeaveBalanceCommand addLeaveBalanceCommand =
                new AddLeaveBalanceCommand(INDEX_FIRST_PERSON, addedLeaves);

        String leaveCapacityString = personWithMaxLeaveBalance.getLeaveBalance()
                .getRemainingLeaveCapacity().toString();
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_ADD_INPUT,
                LeaveBalance.MAX_LEAVES,
                "leaves", leaveCapacityString,
                leaveCapacityString.equals("1") ? "leave" : "leaves");

        assertCommandFailure(addLeaveBalanceCommand, model, expectedMessage);
    }

    @Test
    public void equals() {
        final AddLeaveBalanceCommand standardCommand = new AddLeaveBalanceCommand(INDEX_FIRST_PERSON,
                new LeaveBalance(VALID_LEAVES_AMY));

        // Same values -> returns true
        AddLeaveBalanceCommand commandWithSameValues = new AddLeaveBalanceCommand(INDEX_FIRST_PERSON,
                new LeaveBalance(VALID_LEAVES_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // Same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // Different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // Different index -> returns false
        assertFalse(standardCommand.equals(new AddLeaveBalanceCommand(INDEX_SECOND_PERSON,
                new LeaveBalance(VALID_LEAVES_AMY))));

        // Different number of leaves -> returns false
        assertFalse(standardCommand.equals(new AddLeaveBalanceCommand(INDEX_FIRST_PERSON,
                new LeaveBalance(VALID_LEAVES_BOB))));
    }
}

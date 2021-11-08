package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.LocalDate;
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



public class AssignLeaveCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final LocalDate addedDate = LocalDate.of(2021, 9, 9);

    private Person createPersonWithAddedDate(Person personToAddDateTo, LocalDate date) {
        Name name = personToAddDateTo.getName();
        Phone phone = personToAddDateTo.getPhone();
        Email email = personToAddDateTo.getEmail();
        Address address = personToAddDateTo.getAddress();
        Role role = personToAddDateTo.getRole();
        HourlySalary salary = personToAddDateTo.getSalary();
        HoursWorked hoursWorked = personToAddDateTo.getHoursWorked();
        Overtime overtime = personToAddDateTo.getOvertime();
        CalculatedPay calculatedPay = personToAddDateTo.getCalculatedPay();
        Set<Tag> tags = personToAddDateTo.getTags();

        LeaveBalance newLeaveBalance =
                personToAddDateTo.getLeaveBalance()
                        .removeLeaves(new LeaveBalance("1"));
        LeavesTaken newLeavesTaken = personToAddDateTo.getLeavesTaken().addDate(date);

        return new Person(name, phone, email, address, role, newLeaveBalance, newLeavesTaken,
                salary, hoursWorked, overtime, calculatedPay, tags);
    }

    private Person createPersonWithNoLeaves(Person personToAddDateTo) {
        Name name = personToAddDateTo.getName();
        Phone phone = personToAddDateTo.getPhone();
        Email email = personToAddDateTo.getEmail();
        Address address = personToAddDateTo.getAddress();
        Role role = personToAddDateTo.getRole();
        HourlySalary salary = personToAddDateTo.getSalary();
        HoursWorked hoursWorked = personToAddDateTo.getHoursWorked();
        Overtime overtime = personToAddDateTo.getOvertime();
        LeavesTaken leavesTaken = personToAddDateTo.getLeavesTaken();
        CalculatedPay calculatedPay = personToAddDateTo.getCalculatedPay();
        Set<Tag> tags = personToAddDateTo.getTags();

        LeaveBalance newLeaveBalance = new LeaveBalance("0");

        return new Person(name, phone, email, address, role, newLeaveBalance, leavesTaken,
                salary, hoursWorked, overtime, calculatedPay, tags);
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToAddDateTo = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        AssignLeaveCommand assignLeaveCommand = new AssignLeaveCommand(INDEX_FIRST_PERSON, addedDate);

        Person personWithAddedDate = createPersonWithAddedDate(personToAddDateTo, addedDate);

        String expectedMessage = String.format(AssignLeaveCommand.MESSAGE_SUCCESS,
                addedDate.toString(),
                personToAddDateTo.getName().toString());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToAddDateTo, personWithAddedDate);

        assertCommandSuccess(assignLeaveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AssignLeaveCommand assignLeaveCommand =
                new AssignLeaveCommand(outOfBoundIndex, addedDate);

        assertCommandFailure(assignLeaveCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToAddDateTo = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        AssignLeaveCommand assignLeaveCommand =
                new AssignLeaveCommand(INDEX_FIRST_PERSON, addedDate);

        Person personWithAddedDate = createPersonWithAddedDate(personToAddDateTo, addedDate);

        String expectedMessage = String.format(AssignLeaveCommand.MESSAGE_SUCCESS,
                addedDate.toString(),
                personToAddDateTo.getName().toString());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        showPersonAtIndex(expectedModel, INDEX_FIRST_PERSON);
        expectedModel.setPerson(personToAddDateTo, personWithAddedDate);

        assertCommandSuccess(assignLeaveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // Ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        AssignLeaveCommand assignLeaveCommand = new AssignLeaveCommand(outOfBoundIndex, addedDate);

        assertCommandFailure(assignLeaveCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_insufficientLeaves_throwsCommandException() {
        Person personToAddDateTo = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person personWithNoLeaves = createPersonWithNoLeaves(personToAddDateTo);
        model.setPerson(personToAddDateTo, personWithNoLeaves);

        AssignLeaveCommand assignLeaveCommand = new AssignLeaveCommand(INDEX_FIRST_PERSON, addedDate);

        String expectedMessage = String.format(Messages.MESSAGE_INSUFFICIENT_LEAVES,
                personWithNoLeaves.getName().toString());

        assertCommandFailure(assignLeaveCommand, model, expectedMessage);
    }

    @Test
    public void execute_duplicateDate_throwsCommandException() {
        Person personToAddDateTo = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person personWithAddedDate = createPersonWithAddedDate(personToAddDateTo, addedDate);
        model.setPerson(personToAddDateTo, personWithAddedDate);

        AssignLeaveCommand assignLeaveCommand = new AssignLeaveCommand(INDEX_FIRST_PERSON, addedDate);

        String expectedMessage = String.format(Messages.MESSAGE_DATE_ALREADY_ASSIGNED,
                personWithAddedDate.getName().toString(), addedDate);

        assertCommandFailure(assignLeaveCommand, model, expectedMessage);
    }

    @Test
    public void equals() {
        final AssignLeaveCommand standardCommand =
                new AssignLeaveCommand(INDEX_FIRST_PERSON, LocalDate.parse(VALID_DATE_AMY));

        // Same values -> returns true
        AssignLeaveCommand commandWithSameValues =
                new AssignLeaveCommand(INDEX_FIRST_PERSON, LocalDate.parse(VALID_DATE_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // Same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // Different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // Different index -> returns false
        assertFalse(standardCommand.equals(new AssignLeaveCommand(INDEX_SECOND_PERSON,
                LocalDate.parse(VALID_DATE_AMY))));

        // Different date -> returns false
        assertFalse(standardCommand.equals(new AssignLeaveCommand(INDEX_FIRST_PERSON,
                LocalDate.parse(VALID_DATE_BOB))));
    }
}

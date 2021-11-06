package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HOURSWORKED_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HOURSWORKED_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_OVERTIME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_OVERTIME_BOB;
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

public class DeductHoursWorkedCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final HoursWorked removedHoursWorked = new HoursWorked("10");
    private final Overtime removedOvertime = new Overtime("5");
    private final HoursWorked invalidRemovedHoursWorked = new HoursWorked("100");
    private final Overtime invalidRemovedOvertime = new Overtime("100");

    private Person createPersonWithHoursRemoved(Person personToRemoveHoursFrom, HoursWorked hoursWorked,
                                                Overtime overtime) {
        Name name = personToRemoveHoursFrom.getName();
        Phone phone = personToRemoveHoursFrom.getPhone();
        Email email = personToRemoveHoursFrom.getEmail();
        Address address = personToRemoveHoursFrom.getAddress();
        Role role = personToRemoveHoursFrom.getRole();
        LeaveBalance leaves = personToRemoveHoursFrom.getLeaveBalance();
        LeavesTaken leavesTaken = personToRemoveHoursFrom.getLeavesTaken();
        HourlySalary salary = personToRemoveHoursFrom.getSalary();
        CalculatedPay calculatedPay = personToRemoveHoursFrom.getCalculatedPay();
        Set<Tag> tags = personToRemoveHoursFrom.getTags();

        HoursWorked newHours = personToRemoveHoursFrom.getHoursWorked().removeHoursWorked(hoursWorked);
        Overtime newOvertime = personToRemoveHoursFrom.getOvertime().removeOvertime(overtime);

        return new Person(name, phone, email, address, role, leaves, leavesTaken,
                salary, newHours, newOvertime, calculatedPay, tags);
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToRemoveHoursFrom = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeductHoursWorkedCommand deductHoursWorkedCommand =
                new DeductHoursWorkedCommand(INDEX_FIRST_PERSON, removedHoursWorked, removedOvertime);

        Person personWithRemovedHours =
                createPersonWithHoursRemoved(personToRemoveHoursFrom, removedHoursWorked, removedOvertime);

        String expectedMessage = String.format(DeductHoursWorkedCommand.MESSAGE_SUCCESS,
                        personWithRemovedHours.getName().toString(),
                        personWithRemovedHours.getHoursWorked().toString(),
                        personWithRemovedHours.getHoursWorked().toString().equals("1") ? "" : "s",
                        personWithRemovedHours.getOvertime().toString(),
                        personWithRemovedHours.getOvertime().toString().equals("1") ? "" : "s");

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToRemoveHoursFrom, personWithRemovedHours);

        assertCommandSuccess(deductHoursWorkedCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeductHoursWorkedCommand deductHoursWorkedCommand =
                new DeductHoursWorkedCommand(outOfBoundIndex, removedHoursWorked, removedOvertime);

        assertCommandFailure(deductHoursWorkedCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToRemoveHoursFrom = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeductHoursWorkedCommand deductHoursWorkedCommand =
                new DeductHoursWorkedCommand(INDEX_FIRST_PERSON, removedHoursWorked, removedOvertime);

        Person personWithRemovedHours =
                createPersonWithHoursRemoved(personToRemoveHoursFrom, removedHoursWorked, removedOvertime);

        String expectedMessage = String.format(DeductHoursWorkedCommand.MESSAGE_SUCCESS,
                personWithRemovedHours.getName().toString(),
                personWithRemovedHours.getHoursWorked().toString(),
                personWithRemovedHours.getHoursWorked().toString().equals("1") ? "" : "s",
                personWithRemovedHours.getOvertime().toString(),
                personWithRemovedHours.getOvertime().toString().equals("1") ? "" : "s");

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        showPersonAtIndex(expectedModel, INDEX_FIRST_PERSON);
        expectedModel.setPerson(personToRemoveHoursFrom, personWithRemovedHours);

        assertCommandSuccess(deductHoursWorkedCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // Ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeductHoursWorkedCommand deductHoursWorkedCommand =
                new DeductHoursWorkedCommand(outOfBoundIndex, removedHoursWorked, removedOvertime);

        assertCommandFailure(deductHoursWorkedCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_notEnoughHoursWorked_throwsCommandException() {
        DeductHoursWorkedCommand deductHoursWorkedCommand =
                new DeductHoursWorkedCommand(INDEX_FIRST_PERSON, invalidRemovedHoursWorked, removedOvertime);
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        assertCommandFailure(deductHoursWorkedCommand, model,
                String.format(Messages.MESSAGE_INVALID_REMOVE_INPUT,
                        invalidRemovedHoursWorked.toString(),
                        invalidRemovedHoursWorked.toString().equals("1") ? "hour worked" : "hours worked",
                        firstPerson.getHoursWorked().toString(),
                        firstPerson.getHoursWorked().toString().equals("1") ? "hour worked" : "hours worked"));
    }

    @Test
    public void execute_notEnoughOvertime_throwsCommandException() {
        DeductHoursWorkedCommand deductHoursWorkedCommand =
                new DeductHoursWorkedCommand(INDEX_FIRST_PERSON, removedHoursWorked, invalidRemovedOvertime);
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        assertCommandFailure(deductHoursWorkedCommand, model,
                String.format(Messages.MESSAGE_INVALID_REMOVE_INPUT,
                        invalidRemovedOvertime.toString(),
                        invalidRemovedOvertime.toString().equals("1")
                                ? "overtime hour worked" : "overtime hours worked",
                        firstPerson.getOvertime().toString(),
                        firstPerson.getOvertime().toString().equals("1")
                                ? "overtime hour worked" : "overtime hours worked"));
    }

    @Test
    public void equals() {
        final DeductHoursWorkedCommand standardCommand =
                new DeductHoursWorkedCommand(INDEX_FIRST_PERSON, new HoursWorked(VALID_HOURSWORKED_AMY),
                        new Overtime(VALID_OVERTIME_AMY));

        // Same values -> returns true
        DeductHoursWorkedCommand commandWithSameValues =
                new DeductHoursWorkedCommand(INDEX_FIRST_PERSON, new HoursWorked(VALID_HOURSWORKED_AMY),
                        new Overtime(VALID_OVERTIME_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // Same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // Different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // Different index -> returns false
        assertFalse(standardCommand.equals(new DeductHoursWorkedCommand(INDEX_SECOND_PERSON,
                new HoursWorked(VALID_HOURSWORKED_AMY), new Overtime(VALID_OVERTIME_AMY))));

        // Different number of leaves -> returns false
        assertFalse(standardCommand.equals(new DeductHoursWorkedCommand(INDEX_FIRST_PERSON,
                new HoursWorked(VALID_HOURSWORKED_BOB), new Overtime(VALID_OVERTIME_BOB))));
    }
}

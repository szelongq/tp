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
import seedu.address.model.person.Leave;
import seedu.address.model.person.LeavesTaken;
import seedu.address.model.person.Name;
import seedu.address.model.person.Overtime;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.tag.Tag;

public class AddHoursWorkedCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final HoursWorked addedHoursWorked = new HoursWorked("10");
    private final Overtime addedOvertime = new Overtime("10");

    private Person createPersonWithAddedHours(Person personToAddHoursTo, HoursWorked hoursWorked,
                                              Overtime overtime) {
        Name name = personToAddHoursTo.getName();
        Phone phone = personToAddHoursTo.getPhone();
        Email email = personToAddHoursTo.getEmail();
        Address address = personToAddHoursTo.getAddress();
        Role role = personToAddHoursTo.getRole();
        Leave leaves = personToAddHoursTo.getLeaves();
        LeavesTaken leavesTaken = personToAddHoursTo.getLeavesTaken();
        HourlySalary salary = personToAddHoursTo.getSalary();
        CalculatedPay calculatedPay = personToAddHoursTo.getCalculatedPay();
        Set<Tag> tags = personToAddHoursTo.getTags();

        HoursWorked newHours = personToAddHoursTo.getHoursWorked().addHoursWorked(hoursWorked);
        Overtime newOvertime = personToAddHoursTo.getOvertime().addOvertime(overtime);

        return new Person(name, phone, email, address, role, leaves, leavesTaken,
                salary, newHours, newOvertime, calculatedPay, tags);
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToAddHoursTo = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        AddHoursWorkedCommand addHoursWorkedCommand =
                new AddHoursWorkedCommand(INDEX_FIRST_PERSON, addedHoursWorked, addedOvertime);

        Person personWithAddedHours = createPersonWithAddedHours(personToAddHoursTo, addedHoursWorked, addedOvertime);

        String expectedMessage =
                String.format(AddHoursWorkedCommand.MESSAGE_SUCCESS, personWithAddedHours);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToAddHoursTo, personWithAddedHours);

        assertCommandSuccess(addHoursWorkedCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AddHoursWorkedCommand addHoursWorkedCommand =
                new AddHoursWorkedCommand(outOfBoundIndex, addedHoursWorked, addedOvertime);

        assertCommandFailure(addHoursWorkedCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToAddHoursTo = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        AddHoursWorkedCommand addHoursWorkedCommand =
                new AddHoursWorkedCommand(INDEX_FIRST_PERSON, addedHoursWorked, addedOvertime);

        Person personWithAddedHours = createPersonWithAddedHours(personToAddHoursTo, addedHoursWorked, addedOvertime);

        String expectedMessage =
                String.format(AddHoursWorkedCommand.MESSAGE_SUCCESS, personWithAddedHours);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        showPersonAtIndex(expectedModel, INDEX_FIRST_PERSON);
        expectedModel.setPerson(personToAddHoursTo, personWithAddedHours);

        assertCommandSuccess(addHoursWorkedCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // Ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        AddHoursWorkedCommand addHoursWorkedCommand =
                new AddHoursWorkedCommand(outOfBoundIndex, addedHoursWorked, addedOvertime);

        assertCommandFailure(addHoursWorkedCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final AddHoursWorkedCommand standardCommand =
                new AddHoursWorkedCommand(INDEX_FIRST_PERSON, new HoursWorked(VALID_HOURSWORKED_AMY),
                        new Overtime(VALID_OVERTIME_AMY));

        // Same values -> returns true
        AddHoursWorkedCommand commandWithSameValues =
                new AddHoursWorkedCommand(INDEX_FIRST_PERSON, new HoursWorked(VALID_HOURSWORKED_AMY),
                        new Overtime(VALID_OVERTIME_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // Same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // Different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // Different index -> returns false
        assertFalse(standardCommand.equals(new AddHoursWorkedCommand(INDEX_SECOND_PERSON,
                new HoursWorked(VALID_HOURSWORKED_AMY), new Overtime(VALID_OVERTIME_AMY))));

        // Different number of leaves -> returns false
        assertFalse(standardCommand.equals(new AddHoursWorkedCommand(INDEX_FIRST_PERSON,
                new HoursWorked(VALID_HOURSWORKED_BOB), new Overtime(VALID_OVERTIME_BOB))));
    }
}

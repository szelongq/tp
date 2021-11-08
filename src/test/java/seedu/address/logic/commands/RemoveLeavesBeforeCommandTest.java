package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
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
import seedu.address.model.person.predicates.NameContainsKeywordsPredicate;
import seedu.address.model.tag.Tag;

public class RemoveLeavesBeforeCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final LocalDate filterDate = LocalDate.of(2021, 9, 9);
    private final LocalDate unfilteredDate = LocalDate.of(2021, 9, 10);

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

        LeaveBalance leaveBalance = personToAddDateTo.getLeaveBalance();
        LeavesTaken newLeavesTaken = personToAddDateTo.getLeavesTaken().addDate(date);

        return new Person(name, phone, email, address, role, leaveBalance, newLeavesTaken,
                salary, hoursWorked, overtime, calculatedPay, tags);
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToAddDateTo = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person personWithAddedDate = createPersonWithAddedDate(personToAddDateTo, filterDate);
        personWithAddedDate = createPersonWithAddedDate(personWithAddedDate, unfilteredDate);
        model.setPerson(personToAddDateTo, personWithAddedDate);

        RemoveLeavesBeforeCommand removeLeavesBeforeCommand = new RemoveLeavesBeforeCommand(filterDate);

        Person expectedPerson = createPersonWithAddedDate(personToAddDateTo, unfilteredDate);

        String expectedMessage = String.format(RemoveLeavesBeforeCommand.MESSAGE_SUCCESS, filterDate);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personWithAddedDate, expectedPerson);

        assertCommandSuccess(removeLeavesBeforeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToAddDateTo = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person personWithAddedDate = createPersonWithAddedDate(personToAddDateTo, filterDate);
        personWithAddedDate = createPersonWithAddedDate(personWithAddedDate, unfilteredDate);
        model.setPerson(personToAddDateTo, personWithAddedDate);

        RemoveLeavesBeforeCommand removeLeavesBeforeCommand = new RemoveLeavesBeforeCommand(filterDate);

        Person expectedPerson = createPersonWithAddedDate(personToAddDateTo, unfilteredDate);

        String expectedMessage = String.format(RemoveLeavesBeforeCommand.MESSAGE_SUCCESS, filterDate);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        showPersonAtIndex(expectedModel, INDEX_FIRST_PERSON);
        expectedModel.setPerson(personWithAddedDate, expectedPerson);

        assertCommandSuccess(removeLeavesBeforeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_emptyFilteredList_throwsCommandException() {
        // Get an empty filtered list
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList("sajdklsajkldajkljakl")));

        RemoveLeavesBeforeCommand removeLeavesBeforeCommand = new RemoveLeavesBeforeCommand(filterDate);

        assertCommandFailure(removeLeavesBeforeCommand, model, Messages.MESSAGE_EMPTY_FILTERED_LIST);
    }

    @Test
    public void equals() {
        final RemoveLeavesBeforeCommand standardCommand =
                new RemoveLeavesBeforeCommand(LocalDate.parse(VALID_DATE_AMY));

        // Same values -> returns true
        RemoveLeavesBeforeCommand commandWithSameValues =
                new RemoveLeavesBeforeCommand(LocalDate.parse(VALID_DATE_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // Same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // Different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // Different date -> returns false
        assertFalse(standardCommand.equals(new RemoveLeavesBeforeCommand(LocalDate.parse(VALID_DATE_BOB))));
    }
}

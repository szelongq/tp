package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.ImportCommand.MESSAGE_IMPORT_MISSING_FILE;
import static seedu.address.logic.commands.ImportCommand.MESSAGE_IMPORT_SUCCESS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Person;

public class ImportCommandTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "CsvImportTest");
    private static final Path ALL_COLUMNS_PRESENT_FILE = TEST_DATA_FOLDER.resolve("AllColumnsPresent.csv");
    private static final Path MIXED_COLUMN_ORDERING_FILE = TEST_DATA_FOLDER.resolve("MixedColumnOrdering.csv");
    private static final Path MISSING_LEAVES_SALARY_HOURSWORKED_TAGS_FILE =
            TEST_DATA_FOLDER.resolve("MissingLeavesSalaryHoursWorkedTags.csv");
    private Model modelWithDefaultAddressBook;
    private Model model;

    @BeforeEach
    public void setUp() {
        modelWithDefaultAddressBook = new ModelStubWithDefaultAddressBook();
        model = new ModelStubWithDefaultAddressBook();
    }

    @Test
    public void import_invalidFilePath_failure() {
        assertThrows(CommandException.class, MESSAGE_IMPORT_MISSING_FILE, () ->
            new ImportCommand("notAValidPath.csv").execute(model));
    }

    @Test
    public void import_csvAllColumnsPresent_success() throws Exception {
        String filePathString = ALL_COLUMNS_PRESENT_FILE.toString();
        CommandResult result = new ImportCommand(filePathString).execute(model);

        boolean isDifferent = false;
        ObservableList<Person> original = model.getAddressBook().getPersonList();
        ObservableList<Person> imported = modelWithDefaultAddressBook.getAddressBook().getPersonList();

        for (Person person : original) {
            if (!imported.contains(person)) {
                isDifferent = true;
            }
        }

        assertEquals(MESSAGE_IMPORT_SUCCESS, result.getFeedbackToUser());
        assertTrue(isDifferent);
    }

    @Test
    public void import_csvMixedColumnOrdering_success() throws Exception {
        String filePathString = MIXED_COLUMN_ORDERING_FILE.toString();
        CommandResult result = new ImportCommand(filePathString).execute(model);

        boolean isDifferent = false;
        ObservableList<Person> original = model.getAddressBook().getPersonList();
        ObservableList<Person> imported = modelWithDefaultAddressBook.getAddressBook().getPersonList();

        for (Person person : original) {
            if (!imported.contains(person)) {
                isDifferent = true;
            }
        }

        assertEquals(MESSAGE_IMPORT_SUCCESS, result.getFeedbackToUser());
        assertTrue(isDifferent);
    }

    @Test
    public void import_csvMissingLeavesSalaryHoursWorkedTags_success() throws Exception {
        String filePathString = MISSING_LEAVES_SALARY_HOURSWORKED_TAGS_FILE.toString();
        CommandResult result = new ImportCommand(filePathString).execute(model);

        boolean isDifferent = false;
        ObservableList<Person> original = model.getAddressBook().getPersonList();
        ObservableList<Person> imported = modelWithDefaultAddressBook.getAddressBook().getPersonList();

        for (Person person : original) {
            if (!imported.contains(person)) {
                isDifferent = true; // Is there a better way of checking this? Currently is n^2 runtime.
            }
        }

        assertEquals(MESSAGE_IMPORT_SUCCESS, result.getFeedbackToUser());
        assertTrue(isDifferent);
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains the default address book.
     */
    private class ModelStubWithDefaultAddressBook extends ModelStub {
        private ReadOnlyAddressBook addressBook;

        ModelStubWithDefaultAddressBook() {
            this.addressBook = getTypicalAddressBook();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return this.addressBook;
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            this.addressBook = newData;
        }
    }


}

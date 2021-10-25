package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.opencsv.bean.CsvToBeanBuilder;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
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
import seedu.address.model.person.PersonInput;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.tag.Tag;

public class ImportCommand extends Command {
    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": imports a current existing CSV file into HeRon.\n"
            + "Parameters: Absolute Path leading to desired CSV file.\n"
            + "Example: " + COMMAND_WORD + " /Users/Owner/Desktop/toBeImported.csv";

    public static final String MESSAGE_IMPORT_SUCCESS = "File was successfully imported";
    public static final String MESSAGE_IMPORT_FAILURE = "Error occurred while importing the file. ";
    public static final String MESSAGE_IMPORT_MISSING_FILE = MESSAGE_IMPORT_FAILURE + "\n"
            + "Please check the filepath and try again.";
    public static final String MESSAGE_IMPORT_MISSING_FIELDS = MESSAGE_IMPORT_FAILURE + "\n"
            + "Please check all entries have the required fields: Name, Contact Number, Address, Email and Role.";
    public static final String MESSAGE_IMPORT_FORMAT_ERROR = MESSAGE_IMPORT_FAILURE
            + "Please check the formatting of the data.\n"
            + "Ensure that there are no entries with all empty fields in the CSV file "
            + "and the number of header columns match the number of columns in all employee entries.";

    private final String filepathString;

    /**
     * Constructor for the ImportCommand
     * @param filepath The string representation of the filepath of the desired file.
     */
    public ImportCommand(String filepath) {
        requireNonNull(filepath);
        this.filepathString = filepath;
    }

    /**
     * Executes the specific command.
     * @param model {@code Model} which the command should operate on.
     * @return The feedback of the command to be returned to the user
     * @throws CommandException If an error occurs during the import.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            List<Person> newPersonList = processCsv(this.filepathString);
            AddressBook newAddressBook = new AddressBook();
            newAddressBook.setPersons(newPersonList);
            model.setAddressBook(newAddressBook);
        } catch (CommandException e) {
            throw new CommandException(e.getMessage());
        }
        return new CommandResult(MESSAGE_IMPORT_SUCCESS);
    }

    /**
     * Parses and creates the new address book from the data in the CSV file.
     * @param filepath The string representation of the filepath to the desired csv file.
     * @return List A collection of Person objects to be replaced into the address book.
     * @throws CommandException If an error occurs during the processing of the CSV file into Person objects.
     */
    @SuppressWarnings("unchecked")
    // The method is sure to return a List<Person> or null.
    public List<Person> processCsv(String filepath) throws CommandException {
        FileReader fileReader;
        List<Person> newPersonList = new ArrayList<>();
        List<PersonInput> newPersonInputList;
        try {
            fileReader = new FileReader(filepath);
        } catch (FileNotFoundException e) {
            throw new CommandException(MESSAGE_IMPORT_MISSING_FILE);
        }

        try {
            newPersonInputList = new CsvToBeanBuilder(fileReader).withType(PersonInput.class)
                    .build().parse();
        } catch (RuntimeException e) {
            throw new CommandException(MESSAGE_IMPORT_FORMAT_ERROR);
        }
        for (PersonInput input : newPersonInputList) {
            try {
                // Required Fields
                Name name = ParserUtil.parseName(input.getName());
                Phone phone = ParserUtil.parsePhone(input.getPhone());
                Email email = ParserUtil.parseEmail(input.getEmail());
                Address address = ParserUtil.parseAddress(input.getAddress());
                Role role = ParserUtil.parseRole(input.getRole());

                // Optional Fields
                Leave leaves = buildLeave(input);
                HourlySalary hourlySalary = buildSalary(input);
                HoursWorked hoursWorked = buildHoursWorked(input);
                Overtime overtime = buildOvertime(input);
                Set<Tag> tagList = buildTags(input);

                LeavesTaken leavesTaken = new LeavesTaken(); // Leaves taken are not read from file

                newPersonList.add(new Person(name, phone, email, address, role, leaves, leavesTaken,
                        hourlySalary, hoursWorked, overtime, new CalculatedPay("0"), tagList));
            } catch (ParseException e) {
                throw new CommandException(MESSAGE_IMPORT_MISSING_FIELDS);
            }
        }
        return newPersonList;
    }

    /**
     * Creates a Leave object
     * @param input PersonInput object created by the bean.
     * @return An Leave object for the Person constructor
     * @throws ParseException If an error occurs while parsing the String input.
     */
    private Leave buildLeave(PersonInput input) throws ParseException {
        return input.getLeaves() == null
                ? new Leave("0")
                : ParserUtil.parseLeaves(input.getLeaves());
    }

    /**
     * Creates a HourlySalary object
     * @param input PersonInput object created by the bean.
     * @return An HourlySalary object for the Person constructor
     * @throws ParseException If an error occurs while parsing the String input.
     */
    private HourlySalary buildSalary(PersonInput input) throws ParseException {
        return input.getSalary() == null
                ? new HourlySalary("0")
                : ParserUtil.parseSalary(input.getSalary());
    }

    /**
     * Creates a HoursWorked object
     * @param input PersonInput object created by the bean.
     * @return An HoursWorked object for the Person constructor
     * @throws ParseException If an error occurs while parsing the String input.
     */
    private HoursWorked buildHoursWorked(PersonInput input) throws ParseException {
        return input.getHoursWorked() == null
                ? new HoursWorked("0")
                : ParserUtil.parseHoursWorked(input.getHoursWorked());

    }

    /**
     * Creates an Overtime object
     * @param input PersonInput object created by the bean.
     * @return An Overtime object for the Person constructor
     * @throws ParseException If an error occurs while parsing the String input.
     */
    private Overtime buildOvertime(PersonInput input) throws ParseException {
        return input.getOvertime() == null
                ? new Overtime("0")
                : ParserUtil.parseOvertime(input.getOvertime());
    }

    /**
     * Creates a Set object containing all the corresponding tags for the entry
     * @param input PersonInput object created by the bean.
     * @return An Set object for the Person constructor
     * @throws ParseException If an error occurs while parsing the String input.
     */
    private Set<Tag> buildTags(PersonInput input) throws ParseException {
        return (input.getTags() == null || input.getTags().equals(""))
                ? new HashSet<>()
                : ParserUtil.parseTags(Arrays.asList(input.getTags().split("/")));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ImportCommand // instanceof handles nulls
                && filepathString.equals(((ImportCommand) other).filepathString));
    }

    public String getFilepathString() {
        return this.filepathString;
    }

}

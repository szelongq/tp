package seedu.address.logic.commands;

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
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonInput;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.tag.Tag;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Objects.requireNonNull;

public class ImportCommand extends Command {
    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": imports a current existing CSV file into HeRon."
            + "Parameters: Path leading to desired CSV file."
            +  "Example: " + COMMAND_WORD + " ~/Desktop/toBeImported.csv";

    public static final String MESSAGE_IMPORT_SUCCESS = "File was successfully imported";
    public static final String MESSAGE_IMPORT_FAILURE = "Error occurred while importing the file. ";
    public static final String MESSAGE_IMPORT_MISSING_FILE = MESSAGE_IMPORT_FAILURE +
            "Please check the filepath and try again."; // Should be an error for finding the file.
    public static final String MESSAGE_IMPORT_FILE_FORMAT = MESSAGE_IMPORT_FAILURE +
            "Please check the data formatting in the file and try again.";

    private final String filepath;

    public ImportCommand(String filepath) {
        requireNonNull(filepath);

        this.filepath = filepath;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            List<Person> newPersonList = processCSV(this.filepath);
            AddressBook newAddressBook = new AddressBook();
            newAddressBook.setPersons(newPersonList);
            model.setAddressBook(newAddressBook);
        } catch (CommandException e) {
            throw new CommandException(e.getMessage());
        }
        return new CommandResult(MESSAGE_IMPORT_SUCCESS);
    }

    public List<Person> processCSV(String filepath) throws CommandException {
        FileReader fileReader;
        List<String[]> allData;
        List<Person> newPersonList = new ArrayList<>();
        try {
            fileReader = new FileReader(filepath);
        } catch (FileNotFoundException e) {
            throw new CommandException(MESSAGE_IMPORT_MISSING_FILE);
        }

        List<PersonInput> newPersonInputList = new CsvToBeanBuilder(fileReader).withType(PersonInput.class).build().parse();
        for (PersonInput input : newPersonInputList) {
            try {
                Name name = ParserUtil.parseName(input.getName());
                Phone phone = ParserUtil.parsePhone(input.getPhone());
                Email email = ParserUtil.parseEmail(input.getEmail());
                Address address = ParserUtil.parseAddress(input.getAddress());
                Role role = ParserUtil.parseRole(input.getRole());
                Leave leaves = ParserUtil.parseLeaves(input.getLeaves());
                HourlySalary hourlySalary = ParserUtil.parseSalary(input.getSalary());
                HoursWorked hoursWorked = ParserUtil.parseHoursWorked(input.getHoursWorked());
                Set<Tag> tagList = input.getTags().equals("")
                        ? new HashSet<>()
                        : ParserUtil.parseTags(Arrays.asList(input.getTags().split("/")));
                newPersonList.add(new Person(name, phone, email, address, role, leaves, hourlySalary, hoursWorked,
                        new CalculatedPay("0"), tagList));
            } catch (ParseException e) {
                throw new CommandException("Error while parsing file.");
            }
        }
        return newPersonList;
    }
}

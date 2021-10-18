package seedu.address.logic.commands;

import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;

import com.opencsv.CSVReader;
import seedu.address.model.person.*;
import seedu.address.model.tag.Tag;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static java.util.Objects.requireNonNull;

public class ImportCommand extends Command {
    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": imports a current existing CSV file into HeRon."
            + "Parameters: Path leading to desired CSV file."
            +  "Example: " + COMMAND_WORD + " ~/Desktop/toBeImported.csv";

    public static final String MESSAGE_IMPORT_SUCCESS = "File was successfully imported";
    public static final String MESSAGE_IMPORT_FAILURE = "Error occurred while importing the file.";
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
            processCSV(this.filepath, model);
        } catch (CommandException e) {
            throw new CommandException(MESSAGE_IMPORT_FAILURE);
        }
        return new CommandResult(MESSAGE_IMPORT_SUCCESS);

        // Clear current existing entries to reset to default.
        //model.setAddressBook(new AddressBook());

        /*File file = getFile(this.filepath);
        if (file == null) {
            return new CommandResult(MESSAGE_IMPORT_MISSING_FILE);
        }*/
    }

    public File getFile(String filepath) {
        File file = new File(filepath);
        if (!file.exists()) {
            return null;
        }
        return file;
    }

    public void processCSV(String filepath, Model model) throws CommandException {
        FileReader fileReader;
        List<String[]> allData;
        try {
            fileReader = new FileReader(filepath);
        } catch (FileNotFoundException e) {
            throw new CommandException("");
        }

        try {
            CSVReader csvReader = new CSVReaderBuilder(fileReader).withSkipLines(1).build();
            allData = csvReader.readAll();
        } catch (IOException | CsvException e) {
            throw new CommandException("");
        }

        // Assumes all required data is present, and in correct order.
        for (String[] row : allData) {
            try {
                Name name = ParserUtil.parseName(row[0]);
                Phone phone = ParserUtil.parsePhone(row[1]);
                Email email = ParserUtil.parseEmail(row[2]);
                Address address = ParserUtil.parseAddress(row[3]);
                Role role = ParserUtil.parseRole(row[4]);
                Leave leaves = ParserUtil.parseLeaves(row[5]);
                HourlySalary hourlySalary = ParserUtil.parseSalary(row[6]);
                HoursWorked hoursWorked = ParserUtil.parseHoursWorked(row[7]);
                Set<Tag> tagList = ParserUtil.parseTags(Arrays.asList((row[9].split(","))));//Need to think about how to parse the tags.
                Person newPerson = new Person(name, phone, email, address, role, leaves, hourlySalary, hoursWorked,
                        new CalculatedPay("0"), tagList);
                CommandResult addResult = new AddCommand(newPerson).execute(model);
            } catch (ParseException | CommandException e) {
                throw new CommandException("Error occurred while parsing.");
            }
        }
    }
}

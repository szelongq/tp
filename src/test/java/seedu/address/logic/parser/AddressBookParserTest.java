package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddLeaveBalanceCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeductLeaveBalanceCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.PayCommand;
import seedu.address.logic.commands.SetOvertimePayRateCommand;
import seedu.address.logic.commands.StartPayrollCommand;
import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.commands.ViewOvertimePayRateCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.OvertimePayRate;
import seedu.address.model.person.LeaveBalance;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);

    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    /*@Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }*/

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_addLeaves() throws Exception {
        final LeaveBalance leaveBalance = new LeaveBalance("3");
        AddLeaveBalanceCommand command =
                (AddLeaveBalanceCommand) parser.parseCommand(AddLeaveBalanceCommand.COMMAND_WORD + " "
                        + INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_LEAVE + leaveBalance);
        assertEquals(new AddLeaveBalanceCommand(INDEX_FIRST_PERSON, leaveBalance), command);
    }

    @Test
    public void parseCommand_removeLeaves() throws Exception {
        final LeaveBalance numberOfLeaves = new LeaveBalance("3");
        DeductLeaveBalanceCommand command =
                (DeductLeaveBalanceCommand) parser.parseCommand(DeductLeaveBalanceCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_LEAVE + numberOfLeaves);
        assertEquals(new DeductLeaveBalanceCommand(INDEX_FIRST_PERSON, numberOfLeaves), command);
    }

    @Test
    public void parseCommand_startPayroll() throws Exception {
        assertTrue(parser.parseCommand(StartPayrollCommand.COMMAND_WORD) instanceof StartPayrollCommand);
        assertTrue(parser.parseCommand(StartPayrollCommand.COMMAND_WORD + " 3") instanceof StartPayrollCommand);
    }

    @Test
    public void parseCommand_pay() throws Exception {
        PayCommand paySingleCommand = (PayCommand) parser.parseCommand(
                PayCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new PayCommand(INDEX_FIRST_PERSON), paySingleCommand);

        PayCommand payAllCommand = (PayCommand) parser.parseCommand(
                PayCommand.COMMAND_WORD + " " + PayCommand.PAY_ALL_COMMAND_PHRASE);
        assertEquals(new PayCommand(), payAllCommand);
    }

    @Test
    public void parseCommand_viewCommand() throws Exception {
        ViewCommand command = (ViewCommand) parser.parseCommand(
                ViewCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new ViewCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_viewOvertimePayRate() throws Exception {
        assertTrue(parser.parseCommand(ViewOvertimePayRateCommand.COMMAND_WORD) instanceof ViewOvertimePayRateCommand);
        assertTrue(parser.parseCommand(ViewOvertimePayRateCommand.COMMAND_WORD + " 3")
                instanceof ViewOvertimePayRateCommand);
    }

    @Test
    public void parseCommand_setOvertimePayRate() throws Exception {
        SetOvertimePayRateCommand command = (SetOvertimePayRateCommand) parser.parseCommand(
                SetOvertimePayRateCommand.COMMAND_WORD + " 1.5");
        OvertimePayRate newOvertimePayRate = new OvertimePayRate("1.5");
        assertEquals(new SetOvertimePayRateCommand(newOvertimePayRate), command);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}

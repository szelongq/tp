package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.OvertimePayRate;
import seedu.address.model.UserPrefs;


public class SetOvertimePayRateCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validOvertimePayRate_success() {
        OvertimePayRate newOvertimePayRate = new OvertimePayRate("2.0");
        SetOvertimePayRateCommand setOvertimePayRateCommand =
                new SetOvertimePayRateCommand(newOvertimePayRate);

        String expectedMessage = String.format(SetOvertimePayRateCommand.MESSAGE_SET_RATE_SUCCESS,
                newOvertimePayRate);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setOvertimePayRate(newOvertimePayRate);

        assertCommandSuccess(setOvertimePayRateCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        // Shorten OvertimePayRate to Opr for better readability

        OvertimePayRate oprFirst = new OvertimePayRate("2.0");
        OvertimePayRate oprSecond = new OvertimePayRate("2.5");

        SetOvertimePayRateCommand setOprFirstCommand = new SetOvertimePayRateCommand(oprFirst);
        SetOvertimePayRateCommand setOprSecondCommand = new SetOvertimePayRateCommand(oprSecond);

        // same object -> returns true
        assertTrue(setOprFirstCommand.equals(setOprFirstCommand));

        // same values -> returns true
        SetOvertimePayRateCommand setOprFirstCommandCopy = new SetOvertimePayRateCommand(oprFirst);
        assertTrue(setOprFirstCommand.equals(setOprFirstCommandCopy));

        // different types -> returns false
        assertFalse(setOprFirstCommand.equals(1));

        // null -> returns false
        assertFalse(setOprFirstCommand.equals(null));

        // different overtime pay rate -> returns false
        assertFalse(setOprFirstCommand.equals(setOprSecondCommand));
    }

}

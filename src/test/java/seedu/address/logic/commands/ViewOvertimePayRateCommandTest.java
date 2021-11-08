package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.OvertimePayRate;

public class ViewOvertimePayRateCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_viewOvertimePayRate_success() {
        OvertimePayRate overtimePayRate = model.getOvertimePayRate();
        String expectedMessage = String.format(ViewOvertimePayRateCommand.SHOWING_RATE_MESSAGE, overtimePayRate);

        assertCommandSuccess(new ViewOvertimePayRateCommand(), model, expectedMessage, expectedModel);
    }
}

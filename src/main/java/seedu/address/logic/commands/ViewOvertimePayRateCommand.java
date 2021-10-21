package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Displays the currently set pay rate for overtime.
 */
public class ViewOvertimePayRateCommand extends Command {

    public static final String COMMAND_WORD = "viewOvertimePayRate";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows the currently set pay rate for overtime.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_RATE_MESSAGE = "Current overtime pay rate: %1$f";

    @Override
    public CommandResult execute(Model model) {
        double overtimePayRate = model.getOvertimePayRate();
        return new CommandResult(String.format(SHOWING_RATE_MESSAGE, overtimePayRate));
    }
}

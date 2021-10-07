package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Adds some number of leaves to an employee in HeRon.
 */
public class AddLeavesCommand extends Command {

    public static final String COMMAND_WORD = "addLeaves";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult("Leaves added");
    }
}

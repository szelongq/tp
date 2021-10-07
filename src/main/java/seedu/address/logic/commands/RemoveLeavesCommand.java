package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Removes some number of leaves from an employee in HeRon.
 */
public class RemoveLeavesCommand extends Command {

    public static final String COMMAND_WORD = "removeLeaves";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult("Leaves removed");
    }
}

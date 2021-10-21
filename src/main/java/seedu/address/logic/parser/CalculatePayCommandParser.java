package seedu.address.logic.parser;

import seedu.address.logic.commands.StartPayrollCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new StartPayrollCommand object
 */
public class CalculatePayCommandParser implements Parser<StartPayrollCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the StartPayrollCommand
     * and returns a StartPayrollCommand object for execution.
     */
    public StartPayrollCommand parse(String args) throws ParseException {
        return new StartPayrollCommand();
    }

}

package seedu.address.logic.commands;

import org.junit.jupiter.api.Test;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Leaves;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.logic.commands.AddLeavesCommand.MESSAGE_ARGUMENTS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEAVES_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEAVES_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

public class AddLeavesCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute() {
        Leaves leaves = new Leaves("3");

        assertCommandFailure(new AddLeavesCommand(INDEX_FIRST_PERSON, leaves), model,
                String.format(MESSAGE_ARGUMENTS, INDEX_FIRST_PERSON.getOneBased(), leaves));
    }

    @Test
    public void equals() {
        final AddLeavesCommand standardCommand = new AddLeavesCommand(INDEX_FIRST_PERSON, new Leaves(VALID_LEAVES_AMY));

        // Same values -> returns true
        AddLeavesCommand commandWithSameValues = new AddLeavesCommand(INDEX_FIRST_PERSON, new Leaves(VALID_LEAVES_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // Same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // Different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // Different index -> returns false
        assertFalse(standardCommand.equals(new AddLeavesCommand(INDEX_SECOND_PERSON, new Leaves(VALID_LEAVES_AMY))));

        // Different number of leaves -> returns false
        assertFalse(standardCommand.equals(new AddLeavesCommand(INDEX_FIRST_PERSON, new Leaves(VALID_LEAVES_BOB))));
    }
}

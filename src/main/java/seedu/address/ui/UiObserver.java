package seedu.address.ui;

import seedu.address.model.person.Person;

/**
 * Dedicated observer class for Ui components
 */
public interface UiObserver {

    /** Updates the observer with {@code person} to view */
    void update(Person person);

}

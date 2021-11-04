package seedu.address.model.person;

import java.util.ArrayList;

import seedu.address.ui.UiObserver;


/**
 * Observable class for Person to use Observer pattern to update InfoPanel.
 */
public class ObservablePerson {
    private Person personToView;
    private ArrayList<UiObserver> uiObserverList = new ArrayList<>();

    /**
     * Initialises the ObservablePerson object with the person to be viewed.
     */
    public ObservablePerson(Person person) {
        personToView = person;
    }

    /**
     * Sets the new person to be viewed in the ObservablePerson object,
     * and updates the respective Ui components that are observing it.
     */
    public void setPerson(Person person) {
        personToView = person;
        updateUi();
    }

    public Person get() {
        return personToView;
    }

    /**
     * Adds any {@code UiObserver} to the list of observers to be updated when
     * {@code personToView} is updated through {@code setPerson}.
     */
    public void addUiObserver(UiObserver observer) {
        uiObserverList.add(observer);
    }

    /**
     * Updates the {@code UiObservers} in the observer list with the current person to view.
     */
    private void updateUi() {
        for (UiObserver obs : uiObserverList) {
            obs.update(personToView);
        }
    }
}

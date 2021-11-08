package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import seedu.address.ui.UiObserver;


/**
 * Observable class for Person to use Observer pattern to update InfoPanel.
 */
public class ObservablePerson {

    private Person personToView;
    private final ArrayList<UiObserver> uiObserverList = new ArrayList<>();

    /**
     * Initialises the ObservablePerson object with the person to be viewed.
     * @param person Person to be viewed
     */
    public ObservablePerson(Person person) {
        requireNonNull(person);
        personToView = person;
    }

    /**
     * Initialises the ObservablePerson object with null as person to view,
     * representing that no person is to be viewed.
     */
    public ObservablePerson() {
        personToView = null;
    }

    /**
     * Sets the new person to be viewed in the ObservablePerson object,
     * and updates the respective Ui components that are observing it.
     * @param person New person to be viewed
     */
    public void setPerson(Person person) {
        requireNonNull(person);

        personToView = person;
        updateUi();
    }

    /**
     * Sets person to be viewed as null, representing that no person is to be viewed,
     * and updates the respective Ui components that are observing it.
     */
    public void setEmptyPerson() {
        personToView = null;
        updateUi();
    }

    public Person getPerson() {
        return personToView;
    }

    /**
     * Adds any {@code UiObserver} to the list of observers to be updated when
     * {@code personToView} is updated through {@code setPerson}.
     * @param observer Observer to be updated upon change of viewing person
     */
    public void addUiObserver(UiObserver observer) {
        uiObserverList.add(observer);
    }

    /**
     * Updates all the {@code UiObserver} in the observer list with the current person to view.
     */
    private void updateUi() {
        for (UiObserver obs : uiObserverList) {
            obs.update(personToView);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ObservablePerson // instanceof handles nulls
                && personToView.equals(((ObservablePerson) other).getPerson()));
    }
}

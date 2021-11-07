package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TypicalPersons;
import seedu.address.ui.UiObserver;

public class ObservablePersonTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ObservablePerson(null));
    }

    @Test
    public void constructor_noArgs_returnNullAsViewingPerson() {
        ObservablePerson emptyPerson = new ObservablePerson();
        assertNull(emptyPerson.getPerson());
    }

    @Test
    public void setEmptyPerson_success() {
        Person currPerson = new PersonBuilder().build();
        UiObserverStub observer = new UiObserverStub(currPerson);
        ObservablePerson obsPerson = new ObservablePerson(currPerson);

        obsPerson.addUiObserver(observer);
        obsPerson.setEmptyPerson();

        // Observable person should be set to null
        assertNull(obsPerson.getPerson());
        // Observer should be updated to same value as Observable on change
        assertEquals(obsPerson.getPerson(), observer.getViewPerson());
    }

    @Test
    public void setViewPerson_success() {
        Person currPerson = TypicalPersons.ALICE;
        Person nextPersonToView = TypicalPersons.CARL;
        UiObserverStub observer = new UiObserverStub(currPerson);
        ObservablePerson obsPerson = new ObservablePerson(currPerson);

        obsPerson.addUiObserver(observer);
        obsPerson.setPerson(nextPersonToView);

        // Observable person should be set to null
        assertEquals(obsPerson.getPerson(), nextPersonToView);
        // Observer should be updated to same value as Observable on change
        assertEquals(obsPerson.getPerson(), observer.getViewPerson());
    }

    @Test
    public void equals() {
        ObservablePerson emptyObsPerson = new ObservablePerson();
        ObservablePerson aliceObsPerson = new ObservablePerson(new PersonBuilder().withName("Alice").build());
        ObservablePerson bobObsPerson = new ObservablePerson(new PersonBuilder().withName("Bob").build());

        // same object
        assertTrue(aliceObsPerson.equals(aliceObsPerson));
        assertTrue(emptyObsPerson.equals(emptyObsPerson));

        // same value
        ObservablePerson anotherAliceObsPerson = new ObservablePerson(new PersonBuilder().withName("Alice").build());
        assertTrue(aliceObsPerson.equals(anotherAliceObsPerson));

        // different types
        assertFalse(aliceObsPerson.equals(3));

        // null case
        assertNotNull(aliceObsPerson);

        // different viewing person
        assertFalse(aliceObsPerson.equals(bobObsPerson));

    }

    private class UiObserverStub implements UiObserver {
        private Person personToView;

        public UiObserverStub(Person p) {
            requireNonNull(p);
            this.personToView = p;
        }

        @Override
        public void update(Person person) {
            this.personToView = person;
        }

        public Person getViewPerson() {
            return personToView;
        }
    }
}

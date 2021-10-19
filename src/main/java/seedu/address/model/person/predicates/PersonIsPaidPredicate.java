package seedu.address.model.person.predicates;

import java.util.function.Predicate;

import seedu.address.model.person.Person;

public class PersonIsPaidPredicate implements Predicate<Person> {

    @Override
    public boolean test(Person person) {
        return !person.isPaid();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonIsPaidPredicate);
    }
}

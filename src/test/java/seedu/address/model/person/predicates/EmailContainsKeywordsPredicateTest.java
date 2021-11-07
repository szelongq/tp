package seedu.address.model.person.predicates;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

class EmailContainsKeywordsPredicateTest {

    @Test
    void equals() {
        List<String> firstPredicateKeywordList = Arrays.asList("gmail", "yahoo");
        List<String> secondPredicateKeywordList = Arrays.asList("gmail");

        EmailContainsKeywordsPredicate firstPredicate =
                new EmailContainsKeywordsPredicate(firstPredicateKeywordList);

        EmailContainsKeywordsPredicate firstPredicateCopy =
                new EmailContainsKeywordsPredicate(firstPredicateKeywordList);

        EmailContainsKeywordsPredicate secondPredicate =
                new EmailContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different type -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different keyword list -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    void test_emailContainsKeywords_returnsTrue() {
        // One keyword
        EmailContainsKeywordsPredicate predicate =
                new EmailContainsKeywordsPredicate(Collections.singletonList("gmail"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice@gmail.com").build()));

        // Multiple keywords
        predicate = new EmailContainsKeywordsPredicate(Arrays.asList("bob", "example"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("bob@example.com").build()));

        // One matching keyword
        assertTrue(predicate.test(new PersonBuilder().withEmail("charles@bob.com").build()));
        assertTrue(predicate.test(new PersonBuilder().withEmail("charles@example.com").build()));

        // Mixed-case keywords
        predicate = new EmailContainsKeywordsPredicate(Arrays.asList("bOb", "gMail"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("davie123@gmail.com").build()));
        assertTrue(predicate.test(new PersonBuilder().withEmail("bob321@example.com").build()));
    }

    @Test
    public void test_addressDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        EmailContainsKeywordsPredicate predicate = new EmailContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withEmail("alice@example.com").build()));

        // Non-matching keyword
        predicate = new EmailContainsKeywordsPredicate(Arrays.asList("gmail"));
        assertFalse(predicate.test(new PersonBuilder().withEmail("alex@example.com").build()));

    }
}

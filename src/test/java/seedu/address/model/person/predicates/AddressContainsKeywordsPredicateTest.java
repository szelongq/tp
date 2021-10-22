package seedu.address.model.person.predicates;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

class AddressContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Arrays.asList("Tampines", "Clementi");
        List<String> secondPredicateKeywordList = Arrays.asList("Tampines", "Boon Lay");

        AddressContainsKeywordsPredicate firstPredicate =
                new AddressContainsKeywordsPredicate(firstPredicateKeywordList);

        AddressContainsKeywordsPredicate firstPredicateCopy =
                new AddressContainsKeywordsPredicate(firstPredicateKeywordList);

        AddressContainsKeywordsPredicate secondPredicate =
                new AddressContainsKeywordsPredicate(secondPredicateKeywordList);

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
    public void test_addressContainsKeywords_returnsTrue() {
        // One keyword
        AddressContainsKeywordsPredicate predicate =
                new AddressContainsKeywordsPredicate(Collections.singletonList("Tampines"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("Blk 93, Tampines Street 82, #02-19").build()));

        // Multiple keywords
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("Tampines", "Street"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("Blk 93, Tampines Street 82, #02-19").build()));

        // One matching keyword
        assertTrue(predicate.test(new PersonBuilder().withAddress("Blk 12, Bedok Street 23, #09-30").build()));

        // Mixed-case keywords
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("beDOk", "taMPineS"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("Blk 93, Tampines Street 82, #02-19").build()));
        assertTrue(predicate.test(new PersonBuilder().withAddress("Blk 12, Bedok Street 23, #09-30").build()));
    }

    @Test
    public void test_addressDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        AddressContainsKeywordsPredicate predicate = new AddressContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withAddress("Main Street").build()));

        // Non-matching keyword
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("Avenue"));
        assertFalse(predicate.test(new PersonBuilder().withAddress("Blk 157, Lorong 15").build()));

    }
}

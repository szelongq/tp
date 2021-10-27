package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.CalculatedPay;
import seedu.address.model.person.Email;
import seedu.address.model.person.HourlySalary;
import seedu.address.model.person.HoursWorked;
import seedu.address.model.person.LeaveBalance;
import seedu.address.model.person.Name;
import seedu.address.model.person.Overtime;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;

public class JsonAdaptedPersonTest {
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_CALCULATEDPAY_CHAR = "a";
    private static final String INVALID_CALCULATEDPAY_NEGATIVE = "-1";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_HOURLYSALARY_CHAR = "yolo";
    private static final String INVALID_HOURLYSALARY_NEGATIVE = "-3000";
    private static final String INVALID_HOURSWORKED_CHAR = "A day";
    private static final String INVALID_HOURSWORKED_NEGATIVE = "-27";
    private static final String INVALID_LEAVES_CHAR = "a";
    private static final String INVALID_LEAVES_NEGATIVE = "-14";
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_OVERTIME_CHAR = "one";
    private static final String INVALID_OVERTIME_NEGATIVE = "-2";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ROLE = "Pro <Junior> Java Expert";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_CALCULATEDPAY = BENSON.getCalculatedPay().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_HOURLYSALARY = BENSON.getSalary().toString();
    private static final String VALID_HOURSWORKED = BENSON.getHoursWorked().toString();
    private static final String VALID_LEAVES = BENSON.getLeaveBalance().toString();
    private static final List<LocalDate> VALID_LEAVES_TAKEN = BENSON.getLeavesTaken().toList();
    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_OVERTIME = BENSON.getOvertime().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_ROLE = BENSON.getRole().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_ROLE,
                VALID_LEAVES, VALID_LEAVES_TAKEN, VALID_HOURLYSALARY, VALID_HOURSWORKED, VALID_OVERTIME,
                        VALID_CALCULATEDPAY, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_ROLE, VALID_LEAVES, VALID_LEAVES_TAKEN, VALID_HOURLYSALARY, VALID_HOURSWORKED,
                VALID_OVERTIME, VALID_CALCULATEDPAY, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_ROLE, VALID_LEAVES, VALID_LEAVES_TAKEN, VALID_HOURLYSALARY, VALID_HOURSWORKED,
                        VALID_OVERTIME, VALID_CALCULATEDPAY, VALID_TAGS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS, VALID_ROLE,
                VALID_LEAVES, VALID_LEAVES_TAKEN, VALID_HOURLYSALARY, VALID_HOURSWORKED, VALID_OVERTIME,
                VALID_CALCULATEDPAY, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS, VALID_ROLE,
                        VALID_LEAVES, VALID_LEAVES_TAKEN, VALID_HOURLYSALARY, VALID_HOURSWORKED, VALID_OVERTIME,
                        VALID_CALCULATEDPAY, VALID_TAGS);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, null, VALID_ADDRESS, VALID_ROLE,
                VALID_LEAVES, VALID_LEAVES_TAKEN, VALID_HOURLYSALARY, VALID_HOURSWORKED, VALID_OVERTIME,
                VALID_CALCULATEDPAY, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS, VALID_ROLE,
                        VALID_LEAVES, VALID_LEAVES_TAKEN, VALID_HOURLYSALARY, VALID_HOURSWORKED, VALID_OVERTIME,
                        VALID_CALCULATEDPAY, VALID_TAGS);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, null, VALID_ROLE,
                VALID_LEAVES, VALID_LEAVES_TAKEN, VALID_HOURLYSALARY, VALID_HOURSWORKED, VALID_OVERTIME,
                VALID_CALCULATEDPAY, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullRole_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, null,
                VALID_LEAVES, VALID_LEAVES_TAKEN, VALID_HOURLYSALARY, VALID_HOURSWORKED, VALID_OVERTIME,
                VALID_CALCULATEDPAY, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Role.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidRole_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                INVALID_ROLE, VALID_LEAVES, VALID_LEAVES_TAKEN, VALID_HOURLYSALARY, VALID_HOURSWORKED, VALID_OVERTIME,
                VALID_CALCULATEDPAY, VALID_TAGS);
        String expectedMessage = Role.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullLeaves_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_ROLE, null, VALID_LEAVES_TAKEN, VALID_HOURLYSALARY, VALID_HOURSWORKED, VALID_OVERTIME,
                VALID_CALCULATEDPAY, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, LeaveBalance.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_negativeLeaves_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_ROLE, INVALID_LEAVES_NEGATIVE, VALID_LEAVES_TAKEN, VALID_HOURLYSALARY, VALID_HOURSWORKED,
                VALID_OVERTIME, VALID_CALCULATEDPAY, VALID_TAGS);
        String expectedMessage = LeaveBalance.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_characterInLeaves_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_ROLE, INVALID_LEAVES_CHAR, VALID_LEAVES_TAKEN, VALID_HOURLYSALARY, VALID_HOURSWORKED,
                VALID_OVERTIME, VALID_CALCULATEDPAY, VALID_TAGS);
        String expectedMessage = LeaveBalance.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullSalary_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_ROLE, VALID_LEAVES, VALID_LEAVES_TAKEN, null, VALID_HOURSWORKED, VALID_OVERTIME,
                VALID_CALCULATEDPAY, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, HourlySalary.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_negativeSalary_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_ROLE, VALID_LEAVES, VALID_LEAVES_TAKEN, INVALID_HOURLYSALARY_NEGATIVE, VALID_HOURSWORKED,
                VALID_OVERTIME, VALID_CALCULATEDPAY, VALID_TAGS);
        String expectedMessage = HourlySalary.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_characterInSalary_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_ROLE, VALID_LEAVES, VALID_LEAVES_TAKEN, INVALID_HOURLYSALARY_CHAR, VALID_HOURSWORKED,
                VALID_OVERTIME, VALID_CALCULATEDPAY, VALID_TAGS);
        String expectedMessage = HourlySalary.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullHoursWorked_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_ROLE, VALID_LEAVES, VALID_LEAVES_TAKEN, VALID_HOURLYSALARY, null,
                VALID_OVERTIME, VALID_CALCULATEDPAY, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, HoursWorked.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_negativeHoursWorked_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_ROLE, VALID_LEAVES, VALID_LEAVES_TAKEN, VALID_HOURLYSALARY, INVALID_HOURSWORKED_NEGATIVE,
                VALID_OVERTIME, VALID_CALCULATEDPAY, VALID_TAGS);
        String expectedMessage = HoursWorked.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_characterInHoursWorked_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_ROLE, VALID_LEAVES, VALID_LEAVES_TAKEN, VALID_HOURLYSALARY, INVALID_HOURSWORKED_CHAR,
                VALID_OVERTIME, VALID_CALCULATEDPAY, VALID_TAGS);
        String expectedMessage = HoursWorked.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_negativeOvertime_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_ROLE, VALID_LEAVES, VALID_LEAVES_TAKEN, VALID_HOURLYSALARY, VALID_HOURSWORKED,
                INVALID_OVERTIME_NEGATIVE, VALID_CALCULATEDPAY, VALID_TAGS);
        String expectedMessage = Overtime.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_characterInOvertime_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_ROLE, VALID_LEAVES, VALID_LEAVES_TAKEN, VALID_HOURLYSALARY, VALID_HOURSWORKED,
                INVALID_OVERTIME_CHAR, VALID_CALCULATEDPAY, VALID_TAGS);
        String expectedMessage = Overtime.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_negativeCalculatedPay_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_ROLE, VALID_LEAVES, VALID_LEAVES_TAKEN, VALID_HOURLYSALARY, VALID_HOURSWORKED, VALID_OVERTIME,
                INVALID_CALCULATEDPAY_NEGATIVE, VALID_TAGS);
        String expectedMessage = CalculatedPay.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_characterInCalculatedPay_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_ROLE, VALID_LEAVES, VALID_LEAVES_TAKEN, VALID_HOURLYSALARY, VALID_HOURSWORKED, VALID_OVERTIME,
                INVALID_CALCULATEDPAY_CHAR, VALID_TAGS);
        String expectedMessage = CalculatedPay.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_ROLE,
                        VALID_LEAVES, VALID_LEAVES_TAKEN, VALID_HOURLYSALARY, VALID_HOURSWORKED, VALID_OVERTIME,
                        VALID_CALCULATEDPAY, invalidTags);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

}

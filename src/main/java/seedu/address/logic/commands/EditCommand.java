package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOURLYSALARY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOURSWORKED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OVERTIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.CalculatedPay;
import seedu.address.model.person.Email;
import seedu.address.model.person.HourlySalary;
import seedu.address.model.person.HoursWorked;
import seedu.address.model.person.LeaveBalance;
import seedu.address.model.person.LeavesTaken;
import seedu.address.model.person.Name;
import seedu.address.model.person.Overtime;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the employee identified "
            + "by the index number used in the displayed employee list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_ROLE + "ROLE] "
            + "[" + PREFIX_LEAVE + "LEAVES] "
            + "[" + PREFIX_HOURLYSALARY + "SALARY] "
            + "[" + PREFIX_HOURSWORKED + "HOURS_WORKED] "
            + "[" + PREFIX_OVERTIME + "OVERTIME] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Employee: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    public static final String MESSAGE_DUPLICATE_PERSON = "This employee already exists in HeRon.";
    public static final String MESSAGE_DUPLICATE_PHONE = "The given phone number is already used by "
            + "another employee in HeRon";
    public static final String MESSAGE_DUPLICATE_EMAIL = "The given email is already used by another employee in HeRon";

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

        if (!personToEdit.isSamePerson(editedPerson)) {
            if (model.hasPerson(editedPerson)) {
                throw new CommandException(MESSAGE_DUPLICATE_PERSON);
            }

            if (model.hasDuplicatePhone(editedPerson) && !editedPerson.getPhone().equals(personToEdit.getPhone())) {
                throw new CommandException(MESSAGE_DUPLICATE_PHONE);
            }

            if (model.hasDuplicateEmail(editedPerson) && !editedPerson.getEmail().equals(personToEdit.getEmail())) {
                throw new CommandException(MESSAGE_DUPLICATE_EMAIL);
            }
        }

        if (personToEdit.isSamePerson(editedPerson)) {
            if (model.hasDuplicatePhone(editedPerson) && !editedPerson.getPhone().equals(personToEdit.getPhone())) {
                throw new CommandException(MESSAGE_DUPLICATE_PHONE);
            }

            if (model.hasDuplicateEmail(editedPerson) && !editedPerson.getEmail().equals(personToEdit.getEmail())) {
                throw new CommandException(MESSAGE_DUPLICATE_EMAIL);
            }
        }

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.setViewingPerson(editedPerson);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, editedPerson));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        Role updatedRole = editPersonDescriptor.getRole().orElse(personToEdit.getRole());
        LeaveBalance updatedLeaveBalance = editPersonDescriptor.getLeaves().orElse(personToEdit.getLeaveBalance());
        // Edit command does not allow editing dates in leaves taken
        LeavesTaken updatedLeavesTaken = personToEdit.getLeavesTaken();
        HourlySalary updatedHourlySalary = editPersonDescriptor.getSalary().orElse(personToEdit.getSalary());
        HoursWorked updatedHours = editPersonDescriptor.getHoursWorked().orElse(personToEdit.getHoursWorked());
        Overtime updatedOvertime = editPersonDescriptor.getOvertime().orElse(personToEdit.getOvertime());
        CalculatedPay updatedCalculatedPay =
                editPersonDescriptor.getCalculatedPay().orElse(personToEdit.getCalculatedPay());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedRole,
                updatedLeaveBalance, updatedLeavesTaken, updatedHourlySalary, updatedHours, updatedOvertime,
                updatedCalculatedPay, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editPersonDescriptor.equals(e.editPersonDescriptor);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Role role;
        private LeaveBalance leaveBalance;
        private HourlySalary hourlySalary;
        private HoursWorked hoursWorked;
        private Overtime overtime;
        private CalculatedPay calculatedPay;

        private Set<Tag> tags;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setRole(toCopy.role);
            setLeaves(toCopy.leaveBalance);
            setSalary(toCopy.hourlySalary);
            setHoursWorked(toCopy.hoursWorked);
            setOvertime(toCopy.overtime);
            setCalculatedPay(toCopy.calculatedPay);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address, role, leaveBalance, hourlySalary,
                    hoursWorked, overtime, calculatedPay, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setRole(Role role) {
            this.role = role;
        }

        public Optional<Role> getRole() {
            return Optional.ofNullable(role);
        }

        public void setLeaves(LeaveBalance leaveBalance) {
            this.leaveBalance = leaveBalance;
        }

        public Optional<LeaveBalance> getLeaves() {
            return Optional.ofNullable(leaveBalance);
        }

        public void setSalary(HourlySalary hourlySalary) {
            this.hourlySalary = hourlySalary;
        }

        public Optional<HourlySalary> getSalary() {
            return Optional.ofNullable(hourlySalary);
        }

        public void setHoursWorked(HoursWorked hours) {
            this.hoursWorked = hours;
        }

        public Optional<HoursWorked> getHoursWorked() {
            return Optional.ofNullable(hoursWorked);
        }

        public void setOvertime (Overtime overtime) {
            this.overtime = overtime;
        }

        public Optional<Overtime> getOvertime() {
            return Optional.ofNullable(overtime);
        }

        public void setCalculatedPay(CalculatedPay calculatedPay) {
            this.calculatedPay = calculatedPay;
        }

        public Optional<CalculatedPay> getCalculatedPay() {
            return Optional.ofNullable(calculatedPay);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            // state check
            EditPersonDescriptor e = (EditPersonDescriptor) other;

            return getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getEmail().equals(e.getEmail())
                    && getAddress().equals(e.getAddress())
                    && getRole().equals(e.getRole())
                    && getLeaves().equals(e.getLeaves())
                    && getSalary().equals(e.getSalary())
                    && getHoursWorked().equals(e.getHoursWorked())
                    && getOvertime().equals(e.getOvertime())
                    && getCalculatedPay().equals(e.getCalculatedPay())
                    && getTags().equals(e.getTags());
        }
    }
}

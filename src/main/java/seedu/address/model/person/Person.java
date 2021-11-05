package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();

    // Employee fields
    private final Role role;
    private final LeaveBalance leaveBalance;
    private final LeavesTaken leavesTaken;
    private final HourlySalary hourlySalary;
    private final HoursWorked hoursWorked;
    private final Overtime overtime;
    private final CalculatedPay calculatedPay;

    /**
     * Constructs a {@code Person} object.
     * All fields except for overtime and leavesTaken must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Role role, LeaveBalance leaveBalance,
                  HourlySalary hourlySalary, HoursWorked hoursWorked, CalculatedPay pay, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, role, leaveBalance, hourlySalary, hoursWorked, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.role = role;
        this.leaveBalance = leaveBalance;
        this.leavesTaken = new LeavesTaken();
        this.hourlySalary = hourlySalary;
        this.hoursWorked = hoursWorked;
        this.overtime = new Overtime("0");
        this.calculatedPay = pay;
        this.tags.addAll(tags);
    }

    /**
     * Constructs a {@code Person} object with overtime.
     * All fields, including overtime and leavesTaken, must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Role role, LeaveBalance leaveBalance,
                  LeavesTaken leavesTaken, HourlySalary salary, HoursWorked hoursWorked, Overtime overtime,
                  CalculatedPay pay, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, role, leaveBalance, salary, hoursWorked, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.role = role;
        this.leaveBalance = leaveBalance;
        this.leavesTaken = leavesTaken;
        this.hourlySalary = salary;
        this.hoursWorked = hoursWorked;
        this.overtime = overtime;
        this.calculatedPay = pay;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public Role getRole() {
        return role;
    }

    public LeaveBalance getLeaveBalance() {
        return leaveBalance;
    }

    public LeavesTaken getLeavesTaken() {
        return leavesTaken;
    }

    public HourlySalary getSalary() {
        return hourlySalary;
    }

    public HoursWorked getHoursWorked() {
        return hoursWorked;
    }

    public Overtime getOvertime() {
        return overtime;
    }

    public CalculatedPay getCalculatedPay() {
        return calculatedPay;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if the person's {@code CalculatedPay} value is not zero.
     * This means that the person still has pay that has not been paid yet.
     */
    public boolean isPaid() {
        return calculatedPay.value == 0;
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same phone.
     * No two different employees should have the same phone number.
     */
    public boolean hasSamePhone(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }
        return otherPerson != null
                && otherPerson.getPhone().equals(getPhone());
    }

    /**
     * Returns true if both persons have the same email.
     * No two different employees should have the same email.
     */
    public boolean hasSameEmail(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }
        return otherPerson != null
                && otherPerson.getEmail().equals(getEmail());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return otherPerson.getName().equals(getName())
                && otherPerson.getPhone().equals(getPhone())
                && otherPerson.getEmail().equals(getEmail())
                && otherPerson.getAddress().equals(getAddress())
                && otherPerson.getRole().equals(getRole())
                && otherPerson.getLeaveBalance().equals(getLeaveBalance())
                && otherPerson.getLeavesTaken().equals(getLeavesTaken())
                && otherPerson.getSalary().equals(getSalary())
                && otherPerson.getOvertime().equals(getOvertime())
                && otherPerson.getCalculatedPay().equals(getCalculatedPay())
                && otherPerson.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, role, leaveBalance, leavesTaken,
                hourlySalary, hoursWorked, overtime, calculatedPay, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append("; Phone: ")
                .append(getPhone())
                .append("; Email: ")
                .append(getEmail())
                .append("; Address: ")
                .append(getAddress())
                .append("; Role: ")
                .append(getRole())
                .append("; Leaves: ")
                .append(getLeaveBalance())
                .append("; Salary: ")
                .append(getSalary())
                .append("; Hours Worked: ")
                .append(getHoursWorked());

        Set<Tag> tags = getTags();
        if (!tags.isEmpty()) {
            builder.append("; Tags: ");
            tags.forEach(builder::append);
        }
        return builder.toString();
    }

}

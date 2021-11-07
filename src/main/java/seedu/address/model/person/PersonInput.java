package seedu.address.model.person;

import com.opencsv.bean.CsvBindByName;

/**
 * Represents a PersonInput object with raw data taken from the csv file.
 * Used as an intermediate object before converting this object into a person object to be used by the AddressBook.
 */
public class PersonInput {

    // Identity fields
    @CsvBindByName(column = "Name", required = true)
    private String name;
    @CsvBindByName(column = "Contact Number", required = true)
    private String phone;
    @CsvBindByName(column = "Email", required = true)
    private String email;

    // Data fields
    @CsvBindByName(column = "Residential Address", required = true)
    private String address;
    @CsvBindByName(column = "Tags", required = false)
    private String tags;

    // Employee fields
    @CsvBindByName(column = "Role", required = true)
    private String role;

    @CsvBindByName(column = "Leave Balance", required = false)
    private String leave;
    @CsvBindByName(column = "Salary", required = false)
    private String salary;
    @CsvBindByName(column = "Hours Worked", required = false)
    private String hoursWorked;
    @CsvBindByName(column = "Overtime", required = false)
    private String overtime;

    public PersonInput(){};

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getRole() {
        return role;
    }

    public String getLeaves() {
        return leave;
    }

    public String getSalary() {
        return salary;
    }

    public String getHoursWorked() {
        return hoursWorked;
    }

    public String getOvertime() {
        return overtime;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setLeaves(String leave) {
        this.leave = leave;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public void setHoursWorked(String hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public void setOvertime(String overtime) {
        this.overtime = overtime;
    }


}


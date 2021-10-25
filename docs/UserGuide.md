---
layout: page
title: User Guide
---

HeRon is a desktop application for HR managers to assist in managing HR administrative tasks such as tracking leaves and offs, calculating pay and updating payroll information. It is optimized for use via a Command Line Interface (CLI) while still having the benefits of a Graphical User Interface (GUI). If you are a fast typer, HeRon can get your tasks done faster as compared to traditional GUI apps.


* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `11` or above installed in your Computer.

2. Download the latest `HeRon.jar` from [here](#).

3. Copy the file to the folder you want to use as the _home folder_ for your HeRon.

4. Double-click the file to start the app. The GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

5. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * **`list`** : Lists all contacts.

   * **`add`**`n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 r/Admin Assistant l/14 s/9.50 hw/40` : Adds a contact named `John Doe` to the Employee Book.

   * **`delete`**`3` : Deletes the 3rd contact shown in the current list.

   * **`clear`** : Deletes all contacts.

   * **`exit`** : Exits the app.

6. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* If a parameter is expected only once in the command but you specified it multiple times, only the last occurrence of the parameter will be taken.<br>
  e.g. if you specify `p/12341234 p/56785678`, only `p/56785678` will be taken.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

</div>

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`


### Adding an employee: `add`

Adds an employee to the employee book.

Format: `add  n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS r/ROLE l/LEAVES s/HOURLYSALARY hw/HOURSWORKED [t/TAG]…​`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
An employee can have any number of tags (including 0)
</div>

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 r/Admin Assistant l/14 s/9.50 hw/40`
* `add n/Betsy Crowe t/friend r/Designer s/25 hw/60  l/21  e/betsycrowe@example.com a/Newgate Prison p/1234567 t/criminal`

### Listing all employees : `list`

Shows a list of all employees in the employee book.

Format: `list`

### Editing an employee : `edit`

Edits an existing employee in the employee book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [r/ROLE] [l/LEAVES] [s/SALARY] [hw/HOURS_WORKED] [o/OVERTIME] [t/TAG]…​`

* Edits the employee at the specified `INDEX`. The index refers to the index number shown in the displayed employee list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the employee will be removed i.e adding of tags is not cumulative.
* You can remove all the employee’s tags by typing `t/` without
    specifying any tags after it.
* The value of LEAVES, HOURS_WORKED and OVERTIME **must be a positive integer.**
* The value of SALARY **must be a non-negative number.**

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com l/15` Edits the phone number, email address and leaves of the 1st employee to be `91234567`, `johndoe@example.com` and `15` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd employee to be `Betsy Crower` and clears all existing tags.

### Locating employees by name: `find`

Find employees using the specified field, checking if their information field contains any of the given keywords / queries.

Format: `find [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [r/ROLE] [l/LEAVES] [s/SALARY] [d/DATE] [t/TAG]...`

* At least one field should be specified.
* The keyword search is not case-sensitive.
* For each field, you can search using multiple keywords.
  * For example, `find n/John Mike r/Manager` will return all employees with the role "Manager" and with the names John or Mike.
* You can query employees who meet the specified condition for the number of leaves by entering `l/<LEAVES` (for less than LEAVES) or `l/>LEAVES` (for more than LEAVES)
  * For example, enter `find l/>5` to find all employees who still have more than 5 leaves remaining.
* Input dates should be of the form YYYY-MM-DD.

Examples:
* `find n/John` returns `john` and `John Doe`
* `find n/alex david l/<3` returns `Alex Yeoh`, `David Li` as long as they have less than 3 leaves left.<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)
* `find d/2021-10-10 2021-10-11` returns all employees that have taken leaves on 10th and 11th October 2021.<br>  


### Deleting an employee : `delete`

Deletes the specified employee from the application.

Format: `delete INDEX`

* Deletes the employee at the specified `INDEX`.
* The index refers to the index number shown in the displayed employee list.
* The index **must be a positive integer** 1, 2, 3, …​
* The index cannot exceed the length of the employee list.

Examples:
* `list` followed by `delete 2` deletes the 2nd employee in the employee book.
* `find Betsy` followed by `delete 1` deletes the 1st employee in the results of the `find` command.

### Add number of leaves for an employee : `addLeaveBalance`

Adds the specified number of leaves to the current leave balance (number of days of leave left) of a chosen employee.

Format: `addLeaveBalance INDEX l/LEAVES`

* Adds the specified number to the number of leaves of the employee at the specified `INDEX`.
* The index refers to the index number shown in the displayed employee list.
* The index **must be a positive integer** 1, 2, 3, …
* The number of leaves **must be a positive integer** 1, 2, 3, …

Examples:
* `list` followed by `addLeaveBalance 3 l/4` adds 4 days of leave to the 3rd employee in the employee book.
* `find Sam` followed by `addLeaveBalance 1 l/1` adds 1 day of leave to the 1st employee in the results of the `find` command.

### Subtract number of leaves for an employee : `subtractLeaveBalance`

Subtracts the specified number of leaves from the current leave balance (number of days of leave left) of a chosen employee.

Format: `subtractLeaveBalance INDEX l/LEAVES`

* Subtracts the specified number from the number of leaves of the employee at the specified `INDEX`.
* The index refers to the index number shown in the displayed employee list.
* The index **must be a positive integer** 1, 2, 3, …
* The number of leaves **must be a positive integer** 1, 2, 3, …
* The number of leaves to be removed **cannot be greater than the amount of leaves in the employee's leave balance.** 

Examples:
* `list` followed by `subtractLeaveBalance 2 l/1` removes 1 day of leave from the 2nd employee in the employee book.
* `find Anthony` followed by `subtractLeaveBalance 4 l/2` removes 2 days of leave from the 4th employee in the results of the `find` command.

### Assign a leave with a date to an employee : `assignLeave`

Assigns a leave that is associated with a date to a chosen employee.

Format: `assignLeave INDEX d/DATE`

* Assigns a leave to the employee at the specified `INDEX`, while subtracting 1 leave from the employee's leave balance.
* The employee must have **at least 1 leave** in their leave balance.   
* The index refers to the index number shown in the displayed employee list.
* The index **must be a positive integer** 1, 2, 3, …
* The date **must be valid** and of the form **YYYY-MM-DD**.

Examples:
* `list` followed by `assignLeave 2 d/2021-11-10` assigns a leave with the date 10th November 2021 to the 2nd employee in the employee book.
* `find Anthony` followed by `assignLeave 1 d/2021-01-08` assigns a leave with the date 8th January 2021 to the 1st employee in the results of the `find` command.

### Add number of hours worked/overtime to an employee : `addHoursWorked`

Adds the specified number of hours worked or overtime to a chosen employee.

Format: `addHoursWorked INDEX [hw/HOURS_WORKED] [o/OVERTIME]`

* At least one field (HOURS_WORKED or OVERTIME) should be specified.
* Adds the specified number of hours worked/overtime to the employee at the specified `INDEX`.
* The index refers to the index number shown in the displayed employee list.
* The index **must be a positive integer** 1, 2, 3, …
* The number of hours worked/overtime **must be a positive integer** 1, 2, 3, …

Examples:
* `list` followed by `addHoursWorked 5 hw/5 o/5` adds 5 hours worked and 5 hours of overtime to the 5th employee in the employee book.
* `find Sam` followed by `addHoursWorked 2 o/5` adds 5 hours of overtime to the 2nd employee in the results of the `find` command.

### Remove number of hours worked/overtime from an employee : `removeHoursWorked`

Removes the specified number of hours worked or overtime from a chosen employee.

Format: `removeHoursWorked INDEX [hw/HOURS_WORKED] [o/OVERTIME]`

* At least one field (HOURS_WORKED or OVERTIME) should be specified.
* Removes the specified number of hours worked/overtime from the employee at the specified `INDEX`.
* The index refers to the index number shown in the displayed employee list.
* The index **must be a positive integer** 1, 2, 3, …
* The number of hours worked/overtime **must be a positive integer** 1, 2, 3, …
* The number of hours worked/overtime to be removed **cannot be greater than the employee's current number of hours worked/overtime.**

Examples:
* `list` followed by `removeHoursWorked 2 hw/5 o/3` removes 5 hours worked and 3 hours of overtime from the 2nd employee in the employee book.
* `find Sam` followed by `removeHoursWorked 1 o/2` removes 2 hours of overtime from the 1st employee in the results of the `find` command.

### Clearing all entries : `clear`

Clears all entries from the employee book.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Start Payroll : `startPayroll`

Calculates the payroll of all employees based on recorded hours worked and overtime done so far.

Format: `startPayroll`
* Calculates the payroll of **all** employees and displays the list of all employees.
* All employees must not have any pay pending from the previous payroll.

Example:
* `find Betsy` followed by `calculate 2` gets the salary of the 2nd employee in the results of the `find` command.

### Saving the data

HeRon data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

HeRon data are saved as a JSON file `[JAR file location]/data/HeRon.json`. Advanced users are welcome to update data directly by editing the data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, HeRon will discard all data and start with an empty data file at the next run.
</div>

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous HeRon home folder.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
--------|------------------
**Add** | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS r/ROLE l/LEAVES s/HOURLYSALARY hw/HOURSWORKED [t/TAG]…​` <br> e.g., `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 r/Admin Assistant l/14 s/9.50 hw/40 t/friend t/colleague`
**Add to Leave Balance** | `addLeaveBalance INDEX l/LEAVES` <br> e.g., `addLeaves 1 l/2`
**Subtract from Leave Balance** | `subtractLeaveBalance INDEX l/LEAVES` <br> e.g., `removeLeaves 4 l/1`
**Assign Leave** |  `assignLeave INDEX d/DATE` <br> e.g., `assignLeaves d/2021-10-30`
**Add Hours Worked/Overtime** | `addHoursWorked INDEX [hw/HOURS_WORKED] [o/OVERTIME]` <br> e.g., `addHoursWorked 1 hw/2 o/3`
**Remove Hours Worked/Overtime** | `removeHoursWorked INDEX [hw/HOURS_WORKED] [o/OVERTIME]` <br> e.g., `removeHoursWorked 4 hw/1 o/2`
**Clear** | `clear`
**Start Payroll** | `startPayroll`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit** | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [r/ROLE] [l/LEAVES] [s/HOURLYSALARY] [hw/HOURS_WORKED] [o/OVERTIME] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com l/15`
**Find** | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**List** | `list`
**Help** | `help`

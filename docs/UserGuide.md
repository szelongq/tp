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

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [r/ROLE] [l/LEAVES] [s/SALARY] [h/HOURS_WORKED] [t/TAG]…​`

* Edits the employee at the specified `INDEX`. The index refers to the index number shown in the displayed employee list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the employee will be removed i.e adding of tags is not cumulative.
* You can remove all the employee’s tags by typing `t/` without
    specifying any tags after it.
* The value of LEAVES **must be a positive integer.**
* The value of SALARY **must be a non-negative number.**

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com l/15` Edits the phone number, email address and leaves of the 1st employee to be `91234567`, `johndoe@example.com` and `15` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd employee to be `Betsy Crower` and clears all existing tags.

### Locating employees by name: `find`

Find employees using specified fields, checking if their information field contains any of the given keywords / queries.

Format: `find [KEYWORDS]... [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [r/ROLE] [l/LEAVES] [s/SALARY] [hw/HOURS_WORKED] [o/OVERTIME] [t/TAG]...`

* At least one field should be specified.
* The order of the fields do not matter except for the `[KEYWORD]` field, which must come right after `find`.  
* The filters work differently for each field and can be generalised to 3 types of queries, described below. A single find command can contain all 3 types of queries at once.
  * **Type 1 Query: Keyword Matching**
    * Fields: `n/NAME`, `p/PHONE`, `e/EMAIL`, `a/ADDRESS`, `r/ROLE`, `t/TAG`
      * These fields will find all people who contain the given keywords in their respective fields. They are not case-sensitive.
      * The exception is the `p/PHONE` field, which only finds exact matches.
      
    * For example, `find p/91234567 e/alice bob r/Admin` will find anyone who satisfies the following 3 criteria:
      1. has the phone number 91234567, 
      2. whose email contains `alice` or `bob`, and 
      3. whose role contains `Admin`.
      
  * **Type 2 Query: Value Based Comparison**  
    * Fields: `hw/HOURS_WORKED`, `l/LEAVES`, `s/SALARY`, `o/OVERTIME`
      * These fields must be specified with a comparison and a value to compare the respective field to. Valid comparisons are
        * `>`: more than
        * `>=`: more than or equal to
        * `=`: equal to
        * `<`: less than
        * `<=`: less than or equal to
        
    * For example, `find hw/>=10 l/<7` will find anyone who satisfies the following 2 criteria:
      1. has worked more than or exactly 10 hours, and
      2. has less than 7 days of leave left (e.g. 6 and below)
          
    * You cannot enter more than 1 comparison or value to compare to. For example, `find hw/<10 >5` is not valid.
      
  * **Type 3 Query: Condition Based Filter**
    * There are no fields attached to this query. Instead, specific keywords are available for use.
      * These keywords must be used right after `find` and cannot be used after a field is specified (for example `n/`).
      * Keywords available include:
        * `unpaid`
        * More to be added.
    * For example, `find unpaid` will find all employees who are considered unpaid
    
* For each field, you can search using multiple keywords by separating each keyword with a space, in the same field.
  * For example, `find n/John Mike` will return all employees whose name contains either John or Mike.

Examples:
* `find unpaid n/John Mike r/admin l/<=5 o/>3` finds all employees who satisfy all the following criteria:
  1. is considered unpaid in the system,
  2. whose name is either John or Mike,
  3. whose role contains the word `admin`,
  4. has 5 or less than 5 leaves, and
  5. has strictly more than 3 days of overtime
  
* (To be updated) `find n/alex david l/<3` returns `Alex Yeoh`, `David Li` as long as they have less than 3 leaves left.<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png) 
  
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

### Add number of leaves for an employee : `addLeaves`

Adds the specified number of days to the current leave quota (number of days of leave left) of a chosen employee.

Format: `addLeaves INDEX NO_OF_DAYS`

* Adds the specified number to the number of leaves of the employee at the specified `INDEX`. 
* The index refers to the index number shown in the displayed employee list.
* The index **must be a positive integer** 1, 2, 3, …
* The number of days **must be a positive integer** 1, 2, 3, …

Examples:
* `list` followed by `addLeaves 3 4` adds 4 days of leave to the 3rd employee in the employee book.
* `find Sam` followed by `addLeaves 1 1` adds 1 day of leave to the 1st employee in the results of the `find` command.

### Remove number of leaves for an employee : `removeLeaves`

Removes the specified number of days from the current leave quota (number of days of leave left) of a chosen employee.

Format: `removeLeaves INDEX NO_OF_DAYS`

* Removes the specified number from the number of leaves of the employee at the specified `INDEX`. 
* The index refers to the index number shown in the displayed employee list.
* The index **must be a positive integer** 1, 2, 3, …
* The number of days **must be a positive integer** 1, 2, 3, …

Examples:
* `list` followed by `removeLeaves 2 1` removes 1 day of leave from the 2nd employee in the employee book.
* `find Anthony` followed by `removeLeaves 4 2` removes 2 days of leave from the 4th employee in the results of the `find` command.

### Clearing all entries : `clear`

Clears all entries from the employee book.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Calculating Salary : `calculate` `[to be implemented in v1.2]`

Calculate and display the monthly hourlySalary of the specified employee.

Format: `calculate INDEX`
* Calculate and displays the monthly Salary of the employee at the specified `INDEX`.
* The index refers to the index number shown in the displayed employee list.
* The index **must be a positive integer** 1, 2, 3, …​

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
**Add Leaves** | `addLeaves INDEX NO_OF_DAYS` <br> e.g., `addLeaves 1 2`
**Remove Leaves** | `removeLeaves INDEX NO_OF_DAYS` <br> e.g., `removeLeaves 4 1`
**Clear** | `clear`
**Calculate** | `calculate INDEX`<br> e.g., `calculate 3`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit** | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [r/ROLE] [l/LEAVES] [s/HOURLYSALARY] [h/HOURS_WORKED] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com l/15`
**Find** | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**List** | `list`
**Help** | `help`

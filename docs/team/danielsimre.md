---
layout: page
title: Daniel Sim's Project Portfolio Page
---

### Project: HeRon

HeRon is a desktop application for HR managers to assist in managing HR administrative tasks such as tracking leaves and offs, calculating pay and updating payroll information.

HeRon is a project adapted from AddressBook - Level 3. It is written in Java, and has about 10 kLoC.

Given below are my contributions to the project.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2122s1.github.io/tp-dashboard/?search=danielsimre&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2021-09-17)

* **Enhancements Implemented**:
  * Added functionality to keep track of an employee's leave balance
  * Added functionality to keep track of an employee's overtime
  * Added functionality to assign leaves to employees
  * Added functionality to remove outdated assigned leaves from employees
  * Added commands to allow user to add/deduct an employee's leave balance, hours worked,
    without having to specify an exact value via the edit command
  * Updated `find` command with the ability to search employees by their assigned leave dates,
    either with a date range, or an individual date

* **Documentation**:
  * User Guide:
    * Added documentation for the following commands: 
        * `addLeaveBalance`
        * `deductLeaveBalance`
        * `addHoursWorked`
        * `deductHoursWorked`
        * `assignLeave`
        * `removeLeavesBefore`
        * `find` (Section about searching with dates)
  * Developer Guide:
    * Added implementation details for:
        * `LeaveBalance`
        * `addLeaveBalance` and `deductLeaveBalance`
        * `LeavesTaken`
        * `assignLeave` and `removeLeavesBefore`
    * Added use cases and manual test cases for the following commands:
        * `addLeaveBalance`
        * `deductLeaveBalance`
        * `addHoursWorked`
        * `deductHoursWorked`
        * `assignLeave`
        * `removeLeavesBefore`

* **Team Contributions**
  * Helped to create issues relating to each milestone

* **Review Contributions**
  * Main reviewer for Sze Long's pull requests

* **Contributions to the Developer Guide (Extracts)**
  * Class diagram for `Person` attribute classes
    ![PersonClassDiagram](../images/PersonClassDiagram.png)
  * Sequence diagram for `AddLeaveBalancCommand`
    ![AddLeaveBalanceSequenceDiagram](../images/AddLeaveBalanceSequenceDiagram.png)
  * Sequence diagram for `RemoveLeavesBeforeCommand`
    ![RemoveLeavesBeforeSequenceDiagram](../images/RemoveLeavesBeforeSequenceDiagram.png)

* **Contributions to the User Guide (Extracts)**
    * **Type 4 Query: Date Based Comparison**
      * Fields: `d/DATE`
        * This field will find all people who have taken a leave on a given date or within a range of dates. (start and end dates inclusive)
        * There are two ways to search using dates: individual dates or date ranges.
          * For individual dates, simply type in a date of the form YYYY-MM-DD.
          * For date ranges, simply type in two dates in the form YYYY-MM-DD:YYYY-MM-DD.
        * Both individual dates and date ranges can be combined into one query.
      * For example, `find d/2021-10-10 2021-11-01:2021-11-05` will find anyone who satisfies **either** of the following 2 criteria:
        1. has taken a leave on October 10th 2021, or
        2. has taken a leave between the dates November 1st 2021 and November 5th 2021, start and end dates inclusive.
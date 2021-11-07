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
    * Added use cases for the following commands:
        * `addLeaveBalance`
        * `deductLeaveBalance`
        * `addHoursWorked`
        * `deductHoursWorked`
        * `assignLeave`
* **Team Contributions**
  * Helped to create issues relating to each milestone
* **Review Contributions**
  * Main reviewer for Sze Long's pull requests
  
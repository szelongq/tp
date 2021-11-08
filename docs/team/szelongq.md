---
layout: page
title: Quek Sze Long's Project Portfolio Page
---

### Project: HeRon

HeRon is a desktop application for HR managers of Small-Medium Enterprises (SMEs) to assist in managing HR 
administrative tasks such as tracking leaves and offs, calculating pay and updating payroll information. 
It is optimized for use via a Command Line Interface (CLI) while still having the benefits of a 
Graphical User Interface (GUI).

HeRon is a project adapted from AddressBook - Level 3.

Given below are my contributions to the project.

* **New Feature**: Added a start payroll command to calculate the pay of all employees.
  * Justification: This feature is important for the target users, HR managers, as one of their major tasks is to
    compute payroll. This helps users to complete calculations easily.<br><br>
    
* **New Feature**: Enhanced the pay command to allow the user to pay all employees in the filtered list at once.
  * Justification: In a company, it is likely that the user (HR managers) would pay multiple employees at once, and 
    the original pay command only pays one employee at a time. This enhancement greatly improves the convenience of
    keeping track of which employees have been paid.
  * Highlights: The implementation turned out to be challenging, as possible interactions with other commands such as
    startPayroll and addHoursWorked had to be considered. As a result, more integration testing had to be done.<br><br>
    
* **New Feature**: Added an overtime pay rate attribute in the user preferences.
  * What it does: The overtime pay rate is used in payroll calculations as an additional salary multiplier 
    for overtime work done.
  * Justification: Users are unlikely to change the overtime pay rate of their company very often, hence setting it to
    be in the user preferences allows users to change the rate and the information will be retained, improving 
    convenience.
  * Highlights: It was slightly challenging to decide how to store the overtime pay rate attribute. After deciding on
    adding it as an attribute to the user preferences, a number of refactoring also had to be done to various child 
    classes. <br><br>
    
* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2122s1.github.io/tp-dashboard/?search=szelong&sort=groupTitle&sortWithin=title&since=2021-09-17&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=false&tabOpen=true&tabType=authorship&tabAuthor=szelongq&tabRepo=AY2122S1-CS2103T-F11-3%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=&authorshipIsBinaryFileTypeChecked=false) <br><br>

<div style="page-break-after: always;"></div>

* **Contribution to Documentation**:
  * **User Guide**:
    * Added documentation for the following payroll-related features:
      ([\#104](https://github.com/AY2122S1-CS2103T-F11-3/tp/pull/104/commits/1dfceed7fbcd2b01add2a89677b2c12f1cd73ec8))
      * `startPayroll`
      * `pay`
      * `viewOvertimePayRate`
      * `setOvertimePayRate`
    * Organized documented features into subcategories for easier referencing 
      ([\#104](https://github.com/AY2122S1-CS2103T-F11-3/tp/pull/104/commits/57ba9b399df24f384a747f04e17a3a274cb8e0eb)) 
      
  * **Developer Guide**:
    * Added the following documentation for the `startPayroll` feature:
      * Implementation Details ([\#87](https://github.com/AY2122S1-CS2103T-F11-3/tp/pull/87),
        [\#201](https://github.com/AY2122S1-CS2103T-F11-3/tp/pull/201),
        [\#205](https://github.com/AY2122S1-CS2103T-F11-3/tp/pull/226))
      * UML Diagrams ([\#201](https://github.com/AY2122S1-CS2103T-F11-3/tp/pull/201))
      * Use cases ([\#87](https://github.com/AY2122S1-CS2103T-F11-3/tp/pull/87))
      * Instructions for manual testing ([\#205](https://github.com/AY2122S1-CS2103T-F11-3/tp/pull/226)) <br><br>
  
* **Contribution to team-based tasks**:
  * Tracked progress on team tasks on the tP dashboard
    * Updated team repo documents to remove mention of AB-3 during set-up
    * Submitted product concept to TEAMMATES and updated in the documentation
  * Managed issues tracking from PE-D for v1.4.
    * Made group progress report regarding PE-D bug triaging progress.
  * Formatted the User Guide to be PDF-friendly <br><br>

* **Review Contributions**:
  * Main reviewer for @onewaykiat's pull requests.

<div style="page-break-after: always;"></div>

#### Contributions to the Developer Guide (Extracts):

##### Start Payroll feature

The start payroll feature is provided through `StartPayrollCommand`.
It extends `Command` with the following added methods to calculate the payroll for every employee:
- `StartPayrollCommand#calculatePay(HourlySalary salary, HoursWorked hoursWorked,
  Overtime overtime, OvertimePayRate overtimePayRate)` - Calculates the payroll based on the given parameters and
  returns a new `CalculatedPay` object.
- `StartPayrollCommand#createPersonWithCalculatedPay(Person personWithCalculatedPay,
  CalculatedPay newCalculatedPay)` - Creates a new `Person` that is a copy of the given `Person` parameter
  except with the updated `CalculatedPay` value.
- `StartPayrollCommand#createPersonWithZeroHoursWorkedAndOvertime(Person person)` - Creates a new `Person` that is a copy of the
  given `Person` parameter except with the `HoursWorked` and `Overtime` values set to 0.

...
  
The following sequence diagram describes the operations in executing a `StartPayrollCommand`.

![StartPayrollSequenceDiagram](../images/StartPayrollSequenceDiagram.png)

<div style="page-break-after: always;"></div>

#### Contributions to the User Guide (Extracts):

##### Start Payroll : `startPayroll`

Calculates the payroll of all employees and mark them as awaiting the payment.

The payroll formula is: `HOURSWORKED * HOURLYSALARY + OVERTIME * HOURLYSALARY * OVERTIMEPAYRATE`,<br>
where `OVERTIMEPAYRATE` is the added pay rate for overtime as compared to normal work hours.<br>
It can be viewed through the `viewOvertimePayRate` command or changed through the `setOvertimePayRate` command.

Format: `startPayroll`
* Calculates the payroll of **all employees regardless of current viewing list** based on the formula above.
* After that, marks all employees as awaiting payment of the calculated amount.
  <br>This will produce red labels under each employee data stating 'NOT PAID' and the amount they are owed.
* The number of hours worked and overtime hours worked for the employee will be reset to 0 as well
  so that hours counting towards the next payroll can continue to be added.
* Finally, display the list of all employees.
* This command is typically followed up by `pay` commands to mark employees as paid,
  after their salaries are given in real life.

...

<div markdown="block" class="alert alert-info">
**:information_source: Notes:**

* All employees must not have any pay pending from the previous payroll (i.e. no red 'NOT PAID' label). Otherwise, an error will be thrown
  and no action will be taken.
* To start a new payroll, first make sure to pay all employees using the `pay` command.
</div>


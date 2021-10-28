---
layout: page
title: Developer Guide
---
* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* {list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams in this document can be found in the [diagrams](https://github.com/se-edu/addressbook-level3/tree/master/docs/diagrams/) folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.
</div>

### Architecture

<img src="images/ArchitectureDiagram.png" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** has two classes called [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java). It is responsible for,
* At app launch: Initializes the components in the correct sequence, and connects them up with each other.
* At shut down: Shuts down the components and invokes cleanup methods where necessary.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

The rest of the App consists of four components.

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.


**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<img src="images/ComponentManagers.png" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

![Structure of the UI Component](images/UiClassDiagram.png)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<img src="images/LogicClassDiagram.png" width="550"/>

How the `Logic` component works:
1. When `Logic` is called upon to execute a command, it uses the `AddressBookParser` class to parse the user command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `AddCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to add a person).
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

The Sequence Diagram below illustrates the interactions within the `Logic` component for the `execute("delete 1")` API call.

![Interactions Inside the Logic Component for the `delete 1` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.
</div>

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<img src="images/ParserClasses.png" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<img src="images/ModelClassDiagram.png" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<div markdown="span" class="alert alert-info">:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<img src="images/BetterModelClassDiagram.png" width="450" />

</div>


### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<img src="images/StorageClassDiagram.png" width="550" />

The `Storage` component,
* can save both address book data and user preference data in json format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.addressbook.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedAddressBook#commit()` — Saves the current address book state in its history.
* `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

![UndoRedoState0](images/UndoRedoState0.png)

Step 2. The user executes `delete 5` command to delete the 5th person in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

![UndoRedoState1](images/UndoRedoState1.png)

Step 3. The user executes `add n/David …​` to add a new person. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

![UndoRedoState2](images/UndoRedoState2.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</div>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

![UndoRedoState3](images/UndoRedoState3.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</div>

The following sequence diagram shows how the undo operation works:

![UndoSequenceDiagram](images/UndoSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</div>

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</div>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

![UndoRedoState4](images/UndoRedoState4.png)

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

![UndoRedoState5](images/UndoRedoState5.png)

The following activity diagram summarizes what happens when a user executes a new command:

<img src="images/CommitActivityDiagram.png" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire address book.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_

### Updating Info Panel display

#### Current Implementation
The `InfoPanel` class controls the information to be displayed on the Info Panel, with the method `updateInfoPanel(Person p)`
which updates the content to be displayed with `Person`'s information.

`ModelManager` contains viewingPerson which represents the person whose data is to be displayed onto the panel, which 
the `Logic` interface has access to.

`MainWindow` houses the InfoPanel, which calls the update method with the `viewingPerson` passed into it.
The updated `viewingPerson` is passed to the update method by through calling the accessor method from the `Logic` component.

Walk-through example:

1. User launches the application for the first time. `viewingPerson` will be initialized to be the first person in the addressBook list
(otherwise it is set to an example person), with `InfoPanel` initialized with `viewingPerson` and displaying its information.

2. User executes `view 2` command in order to view the 2nd person in the addressBook. `ViewCommand` then calls `Model#setViewingPerson()`
which sets the `viewingPerson` to be the 2nd person

Upon execution of the command, `MainWindow` calls `InfoPanel#updateInfoPanel())` with the updated viewingPerson, and updates the display.

Design Considerations:
Pros: Easy to implement, ensures that InfoPanel always displays accurate up-to-date information
Cons: Difficult to extend InfoPanel to display other information with current implementation

### Start Payroll feature

#### Current Implementation

The start payroll feature is provided through `StartPayrollCommand`.
It extends `Command` with the following added methods to calculate the payroll for every employee:
- `StartPayrollCommand#calculatePay(HourlySalary salary, HoursWorked hoursWorked,
  Overtime overtime, OvertimePayRate overtimePayRate)` - Calculates the payroll based on the given parameters and
  returns a new `CalculatedPay` object.
- `StartPayrollCommand#createPersonWithCalculatedPay(Person personWithCalculatedPay,
  CalculatedPay newCalculatedPay)` - Creates a new `Person` that is a copy of the given `Person` parameter
  except with the updated `CalculatedPay` value.

Given below is an example of how StartPayrollCommand works.

Step 1. The user enters the command word 'startPayroll'. The `addressBookParser` parses the input,
creates a `StartPayrollCommand` and executes it.

Step 2. In the new instance of `StartPayrollCommand`, upon starting execution,
the list of employees to be viewed in `Model` is set to be unfiltered using `Model#updateFilteredPersonList()`.
The list of all employees is then retrieved by calling `Model#getFilteredPersonList()`.

Step 3. Each employee in the list of employees are checked if they have any previously calculated payroll that have not
been paid yet by calling `Person#isPaid()` on the employee. If an employee is unpaid,
a `CommandException` will be thrown.

Step 4. If there are no employees who are unpaid, calculations of payroll will proceed through the following substeps
for each employee in the list:

Step 4.1. Retrieve the current `overtimePayRate` in the application from the `Model`
using `Model#getOvertimePayRate()`.<br>
After that, retrieve the following attributes from the employee `Person` object:
- `hourlySalary` - The employee's salary per hour.
- `hoursWorked` - How many hours the employee has worked for (excluding overtime).
- `overtime` - How many hours of overtime the employee has worked for.

Step 4.2. The `CalculatedPay` object representing the calculated employee's pay is created by calling
the `StartPayrollCommand#calculatePay()` method, with the earlier retrieved values (`overtimePayRate`, `hourlySalary`,
`hoursWorked`, `overtime`) as parameters.

Step 4.3. An updated copy of the employee `Person` object is created with the new `CalculatePay` attribute using
`StartPayrollCommand#createPersonWithCalculatedPay()`.

Step 4.4. The employee `Person` object in the `Model` is then replaced with the updated copy using `Model#setPerson()`.

Step 5. After every employee in the list has had their payroll calculated, the `StartPayrollCommand` returns a
`CommandResult` to signal successful execution.

--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

* is a HR manager of a small company (10 - 50 employees)
* is the only one in charge of handling HR
* has a need to manage information on all employees
* prefers desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**:
* More convenient to manage employees’ information than a typical mouse/GUI driven app.
* Able to quickly assess the balance of work between employees.


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                                    | I want to …​                                            | So that I can…​                                                     |
| -------- | ------------------------------------------ | --------------------------------------------------------------| ---------------------------------------------------------------------- |
| `* * *`  | user                                       | add a new employee                                                                                                    |                                                                                                                           |
| `* * *`  | user                                       | delete an employee                                                                                                    | remove entries that I no longer need.                                                                                     |
| `* * * ` | user                                       | edit an employee's data                                                                                               |                                                                                                                           |
| `* * *`  | user with many employees in the app        | search for an employee's data easily                                                                                  | locate details of employees without having to go through the entire list.                                                   |
| `* * *`  | user                                       | keep track of my employee's current leaves                                                                            | determine if a given employee's request for leaveBalance is valid.                                                               |
| `* * *`  | user                                       | update leaves for certain employees                                                                                   | have a more accurate count of their remaining leaves.                                                                     |
| `* * *`  | user                                       | keep track of an employee's unpaid leaves                                                                             | know how much to deduct from their monthly salary.                                                                        |
| `* * *`  | user                                       | get all the offs and leaves that every employee has                                                                   | get those with remaining leaves to clear them before the end of the year.                                                 |
| `* * *`  | user                                       | calculate the pay of all employees and view them easily                                                               | make it easier to complete administrative tasks.                                                                          |
| `* * *`  | user                                       | have an application to account for factors like unpaid leaves and overtime when calculating salary                     | reduce chances of errors by manual calculations.                                                                          |
| `* * *`  | user                                       | keep track of employees which I have not paid yet                                                                     | ensure all employees are paid.                                                                                            |
| `* * *`  | user                                       | get all the overtime that every employee has done for the month                                                       | keep track of each employee and analyze the amount of overtime being done in the company.                                 |
| `* * *`  | organized user                             | group up employees into specified groups                                                                              | sort through them easily with color coding and tags.                                                                      |
| `* *  `  | user                                       | input a schedule for an employee                                                                                      | keep track of how many hours he/she are intending to work.                                                                  |
| `* *  `  | user                                       | schedule employees and their work schedules                                                                           | ensure they hit the minimum work quota and all employees are working equally.                                             |
| `* *  `  | user in a company that does shift work     | see the schedule for the week                                                                                         | have a rough idea of how to assign people to shifts.                                                                      |
| `* *  `  | user                                       | generate a work schedule according to certain specified rules                                                         | do not have to make one manually while ensuring it abides by the rules.                                                   |
| `* *  `  | user                                       | keep track of any documents relating to my employees.                                                                 |                                                                                                                           |
| `* *  `  | user                                       | keep tack of any complaints lodged against employees                                                                  | address the employees accordingly.                                                                                        |
| `* *  `  | user                                       | have short and readable summaries of day-to-day statuses in the company                                               | access it at anytime for any potential tracking in the future.                                                            |
| `* *  `  | user                                       | get a summary of certain information of employees                                                                     | include it easily in monthly reports.                                                                                     |
| `* *  `  | organized user                             | group up employees into specified groups                                                                              | update information for the specific group without manually updating each member one by one.                               |
| `* *  `  | organized user                             | be able to easily archive/filter employees that are not relevant                                                      | ensure they do not distract me from doing my work.                                                                        |
| `* *  `  | user                                       | compute all the relevant information that I require in a short amount of time regardless of the number of employees   | ensure the workflow will take similar amounts of time regardless of company size.                                         |
| `* *  `  | expert user                                | have an automation system that handles tasks which have to be done periodically (eg. everyday)                        | do not have to do them manually and make the processing of data more efficient.                                           |
| `* *  `  | expert user                                | make the least effort to get the employee's information                                                               | be more efficient.                                                                                                        |
| `* *  `  | new user                                   | spend the least effort to learn how to use the application                                                            | spend more time focusing on my tasks.                                                                                     |
| `* *  `  | forgetful user                             | have a convenient way to access help while using the application                                                      | have an overview of how to execute certain commands if I forget.                                                          |
| `* *  `  | lazy user                                  | have customized shortcuts                                                                                             | reduce effort needed to complete my tasks, especially if they are done often.                                             |
| `* *  `  | user                                       | have different settings for different employees                                                                       | quickly do computations on all employees even if they are treated differently, such as having different pay calculations. |
| `* *  `  | user                                       | save certain employee settings and apply them to other employees                                                      | add new employees into the database with previously used settings.                                                        |
| `* *  `  | potential user                             | insert dummy data into the app                                                                                        | see how the interface looks.                                                                                              |
| `* *  `  | potential user                             | clear out all the dummy data inserted                                                                                 | immediately start using the app after testing it.                                                                         |
| `* *  `  | user                                       | can see everything                                                                                                    | feel like I am in charge.                                                                                                 |
| `* *  `  | user dealing with confidential information | have some form of authentication                                                                                      | ensure only authorized personnel are allowed to access the data.                                                          |
| `* *  `  | user                                       | mark important days such as payday                                                                                    | manage my work more effectively.                                                                                          |

### Use cases

(For all use cases below, the **System** is the `HeRon` and the **Actor** is the `user`, unless specified otherwise)

**Use case: Delete an employee**

**MSS**

1.  User requests to list employees
2.  HeRon shows a list of employees
3.  User requests to delete a specific employee in the list
4.  HeRon deletes the employee

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. HeRon shows an error message.

      Use case resumes at step 2.

**Use case: Add leaves to an employee**

**MSS**

1.  User requests to list employees
2.  HeRon shows a list of employees
3.  User requests to add a certain number of leaves to a specific employee in the list
4.  HeRon adds the leaves to the employee

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. HeRon shows an error message.

      Use case resumes at step 2.

* 3b. The number of leaves to be added is invalid. (If the input is not a positive integer)

    * 3b1. HeRon shows an error message.

      Use case resumes at step 2.

**Use case: Remove leaves from an employee**

Guarantees:
* The number of leaves of the employee after the operation will never be negative.

**MSS**

1.  User requests to list employees
2.  HeRon shows a list of employees
3.  User requests to remove a certain number of leaves from a specific employee in the list
4.  HeRon removes the leaves from the employee

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. HeRon shows an error message.

      Use case resumes at step 2.

* 3b. The number of leaves to be removed is invalid. (If the input is not a positive integer)

    * 3b1. HeRon shows an error message.

      Use case resumes at step 2.

* 3c. The number of leaves to be removed is greater than the amount of leaves the employee actually has.

    * 3c1. HeRon shows an error message.

      Use case resumes at step 2.

**Use case: Assign a leave to an employee**

Guarantees:
* The number of leaves of the employee after the operation will never be negative.

**MSS**

1.  User requests to list employees
2.  HeRon shows a list of employees
3.  User requests to assign a leave to an employee with a date
4.  HeRon assigns the leave to the employee and subtracts a leave from the employee's leave balance

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. HeRon shows an error message.

      Use case resumes at step 2.

* 3b. The date associated with the leave is invalid.

    * 3b1. HeRon shows an error message.

      Use case resumes at step 2.

* 3c. The employee has no more leaves remaining in their leave balance.

    * 3c1. HeRon shows an error message.

      Use case resumes at step 2.

**Use case: Add hours worked/overtime to an employee**

**MSS**

1.  User requests to list employees
2.  HeRon shows a list of employees
3.  User requests to add a certain number hours worked and/or overtime to a specific employee in the list
4.  HeRon adds the hours worked/overtime to the employee

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. HeRon shows an error message.

      Use case resumes at step 2.

* 3b. The number of hours worked/overtime to be added is invalid. (If the input is not a positive integer)

    * 3b1. HeRon shows an error message.

      Use case resumes at step 2.

**Use case: Remove hours worked/overtime from an employee**

Guarantees:
* The number of hours worked/overtime of the employee after the operation will never be negative.

**MSS**

1.  User requests to list employees
2.  HeRon shows a list of employees
3.  User requests to remove a certain number hours worked and/or overtime from a specific employee in the list
4.  HeRon removes the hours worked/overtime from the employee

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. HeRon shows an error message.

      Use case resumes at step 2.

* 3b. The hours worked/overtime to be removed is invalid. (If the input is not a positive integer)

    * 3b1. HeRon shows an error message.

      Use case resumes at step 2.

* 3c. The number of hours worked/overtime to be removed is greater than the number of hours worked/overtime the employee actually has.

    * 3c1. HeRon shows an error message.

      Use case resumes at step 2.

**Use Case: Finding an employee**

**MSS**

1.  User requests to list employees
2.  HeRon shows a list of employees
3.  User requests to find all employees with a given query (name, phone, tags, and other valid queries)
4.  HeRon shows a list of employees with that given name

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given query does not match any user in HeRon

    * 3a1. HeRon shows an error message

      Use case resumes at step 2.

* 3b. The given query is invalid (because it contains a tag that cannot be understood)

    * 3b1. HeRon shows an error message.

      Use case resumes at step 2.

**Use case: Edit an employee**

**MSS**

1. User requests to list employees
2. HeRon shows list of employees
3. User requests to edit a specific employee on the list
4. HeRon replaces the data of the employee based on the user's specified inputs
5. HeRon displays employee data of the edited employee to user

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. HeRon shows an error message.

        Use case resumes at step 2.

* 3b. No field has been provided

    * 3b1. HeRon shows an error message informing user about the lack of a field

        Use case resumes at step 2.

* 3c. Input into field is invalid

    * 3c1. HeRon shows an error message informing user about which field has invalid input and why it is invalid

        Use case resumes at step 2.

**Use case: Calculate payroll for all employees**

**MSS**

1.  User requests to list employees
2.  HeRon shows a list of employees
3.  User requests to calculate the payroll for all employees
4.  HeRon shows the list of all employees
5.  HeRon calculates the payroll and updates all employees' calculated pay information.

    Use case ends.

**Extensions**

* 5a. There is an employee who has not yet been paid the previous calculated pay.

    * 5a1. HeRon shows an error message.

        Use case resumes at step 4.

**Use case: Adding a Tag to an employee**

**MSS**
1. User searches for a certain employee.
2. HeRon returns the results of the search.
3. User tags the specified employees with a specified tag.
4. Tag is added to the employee.

   Use case ends.

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. HeRon shows an error message.

      Use case resumes at step 3.

* 3b. The given tag is invalid.

    * 3b1. HeRon shows an error message.

        Use case resumes at step 3.

**Use case: Updating details of group of employees with specified tag.**

**MSS**
1. User searches for employees with specified tag.
2. HeRon returns the results of the search.
3. User updates the details of the specified employees.
4. Employees in the group tag have their details updated.

   Use case ends.

**Extensions**

* 2a. The result list is empty.

  Use case ends.

* 3a. The given corresponding tag of the detail(s) to be updated is invalid.

    * 3a1. HeRon shows an error message.

      Use case resumes at step 3.

* 3b. The given detail(s) to be updated is invalid.

    * 3b1. HeRon shows an error message.

      Use case resumes at step 3.

*{More to be added}*

### Non-Functional Requirements

1. Should work on any _mainstream OS_ as long as it has Java `11` or above installed.
2. Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
3. A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4. User interface should be able to comfortably fit the information of at least 50 employees.
5. Should be usable offline.
6. Should respond within 2 seconds within the users’ command.
7. Should only allow authorized personnel to have access to the application’s data.
8. Should secure any files it produces.

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, OS-X
* **Private contact detail**: A contact detail that is not meant to be shared with others
* **Salary**: Monthly hourlySalary payout to employees
* **Leave**: Refers to a day when an employee has permission to be absent from work

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting a person

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

2. _{ more test cases …​ }_

### Editing an employee

1. Editing an employee while list of employees are shown

    1. Prerequisites: List all persons using the `list` command. Multiple employees in the list.

    2. Test case: `edit 1 n/Tsang Wei Ooi`<br>
        Expected: First employee's name is updated with `Tsang Wei Ooi`. Details of the edited employee is shown in status message. Timestamp in the status bar is updated.

    3. Test case: `edit`<br>
        Expected: No data is being edited. Error details shown in status message. Status bar remains the same.

    4. Test case: `edit 1 p/91234567 s/9.50`<br>
        Expected: First employee's phone number and salary is updated as `91234567` and `9.50` respectively together. Details of edited contact shown in the status message. Timestamp in the status bar is updated.

    5. Other incorrect edit commands to try: `edit x` (where x is larger than list size), `edit 1 s/-2.00` (salary should not be negative) etc. <br>
        Expected: No employee data is being edited. Error details shown in the status message. Status bar remains the same.

2. _{ possibly more test cases? …​ }_

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_

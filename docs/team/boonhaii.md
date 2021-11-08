---
layout: page
title: Boon Hai's Project Portfolio Page
---

### Project: HeRon

HeRon is a desktop application for HR managers to assist in managing HR administrative tasks such as tracking leaves and offs, calculating pay and updating payroll information.

HeRon is a project adapted from AddressBook - Level 3.

Given below are my contributions to the project.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2122s1.github.io/tp-dashboard/?search=boonhaii&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2021-09-17)

* **New Feature**: Implemented `import` feature. ([#71](https://github.com/AY2122S1-CS2103T-F11-3/tp/pull/71), [#82](https://github.com/AY2122S1-CS2103T-F11-3/tp/pull/82), [#97](https://github.com/AY2122S1-CS2103T-F11-3/tp/pull/97), [#186](https://github.com/AY2122S1-CS2103T-F11-3/tp/pull/186))
    * What it does: Parses through csv files and import the data into HeRon, and indicates the error locations if issues are present in the file (e.g. Wrong formatting, Invalid/Duplicate/Missing Data).
    * Justification: To start out using HeRon, users have to individually add in employees. While HeRon is optimised for CLI users, it still takes a significant amount of time to input all the data. In most organizations not using HR applications, personnel information are commonly stored in Excel, which can be exported into a csv file. The produced csv file can be easily imported into HeRon, reducing the effort required to transition into HeRon.
    * Highlights: The implementation was challenging as there was a need to learn and understand a new library. In addition, two of the main challenges during implementation were: 
      1. To specify the exact places were an error has been found to make importing more user-friendly.
      2. To cater for the validity checks brought about by the updated `add` feature, where duplicate names, emails and phone numbers were not allowed.

* **Enhancements to existing features**: 
  * Update `add` feature. ([#41](https://github.com/AY2122S1-CS2103T-F11-3/tp/pull/41), [#52](https://github.com/AY2122S1-CS2103T-F11-3/tp/pull/52), [#63](https://github.com/AY2122S1-CS2103T-F11-3/tp/pull/63), [#186](https://github.com/AY2122S1-CS2103T-F11-3/tp/pull/186))
    * `add` now requires new fields, including Role, LeaveBalance, HourlySalary and HoursWorked, as properties of an employee.
    * `add` checks for similar names, or duplicate phone numbers and emails. This is to mimic real world context where employees are unlikely to have the same full name, phone numbers or emails.
    * `add` now processes the names of employees being added, removing additional whitespace typos and making it title case.
    * Add new test cases adapted to suit the new functionality of `add`.
  * Add test cases for `AssignLeaveCommandParser` and `RemoveLeavesBeforeCommandParser`. ([#237](https://github.com/AY2122S1-CS2103T-F11-3/tp/pull/237))

* **Documentation**:
  * User Guide:
    * Updated documentation for the features `add`. ([#31](https://github.com/AY2122S1-CS2103T-F11-3/tp/pull/31))
    * Added documentation for feature `import`.([#91](https://github.com/AY2122S1-CS2103T-F11-3/tp/pull/91))
    * Update all command example screenshots to fit the latest version of HeRon.([#197](https://github.com/AY2122S1-CS2103T-F11-3/tp/pull/197))
  * Developer Guide:
    * Add implementation details for `import` feature. ([#103](https://github.com/AY2122S1-CS2103T-F11-3/tp/pull/103))
    * Explained design considerations for `import` feature.([#103](https://github.com/AY2122S1-CS2103T-F11-3/tp/pull/103))
    * Added UML Diagrams: ImportProcessData and ImportSeqeuenceDiagram. ([#103](https://github.com/AY2122S1-CS2103T-F11-3/tp/pull/103))

* **Community**:
  * Team-Based Tasks
    * Added User Stories for HeRon ([#29](https://github.com/AY2122S1-CS2103T-F11-3/tp/pull/29)).
    * Update AboutUs.md ([#31](https://github.com/AY2122S1-CS2103T-F11-3/tp/pull/31))
    * Format Developer Guide PDF file ([#231](https://github.com/AY2122S1-CS2103T-F11-3/tp/pull/231))
  * Review/Mentoring Contributions
    * Reviewed a total of 14 PRs: [#37](https://github.com/AY2122S1-CS2103T-F11-3/tp/pull/37), [#45](https://github.com/AY2122S1-CS2103T-F11-3/tp/pull/45), [#54](https://github.com/AY2122S1-CS2103T-F11-3/tp/pull/54), [#67](https://github.com/AY2122S1-CS2103T-F11-3/tp/pull/67), [#83](https://github.com/AY2122S1-CS2103T-F11-3/tp/pull/83), [#92](https://github.com/AY2122S1-CS2103T-F11-3/tp/pull/92), [#108](https://github.com/AY2122S1-CS2103T-F11-3/tp/pull/108), [#109](https://github.com/AY2122S1-CS2103T-F11-3/tp/pull/109), [#192](https://github.com/AY2122S1-CS2103T-F11-3/tp/pull/192), [#194](https://github.com/AY2122S1-CS2103T-F11-3/tp/pull/194), [#195](https://github.com/AY2122S1-CS2103T-F11-3/tp/pull/195), [#209](https://github.com/AY2122S1-CS2103T-F11-3/tp/pull/209), [#225](https://github.com/AY2122S1-CS2103T-F11-3/tp/pull/225), [#240](https://github.com/AY2122S1-CS2103T-F11-3/tp/pull/240)


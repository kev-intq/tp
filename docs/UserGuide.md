---
layout: page
title: User Guide
---
Greetings, TAs of NUS! camNUS is a **desktop app** for Teaching Assistants (TAs) to **manage their own contacts, as well as assessments and class participation among students** in their contact book. It is **optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI).

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `11` or above installed in your Computer.

1. Download the latest `camNUS.jar` from [here](https://github.com/AY2122S2-CS2103T-W13-2/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your camNUS app.

1. Double-click the file to start the app. The GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * **`list`** : Lists all contacts.

   * **`add`**`n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a contact named `John Doe` to the Address Book.

   * **`delete`**`3` : Deletes the 3rd contact shown in the current list.

   * **`clear`** : Deletes all contacts.

   * **`exit`** : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

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

Opens a window containing a list of help commands.

![help message](images/helpMessage.png)

Format: `help [n/COMMAND_NAME]`
* `COMMAND_NAME` is optional
* Directs the user to the list segment referred to by `COMMAND_NAME`
* Directs the user to the start of the list if `COMMAND_NAME` is unspecified

Examples:
* `help`
* `help n/delete`
* `help n/add`

### Adding Commands

#### Adding a person: `add`

Adds a person to the address book.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A person can have any number of tags (including 0)
</div>

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
* `add n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 t/criminal`

#### Adding a class: `add_class`

Adds a class to the module.

Format: `add_class tn/TUTORIAL_NAME v/VENUE d/DAY tm/TIME`
* `DAY` should be spelt in full or 3-letter abbreviation
* `TIME` will be in 1 hour block

Examples:
* `add_class tn/T04 v/LT13 d/Monday tm/13:00`
* `add_class tn/G04 v/E-LEARNING d/Wed tm/10:00`

#### Adding student to a class: `add_student`

Adds a specified student to a given class.

Format: `add_student n/NAME id/STUDENT_ID tn/TUTORIAL_NAME`

* Adds the person with the specified `NAME` as a student belonging to a specified class.
* `STUDENT_ID` refers to the student's unique NUSNET ID.
* `TUTORIAL_NAME` refers to the name of the tutorial group the student is assigned to.

Examples:
* `add_student 1 id/e01234567 tn/T13`

#### Adding an assessment component: `add_assessment`

Adds an assessment component to the module.

Format: `add_assessment as/ASSESSMENT_NAME w/WEIGHTAGE f/FULL_MARKS`
* `WEIGHTAGE` is out of 100%.
* `FULL_MARKS` is the full marks of the assessment.

Examples:
* `add_assessment as/Attendance w/5 f/1`
* `add_assessment as/Assignment 1 w/10 f/10`

### Listing Commands

#### Listing all persons : `list`

Shows a list of all persons in the address book.

Format: `list`

#### Listing all assessment components : `list_assessment`

Shows a list of all assessment components added.

Format: `list_assessment`

#### Listing all classes: `list_class`

Shows a list of all classes on the any input date.

Formats:

* `list_class`
* `list_class [d/DAY]`
* `DAY` is optional
* `DAY` should be spelt in full or 3-letter abbreviation

Examples:

* `list_class`
![result for `list_class`](images/listClassResult.png)
* `list_class d/Wed`

#### Listing students of a class: `list_student`

Shows a list of all students of a specified class.

Format:

* `list_student INDEX [tn/TUTORIAL_NAME]`
* `TUTORIAL_NAME` is optional if `INDEX` is given.
* Shows list of all students belonging to the class at the specified `INDEX`.
* Shows list of all students belonging to the class with the specified `TUTORIAL_NAME`.
* The index refers to the index number shown in the displayed person list.
* The index must be a positive integer 1, 2, 3, …​

Examples:

* `list_student tn/G04`
* `list_student 1`
  ![result for `list_student 1`](images/listStudentResult.png)

#### Listing scores of students in a class: `list_score`

Shows a list of the scores of all students of a given class for a given assessment component.

Format: `list_score as/ASSESSMENT_NAME tn/TUTORIAL_NAME`

Examples:

* list_score as/Assignment 1 tn/T04
  ![result for `list_score as/Assignment 1 tn/T04`](images/listScoreResult.png)

#### Listing attendance of a class: `list_attendance`

Generates the attendance list of a specified class, or a specified student.

Format: 
1. `list_attendance tn/TUTORIAL_NAME wk/WEEK`
   * `TUTORIAL_NAME` refers to the name of an existing tutorial group.
   * Shows attendance of all students belonging to the class with the specified `TUTORIAL_NAME`.
2. `list_attendance id/STUDENT_ID`
   * `STUDENT_ID` refers to the student's unique NUSNET ID.
   * Shows attendance of student with specified `STUDENT_ID`.

Examples:

* `list_attendance tn/T04 wk/2`
  ![result for `list_attendance tn/T04 wk/2`](images/listAttendance.png)
* `list_attendance id/e0123456`
  ![result for `list_attendance id/e0123456`](images/listAttendanceByStudent.png)

### Editing a person : `edit`

Edits an existing person in the address book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [id/STUDENT_ID] [t/TAG]…​`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without
    specifying any tags after it.

Examples:

*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.

### Locating persons by name: `find`

Finds persons whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:

* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Deleting Commands

#### Deleting a person : `delete`

Deletes the specified person from the address book.

Format: `delete INDEX`

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:

* `list` followed by `delete 2` deletes the 2nd person in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

#### Deleting a class : `delete_class`

Removes a class from the module

Format:
* `delete_class INDEX [tn/TUTORIAL_NAME]`
* `TUTORIAL_NAME` is optional if `INDEX` is given.
* Deletes the class at the specified `INDEX`.
* Deletes the class with the specified `TUTORIAL_NAME`.
* The index refers to the index number shown in the displayed list of classes.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:

* `delete_class 1`
* `delete_class tn/G04`

#### Removing a student  : `remove_student`

Removes a student from a given class, but does not remove their contact from the address book.

Format:
* `remove_student i/INDEX tn/TUTORIAL_NAME`.
* `remove_student id/STUDENT_ID tn/TUTORIAL_NAME`.
* Removes the student with the specified `INDEX` or `STUDENT_ID` from the class with specified `TUTORIAL_NAME`.
* The `INDEX` refers to the index number shown in the displayed list of student in the class.
* The `STUDENT_ID` refers to the student_id of a particular student.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:

* `remove_student i/1 tn/G04`
* `remove_student id/e0123456 tn/G04`

#### Deleting an assessment component: `delete_assessment`

Deletes an assessment component from the module, removing all information about the assessment from the students taking the module.

Format: `delete_assessment as/ASSESSMENT_NAME`

* Deletes the assessment with the specified `ASSESSMENT_NAME`.

Examples:

* `delete_assessment as/Attendance`
* `delete_assessment as/Assignment 1`

### Assigning assessment score to a student: `grade`

Assigns a score to a student in a specified assessment component.

Format: `grade as/ASSESSMENT_NAME n/NAME s/SCORE`

Example: `grade as/Test 1 n/Amy Tan s/5`

### Marking attendance for a student: `mark_attendance`

Marks attendance for a specified student or all students in a specified class for a specified week. 

Format: `mark_attendance tn/TUTORIAL_NAME [id/STUDENT_ID] wk/WEEK`

* `STUDENT_ID` is optional.
* `STUDENT_ID` refers to the student's unique NUSNET ID.
* `TUTORIAL_NAME` refers to the name of the tutorial group the student is assigned to.

Examples:

* `mark_attendance tn/T04 id/e0123456 wk/1`
* `mark_attendance tn/T04 wk/1`

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

AddressBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

AddressBook data are saved as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run.
</div>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous camNUS home folder.

--------------------------------------------------------------------------------------------------------------------

## Command summary

| Action                | Format, Examples                                                                                                                                                       |
|-----------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Add**               | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665  t/friend t/colleague` |
| **Add Class**         | `add_class tn/TUTORIAL_NAME v/VENUE d/DAY tm/TIME` <br> e.g., `add_class c/T04 v/LT13 d/Monday t/13:00`                                                                |
| **Add Student**       | `add_student n/NAME id/STUDENT_ID tn/TUTORIAL_NAME` <br> e.g., `add_student 1 id/e0123456 tn/T13`                                                                      |
| **Add Assessment**    | `add_assessment as/ASSESSMENT_NAME w/WEIGHTAGE s/SCORE` <br> e.g., `add_assessment as/Attendance w/5 s/1`                                                              |
| **Clear**             | `clear`                                                                                                                                                                |
| **Delete**            | `delete INDEX`<br> e.g., `delete 3`                                                                                                                                    |
| **Delete Class**      | `delete_class INDEX [tn/TUTORIAL_NAME]` <br> e.g., `delete_class 1 [tn/G04]`                                                                                           |
| **Remove Student**    | `remove_student i/INDEX tn/TUTORIAL_NAME` <br> e.g. `remove_student i/1 tn/G04`                                                                                        |
| **Delete Assessment** | `delete_assessment as/ASSESSMENT_NAME` <br> e.g., `delete_assessment as/Attendance`                                                                                    |
| **Edit**              | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [id/STUDENT_ID] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`                            |
| **Find**              | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`                                                                                                             |
| **List**              | `list`                                                                                                                                                                 |
| **List Assessment**   | `list_assessment`                                                                                                                                                      |
| **List Attendance**   | `list_attendance tn/TUTORIAL_NAME wk/WEEK` <br> e.g., `list_attendance tn/T04 wk/1`                                                                                    |
| **List Class**        | `list_class [d/DAY]` <br> e.g., `list_class d/Wed`                                                                                                                     |
| **List Student**      | `list_student INDEX [tn/TUTORIAL_NAME]` <br> e.g., `list_student 1 [tn/G04]`                                                                                           |
| **Grade**             | `grade as/ASSESSMENT_NAME n/NAME s/SCORE` <br> e.g., `grade as/Test 1 n/Amy Tan s/5`                                                                                   |
| **Mark Attendance**   | `mark_attendance tn/TUTORIAL_NAME [id/STUDENT_ID] wk/WEEK` <br> e.g., `mark_attendance tn/T04 id/e0123456 wk/1`                                                        |
| **Help**              | `help [n/COMMAND_NAME]` <br> e.g.,`help n/delete`                                                                                                                      |

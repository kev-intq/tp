package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.assessment.Assessment;
import seedu.address.model.assessment.AssessmentName;
import seedu.address.model.assessment.AssessmentResults;
import seedu.address.model.assessment.Score;
import seedu.address.model.assessment.StudentResult;
import seedu.address.model.assessment.UniqueAssessmentList;
import seedu.address.model.assessment.exceptions.StudentResultNotFound;
import seedu.address.model.person.Name;
import seedu.address.model.person.NusNetId;
import seedu.address.model.person.Person;
import seedu.address.model.person.Student;
import seedu.address.model.person.UniqueFilteredPersonsList;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.tutorial.Tutorial;
import seedu.address.model.tutorial.TutorialName;
import seedu.address.model.tutorial.UniqueTutorialList;

/**
 * Wraps all data at the address-book level (persons, tutorials, assessments)
 * Duplicates are not allowed (by .isSamePerson comparison)
 * TODO: add methods for tutorial and assessment list which will be used by the ModelManager
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniqueTutorialList tutorials = new UniqueTutorialList();
    private final UniqueAssessmentList assessments = new UniqueAssessmentList();
    private final UniqueFilteredPersonsList filteredPersons = new UniqueFilteredPersonsList();

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Replaces the contents of the tutorial list with {@code tutorials},
     * which does not contain duplicate tutorials.
     */
    public void setTutorials(List<Tutorial> tutorials) {
        this.tutorials.setTutorials(tutorials);
    }

    /**
     * Replaces the contents of the assessment list with {@code assessments},
     * which does not contain duplicate assessments.
     */
    public void setAssessments(List<Assessment> assessments) {
        this.assessments.setAssessments(assessments);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setTutorials(newData.getTutorialList());
        setAssessments(newData.getAssessmentList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    /**
     * Returns true if a person with the same name as {@code name} exists in the address book.
     */
    public boolean hasPersonWithName(Name name) {
        requireNonNull(name);
        return persons.hasPersonWithName(name);
    }

    /**
     * Returns a person with the same name as {@code name}.
     * This function will only be called if passed the check that
     * a matching {@code Person} exists in the address book.
     */
    public Person getPersonWithName(Name name) {
        return persons.getPersonWithName(name);
    }

    //// assessment-level operations
    /**
     * Returns true if an assessment with the same identity as {@code assessment} exists in the address book.
     */
    public boolean hasAssessment(Assessment assessment) {
        return assessments.contains(assessment);
    }

    /**
     * Adds the given assessment and corresponding AssessmentResults to every tutorial in the address book.
     * @param toAdd must not already exist in the address book.
     */
    public void addAssessment(Assessment toAdd) {
        assessments.add(toAdd);
        tutorials.addAssessment(toAdd);
    }

    /**
     * Returns true if an assessment with the given name exists in the address book.
     */
    public boolean hasAssessmentWithName(AssessmentName name) {
        return assessments.containsByName(name);
    }

    public Assessment getAssessmentWithName(AssessmentName name) {
        return assessments.getByName(name);
    }

    /**
     * Removes the assessment with the given name and corresponding AssessmentResults from every tutorial
     * in the address book.
     */
    public Assessment removeAssessmentWithName(AssessmentName name) {
        Assessment toRemove = assessments.removeByName(name);
        tutorials.removeAssessmentByName(name);
        return toRemove;
    }

    // result-level operations

    /**
     * Returns true if the address book contains the results of a student called {@code studentName}
     * for an assessment called {@code assessmentName}.
     */
    public boolean hasStudentResult(Name studentName, AssessmentName assessmentName) {
        requireAllNonNull(studentName, assessmentName);
        NusNetId studentId = persons.getIdOfStudent(studentName);
        TutorialName tutorialName = persons.getTutorialNameOfStudent(studentName);
        Tutorial tut = getTutorialWithName(tutorialName);
        return tut.hasStudentResult(assessmentName, studentId);
    }

    /**
     * Adds a result with value {@code score} for the student called {@code studentName} in the assessment
     * called {@code assessmentName}.
     */
    public void addStudentResult(Name studentName, AssessmentName assessmentName, Score score) {
        requireAllNonNull(studentName, assessmentName, score);
        NusNetId studentId = getIdOfStudent(studentName);
        TutorialName tutorialName = getTutorialNameOfStudent(studentName);
        Tutorial tut = getTutorialWithName(tutorialName);
        StudentResult result = new StudentResult(studentId, score);
        tut.addStudentResult(assessmentName, result);
    }

    /**
     * Sets the result for the student called {@code studentName} in the assessment with {@code assessmentName}
     * to {@code score}.
     * The StudentResult for {@code studentName} should already exist in the address book.
     */
    public void setStudentResult(Name studentName, AssessmentName assessmentName, Score score) {
        requireAllNonNull(studentName, assessmentName, score);
        if (!hasStudentResult(studentName, assessmentName)) {
            throw new StudentResultNotFound();
        }
        NusNetId studentId = getIdOfStudent(studentName);
        TutorialName tutorialName = getTutorialNameOfStudent(studentName);
        Tutorial tut = getTutorialWithName(tutorialName);
        tut.setStudentResult(assessmentName, studentId, score);
    }

    public AssessmentResults getAssessmentResults(TutorialName tutName, AssessmentName assessmentName) {
        requireAllNonNull(tutName, assessmentName);
        Tutorial tut = getTutorialWithName(tutName);
        return tut.getAssessmentResult(assessmentName);
    }

    //// util methods

    /**
     * Returns true if a tutorial with the same identity as {@code tutorial} exists in the address book.
     */
    public boolean hasTutorial(Tutorial tutorial) {
        requireNonNull(tutorial);
        return tutorials.contains(tutorial);
    }

    /**
     * Returns true if a tutorial with the name {@code tutorialName} exists.
     */
    public boolean hasTutorialWithName(TutorialName tutorialName) {
        requireNonNull(tutorialName);
        return tutorials.containsTutorialWithName(tutorialName);
    }

    /**
     * Returns a {@code Tutorial} with the name {@code tutorialName}. Used together with
     * {@see hasTutorialWithName} to check if the tutorial exists.
     */
    public Tutorial getTutorialWithName(TutorialName tutorialName) {
        return tutorials.getTutorialWithName(tutorialName);
    }

    /**
     * Adds a tutorial to the address book.
     * The tutorial must not already exist in the address book.
     */
    public void addTutorial(Tutorial t) {
        tutorials.add(t);
    }

    /**
     * Replaces the given tutorial {@code target} in the list with {@code editedTutorial}.
     * {@code target} must exist in the address book.
     * The tutorial identity of {@code editedTutorial} must not be the same as another existing
     * tutorial in the address book.
     */
    public void setTutorial(Tutorial target, Tutorial editedTutorial) {
        requireNonNull(editedTutorial);

        tutorials.setTutorial(target, editedTutorial);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeTutorial(Tutorial key) {
        tutorials.remove(key);
    }

    @Override
    public String toString() {
        return persons.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Tutorial> getTutorialList() {
        return tutorials.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Assessment> getAssessmentList() {
        return assessments.asUnmodifiableObservableList();
    }

    public UniqueAssessmentList getUniqueAssessmentList() {
        return assessments;
    }

    @Override
    public ObservableList<Person> getFilteredPersonsList() {
        return filteredPersons.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && persons.equals(((AddressBook) other).persons));
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }

    ///student-level methods

    /**
     *  Returns true if a student with the same identity as {@code student}
     *  exists in the tutorial with the same tutorial name as {@code tutorialName}.
     */
    public boolean hasStudent(Student student) {
        requireNonNull(student);
        TutorialName tutorialName = student.getTutorialName();
        return tutorials.containsTutorialWithName(tutorialName)
                && tutorials.getTutorialWithName(tutorialName).contains(student);
    }

    /**
     * Adds a student to the address book in a specified tutorial with the
     * tutorial name {@code tutorialName}.
     * The student must exist in the address book as a person.
     * The student must not exist in the tutorial.
     */
    public void addStudent(Student student) {
        requireNonNull(student);
        Person personMatch = persons.getPersonWithName(student.getName());
        persons.setPerson(personMatch, student);
        getTutorialWithName(student.getTutorialName()).generateAttendance();
    }

    /**
     * Removes a {@code student} from the address book
     */
    public void removeStudent(Student student) {
        requireNonNull(student);
        Person toReplaceStudent = new Person(student.getName(), student.getPhone(), student.getEmail(),
                student.getAddress(), student.getTags());
        persons.setPerson(student, toReplaceStudent);
        tutorials.getTutorialWithName(student.getTutorialName()).generateAttendance();
    }

    /// filteredPersons-level methods
    /**
     * Replaces the contents of the filtered person list with {@code filteredPersons}.
     * {@code filteredPersons} must not contain duplicate persons.
     */
    public void setFilteredPersons(List<Person> filteredPersons) {
        this.filteredPersons.setPersons(filteredPersons);
    }

    /**
     * Returns the student with the same student id as {@code id}
     */
    public Student getStudentWithId(NusNetId id) {
        return persons.getStudentWithId(id);
    }

    /**
     * Returns true if a student with the same student ID as {@code id}
     * exists in the tutorial with the same tutorial name as {@code tutorialName}.
     */
    public boolean tutorialHasStudentWithId(NusNetId id, TutorialName tutorialName) {
        return tutorials.tutorialHasStudentWithId(id, tutorialName);
    }

    public NusNetId getIdOfStudent(Name studentName) {
        requireNonNull(studentName);
        return persons.getIdOfStudent(studentName);
    }

    public TutorialName getTutorialNameOfStudent(Name studentName) {
        requireNonNull(studentName);
        return persons.getTutorialNameOfStudent(studentName);
    }

    /**
     * Marks attendance for the specific week for all students in the specified tutorial
     */
    public void markAttendanceForClass(Tutorial tutorial, int week) {
        requireAllNonNull(tutorial, week);
        tutorial.markAllAttendance(week);
    }

    /**
     * Marks attendance for the specific week for the specified student in the specified tutorial
     */
    public void markAttendanceForStudent(Tutorial tutorial, NusNetId studentId, int week) {
        requireAllNonNull(tutorial, studentId, week);
        tutorial.markStudentAttendance(studentId, week);
    }

    /**
     * Unmarks attendance for the specific week for all students in the specified tutorial
     */
    public void unmarkAttendanceForClass(Tutorial tutorial, int week) {
        requireAllNonNull(tutorial, week);
        tutorial.unmarkAllAttendance(week);
    }

    /**
     * Unmarks attendance for the specific week for the specified student in the specified tutorial
     */
    public void unmarkAttendanceForStudent(Tutorial tutorial, NusNetId studentId, int week) {
        requireAllNonNull(tutorial, studentId, week);
        tutorial.unmarkStudentAttendance(studentId, week);
    }
}

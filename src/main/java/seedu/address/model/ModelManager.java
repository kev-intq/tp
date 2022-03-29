package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.assessment.Assessment;
import seedu.address.model.assessment.AssessmentName;
import seedu.address.model.assessment.AssessmentResults;
import seedu.address.model.assessment.Score;
import seedu.address.model.assessment.StudentResult;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.person.Name;
import seedu.address.model.person.NusNetId;
import seedu.address.model.person.Person;
import seedu.address.model.person.Student;
import seedu.address.model.tutorial.Tutorial;
import seedu.address.model.tutorial.TutorialName;

/**
 * Represents the in-memory model of the address book data.
 * TODO: add methods for AddressBook to support command logic
 * The AddressBook contains the tutorial list and assessment list.
 * e.g. TutorialManager to provide higher-level methods.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Tutorial> filteredTutorials;
    private final FilteredList<Assessment> filteredAssessments;
    private final FilteredList<Person> filteredPersonsByMultiplePredicate;

    private ObservableList<Attendance> displayAttendanceList;
    private ObservableList<StudentResult> displayAssessmentResults;

    /**
     * A list containing all Students in the address book.
     * DO NOT MODIFY (as it is the list that {@link seedu.address.model.person.UniqueStudentsInTutorialList}
     * listens to).
     */
    private final FilteredList<Person> allStudents;
    /**
     * Used for the GUI display.
     * Can modify if needed.
     */
    private final FilteredList<Person> filteredStudents;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredTutorials = new FilteredList<>(this.addressBook.getTutorialList());
        filteredAssessments = new FilteredList<>(this.addressBook.getAssessmentList());
        filteredPersonsByMultiplePredicate = new FilteredList<>(this.addressBook.getFilteredPersonsList());

        allStudents = new FilteredList<>(this.addressBook.getPersonList(), PREDICATE_SHOW_ALL_STUDENTS);
        filteredStudents = new FilteredList<>(allStudents);

    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public boolean hasPersonWithName(Name name) {
        requireNonNull(name);
        return addressBook.hasPersonWithName(name);
    }

    @Override
    public Person getPersonWithName(Name name) {
        requireNonNull(name);
        return addressBook.getPersonWithName(name);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
    }

    //=========== Assessments =============================================================
    /**
     * Returns an unmodifiable view of the list of {@code Assessment} backed by the internal list of
     * {@code AddressBook}
     */
    @Override
    public ObservableList<Assessment> getAssessmentList() {
        return addressBook.getAssessmentList();
    }

    @Override
    public ObservableList<Assessment> getFilteredAssessmentList() {
        return filteredAssessments;
    }

    @Override
    public void updateFilteredAssessmentList(Predicate<Assessment> predicate) {
        requireNonNull(predicate);
        filteredAssessments.setPredicate(predicate);
    }

    @Override
    public boolean hasAssessment(Assessment assessment) {
        requireNonNull(assessment);
        return addressBook.hasAssessment(assessment);
    }

    @Override
    public void addAssessment(Assessment toAdd) {
        requireNonNull(toAdd);
        addressBook.addAssessment(toAdd);
    }

    @Override
    public boolean hasAssessmentWithName(AssessmentName name) {
        requireNonNull(name);
        return addressBook.hasAssessmentWithName(name);
    }

    @Override
    public Assessment getAssessmentWithName(AssessmentName name) {
        requireNonNull(name);
        return addressBook.getAssessmentWithName(name);
    }

    @Override
    public Assessment removeAssessmentWithName(AssessmentName name) {
        requireNonNull(name);
        return addressBook.removeAssessmentWithName(name);
    }

    //=========== Assessment Results =============================================================
    @Override
    public ObservableList<StudentResult> getDisplayAssessmentResults() {
        requireNonNull(displayAssessmentResults);
        return displayAssessmentResults;
    }

    @Override
    public boolean hasStudentResult(Name studentName, AssessmentName assessmentName) {
        requireAllNonNull(studentName, assessmentName);
        return addressBook.hasStudentResult(studentName, assessmentName);
    }

    @Override
    public void addStudentResult(Name studentName, AssessmentName assessmentName, Score score) {
        requireAllNonNull(studentName, assessmentName, score);
        addressBook.addStudentResult(studentName, assessmentName, score);
    }

    @Override
    public void setStudentResult(Name studentName, AssessmentName assessmentName, Score sc) {
        requireAllNonNull(studentName, assessmentName, sc);
        addressBook.setStudentResult(studentName, assessmentName, sc);
    }

    @Override
    public void markAttendanceForClass(Tutorial tutorial, int week) {
        requireAllNonNull(tutorial, week);
        addressBook.markAttendanceForClass(tutorial, week);
    }

    @Override
    public void markAttendanceForStudent(Tutorial tutorial, NusNetId studentId, int week) {
        requireAllNonNull(tutorial, studentId, week);
        addressBook.markAttendanceForStudent(tutorial, studentId, week);
    }

    @Override
    public void updateFilteredAttendanceList(Tutorial tutorial, NusNetId studentId) {
        requireAllNonNull(tutorial);
        ObservableList<Attendance> attendanceList = FXCollections.observableArrayList();
        if (studentId != null) {
            attendanceList.setAll(tutorial.getAttendanceList().getAttendancesByStudentID(studentId));
        } else {
            attendanceList.setAll(tutorial.getAttendanceList().getAttendances());
        }
        displayAttendanceList = attendanceList;
    }

    @Override
    public ObservableList<Attendance> getFilteredAttendanceList() {
        return displayAttendanceList;
    }

    @Override
    public void updateDisplayAssessmentResults(TutorialName tutName, AssessmentName assessmentName) {
        requireAllNonNull(tutName, assessmentName);
        AssessmentResults assessmentResults = addressBook.getAssessmentResults(tutName, assessmentName);
        displayAssessmentResults = assessmentResults.asUnmodifiableStudentResultsList();
    }

    //=========== Tutorials =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Tutorial} backed by the internal list of
     * {@code AddressBook}
     */
    @Override
    public ObservableList<Tutorial> getFilteredTutorialList() {
        return filteredTutorials;
    }

    @Override
    public boolean hasTutorial(Tutorial tutorial) {
        requireNonNull(tutorial);
        return addressBook.hasTutorial(tutorial);
    }

    @Override
    public boolean hasTutorialWithName(TutorialName tutorialName) {
        requireNonNull(tutorialName);
        return addressBook.hasTutorialWithName(tutorialName);
    }

    @Override
    public Tutorial getTutorialWithName(TutorialName tutorialName) {
        requireNonNull(tutorialName);
        return addressBook.getTutorialWithName(tutorialName);
    }

    @Override
    public void deleteTutorial(Tutorial target) {
        addressBook.removeTutorial(target);
    }

    @Override
    public void addTutorial(Tutorial tutorial) {
        addressBook.addTutorial(tutorial);
        updateFilteredTutorialList(PREDICATE_SHOW_ALL_TUTORIALS);
    }

    @Override
    public void setTutorial(Tutorial target, Tutorial editedTutorial) {
        requireAllNonNull(target, editedTutorial);

        addressBook.setTutorial(target, editedTutorial);
    }

    @Override
    public void updateFilteredTutorialList(Predicate<Tutorial> predicate) {
        requireNonNull(predicate);
        filteredTutorials.setPredicate(predicate);
    }

    //=========== Students =============================================================

    @Override
    public boolean hasStudentWithName(Name studentName) {
        requireNonNull(studentName);
        return allStudents.stream().anyMatch(x -> x.getName().equals(studentName));
    }

    @Override
    public boolean hasStudentWithId(NusNetId studentId) {
        requireNonNull(studentId);
        return allStudents.stream().anyMatch(x -> ((Student) x).getStudentId().equals(studentId));
    }

    @Override
    public Student getStudentWithId(NusNetId id) {
        requireNonNull(id);
        return addressBook.getStudentWithId(id);
    }

    @Override
    public boolean tutorialHasStudentWithId(NusNetId id, TutorialName tutorialName) {
        return addressBook.tutorialHasStudentWithId(id, tutorialName);
    }

    @Override
    public void addStudent(Student student) {
        requireNonNull(student);
        addressBook.addStudent(student);
    }

    @Override
    public FilteredList<Person> getAllStudentsList() {
        return allStudents;
    }

    @Override
    public FilteredList<Person> getFilteredStudentList() {
        return filteredStudents;
    }

    @Override
    public void updateFilteredStudentList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredStudents.setPredicate(predicate);
    }

    /**
     * Removes a {@code student} from the address book
     */
    public void removeStudent(Student student) {
        requireNonNull(student);
        addressBook.removeStudent(student);
    }

    @Override
    public TutorialName getTutorialNameOfStudent(Name studentName) {
        requireNonNull(studentName);
        return addressBook.getTutorialNameOfStudent(studentName);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code AddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return addressBook.equals(other.addressBook)
                && userPrefs.equals(other.userPrefs)
                && filteredPersons.equals(other.filteredPersons);
    }

    //=========== Filtered Person Multiple Predicate List Accessors ==================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code AddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonsMultiPredList() {
        return filteredPersonsByMultiplePredicate;
    }

    @Override
    public void setFilteredPersonsMultiPredList(List<Person> persons) {
        requireNonNull(persons);
        addressBook.setFilteredPersons(persons);
    }


}


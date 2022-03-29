package seedu.address.model.tutorial;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.model.DisplayType;
import seedu.address.model.Displayable;
import seedu.address.model.assessment.Assessment;
import seedu.address.model.assessment.AssessmentName;
import seedu.address.model.assessment.AssessmentResults;
import seedu.address.model.assessment.AssessmentResultsList;
import seedu.address.model.assessment.Score;
import seedu.address.model.assessment.StudentResult;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.attendance.AttendanceList;
import seedu.address.model.person.NusNetId;
import seedu.address.model.person.Person;
import seedu.address.model.person.Student;
import seedu.address.model.person.UniqueStudentsInTutorialList;
import seedu.address.model.person.exceptions.StudentNotFoundException;

/**
 * Represents a Class (for a module) in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Tutorial implements Displayable {
    // Identity fields
    private static final DisplayType displayType = DisplayType.CLASS;

    private final TutorialName tutorialName;
    private final Day day;
    private final Time time;
    private final Venue venue;
    private final int weeks;

    // Data fields
    private UniqueStudentsInTutorialList studentsList;
    private final AssessmentResultsList assessmentResultsList;
    private final AttendanceList attendanceList;

    /**
     * Every field must be present and not null.
     *
     * @param name the tutorial's name.
     * @param v the venue the tutorial is hosted at.
     * @param d the day of the week the tutorial falls on.
     * @param t the time the tutorial starts.
     * @param weeks the amount of weeks the tutorial is held for.
     */
    public Tutorial(TutorialName name, Venue v, Day d, Time t, int weeks) {
        requireAllNonNull(name, d, t);
        tutorialName = name;
        venue = v;
        day = d;
        time = t;
        this.weeks = weeks;
        assessmentResultsList = new AssessmentResultsList(name);
        attendanceList = new AttendanceList(new ArrayList<Attendance>(), weeks);
    }

    /**
     * Every field must be present and not null.
     *
     * @param name the tutorial's name.
     * @param v the venue the tutorial is hosted at.
     * @param d the day of the week the tutorial falls on.
     * @param t the time the tutorial starts.
     * @param weeks the amount of weeks the tutorial is held for.
     * @param allStudents the allStudents list in the ModelManager.
     */
    public Tutorial(TutorialName name, Venue v, Day d, Time t, int weeks, FilteredList<Person> allStudents) {
        requireAllNonNull(name, d, t);
        tutorialName = name;
        venue = v;
        day = d;
        time = t;
        this.weeks = weeks;
        studentsList = new UniqueStudentsInTutorialList(allStudents, name);
        assessmentResultsList = new AssessmentResultsList(name);
        attendanceList = new AttendanceList(new ArrayList<Attendance>(), weeks);
    }

    /**
     * Constructor for Tutorial, used for loading from storage.
     */
    public Tutorial(TutorialName name, Venue v, Day d, Time t, int weeks, AttendanceList attendance,
                    AssessmentResultsList results) {
        requireAllNonNull(name, d, t, results);
        tutorialName = name;
        venue = v;
        day = d;
        time = t;
        this.weeks = weeks;
        attendanceList = attendance;
        assessmentResultsList = results;
    }

    public TutorialName getTutorialName() {
        return tutorialName;
    }

    public Day getDay() {
        return day;
    }

    public Time getTime() {
        return time;
    }

    public Venue getVenue() {
        return venue;
    }

    public int getWeeks() {
        return weeks;
    }

    public AttendanceList getAttendanceList() {
        return attendanceList;
    }

    public void setStudentsList(FilteredList<Person> allStudents) {
        studentsList = new UniqueStudentsInTutorialList(allStudents, tutorialName);
    }

    public List<AssessmentResults> getUnmodifiableAssessmentResultsList() {
        return assessmentResultsList.asUnmodifiableList();
    }

    public void setAssessmentResultsList(ObservableList<Assessment> assessmentsList) {
        for (Assessment assessment : assessmentsList) {
            assessmentResultsList.add(
                    new AssessmentResults(
                            assessment.getAssessmentName()));
        }
    }

    /**
     * Returns true if given object is a Tutorial.
     */
    public static boolean isTutorial(Object object) {
        return object instanceof Tutorial;
    }

    /**
     * Adds an AssessmentResults to assessmentResultsList.
     */
    public void addAssessmentResults(AssessmentResults results) {
        requireNonNull(results);
        assessmentResultsList.add(results);
    }

    public AssessmentResults getAssessmentResult(AssessmentName assessmentName) {
        requireNonNull(assessmentName);
        return assessmentResultsList.getAssessmentResultsByName(assessmentName);
    }

    /**
     * Removes the AssessmentResults with the given name.
     */
    public void removeAssessmentResultsByName(AssessmentName name) {
        requireNonNull(name);
        assessmentResultsList.removeAssessmentResultsByName(name);
    }

    /**
     * Returns true if both tutorials have the same name.
     * This defines a weaker notion of equality between two tutorials (used for adding and editing tutorials).
     */
    public boolean isSameTutorial(Tutorial other) {
        if (this == other) {
            return true;
        }
        return other != null && other.getTutorialName().equals(getTutorialName());
    }

    /**
     * Returns true if tutorial matches given TutorialName.
     */
    public boolean isSameTutorialName(TutorialName other) {
        return other != null && other.equals(getTutorialName());
    }

    /**
     * Returns true if the studentslist contains an equivalent student as the argument,
     * uses {@code UniqueStudentsInTutorialList#containsStudent(Student)}
     */
    public boolean contains(Student student) {
        return studentsList.containsStudent(student);
    }

    @Override
    public DisplayType getDisplayType() {
        return displayType;
    }

    /**
     * Returns true if the studentsList contains a student with a matching {@code studentId}.
     */
    public boolean containsStudentWithId(NusNetId studentId) {
        return studentsList.containsStudentWithId(studentId);
    }

    public Student getStudentWithId(NusNetId studentId) {
        return studentsList.getStudentWithId(studentId);
    }

    /**
     * Returns true if the tutorial already contains the result of the student with {@code studentId}
     * for the assessment with {@code assessmentName}.
     */
    public boolean hasStudentResult(AssessmentName assessmentName, NusNetId studentId) {
        requireAllNonNull(assessmentName, studentId);
        if (!containsStudentWithId(studentId)) {
            throw new StudentNotFoundException();
        }
        return assessmentResultsList.hasStudentResult(assessmentName, studentId);
    }

    /**
     * Adds the {@code result} to the AssessmentResults with {@code assessmentName} in the assessmentResultsList.
     */
    public void addStudentResult(AssessmentName assessmentName, StudentResult result) {
        requireAllNonNull(assessmentName, result);
        assessmentResultsList.addStudentResult(assessmentName, result);
    }

    /**
     * Sets the result of the student with {@code studentId} for the assessment with {@code assessmentName} to
     * {@code score}.
     */
    public void setStudentResult(AssessmentName assessmentName, NusNetId studentId, Score score) {
        requireAllNonNull(assessmentName, studentId, score);
        assessmentResultsList.setStudentResult(assessmentName, studentId, score);
    }

    /**
     * Generates attendance records for students who do not have any records present in the list.
     */
    public void generateAttendance() {
        attendanceList.generateAttendance(studentsList);
    }

    /**
     * Marks the attendance for the all students.
     *
     * @param week the week that the attendance should be marked for the student.
     */
    public void markAllAttendance(int week) {
        generateAttendance();
        attendanceList.markAllAttendance(week);
    }

    /**
     * Marks the attendance for the specified student.
     *
     * @param studentId the NusNetId of a student.
     * @param week the week that the attendance should be marked for the student.
     */
    public void markStudentAttendance(NusNetId studentId, int week) {
        generateAttendance();
        attendanceList.markAttendanceForStudent(studentId, week);
    }

    /**
     * Unmarks the attendance for the all students.
     *
     * @param week the week that the attendance should be unmarked for the student.
     */
    public void unmarkAllAttendance(int week) {
        generateAttendance();
        attendanceList.unmarkAllAttendance(week);
    }

    /**
     * Unmarks the attendance for the specified student.
     *
     * @param studentId the NusNetId of a student.
     * @param week the week that the attendance should be unmarked for the student.
     */
    public void unmarkStudentAttendance(NusNetId studentId, int week) {
        generateAttendance();
        attendanceList.unmarkAttendanceForStudent(studentId, week);
    }

    /**
     * Returns true if both tutorials have the same identity and data fields.
     * This defines a stronger notion of equality between two tutorials (used for deleting tutorials).
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Tutorial)) {
            return false;
        }

        Tutorial otherTut = (Tutorial) other;
        return otherTut.getTutorialName().equals(getTutorialName())
                && otherTut.getDay().equals(getDay())
                && otherTut.getTime().equals(getTime())
                && otherTut.getVenue().equals(getVenue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(tutorialName, day, time);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTutorialName())
                .append("; Venue: ")
                .append(getVenue())
                .append("; Day: ")
                .append(getDay())
                .append("; Time: ")
                .append(getTime());
        return builder.toString();
    }

}

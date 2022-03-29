package seedu.address.model.attendance;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;

import javafx.collections.transformation.FilteredList;
import seedu.address.model.person.NusNetId;
import seedu.address.model.person.Person;
import seedu.address.model.person.Student;
import seedu.address.model.person.UniqueStudentsInTutorialList;

/**
 * Contains a list of all students attendance lists.
 * The students are in the same Tutorial.
 */
public class AttendanceList {
    private final ArrayList<Attendance> attendances;
    private final int weeks;

    /**
     * Constructs an AttendanceList.
     *
     * @param attendances the list of attendance records.
     * @param weeks the number of weeks to for attendance to be recorded for.
     */
    public AttendanceList(ArrayList<Attendance> attendances, int weeks) {
        requireAllNonNull(attendances);
        this.attendances = attendances;
        this.weeks = weeks;
    }

    public int getWeeks() {
        return weeks;
    }

    public ArrayList<Attendance> getAttendances() {
        return attendances;
    }

    /**
     * Generates attendance records for students who do not have any records present in the list.
     *
     * @param uniqueStudentList the list of students in the tutorial.
     */
    public void generateAttendance(UniqueStudentsInTutorialList uniqueStudentList) {
        FilteredList<Person> studentList = uniqueStudentList.getStudentsInClass();
        for (Person p : studentList) {
            Student student = (Student) p;
            NusNetId studentId = student.getStudentId();
            boolean hasAttendencePresent = false;


            for (Attendance attendance : attendances) {
                if (attendance.getStudentId().equals(studentId)) {
                    hasAttendencePresent = true;
                    break;
                }
            }

            if (!hasAttendencePresent) {
                ArrayList<Integer> studentAttendance = new ArrayList<>();
                for (int i = 0; i < weeks; i++) {
                    studentAttendance.add(0);
                }
                attendances.add(
                        new Attendance(
                                studentAttendance,
                                studentId,
                                new Comment("")));
            }
        }
    }

    /**
     * Returns the attendance of the specified student for the semester.
     *
     * @param studentId the NusNetId of a student.
     * @return the attendance list of the student for the semester.
     */
    public ArrayList<Attendance> getAttendancesByStudentID(NusNetId studentId) {
        Attendance attendanceToAdd = null;
        ArrayList<Attendance> attendanceList = new ArrayList<>();
        for (Attendance attendance : attendances) {
            if (attendance.getStudentId().equals(studentId)) {
                attendanceToAdd = attendance;
                break;
            }
        }
        for (int i = 0; i < weeks; i++) {
            attendanceList.add(attendanceToAdd);
        }
        return attendanceList;
    }

    /**
     * Marks the attendance for the all students.
     *
     * @param week the week that the attendance should be marked for the student.
     */
    public void markAllAttendance(int week) {
        for (Attendance attendance : attendances) {
            attendance.markWeek(week);
        }
    }

    /**
     * Marks the attendance for the specified student.
     *
     * @param studentId the NusNetId of a student.
     * @param week the week that the attendance should be marked for the student.
     */
    public void markAttendanceForStudent(NusNetId studentId, int week) {
        for (Attendance attendance : attendances) {
            if (attendance.getStudentId().equals(studentId)) {
                attendance.markWeek(week);
                break;
            }
        }
    }
}

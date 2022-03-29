package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_TUTORIAL_NOT_FOUND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENTID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIALNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEEK;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.NusNetId;
import seedu.address.model.tutorial.Tutorial;
import seedu.address.model.tutorial.TutorialName;

public class UnmarkAttendanceCommand extends Command {
    public static final String COMMAND_WORD = "unmark_attendance";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Unmarks attendances for a class or specific person. "
            + "Parameters: "
            + PREFIX_TUTORIALNAME + "TUTORIAL NAME "
            + PREFIX_STUDENTID + "STUDENT ID "
            + PREFIX_WEEK + "WEEK "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TUTORIALNAME + "T04 "
            + PREFIX_STUDENTID + "e01234567 "
            + PREFIX_WEEK + "7 ";

    public static final String MESSAGE_MULTIPLE_SUCCESS = "Attendance unmarked for all students on week %s";
    public static final String MESSAGE_SUCCESS = "Attendance unmarked for student %s on week %s";
    public static final String MESSAGE_STUDENT_NOT_IN_CLASS = "The specified student does not exist in this tutorial.";

    private final boolean isUnmarkMultipleAttendances;
    private final TutorialName tutorialToMark;
    private final NusNetId studentToMark;
    private final int week;

    /**
     * Creates an UnmarkAttendanceCommand to unmark attendance the specified {@code Tutorial}
     */
    public UnmarkAttendanceCommand(TutorialName tutorial, NusNetId studentId, int week,
                                   boolean isUnmarkMultipleAttendances) {
        this.isUnmarkMultipleAttendances = isUnmarkMultipleAttendances;
        tutorialToMark = tutorial;
        studentToMark = studentId;
        this.week = week;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasTutorialWithName(tutorialToMark)) {
            throw new CommandException(String.format(MESSAGE_TUTORIAL_NOT_FOUND, tutorialToMark));
        }

        Tutorial tutorial = model.getTutorialWithName(tutorialToMark);

        if (!isUnmarkMultipleAttendances) {
            if (!tutorial.containsStudentWithId(studentToMark)) {
                throw new CommandException(MESSAGE_STUDENT_NOT_IN_CLASS);
            }
            model.unmarkAttendanceForStudent(tutorial, studentToMark, week);
            return new CommandResult(String.format(MESSAGE_SUCCESS, studentToMark, week));
        } else {
            model.unmarkAttendanceForClass(tutorial, week);
            return new CommandResult(String.format(MESSAGE_MULTIPLE_SUCCESS, week));
        }
    }
}

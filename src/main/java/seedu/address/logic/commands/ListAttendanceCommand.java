package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_TUTORIAL_NOT_FOUND;
import static seedu.address.logic.commands.GradeCommand.MESSAGE_STUDENT_NOT_FOUND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENTID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIALNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEEK;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.NusNetId;
import seedu.address.model.person.Student;
import seedu.address.model.tutorial.Tutorial;
import seedu.address.model.tutorial.TutorialName;

public class ListAttendanceCommand extends Command {

    public static final String COMMAND_WORD = "list_attendance";

    public static final String MESSAGE_SUCCESS = "Listed attendance for class %s on week %s";

    public static final String MESSAGE_SUCCESS_STUDENT = "Listed attendance for student %s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists attendance for a specified class and week.\n"
            + "Parameters: [" + PREFIX_TUTORIALNAME + "TUTORIAL NAME] "
            + PREFIX_STUDENTID + "STUDENT ID] " + PREFIX_WEEK + "WEEK "
            + "Example: " + COMMAND_WORD + " " + PREFIX_TUTORIALNAME + "T04 " + PREFIX_WEEK + "1 ";

    private final TutorialName tutorialName;
    private final NusNetId studentId;
    private final int week;

    /**
     * Constructor for a ListAttendanceCommand.
     * @param tutorialName The name of the tutorial containing the specified attendance list.
     * @param week The week of the attendance list requested.
     */
    public ListAttendanceCommand(TutorialName tutorialName, NusNetId studentId, int week) {
        this.tutorialName = tutorialName;
        this.studentId = studentId;
        this.week = week;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (studentId != null) {
            return listByStudentId(model);
        }
        return listByTutorialName(model);
    }

    private CommandResult listByTutorialName(Model model) throws CommandException {
        Tutorial tutorialToList = null;
        for (Tutorial tutorial : model.getAddressBook().getTutorialList()) {
            if (tutorial.getTutorialName().equals(tutorialName)) {
                tutorialToList = tutorial;
                break;
            }
        }

        if (tutorialToList == null) {
            throw new CommandException(MESSAGE_TUTORIAL_NOT_FOUND);
        } else {
            model.updateFilteredAttendanceList(tutorialToList, null);
            String successMessage = String.format(MESSAGE_SUCCESS, tutorialName, week);
            CommandResult commandResult = CommandResult.createAttendanceCommandResult(successMessage);
            commandResult.setAttendanceWeek(week);
            return commandResult;
        }
    }

    private CommandResult listByStudentId(Model model) throws CommandException {
        if (!model.hasStudentWithId(studentId)) {
            throw new CommandException(MESSAGE_STUDENT_NOT_FOUND);
        }

        Student studentToList = model.getStudentWithId(studentId);
        Tutorial tutorialToList = model.getTutorialWithName(studentToList.getTutorialName());

        model.updateFilteredAttendanceList(tutorialToList, studentId);
        String successMessage = String.format(MESSAGE_SUCCESS_STUDENT, studentId);
        CommandResult commandResult = CommandResult.createAttendanceByStudentCommandResult(successMessage);
        return commandResult;
    }

}

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.model.DisplayType;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** Display list of class instead of list of persons. */
    private final DisplayType displayType;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** Help for specific command word to be shown to the user in a separate window. */
    private String showHelpCommandWord;

    /** The application should exit. */
    private final boolean exit;

    /** The week of the attendance requested. */
    private int attendanceWeek;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit, DisplayType displayType) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.displayType = displayType;
    }

    /**
     * Constructs a {@code CommandResult} with the specified fields including a help command word inquiry.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit,
                         DisplayType displayType, String commandWordInquiry) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.displayType = displayType;
        this.showHelpCommandWord = commandWordInquiry;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, DisplayType.PERSON);
    }

    /**
     * Constructs and returns a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value for class related commands.
     *
     * @param feedbackToUser feedback given to user for the result displayed
     * @return the result of the command execution
     */
    public static CommandResult createClassCommandResult(String feedbackToUser) {
        return new CommandResult(feedbackToUser, false, false, DisplayType.CLASS);
    }

    /**
     * Constructs and returns a {@code CommandResult} with the specified {@code feedbackToUser}
     * and other fields set to their default value for student related commands.
     *
     * @param feedbackToUser feedback given to user for the result displayed
     * @return the result of the command execution
     */
    public static CommandResult createStudentCommandResult(String feedbackToUser) {
        return new CommandResult(feedbackToUser, false, false, DisplayType.STUDENT);
    }

    /**
     * Constructs and returns a {@code CommandResult} with the specified {@code feedbackToUser}
     * and other fields set to their default value for assessment related commands.
     */
    public static CommandResult createAssessmentCommandResult(String feedbackToUser) {
        return new CommandResult(feedbackToUser, false, false, DisplayType.ASSESSMENT);
    }

    /**
     * Constructs and returns a {@code CommandResult} with the specified {@code feedbackToUser}
     * and other fields set to their default value for score related commands.
     */
    public static CommandResult createScoreCommandResult(String feedbackToUser) {
        return new CommandResult(feedbackToUser, false, false, DisplayType.SCORE);
    }

    /**
     * Constructs and returns a {@code CommandResult} with the specified {@code feedbackToUser}
     * and other fields set to their default value for attendance related commands.
     */
    public static CommandResult createAttendanceCommandResult(String feedbackToUser) {
        return new CommandResult(feedbackToUser, false, false, DisplayType.ATTENDANCE);
    }

    /**
     * Constructs and returns a {@code CommandResult} with the specified {@code feedbackToUser}
     * and other fields set to their default value for attendance related commands.
     */
    public static CommandResult createAttendanceByStudentCommandResult(String feedbackToUser) {
        return new CommandResult(feedbackToUser, false, false, DisplayType.ATTENDANCE_BY_STUDENT);
    }

    public void setAttendanceWeek(int week) {
        attendanceWeek = week;
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public String getInquiry() {
        return showHelpCommandWord;
    }

    public int getAttendanceWeek() {
        return attendanceWeek;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isInquiry() {
        return !showHelpCommandWord.equals("DEFAULT");
    }

    public boolean isExit() {
        return exit;
    }

    public DisplayType getDisplayType() {
        return displayType;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && showHelp == otherCommandResult.showHelp
                && exit == otherCommandResult.exit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit);
    }

}

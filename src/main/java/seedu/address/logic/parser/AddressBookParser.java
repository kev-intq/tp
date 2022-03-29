package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddAssessmentCommand;
import seedu.address.logic.commands.AddClassCommand;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddStudentCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteAssessmentCommand;
import seedu.address.logic.commands.DeleteClassCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.GradeCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListAssessmentCommand;
import seedu.address.logic.commands.ListAttendanceCommand;
import seedu.address.logic.commands.ListClassCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListScoreCommand;
import seedu.address.logic.commands.ListStudentCommand;
import seedu.address.logic.commands.MarkAttendanceCommand;
import seedu.address.logic.commands.RemoveStudentCommand;
import seedu.address.logic.commands.UnmarkAttendanceCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListAssessmentCommand.COMMAND_WORD:
            return new ListAssessmentCommand();

        case ListStudentCommand.COMMAND_WORD:
            return new ListStudentCommandParser().parse(arguments);

        case ListClassCommand.COMMAND_WORD:
            return new ListClassCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommandParser().parse(arguments);

        case AddAssessmentCommand.COMMAND_WORD:
            return new AddAssessmentCommandParser().parse(arguments);

        case DeleteAssessmentCommand.COMMAND_WORD:
            return new DeleteAssessmentCommandParser().parse(arguments);

        case AddClassCommand.COMMAND_WORD:
            return new AddClassCommandParser().parse(arguments);

        case DeleteClassCommand.COMMAND_WORD:
            return new DeleteClassCommandParser().parse(arguments);

        case AddStudentCommand.COMMAND_WORD:
            return new AddStudentCommandParser().parse(arguments);

        case RemoveStudentCommand.COMMAND_WORD:
            return new RemoveStudentCommandParser().parse(arguments);

        case GradeCommand.COMMAND_WORD:
            return new GradeCommandParser().parse(arguments);

        case ListScoreCommand.COMMAND_WORD:
            return new ListScoreCommandParser().parse(arguments);

        case ListAttendanceCommand.COMMAND_WORD:
            return new ListAttendanceCommandParser().parse(arguments);

        case MarkAttendanceCommand.COMMAND_WORD:
            return new MarkAttendanceCommandParser().parse(arguments);

        case UnmarkAttendanceCommand.COMMAND_WORD:
            return new UnmarkAttendanceCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}

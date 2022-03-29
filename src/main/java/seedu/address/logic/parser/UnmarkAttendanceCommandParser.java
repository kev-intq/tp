package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENTID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIALNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEEK;
import static seedu.address.logic.parser.ParserUtil.arePrefixesPresent;

import seedu.address.logic.commands.UnmarkAttendanceCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NusNetId;
import seedu.address.model.tutorial.TutorialName;

public class UnmarkAttendanceCommandParser implements Parser<UnmarkAttendanceCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MarkAttendanceCommand
     * and returns an MarkAttendanceCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UnmarkAttendanceCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TUTORIALNAME, PREFIX_STUDENTID, PREFIX_WEEK);

        if (!arePrefixesPresent(argMultimap, PREFIX_TUTORIALNAME, PREFIX_WEEK)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    UnmarkAttendanceCommand.MESSAGE_USAGE));
        }

        TutorialName tutorialName = ParserUtil.parseTutorialName(argMultimap.getValue(PREFIX_TUTORIALNAME).get());
        NusNetId studentId;
        boolean isStudentIdPresent = false;
        if (arePrefixesPresent(argMultimap, PREFIX_STUDENTID)) {
            isStudentIdPresent = true;
            studentId = ParserUtil.parseStudentId(argMultimap.getValue(PREFIX_STUDENTID).get());
        } else {
            studentId = null;
        }
        int week = ParserUtil.parseWeek(argMultimap.getValue(PREFIX_WEEK).get());

        return new UnmarkAttendanceCommand(tutorialName, studentId, week, !isStudentIdPresent);
    }

}

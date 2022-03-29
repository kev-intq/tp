package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENTID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIALNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEEK;
import static seedu.address.logic.parser.ParserUtil.arePrefixesPresent;

import seedu.address.logic.commands.ListAttendanceCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NusNetId;
import seedu.address.model.tutorial.TutorialName;

public class ListAttendanceCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the ListAttendanceCommand
     * and returns an ListAttendanceCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListAttendanceCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TUTORIALNAME, PREFIX_WEEK, PREFIX_STUDENTID);
        if (arePrefixesPresent(argMultimap, PREFIX_TUTORIALNAME, PREFIX_WEEK) && argMultimap.getPreamble().isEmpty()) {
            TutorialName tutorialName = ParserUtil.parseTutorialName(argMultimap.getValue(PREFIX_TUTORIALNAME).get());
            int week = ParserUtil.parseWeek(argMultimap.getValue(PREFIX_WEEK).get());
            return new ListAttendanceCommand(tutorialName, null, week);
        }
        if (arePrefixesPresent(argMultimap, PREFIX_STUDENTID) && argMultimap.getPreamble().isEmpty()) {
            NusNetId studentId = ParserUtil.parseStudentId(argMultimap.getValue(PREFIX_STUDENTID).get());
            return new ListAttendanceCommand(null, studentId, -1);
        }
        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListAttendanceCommand.MESSAGE_USAGE));
    }

}

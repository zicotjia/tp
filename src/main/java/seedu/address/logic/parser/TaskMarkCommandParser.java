package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_NAME;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.TaskMarkCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.task.Name;
import seedu.address.model.task.Task;

/**
 * Parses input arguments and creates a new TaskMarkCommand object
 */
public class TaskMarkCommandParser implements Parser<TaskMarkCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the TaskMarkCommand
     * and returns a TaskMarkCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public TaskMarkCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TASK_NAME);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TaskMarkCommand.MESSAGE_USAGE), pe);
        }

        Name taskName = ParserUtil.parseTaskName(argMultimap.getValue(PREFIX_TASK_NAME).get());

        Task task = new Task(taskName);

        return new TaskMarkCommand(index, task);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}

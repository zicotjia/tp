package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAM_INDEX;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.task.Name;
import seedu.address.model.team.Team;

/**
 * Edit the name of the task.
 */
public class TaskEditCommand extends Command {

    public static final String COMMAND_WORD = "taskedit";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the name of the task of the specified team "
            + "by the index number used in the displayed team list.\n"
            + "Parameters: " + PREFIX_TEAM_INDEX + "TEAM-INDEX (must be a positive integer), "
            + PREFIX_TASK_INDEX + "TASK-INDEX (must be a positive integer) "
            + PREFIX_TASK_NAME + "NEW-TASK-NAME \n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_TEAM_INDEX + "1 "
            + PREFIX_TASK_INDEX + "3 " + PREFIX_TASK_NAME + "Design UI";
    public static final String MESSAGE_SUCCESS = "Task edited: %1$s";

    private final Index taskIndex;
    private final Index teamIndex;
    private final Name newName;

    /**
     * Creates a TaskEditCommand to edit the specified {@code Task}
     *
     * @param teamIndex of the team in the filtered team list to edit.
     * @param taskIndex index of the task to be edited.
     * @param newName name of the new task name.
     */
    public TaskEditCommand(Index teamIndex, Index taskIndex, Name newName) {
        requireNonNull(taskIndex);
        requireNonNull(teamIndex);
        requireNonNull(newName);

        this.teamIndex = teamIndex;
        this.taskIndex = taskIndex;
        this.newName = newName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Team> lastShownTeamList = model.getFilteredTeamList();

        if (teamIndex.getZeroBased() >= lastShownTeamList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TEAM_DISPLAYED_INDEX);
        }

        if (taskIndex.getZeroBased() >= lastShownTeamList.get(teamIndex.getZeroBased()).getTasks().getSize()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        model.editTask(teamIndex, taskIndex, newName);
        return new CommandResult(String.format(MESSAGE_SUCCESS, newName));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskEditCommand // instanceof handles nulls
                && taskIndex.equals(((TaskEditCommand) other).taskIndex)
                && teamIndex.equals(((TaskEditCommand) other).teamIndex)
                && newName.equals(((TaskEditCommand) other).newName));
    }
}

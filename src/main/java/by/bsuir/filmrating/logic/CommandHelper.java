package by.bsuir.filmrating.logic;

import java.util.HashMap;
import java.util.Map;
import by.bsuir.filmrating.logic.impl.*;

public final class CommandHelper {
    private static final CommandHelper instance = new CommandHelper();
    private Map<CommandName, ICommand> commands = new HashMap<>();
    public CommandHelper() {
        commands.put(CommandName.DO_LOGIN, new LoginCommand());
        commands.put(CommandName.DO_REGISTRATION, new RegistrationCommand());
        commands.put(CommandName.DO_LOGOUT, new LogoutCommand());
        commands.put(CommandName.ADD_FILM, new AddFilmCommand());
        commands.put(CommandName.ADD_REVIEW, new AddReviewCommand());
        commands.put(CommandName.CHANGE_LANGUAGE, new ChangeLanguageCommand());
        commands.put(CommandName.DELETE_REVIEW, new DeleteReviewCommand());
        commands.put(CommandName.EDIT_FILM, new EditFilmCommand());
        commands.put(CommandName.EDIT_USER, new EditUserCommand());
        commands.put(CommandName.GET_FILM, new GetFilmCommand());
        commands.put(CommandName.GET_FILMS, new GetFilmsCommand());
        commands.put(CommandName.GET_REVIEWS, new GetReviewsCommand());
        commands.put(CommandName.GET_USERS, new GetUsersCommand());
        commands.put(CommandName.NO_SUCH_COMMAND, new NoSuchCommand());
    }
    public static CommandHelper getInstance() {
        return instance;
    }
    public ICommand getCommand(String commandName){
        CommandName name = CommandName.valueOf(commandName.toUpperCase());
        ICommand command;
        if ( null != name){
            command = commands.get(name);
        } else{
            command = commands.get(CommandName.NO_SUCH_COMMAND);
        }
        return command;
    }
}


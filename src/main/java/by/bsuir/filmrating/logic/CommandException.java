package by.bsuir.filmrating.logic;

import by.bsuir.filmrating.exception.ProjectException;

public class CommandException extends ProjectException {
    private static final long serialVersionUID = 1L;
    public CommandException(String msg){
        super(msg);
    }
    public CommandException(String msg, Exception e){
        super(msg, e);
    }
}

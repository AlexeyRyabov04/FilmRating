package by.bsuir.filmrating.logic;

import jakarta.servlet.http.HttpServletRequest;

public interface ICommand {
    String execute(HttpServletRequest request) throws CommandException;
}

package by.bsuir.filmrating.logic.impl;

import by.bsuir.filmrating.logic.CommandException;
import by.bsuir.filmrating.logic.ICommand;
import jakarta.servlet.http.HttpServletRequest;

public class ChangeLanguageCommand implements ICommand {
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String newLang = request.getParameter("language");
        request.getSession().setAttribute("lang", newLang);
        return request.getHeader("Referer");
    }
}

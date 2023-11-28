package by.bsuir.filmrating.logic.impl;

import by.bsuir.filmrating.controller.JspPageName;
import by.bsuir.filmrating.logic.CommandException;
import by.bsuir.filmrating.logic.ICommand;
import jakarta.servlet.http.HttpServletRequest;

public class LogoutCommand implements ICommand {
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        request.getSession().invalidate();
        return JspPageName.LOGIN_PAGE;
    }
}

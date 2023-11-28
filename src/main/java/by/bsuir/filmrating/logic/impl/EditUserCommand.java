package by.bsuir.filmrating.logic.impl;

import by.bsuir.filmrating.controller.JspPageName;
import by.bsuir.filmrating.dao.impl.JDBCUserDao;
import by.bsuir.filmrating.dao.interfaces.UserDao;
import by.bsuir.filmrating.logic.CommandException;
import by.bsuir.filmrating.logic.ICommand;
import jakarta.servlet.http.HttpServletRequest;

public class EditUserCommand implements ICommand {
    private final UserDao usersDao = new JDBCUserDao();
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        int id = Integer.parseInt(request.getParameter("id"));
        int rating = Integer.parseInt(request.getParameter("rating"));
        boolean isBanned = Boolean.parseBoolean(request.getParameter("ban"));
        try {
            usersDao.updateUser(id, rating, isBanned);
            return new GetUsersCommand().execute(request);
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            return JspPageName.ERROR_PAGE;
        }
    }
}

package by.bsuir.filmrating.logic.impl;

import by.bsuir.filmrating.controller.JspPageName;
import by.bsuir.filmrating.dao.impl.JDBCUserDao;
import by.bsuir.filmrating.dao.interfaces.UserDao;
import by.bsuir.filmrating.dto.User;
import by.bsuir.filmrating.logic.CommandException;
import by.bsuir.filmrating.logic.ICommand;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public class GetUsersCommand implements ICommand {
    private final UserDao userDao = new JDBCUserDao();
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        try {
            List<User> users = userDao.getUsers();
            request.setAttribute("usersList", users);
            return JspPageName.ADMIN_PAGE;
        } catch (RuntimeException e) {
            request.setAttribute("error", e.getMessage());
            return JspPageName.ERROR_PAGE;
        }
    }
}

package by.bsuir.filmrating.logic.impl;

import by.bsuir.filmrating.controller.JspPageName;
import by.bsuir.filmrating.dao.impl.JDBCAuthDao;
import by.bsuir.filmrating.dao.interfaces.AuthDao;
import by.bsuir.filmrating.dto.User;
import by.bsuir.filmrating.logic.CommandException;
import by.bsuir.filmrating.logic.ICommand;
import jakarta.servlet.http.HttpServletRequest;

public class LoginCommand implements ICommand {
    private final AuthDao authDao = new JDBCAuthDao();
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        if (password.length() < 4) {
            request.setAttribute("error", "Password should be at least 4 characters");
            return JspPageName.LOGIN_PAGE;
        }
        try {
            User user = authDao.getUser(email, password);
            if (user.getIsBanned()) {
                throw new RuntimeException("Sorry, your account has been banned");
            }
            request.getSession().setAttribute("user", user);
            request.setAttribute("loginFlag", true);
            return JspPageName.LOGIN_PAGE;
        } catch (RuntimeException e) {
            request.setAttribute("error", e.getMessage());
            return JspPageName.LOGIN_PAGE;
        }
    }
}

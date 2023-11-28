package by.bsuir.filmrating.logic.impl;

import by.bsuir.filmrating.controller.JspPageName;
import by.bsuir.filmrating.dao.impl.JDBCAuthDao;
import by.bsuir.filmrating.dao.interfaces.AuthDao;
import by.bsuir.filmrating.logic.CommandException;
import by.bsuir.filmrating.logic.ICommand;
import jakarta.servlet.http.HttpServletRequest;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Objects;

public class RegistrationCommand implements ICommand {
    private final AuthDao authDao = new JDBCAuthDao();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String repeatPassword = request.getParameter("re-password");
        if (!Objects.equals(password, repeatPassword)) {
            request.setAttribute("error", "Passwords should match");
            return JspPageName.REGISTRATION_PAGE;
        }
        if (password.length() < 4) {
            request.setAttribute("error", "Password should be at least 4 characters");
            return JspPageName.REGISTRATION_PAGE;
        }
        try {
            int userId = authDao.registerUser(name, hashPassword(password));
            request.getSession().setAttribute("id", userId);
            request.getSession().setAttribute("name", name);
            request.getSession().setAttribute("role", "client");
            request.setAttribute("message", "Successfully registered, you can login now");
            return JspPageName.REGISTRATION_PAGE;
        } catch (RuntimeException e) {
            request.setAttribute("error", e.getMessage());
            return JspPageName.REGISTRATION_PAGE;
        }
    }
    public static String hashPassword(String password) {
        String salt = BCrypt.gensalt(12);
        return BCrypt.hashpw(password, salt);
    }
}

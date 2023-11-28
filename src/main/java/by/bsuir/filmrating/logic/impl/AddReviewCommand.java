package by.bsuir.filmrating.logic.impl;

import by.bsuir.filmrating.controller.JspPageName;
import by.bsuir.filmrating.dao.impl.JDBCFilmDao;
import by.bsuir.filmrating.dao.interfaces.FilmDao;
import by.bsuir.filmrating.dto.User;
import by.bsuir.filmrating.logic.CommandException;
import by.bsuir.filmrating.logic.ICommand;
import jakarta.servlet.http.HttpServletRequest;

public class AddReviewCommand implements ICommand {
    private final FilmDao filmDao = new JDBCFilmDao();
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        User user = (User)request.getSession().getAttribute("user");
        if (user == null) {
            return JspPageName.LOGIN_PAGE;
        }
        int userId = user.getId();
        String filmName = request.getParameter("filmName");
        double userRating = Double.parseDouble(request.getParameter("userRating"));
        String reviewText = request.getParameter("reviewText");
        try {
            filmDao.addReview(filmName, userRating, reviewText, userId);
            return new GetFilmCommand().execute(request);
        } catch (RuntimeException e) {
            request.setAttribute("error", e.getMessage());
            return JspPageName.ERROR_PAGE;
        }
    }
}

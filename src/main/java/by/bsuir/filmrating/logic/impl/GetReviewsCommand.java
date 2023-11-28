package by.bsuir.filmrating.logic.impl;

import by.bsuir.filmrating.controller.JspPageName;
import by.bsuir.filmrating.dao.impl.JDBCFilmDao;
import by.bsuir.filmrating.dao.interfaces.FilmDao;
import by.bsuir.filmrating.dto.Review;
import by.bsuir.filmrating.dto.User;
import by.bsuir.filmrating.logic.CommandException;
import by.bsuir.filmrating.logic.ICommand;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public class GetReviewsCommand implements ICommand {
    private final FilmDao filmDao = new JDBCFilmDao();
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        User user = (User)request.getSession().getAttribute("user");
        if (user == null) {
            return JspPageName.LOGIN_PAGE;
        }
        String filmName = request.getParameter("filmName");
        int userId = user.getId();
        try {
            List<Review> reviews = filmDao.getReviews(filmName, userId);
            request.setAttribute("reviews", reviews);
            if (!reviews.isEmpty() && reviews.get(0).getAuthorId() == userId) {
                request.setAttribute("isButtonDisabled", true);
            }
            return JspPageName.FILM_INFO_PAGE;
        } catch (RuntimeException e) {
            request.setAttribute("error", e.getMessage());
            return JspPageName.ERROR_PAGE;
        }
    }
}

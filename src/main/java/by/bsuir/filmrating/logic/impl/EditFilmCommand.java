package by.bsuir.filmrating.logic.impl;

import by.bsuir.filmrating.controller.JspPageName;
import by.bsuir.filmrating.dao.impl.JDBCFilmDao;
import by.bsuir.filmrating.dao.interfaces.FilmDao;
import by.bsuir.filmrating.logic.CommandException;
import by.bsuir.filmrating.logic.ICommand;
import jakarta.servlet.http.HttpServletRequest;

public class EditFilmCommand implements ICommand {
    private final FilmDao filmDao = new JDBCFilmDao();
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        int filmId = Integer.parseInt(request.getParameter("filmId"));
        String filmTitle = request.getParameter("filmTitle");
        String filmDescription = request.getParameter("filmDescription");
        try {
            filmDao.editFilm(filmTitle, filmDescription, filmId);
            return new GetFilmsCommand().execute(request);
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            return JspPageName.ERROR_PAGE;
        }
    }
}

package by.bsuir.filmrating.logic.impl;

import by.bsuir.filmrating.controller.JspPageName;
import by.bsuir.filmrating.dao.impl.JDBCFilmDao;
import by.bsuir.filmrating.dao.interfaces.FilmDao;
import by.bsuir.filmrating.dto.Film;
import by.bsuir.filmrating.logic.CommandException;
import by.bsuir.filmrating.logic.ICommand;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;


public class GetFilmsCommand implements ICommand {
    private final FilmDao filmDao = new JDBCFilmDao();
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        try {
            List<Film> films = filmDao.getFilms();
            request.setAttribute("filmsList", films);
            return JspPageName.FILMS_PAGE;
        } catch (RuntimeException e) {
            request.setAttribute("error", e.getMessage());
            return JspPageName.ERROR_PAGE;
        }
    }
}

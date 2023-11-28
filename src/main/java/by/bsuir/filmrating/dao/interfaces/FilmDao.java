package by.bsuir.filmrating.dao.interfaces;

import by.bsuir.filmrating.dto.Film;
import by.bsuir.filmrating.dto.Review;

import java.util.List;

public interface FilmDao {
    List<Film> getFilms();
    void addFilm(String filmTitle, String filmDescription);
    void editFilm(String filmTitle, String filmDescription, int filmId);
    Film getFilm(String name);
    List<Review> getReviews(String filmName, int userId);
    void addReview(String filmName, double userRating, String reviewText, int userId);
    void deleteReview(int reviewId, String filmName);
}

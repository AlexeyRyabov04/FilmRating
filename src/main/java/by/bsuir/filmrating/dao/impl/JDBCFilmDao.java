package by.bsuir.filmrating.dao.impl;

import by.bsuir.filmrating.dao.ConnectionPool;
import by.bsuir.filmrating.dao.interfaces.FilmDao;
import by.bsuir.filmrating.dto.Film;
import by.bsuir.filmrating.dto.Review;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class JDBCFilmDao implements FilmDao {
    private final ConnectionPool pool = ConnectionPool.getInstance();

    @Override
    public List<Film> getFilms() {
        List<Film> films = new ArrayList<>();
        Connection connection = pool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from films ORDER BY f_rating DESC");
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                final int id = result.getInt("f_id");
                final String name = result.getString("f_name");
                final String description = result.getString("f_description");
                final double rating = result.getDouble("f_rating");
                films.add(new Film(id, name, description, rating));
            }
            pool.releaseConnection(connection);
        } catch (Exception e) {
            pool.releaseConnection(connection);
            throw new RuntimeException(e.getMessage());
        }
        return films;
    }

    @Override
    public void addFilm(String filmTitle, String filmDescription) {
        Connection connection = pool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO films (f_name, f_description) VALUES (?, ?)"
            );
            preparedStatement.setString(1, filmTitle);
            preparedStatement.setString(2, filmDescription);
            preparedStatement.executeUpdate();
            pool.releaseConnection(connection);
        } catch (Exception e) {
            pool.releaseConnection(connection);
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void editFilm(String filmTitle, String filmDescription, int filmId) {
        Connection connection = pool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE films SET f_name = ?, f_description = ? WHERE (f_id = ?)"
            );
            preparedStatement.setString(1, filmTitle);
            preparedStatement.setString(2, filmDescription);
            preparedStatement.setInt(3, filmId);
            preparedStatement.executeUpdate();
            pool.releaseConnection(connection);
        } catch (Exception e) {
            pool.releaseConnection(connection);
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Film getFilm(String name) {
        Film film = null;
        Connection connection = pool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select * from films where f_name = ?"
            );
            preparedStatement.setString(1, name);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                final int id = result.getInt("f_id");
                final String description = result.getString("f_description");
                final double rating = result.getDouble("f_rating");
                film = new Film(id, name, description, rating);
            }
            pool.releaseConnection(connection);
        } catch (Exception e) {
            pool.releaseConnection(connection);
            System.err.println("Films details GET servlet error");
        }
        return film;
    }

    @Override
    public List<Review> getReviews(String filmName, int userId) {
        List<Review> reviews = new ArrayList<>();
        Connection connection = pool.getConnection();
        try {
            PreparedStatement reviewStatement = connection.prepareStatement(
                    "SELECT r.r_id, r.r_comment, r.r_rating, u.u_name, u.u_rating, u.u_id " +
                            "FROM reviews r " +
                            "JOIN films f ON r.r_film_id = f.f_id " +
                            "JOIN users u ON r.r_author_id = u.u_id " +
                            "WHERE f.f_name = ?"
            );
            reviewStatement.setString(1, filmName);

            ResultSet reviewResult = reviewStatement.executeQuery();
            Review currentUserReview = null;
            while (reviewResult.next()) {
                final int reviewId = reviewResult.getInt("r_id");
                final int authorId = reviewResult.getInt("u_id");
                final String authorName = reviewResult.getString("u_name");
                final int userRating = reviewResult.getInt("u_rating");
                final String reviewText = reviewResult.getString("r_comment");
                final double reviewRating = reviewResult.getDouble("r_rating");

                Review review = new Review(reviewId, authorId, authorName, userRating, reviewText, reviewRating);

                if (authorId == userId) {
                    currentUserReview = new Review(reviewId, authorId, authorName, userRating, reviewText, reviewRating);
                } else {
                    reviews.add(review);
                }
            }
            if (currentUserReview != null) {
                List<Review> updatedReviews = new ArrayList<>();
                updatedReviews.add(currentUserReview);
                updatedReviews.addAll(reviews);
                reviews = updatedReviews;
            }
            pool.releaseConnection(connection);
        } catch (Exception e) {
            pool.releaseConnection(connection);
            throw new RuntimeException(e.getMessage());
        }
        return reviews;
    }

    public static void updateFilmRating(int filmId, Connection connection) {
        try {
            PreparedStatement calculateAverageStatement = connection.prepareStatement(
                    "SELECT AVG(r_rating) AS avg_rating FROM reviews WHERE r_film_id = ?"
            );
            calculateAverageStatement.setInt(1, filmId);
            ResultSet avgRatingResult = calculateAverageStatement.executeQuery();
            double newAvgRating = 0.0;
            if (avgRatingResult.next()) {
                newAvgRating = avgRatingResult.getDouble("avg_rating");
            }
            PreparedStatement updateAvgRatingStatement = connection.prepareStatement(
                    "UPDATE films SET f_rating = ? WHERE f_id = ?"
            );
            updateAvgRatingStatement.setDouble(1, newAvgRating);
            updateAvgRatingStatement.setInt(2, filmId);
            updateAvgRatingStatement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void addReview(String filmName, double userRating, String reviewComment, int userId) {
        Connection connection = pool.getConnection();
        try {
            PreparedStatement filmIdStatement = connection.prepareStatement(
                    "SELECT f_id FROM films WHERE f_name = ?"
            );
            filmIdStatement.setString(1, filmName);
            ResultSet filmIdResult = filmIdStatement.executeQuery();

            if (filmIdResult.next()) {
                int filmId = filmIdResult.getInt("f_id");

                PreparedStatement reviewInsertStatement = connection.prepareStatement(
                        "INSERT INTO reviews (r_author_id, r_film_id, r_comment, r_rating) VALUES (?, ?, ?, ?)"
                );
                reviewInsertStatement.setInt(1, userId);
                reviewInsertStatement.setInt(2, filmId);
                reviewInsertStatement.setString(3, reviewComment);
                reviewInsertStatement.setDouble(4, userRating);
                reviewInsertStatement.executeUpdate();
                updateFilmRating(filmId, connection);
            }
            pool.releaseConnection(connection);
        } catch (Exception e) {
            pool.releaseConnection(connection);
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void deleteReview(int reviewId, String filmName) {
        Connection connection = pool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "DELETE FROM reviews WHERE r_id = ?"
            );
            preparedStatement.setInt(1, reviewId);
            preparedStatement.executeUpdate();
            PreparedStatement filmIdStatement = connection.prepareStatement(
                    "SELECT f_id FROM films WHERE f_name = ?"
            );
            filmIdStatement.setString(1, filmName);
            ResultSet filmIdResult = filmIdStatement.executeQuery();
            if (filmIdResult.next()) {
                int filmId = filmIdResult.getInt("f_id");
                updateFilmRating(filmId, connection);
            }
            pool.releaseConnection(connection);
        } catch (Exception e) {
            pool.releaseConnection(connection);
            throw new RuntimeException(e.getMessage());
        }
    }
}

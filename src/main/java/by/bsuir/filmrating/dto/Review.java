package by.bsuir.filmrating.dto;

public class Review {
    private final int id;
    private final int authorId;
    private final String userName;
    private final int userRating;
    private final String text;
    private final double filmRating;

    public Review(int id, int authorId, String userName, int userRating, String text, double filmRating) {
        this.id = id;
        this.authorId = authorId;
        this.userName = userName;
        this.userRating = userRating;
        this.text = text;
        this.filmRating = filmRating;
    }

    public int getId() {
        return id;
    }

    public int getAuthorId() {
        return authorId;
    }

    public String getUserName() {
        return userName;
    }

    public int getUserRating() {
        return userRating;
    }

    public String getText() {
        return text;
    }

    public double getFilmRating() {
        return filmRating;
    }
}


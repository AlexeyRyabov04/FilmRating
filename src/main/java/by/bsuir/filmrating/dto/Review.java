package by.bsuir.filmrating.dto;

public class Review {
    private final int id;
    private final int authorId;
    private final int rating;
    private final String comment;

    public Review(int id, int authorId, int rating, String comment) {
        this.id = id;
        this.authorId = authorId;
        this.rating = rating;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }
    public int getAuthorId() {
        return authorId;
    }
    public String getComment() {
        return comment;
    }
    public double getRating() {
        return rating;
    }

}

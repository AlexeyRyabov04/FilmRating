package by.bsuir.filmrating.dto;

public class User {
    private final int id;
    private final String role;
    private final int rating;
    private final boolean isBanned;
    private final String name;

    public User(int id, String role, int rating, boolean isBanned, String name) {
        this.id = id;
        this.role = role;
        this.rating = rating;
        this.isBanned = isBanned;
        this.name = name;
    }

    public int getId() {
        return id;
    }
    public String getRole() {
        return role;
    }
    public int getRating() {
        return rating;
    }
    public boolean getIsBanned() {
        return isBanned;
    }

    public String getName() {
        return name;
    }

}

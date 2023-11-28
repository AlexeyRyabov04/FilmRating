package by.bsuir.filmrating.dto;

public class Film {
    private final int id;
    private final String name;
    private final String description;
    private final double rating;

    public Film(int id, String name, String description, double rating) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getRating() {
        return rating;
    }

}

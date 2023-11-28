package by.bsuir.filmrating.dao.interfaces;

import by.bsuir.filmrating.dto.User;

import java.util.List;

public interface UserDao {
    List<User> getUsers();
    void updateUser(int id, int rating, boolean isBanned);
}

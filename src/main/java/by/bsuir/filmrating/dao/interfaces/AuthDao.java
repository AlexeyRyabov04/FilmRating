package by.bsuir.filmrating.dao.interfaces;

import by.bsuir.filmrating.dto.User;

public interface AuthDao {
    User getUser(String email, String password);
    int registerUser(String name, String password);
}

package by.bsuir.filmrating.dao.impl;

import by.bsuir.filmrating.dao.ConnectionPool;
import by.bsuir.filmrating.dao.interfaces.UserDao;
import by.bsuir.filmrating.dto.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class JDBCUserDao implements UserDao {
    private final ConnectionPool pool = ConnectionPool.getInstance();
    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        Connection connection = pool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select * from users WHERE u_role = 'client' ORDER BY u_isbanned ASC, u_rating DESC"
            );
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                final int id = result.getInt("u_id");
                final String role = result.getString("u_role");
                final int rating = result.getInt("u_rating");
                final boolean isBanned = result.getBoolean("u_isbanned");
                final String name = result.getString("u_name");
                users.add(new User(id, role, rating, isBanned, name));
            }
            pool.releaseConnection(connection);
        } catch (Exception e) {
            pool.releaseConnection(connection);
            throw new RuntimeException(e.getMessage());
        }
        return users;
    }

    @Override
    public void updateUser(int id, int rating, boolean isBanned) {
        Connection connection = pool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE users SET u_rating = ?, u_isbanned = ? WHERE (u_id = ?)"
            );
            preparedStatement.setInt(1, rating);
            preparedStatement.setBoolean(2, isBanned);
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();
            pool.releaseConnection(connection);
        } catch (Exception e) {
            pool.releaseConnection(connection);
            throw new RuntimeException(e.getMessage());
        }
    }
}

package by.bsuir.filmrating.dao.impl;

import by.bsuir.filmrating.dao.ConnectionPool;
import by.bsuir.filmrating.dao.interfaces.AuthDao;
import by.bsuir.filmrating.dto.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;
public class JDBCAuthDao implements AuthDao {
    private final ConnectionPool pool = ConnectionPool.getInstance();
    @Override
    public User getUser(String name, String password) {
        Connection connection = pool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select * from users where u_name = ?"
            );
            preparedStatement.setString(1, name);

            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                final boolean isPasswordValid = BCrypt.checkpw(password, result.getString("u_password"));
                if (isPasswordValid) {
                    final int id = result.getInt("u_id");
                    final int rating = result.getInt("u_rating");
                    final String role = result.getString("u_role");
                    final boolean isBanned = result.getBoolean("u_isbanned");
                    pool.releaseConnection(connection);
                    return new User(id, role, rating, isBanned, name);
                } else {
                    pool.releaseConnection(connection);
                    throw new Exception("Incorrect name or password");
                }
            } else {
                pool.releaseConnection(connection);
                throw new Exception("Incorrect name or password");
            }
        } catch (Exception e) {
            pool.releaseConnection(connection);
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public int registerUser(String name, String password) {
        Connection connection = pool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into users(u_name, u_password) values(?, ?)",
                    new String[]{"u_id"}
            );
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);
            preparedStatement.executeUpdate();
            int userId = 0;
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                userId = generatedKeys.getInt(1);
            }
            pool.releaseConnection(connection);
            return userId;
        } catch (SQLException e) {
            pool.releaseConnection(connection);
            throw new RuntimeException("Name is already used");
        }
    }
    
}

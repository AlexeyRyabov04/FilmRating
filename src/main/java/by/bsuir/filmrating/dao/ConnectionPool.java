package by.bsuir.filmrating.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {
    private static final ConnectionPool instance = new ConnectionPool();
    private final int POOL_CAPACITY = 10;

    private final BlockingQueue<Connection> connectionQueue = new ArrayBlockingQueue<>(10);
    private final BlockingQueue<Connection> givenAwayConQueue = new ArrayBlockingQueue<>(10);

    public synchronized static ConnectionPool getInstance() {
        return instance;
    }

    private ConnectionPool() {
        ResourceBundle bundle = ResourceBundle.getBundle("database");
        final String url = bundle.getString("db.url");
        final String user = bundle.getString("db.user");
        final String password = bundle.getString("db.password");
        try {
            create(url, user, password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void create(
            String url, String user,
            String password) throws SQLException, ClassNotFoundException {
        final int INITIAL_POOL_SIZE = 5;
        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            connectionQueue.add(createConnection(url, user, password));
        }
    }

    public synchronized Connection getConnection() {
        try {
            Connection connection = connectionQueue.take();
            givenAwayConQueue.add(connection);
            return connection;
        } catch (InterruptedException e) {
            return null;
        }
    }

    public synchronized boolean releaseConnection(Connection connection) {
        connectionQueue.add(connection);
        return givenAwayConQueue.remove(connection);
    }

    private static Connection createConnection(
            String url, String user, String password)
            throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, user, password);
    }
}


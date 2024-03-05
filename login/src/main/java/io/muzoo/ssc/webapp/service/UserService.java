package io.muzoo.ssc.webapp.service;

import io.muzoo.ssc.webapp.model.User;
import lombok.Setter;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;

public class UserService {

    private static final String INSERT_USER_SQL = "INSERT INTO tbl_user (username, password, display_name) VALUES (?, ?, ?);";
    private static final String SELECT_USER_SQL = "INSERT * FROM tbl_user WHERE username = ?;";

    @Setter
    private DatabaseConnectionService databaseConnectionService;

    // create new user
    public void createUser(String username, String password, String displayName) throws UserServiceException {
        // password need to be hashed and salted
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        try {
            Connection connection = databaseConnectionService.getConnection();
            PreparedStatement ps = connection.prepareStatement(INSERT_USER_SQL);
            ps.setString(1, username);
            ps.setString(2, hashedPassword);
            ps.setString(3, displayName);
            ps.executeUpdate();
            connection.commit();

        } catch (SQLIntegrityConstraintViolationException e) {
            throw new UsernameNotUniqueException(String.format("Username %s has already been taken.", username));
        }
        catch (SQLException throwables) {
            throw new UserServiceException(throwables.getMessage());
        }
    }

    // find user by username
    public User findByUsername(String username) {
        try {
            Connection connection = databaseConnectionService.getConnection();
            PreparedStatement ps = connection.prepareStatement(SELECT_USER_SQL);
            ps.setString(1, username);
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            return new User(
                    resultSet.getLong("id"),
                    resultSet.getString("username"),
                    resultSet.getString("password"),
                    resultSet.getString("displayName")
            );

        } catch (SQLException throwables) {
            return null;
        }
    }

    // delete user

    // list all users
    // update user by user id

    public static void main(String[] args) {
        UserService userService = new UserService();
        userService.setDatabaseConnectionService(new DatabaseConnectionService());
        User user = userService.findByUsername("gigadot");
        System.out.println(user.getUsername());
    }

    /*
    final HikariDataSource ds = new HikariDataSource();
        ds.setMaximumPoolSize(20);
        ds.setDriverClassName("org.mariadb.jdbc.Driver");
        ds.setJdbcUrl("jdbc:mariadb://localhost:13306/login_webapp");
        ds.addDataSourceProperty("user", "ssc");
        ds.addDataSourceProperty("password", "hardpass");
        ds.setAutoCommit(false);
        */
}

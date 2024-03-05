package io.muzoo.ssc.webapp.service;

import io.muzoo.ssc.webapp.model.User;
import lombok.Setter;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserService {

    private static final String INSERT_USER_SQL = "INSERT INTO tbl_user (username, password, display_name) VALUES (?, ?, ?);";

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
            //TODO: check what happens if user is duplicated

        } catch (SQLException throwables) {
            throw new UserServiceException(throwables.getMessage());
        }
    }

    public static void main(String[] args) throws UserServiceException {
        UserService userService = new UserService();
        userService.setDatabaseConnectionService(new DatabaseConnectionService());
        userService.createUser("gigadot", "devpass", "Weerapong");
    }

    // find user by username
    // delete user

    // list all users
    // update user by user id


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

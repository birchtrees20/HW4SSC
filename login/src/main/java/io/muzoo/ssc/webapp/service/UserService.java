package io.muzoo.ssc.webapp.service;

import io.muzoo.ssc.webapp.model.User;
import lombok.Setter;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    //user service used in too many places and only one instance of it needed so it should be singleton
    private static final String INSERT_USER_SQL = "INSERT INTO tbl_user (username, password, display_name) VALUES (?, ?, ?);";
    private static final String SELECT_USER_SQL = "SELECT * FROM tbl_user WHERE username = ?;";
    private static final String SELECT_ALL_USERS_SQL = "SELECT * FROM tbl_user;";
    private static final String DELETE_USER_SQL = "DELETE FROM tbl_user WHERE username =?;";
    private static final String UPDATE_USER_SQL = "UPDATE tbl_user SET display_name = ? WHERE username = ?;";
    private static final String UPDATE_USER_PASSWORD_SQL = "UPDATE tbl_user SET password = ? WHERE username = ?;";


    @Setter
    private DatabaseConnectionService databaseConnectionService;

    private static UserService service;
    private UserService() {
    }

    public static UserService getInstance(){
        if (service == null) {
            service = new UserService();
            service.setDatabaseConnectionService(DatabaseConnectionService.getInstance());
        }
        return service;
    }

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
        } catch (SQLException throwables) {
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
                    resultSet.getString("display_Name")
            );

        } catch (SQLException throwables) {
            return null;
        }
    }

    // delete user
    public boolean deleteUserByUsername(String username) {
        try {
            Connection connection = databaseConnectionService.getConnection();
            PreparedStatement ps = connection.prepareStatement(DELETE_USER_SQL);
            ps.setString(1, username);
            int deleteCount = ps.executeUpdate();
            connection.commit();
            return deleteCount > 0;

        } catch (SQLException throwables) {
            return false;
        }
    }

    // update user by user id
    public void updateUserByUsername(String username, String displayName) throws UserServiceException {
        // password need to be hashed and salted
        //String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        try {
            Connection connection = databaseConnectionService.getConnection();
            PreparedStatement ps = connection.prepareStatement(UPDATE_USER_SQL);
            ps.setString(1, displayName);
            ps.setString(2, username);

            ps.executeUpdate();
            connection.commit();

        } catch (SQLException throwables) {
            throw new UserServiceException(throwables.getMessage());
        }    }

    public void changePassword(String username, String newPassword) throws UserServiceException{
        // password need to be hashed and salted

        try {
            Connection connection = databaseConnectionService.getConnection();
            PreparedStatement ps = connection.prepareStatement(UPDATE_USER_PASSWORD_SQL);
            ps.setString(1, BCrypt.hashpw(newPassword, BCrypt.gensalt()));
            ps.setString(2, username);

            ps.executeUpdate();
            connection.commit();

        } catch (SQLException throwables) {
            throw new UserServiceException(throwables.getMessage());
        }    }

    // list all users
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try {
            Connection connection = databaseConnectionService.getConnection();
            PreparedStatement ps = connection.prepareStatement(SELECT_ALL_USERS_SQL);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                users.add(new User(
                        resultSet.getLong("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("display_Name")));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return users;
    }


    public static void main(String[] args) {
        UserService userService = UserService.getInstance();
        try {
            userService.createUser("admin", "123456", "Admin");
        } catch (UserServiceException e) {
            throw new RuntimeException(e);
        }/*
        List<User> users = userService.findAll();
        for (User user : users) {
            System.out.println(user.getUsername());
        }*/
    }
}

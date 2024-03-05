package io.muzoo.ssc.webapp.service;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseConnectionService {

    private final HikariDataSource ds;

    public DatabaseConnectionService() {
        ds = new HikariDataSource();
        ds.setMaximumPoolSize(20);

        // TODO: these are hard coded so it's not convenient and secure.
        // change to use configuration file or environment variable
        ds.setDriverClassName("org.mariadb.jdbc.Driver");
        ds.setJdbcUrl("jdbc:mariadb://localhost:13306/login_webapp");
        ds.addDataSourceProperty("user", "ssc");
        ds.addDataSourceProperty("password", "hardpass");
        ds.setAutoCommit(false);
    }

    public static void main(String[] args) {
        final HikariDataSource ds = new HikariDataSource();
        ds.setMaximumPoolSize(20);
        ds.setDriverClassName("org.mariadb.jdbc.Driver");
        ds.setJdbcUrl("jdbc:mariadb://localhost:13306/login_webapp");
        ds.addDataSourceProperty("user", "ssc");
        ds.addDataSourceProperty("password", "hardpass");
        ds.setAutoCommit(false);

        try {
            Connection connection = ds.getConnection();
            String sql = "INSERT INTO tbl_user (username, password, display_name) VALUES (?, ?, ?);";
            PreparedStatement ps = connection.prepareStatement(sql);
            // setting username column 1
            ps.setString(1, "my_username");
            // setting password column 1
            ps.setString(2, "my_password");
            // setting display_name column 1
            ps.setString(3, "my_display_name");
            ps.executeUpdate();
            //so need to be manually commit the change
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

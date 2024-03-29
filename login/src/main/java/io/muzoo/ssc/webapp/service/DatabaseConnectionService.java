package io.muzoo.ssc.webapp.service;

import com.zaxxer.hikari.HikariDataSource;
import io.muzoo.ssc.webapp.config.ConfigProperties;
import io.muzoo.ssc.webapp.config.ConfigurationLoader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseConnectionService {

    private final HikariDataSource ds;

    private static DatabaseConnectionService service;

    //todo: THE FILE config.properties IS NOT COMMITTED TO GIT REPO

    private DatabaseConnectionService() {
        ds = new HikariDataSource();
        ds.setMaximumPoolSize(50);

/*        ConfigProperties configProperties = ConfigurationLoader.load();
        if (configProperties == null) {
            throw new RuntimeException("Unable to read config.properties");
        }*/



        ds.setDriverClassName("org.mariadb.jdbc.Driver");
        ds.setJdbcUrl("jdbc:mariadb://172.17.0.1:3306/login_webapp");
        ds.addDataSourceProperty("user", "ssc");
        ds.addDataSourceProperty("password", "hardpass");
        ds.setAutoCommit(false);
    }

    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public static DatabaseConnectionService getInstance() {
        if (service == null) {
            service = new DatabaseConnectionService();
        }
        return service;
    }
}

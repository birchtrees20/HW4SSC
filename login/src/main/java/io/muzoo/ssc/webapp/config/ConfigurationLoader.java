package io.muzoo.ssc.webapp.config;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigurationLoader {
    // added static method for loading configuration from disk
    public static ConfigProperties load() {
        String configFilename = "config.properties";
        try (FileInputStream fin = new FileInputStream(configFilename)) {
            Properties prop = new Properties();
            prop.load(fin);

            // get the property value and print it out
            String driverClassName = prop.getProperty("database.driverClassName");
            String connectionURL = prop.getProperty("database.connectionUrl");
            String username = prop.getProperty("database.username");
            String password = prop.getProperty("database.password");

            return new ConfigProperties.ConfigPropertiesBuilder()
                    .databaseDriverClassName(driverClassName)
                    .databaseConnectionURL(connectionURL)
                    .databaseUsername(username)
                    .databasePassword(password)
                    .build();

        } catch (Exception e) {
            return null;
        }
    }
}

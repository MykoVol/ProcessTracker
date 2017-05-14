package com.mykovol.ProcessTracker;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by MykoVol on 3/21/2017.
 */
public class AppProperties {
    private static Logger LOGGER = Logger.getLogger(AppProperties.class);
    private static String jdbcUrl;
    private static String jdbcUsername;

    public static void setJdbcPassword(String[] commandArgs) {
        if (commandArgs.length == 0) {
            LOGGER.error("Empty arguments!");
        } else {
            if (commandArgs[0] == null) {
                LOGGER.error("Null password is given in first parameter");
            } else {
                if (AppProperties.jdbcPassword == null) {
                    LOGGER.error("Password is not set from config file");
                } else {
                    AppProperties.jdbcPassword = AppProperties.jdbcPassword + commandArgs;
                }
            }
        }
    }

    private static String jdbcPassword;

    public static String getJdbcUrl() {
        return jdbcUrl;
    }

    public static String getJdbcUsername() {
        return jdbcUsername;
    }

    public static String getJdbcPassword() {
        return jdbcPassword;
    }

    public static void getProperties() {
        InputStream inputStream = null;

        try {
            Properties prop = new Properties();
            String propFileName = "config.properties";

            inputStream = AppProperties.class.getClassLoader().getResourceAsStream(propFileName);

            try {
                prop.load(inputStream);
            } catch (IOException e) {
                LOGGER.error("property file '" + propFileName + "' not found in the classpath", e);
            }

            jdbcUrl = prop.getProperty("jdbc.url");
            jdbcUsername = prop.getProperty("jdbc.username");
            jdbcPassword = prop.getProperty("jdbc.password");

        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                LOGGER.error("inputStream close", e);
            }
        }
    }

}

package com.alexboriskin.university.dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alexboriskin.university.domain.DAOException;

public class ConnectionFactory {
    private static final Logger log = LogManager.getLogger();
    private static ConnectionFactory instance = new ConnectionFactory();
    private static String URL;
    private static String USER;
    private static String PASSWORD;
    private static String DRIVER_CLASS;

    private ConnectionFactory() {
        try {
            InputStream inputStream = 
                    getClass().getClassLoader().getResourceAsStream("university.properties");
            Properties dbProperties = new Properties();
            dbProperties.load(inputStream);
            //dbProperties.load(new FileInputStream("WEB-INF\\classes\\university.properties"));

            URL = dbProperties.getProperty("url");
            USER = dbProperties.getProperty("user");
            PASSWORD = dbProperties.getProperty("password");
            DRIVER_CLASS = dbProperties.getProperty("driver_class");

            Class.forName(DRIVER_CLASS);
        } catch (Exception e) {
            log.error("Some problem with connection factory... " + e);
        }
    }

    private Connection createConnection() throws DAOException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException ex) {
            log.error("establish connection with: " + URL
                    + " for user " + USER + " ---- " + ex);
            throw new DAOException("establish connection with: " + URL
                    + " for user " + USER + " ---- ", ex);
        }
        return connection;
    }

    public static Connection getConnection() throws DAOException {
        return instance.createConnection();
    }
}

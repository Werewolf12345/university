package com.alexboriskin.university.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alexboriskin.university.domain.DAOException;

public class ConnectionFactory {
    private static final Logger log = LogManager.getLogger();
    private static ConnectionFactory instance = new ConnectionFactory();
    private static final String DATASOURCE_CONTEXT = "java:comp/env/jdbc/mydb";

    private ConnectionFactory() {
    }

    private Connection createConnection() throws DAOException {
        Connection connection = null;

        try {
            Context initialContext = new InitialContext();
            DataSource datasource = (DataSource) initialContext
                    .lookup(DATASOURCE_CONTEXT);
            connection = datasource.getConnection();
            initialContext.close();
        } catch (NamingException ex) {
            log.error("Unable to estabish connection with local DB" + ex);
            throw new DAOException(
                    "Unable to estabish connection with local DB" + ex);
        } catch (SQLException ex) {
            log.error("Unable to estabish connection with local DB" + ex);
            throw new DAOException(
                    "Unable to estabish connection with local DB" + ex);
        }
        return connection;
    }

    public static Connection getConnection() throws DAOException {
        return instance.createConnection();
    }
}

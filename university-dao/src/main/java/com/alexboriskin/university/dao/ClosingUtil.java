package com.alexboriskin.university.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alexboriskin.university.domain.DAOException;

public class ClosingUtil {
    private static final Logger log = LogManager.getLogger();

    public static void close(Connection connection) throws DAOException {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
                log.error("Cannot close connection: " + ex);
                throw new DAOException("Cannot close connection: ", ex);
            }
        }
    }

    public static void close(Statement statement) throws DAOException {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException ex) {
                log.error("Cannot close statement: " + ex);
                throw new DAOException("Cannot close statement", ex);
            }
        }
    }

    public static void close(ResultSet resultSet) throws DAOException {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException ex) {
                log.error("Cannot close resultset: " + ex);
                throw new DAOException("Cannot close resultset", ex);
            }
        }
    }

    public static void close(PreparedStatement preparedStatement)
            throws DAOException {
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException ex) {
                log.error("Cannot close prepared statement: " + ex);
                throw new DAOException("Cannot close prepared statement", ex);
            }
        }
    }
}

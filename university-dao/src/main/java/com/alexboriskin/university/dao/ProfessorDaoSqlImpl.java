package com.alexboriskin.university.dao;

import java.sql.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alexboriskin.university.domain.*;

public class ProfessorDaoSqlImpl extends StaffDaoSqlImpl implements ProfessorDao {
    private static final int NOT_EXISTING = -1;
    private static final Logger log = LogManager.getLogger();
    

    @Override
    public void save(Professor professor) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        AddressDao addressDao = new AddressDaoSqlImpl();
        int addressID = addressDao.getID(professor);
        String sqlExpression = null;
        
        if(addressID == NOT_EXISTING){
            addressDao.save(professor);
            addressID = addressDao.getID(professor);
        }
        
        
        try {
            sqlExpression 
            = "INSERT INTO professors (first_name, last_name, address_id) VALUES (?, ?, ?);";
            connection = ConnectionFactory.getConnection();
            
            preparedStatement = connection
                    .prepareStatement(sqlExpression);
            
            connection.setAutoCommit(false);
                        
            preparedStatement.setString(1, professor.getFirstName());
            preparedStatement.setString(2, professor.getLastName());
            preparedStatement.setInt(3, addressID);
            preparedStatement.executeUpdate();
            connection.commit();
            
            connection.setAutoCommit(true);
        } catch (SQLException ex) {
            log.error("Cannot save professor: " + ex);
            throw new DAOException("Cannot save professor", ex);
        } finally {
            ClosingUtil.close(preparedStatement);
            ClosingUtil.close(connection);
        }
    }
   
    /**
     * @return Professor object from DB or "empty" Professor object if not exists
     * @throws DAOException 
     * 
     * */
    @Override
    public Professor get(int professorID) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sqlExpression 
                = "SELECT first_name, last_name, address_id FROM professors WHERE professor_id = ?;";
        
        String firstName = "";
        String lastName = "";
        int addressID = 0;
        String state = "";
        String address = "";
        int zip = 0; 
        
        try {
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(sqlExpression);
            preparedStatement.setInt(1, professorID);
            resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()) {
                firstName = resultSet.getString("first_name");
                lastName = resultSet.getString("last_name");
                addressID = resultSet.getInt("address_id");
            }
            
            ClosingUtil.close(resultSet);
            ClosingUtil.close(preparedStatement);
            
            sqlExpression 
            = "SELECT state, address, zip FROM addresses WHERE address_id = ?;";
            preparedStatement = connection.prepareStatement(sqlExpression);
            preparedStatement.setInt(1, addressID);
            resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()) {
                state = resultSet.getString("state");
                address = resultSet.getString("address");
                zip = resultSet.getInt("zip");
            }
            
        } catch (SQLException ex) {
            log.error("Cannot get professor: " + ex);
            throw new DAOException("Cannot get professor", ex);
        } finally {
            ClosingUtil.close(resultSet);
            ClosingUtil.close(preparedStatement);
            ClosingUtil.close(connection);
        }
    return new Professor(firstName, lastName, new Address(state, address, zip));
    }

    @Override
    public void delete(Professor professor) throws DAOException {
       super.delete(professor);
        
    }
}


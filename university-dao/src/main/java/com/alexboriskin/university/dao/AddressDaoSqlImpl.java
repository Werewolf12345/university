package com.alexboriskin.university.dao;

import java.sql.*;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alexboriskin.university.domain.*;

public class AddressDaoSqlImpl implements AddressDao {
    private static final int NOT_EXISTING = -1;
    private static final Logger log = LogManager.getLogger();
    private DataSource dataSource;
    
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * @return address ID from DB or -1 if not found
     * @throws DAOException 
     * */    
    @Override
    public int getID(Staff staffMember) throws DAOException {
        Connection connection = null;
        PreparedStatement selectStatement = null;
        ResultSet resultSet = null;
        int addressID = NOT_EXISTING;
        String sqlExpression 
                = "SELECT address_id FROM addresses WHERE zip = ? AND UPPER(address) = UPPER(?);";
        
        try {
            connection = dataSource.getConnection();
            selectStatement = connection.prepareStatement(sqlExpression);
            selectStatement.setInt(1, staffMember.getAddress().getZipCode());
            selectStatement.setString(2, staffMember.getAddress().getAddress());
            resultSet = selectStatement.executeQuery();
            
            while(resultSet.next()) {
                addressID = resultSet.getInt("address_id");
            }
            
        } catch (SQLException ex) {
            log.error("Cannot get address ID for member: " + ex);
            throw new DAOException("Cannot get address ID for member", ex);
        } finally {
            ClosingUtil.close(resultSet);
            ClosingUtil.close(selectStatement);
            ClosingUtil.close(connection);
        }
        return addressID;
    }
    
    @Override
    public void save(Staff staffMember) throws DAOException {
        
        //finish if already exists in DB
        if(getID(staffMember) != NOT_EXISTING) {
            return;
        }
        
        Connection connection = null;
        PreparedStatement selectStatement = null;
        String sqlExpression 
                = "INSERT INTO addresses (state, address, zip) VALUES (?, ?, ?);";
        
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            
            selectStatement = connection.prepareStatement(sqlExpression);
            selectStatement.setString(1, staffMember.getAddress().getState());
            selectStatement.setString(2, staffMember.getAddress().getAddress());
            selectStatement.setInt(3, staffMember.getAddress().getZipCode());
            selectStatement.executeUpdate();
            connection.commit();
            
            connection.setAutoCommit(true);
            
        } catch (SQLException ex) {
            log.error("Cannot save address for member: " + ex);
            throw new DAOException("Cannot save address for member", ex);
        } finally {
            ClosingUtil.close(selectStatement);
            ClosingUtil.close(connection);
        }
    }
}

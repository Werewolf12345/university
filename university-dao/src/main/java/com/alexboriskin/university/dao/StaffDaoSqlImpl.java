package com.alexboriskin.university.dao;

import java.sql.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alexboriskin.university.domain.*;

public class StaffDaoSqlImpl implements StaffDao {
    private static final int NOT_EXISTING = -1;
    private static final Logger log = LogManager.getLogger();
    
    /**
     * @return either Student's or Professor's ID from DB, -1 if not found
     * @throws DAOException 
     */
    @Override
    public int getID(Staff staffMember) throws DAOException {
        Connection connection = null;
        PreparedStatement prepareStatement = null;
        ResultSet resultSet = null;
        int memberID = NOT_EXISTING;
        String sqlExpression = null;

        if (staffMember instanceof Student) {
            sqlExpression = "SELECT student_id FROM students WHERE UPPER (first_name)"
                    + " = UPPER (?) AND UPPER (last_name) = UPPER (?) AND address_id = ?;";
        } else if (staffMember instanceof Professor) {
            sqlExpression = "SELECT professor_id FROM professors WHERE UPPER (first_name)"
                    + " = UPPER (?) AND UPPER (last_name) = UPPER (?) AND address_id = ?;";
        }

        try {
            AddressDao addressDao = new AddressDaoSqlImpl();
            connection = ConnectionFactory.getConnection();
            prepareStatement = connection.prepareStatement(sqlExpression);
            prepareStatement.setString(1, staffMember.getFirstName());
            prepareStatement.setString(2, staffMember.getLastName());
            prepareStatement.setInt(3, addressDao.getID(staffMember));
            resultSet = prepareStatement.executeQuery();

            while (resultSet.next()) {
                memberID = resultSet.getInt(1);
            }

        } catch (SQLException ex) {
            log.error("Cannot get ID for member: " + ex);
            throw new DAOException("Cannot get ID for member", ex);
        } finally {
            ClosingUtil.close(resultSet);
            ClosingUtil.close(prepareStatement);
            ClosingUtil.close(connection);
        }
        return memberID;
    }

    @Override
    public void updateNameAndAddress(Staff staffMemberOldData, Staff staffMemberNewData) throws DAOException {
        Connection connection = null;
        PreparedStatement updateMember = null;
        String sqlExpression = null;
        AddressDao addressDao = new AddressDaoSqlImpl();
        int addressID = addressDao.getID(staffMemberNewData);
        
        if(addressID == NOT_EXISTING) {
            addressDao.save(staffMemberNewData);
            addressID = addressDao.getID(staffMemberNewData);
        }

        if (staffMemberOldData instanceof Student) {
            sqlExpression = "UPDATE students SET first_name = ?, last_name = ?, address_id = ? WHERE student_id = ?;";
        } else if (staffMemberOldData instanceof Professor) {
            sqlExpression = "UPDATE professors SET first_name = ?, last_name = ?, address_id = ? WHERE professor_id = ?;";
        }

        try {
            connection = ConnectionFactory.getConnection();
            updateMember = connection.prepareStatement(sqlExpression);
            StaffDao staffDao = new StaffDaoSqlImpl();

            connection.setAutoCommit(false);

            updateMember.setString(1, staffMemberNewData.getFirstName());
            updateMember.setString(2, staffMemberNewData.getLastName());
            updateMember.setInt(3, addressID);
            updateMember.setInt(4, staffDao.getID(staffMemberOldData));
            updateMember.executeUpdate();
            connection.commit();

            connection.setAutoCommit(true);
        } catch (SQLException ex) {
            log.error("Cannot update member: " + ex);
            throw new DAOException("Cannot update member", ex);
        } finally {
            ClosingUtil.close(updateMember);
            ClosingUtil.close(connection);
        }
    }
    
    @Override
    public void delete(Staff staffMember) throws DAOException {
        Connection connection = null;
        PreparedStatement deleteMember = null;
        String sqlExpression = null;

        if (staffMember instanceof Student) {
            sqlExpression = "DELETE FROM students WHERE student_id = ?;";
        } else if (staffMember instanceof Professor) {
            sqlExpression = "DELETE FROM professors WHERE professor_id = ?;";
        }

        try {
            StaffDao staffDao = new StaffDaoSqlImpl();
            connection = ConnectionFactory.getConnection();
            deleteMember = connection.prepareStatement(sqlExpression);
            deleteMember.setInt(1, staffDao.getID(staffMember));
            deleteMember.execute();
        } catch (SQLException ex) {
            log.error("Cannot delete member: ", ex);
            throw new DAOException("Cannot delete member", ex);
        } finally {
            ClosingUtil.close(deleteMember);
            ClosingUtil.close(connection);
        }
    }

}

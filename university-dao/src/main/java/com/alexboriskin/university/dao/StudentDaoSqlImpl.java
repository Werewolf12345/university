package com.alexboriskin.university.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alexboriskin.university.domain.Address;
import com.alexboriskin.university.domain.DAOException;
import com.alexboriskin.university.domain.Group;
import com.alexboriskin.university.domain.Student;

public class StudentDaoSqlImpl extends StaffDaoSqlImpl implements StudentDao{
    private static final int NOT_EXISTING = -1;
    private static final Logger log = LogManager.getLogger();
    private DataSource dataSource;
    private GroupDao groupDao;
    private AddressDao addressDao;
    private StaffDao staffDao;
    
    public void setStaffDao(StaffDao staffDao) {
        this.staffDao = staffDao;
    }

    public void setAddressDao(AddressDao addressDao) {
        this.addressDao = addressDao;
    }

    public void setGroupDao(GroupDao groupDao) {
        this.groupDao = groupDao;
    }
    
    @Override
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
      
    @Override
    public void save(Student student, Group group) throws DAOException {
        Connection connection = null;
        PreparedStatement insertStudent = null;
        int groupID = groupDao.getID(group);
        int addressID = addressDao.getID(student);
        String sqlExpression = null;
        
        if(addressID == NOT_EXISTING){
            addressDao.save(student);
            addressID = addressDao.getID(student);
        }
         
        try {
            sqlExpression 
            = "INSERT INTO students (group_id, first_name, last_name, address_id) VALUES (?, ?, ?, ?);";
            connection = dataSource.getConnection();
            
            insertStudent = connection.prepareStatement(sqlExpression);
            
            connection.setAutoCommit(false);
            
            insertStudent.setInt(1, groupID);
            insertStudent.setString(2, student.getFirstName());
            insertStudent.setString(3, student.getLastName());
            insertStudent.setInt(4, addressID);
            insertStudent.executeUpdate();
            connection.commit();
            
            connection.setAutoCommit(true);
        } catch (SQLException ex) {
            log.error("Cannot save student: " + ex);
            throw new DAOException("Cannot save student", ex);
        } finally {
            ClosingUtil.close(insertStudent);
            ClosingUtil.close(connection);
        }
        
    }
    
    /**
     * @return Student object from DB or "empty" Student object if not exists
     * @throws DAOException 
     * 
     * */
    @Override
    public Student get(int studentID) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sqlExpression 
                = "SELECT first_name, last_name, address_id FROM students WHERE student_id = ?;";
        
        String firstName = "";
        String lastName = "";
        int addressID = 0;
        String state = "";
        String address = "";
        int zip = 0; 
        
        try {
            /*ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Module.xml");
            setDataSource((DataSource)context.getBean("dataSource"));*/
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(sqlExpression);
            preparedStatement.setInt(1, studentID);
            resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()) {
                firstName = resultSet.getString("first_name");
                lastName = resultSet.getString("last_name");
                addressID = resultSet.getInt("address_id");
            }
            
            ClosingUtil.close(preparedStatement);
            ClosingUtil.close(resultSet);
            
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
            log.error("Cannot get student: " + ex);
            throw new DAOException("Cannot get student", ex);
        } finally {
            ClosingUtil.close(resultSet);
            ClosingUtil.close(preparedStatement);
            ClosingUtil.close(connection);
        }
    return new Student(firstName, lastName, new Address(state, address, zip));
    }
    
    @Override
    public int getGroupID(Student student) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int groupID = NOT_EXISTING;
        int studentID = staffDao.getID(student);
        String sqlExpression 
                = "SELECT group_id FROM students WHERE student_id = ?;";
                
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(sqlExpression);
            preparedStatement.setInt(1, studentID);
            resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()) {
                groupID = resultSet.getInt("group_id");
            }
            
        } catch (SQLException ex) {
            log.error("Cannot get group ID for the student: " + ex);
            throw new DAOException("Cannot get group ID for the student", ex);
        } finally {
            ClosingUtil.close(resultSet);
            ClosingUtil.close(preparedStatement);
            ClosingUtil.close(connection);
        }
    return groupID;
    }
    
    @Override
    public void setGroupID(Student student, Group group) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int groupID = groupDao.getID(group);
        int studentID = staffDao.getID(student);
        String sqlExpression 
                = "UPDATE students SET group_id = ? WHERE student_id = ?;";
                
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(sqlExpression);
            preparedStatement.setInt(1, groupID);
            preparedStatement.setInt(2, studentID);
            preparedStatement.execute();
                                  
        } catch (SQLException ex) {
            log.error("Cannot set group ID for the student: " + ex);
            throw new DAOException("Cannot set group ID for the student", ex);
        } finally {
            ClosingUtil.close(preparedStatement);
            ClosingUtil.close(connection);
        }
    
    }
}
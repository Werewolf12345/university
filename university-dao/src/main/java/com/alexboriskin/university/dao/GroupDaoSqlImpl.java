package com.alexboriskin.university.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alexboriskin.university.domain.DAOException;
import com.alexboriskin.university.domain.Group;
import com.alexboriskin.university.domain.Professor;
import com.alexboriskin.university.domain.Student;

public class GroupDaoSqlImpl implements GroupDao {
    private static final int NOT_EXISTING = -1;
    private static final Logger log = LogManager.getLogger();
    private DataSource dataSource;
    private ProfessorDao professorDao;
    private StaffDao staffDao;
    private StudentDao studentDao;
        
    public void setStudentDao(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    public void setStaffDao(StaffDao staffDao) {
        this.staffDao = staffDao;
    }

    public void setProfessorDao(ProfessorDao professorDao) {
        this.professorDao = professorDao;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * @return group ID from DB or -1 if not found
     * @throws DAOException 
     * */
    @Override
    public  int getID(Group group) throws DAOException {
        Connection connection = null;
        PreparedStatement selectStatement = null;
        ResultSet resultSet = null;
        int groupID = NOT_EXISTING;
        String sqlExpression = "SELECT group_id FROM groups WHERE UPPER(name) = UPPER(?);";

        try {
            connection = dataSource.getConnection();
            selectStatement = connection.prepareStatement(sqlExpression);
            selectStatement.setString(1, group.getName());
            resultSet = selectStatement.executeQuery();

            while (resultSet.next()) {
                groupID = resultSet.getInt("group_id");
            }

        } catch (SQLException ex) {
            log.error("Cannot ID for the group: " + ex);
            throw new DAOException("Cannot ID for the group", ex);
        } finally {
            ClosingUtil.close(resultSet);
            ClosingUtil.close(selectStatement);
            ClosingUtil.close(connection);
        }
        return groupID;
    }

    @Override
    public  void save(Group group) throws DAOException {
        int groupID = getID(group);
        Professor counselor = group.getCounselor();
        int counselorID = staffDao.getID(counselor);
        Set<Student> studentsInGroup = group.getStudents();

        if (counselorID == NOT_EXISTING) {
            professorDao.save(counselor);
            counselorID = staffDao.getID(counselor);
        }

        if (groupID == NOT_EXISTING) {
            Connection connection = null;
            PreparedStatement preparedStatement = null;
            String sqlExpression = "INSERT INTO groups (name, counselor_ID) VALUES (?, ?);";
            

            try {
                connection = dataSource.getConnection();
                connection.setAutoCommit(false);

                preparedStatement = connection.prepareStatement(sqlExpression);
                preparedStatement.setString(1, group.getName());
                preparedStatement.setInt(2, counselorID);
                preparedStatement.executeUpdate();
                connection.commit();

                connection.setAutoCommit(true);

            } catch (SQLException ex) {
                log.error("Cannot save group: " + ex);
                throw new DAOException("Cannot save group", ex);
            } finally {
                ClosingUtil.close(preparedStatement);
                ClosingUtil.close(connection);
            }
        }

        for (Student current : studentsInGroup) {
            if (staffDao.getID(current) == NOT_EXISTING) {
                studentDao.save(current, group);
            } else {
                studentDao.setGroupID(current, group);
            }
        }

    }
    
    /**
     * @return Group object from DB or "empty" Group object if not exists
     * @throws DAOException 
     * 
     * */
    @Override
    public Group get(int groupID) throws DAOException {
        String groupName = "";
        Group result = new Group(groupName);
        int counselorID = NOT_EXISTING;
        
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sqlExpression = "SELECT name, counselor_id FROM groups WHERE group_id = ?;";
        
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(sqlExpression);
            preparedStatement.setInt(1, groupID);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                groupName = resultSet.getString("name");
                counselorID = resultSet.getInt("counselor_id");
            }

            ClosingUtil.close(resultSet);
            ClosingUtil.close(preparedStatement);
                       
            result.setCounselor(professorDao.get(counselorID));

            if (!groupName.equals("")) {
                result.setName(groupName);
                sqlExpression = "SELECT student_id FROM students WHERE group_id = ?;";
                preparedStatement = connection.prepareStatement(sqlExpression);
                preparedStatement.setInt(1, groupID);
                resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    result.add(studentDao.get(resultSet.getInt("student_id")));
                }
            }

        } catch (SQLException ex) {
            log.error("Cannot get group: " + ex);
            throw new DAOException("Cannot get group", ex);
        } finally {
            ClosingUtil.close(resultSet);
            ClosingUtil.close(preparedStatement);
            ClosingUtil.close(connection);
        }

        return result;
    }
}

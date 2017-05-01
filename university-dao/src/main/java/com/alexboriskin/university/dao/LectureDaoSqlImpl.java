package com.alexboriskin.university.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alexboriskin.university.domain.Auditorium;
import com.alexboriskin.university.domain.DAOException;
import com.alexboriskin.university.domain.Lecture;
import com.alexboriskin.university.domain.Professor;
import com.alexboriskin.university.domain.Staff;
import com.alexboriskin.university.domain.Student;

public class LectureDaoSqlImpl implements LectureDao {
    private static final int NOT_EXISTING = -1;
    private static final Logger log = LogManager.getLogger();
    private DataSource dataSource;
    private ProfessorDao professorDao;
    private StaffDao staffDao;
    private StudentDao studentDao;
    private GroupDao groupDao;
        
    public void setGroupDao(GroupDao groupDao) {
        this.groupDao = groupDao;
    }
        
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


    @Override
    public void save(Lecture lecture) throws DAOException {

        Connection connection = null;
        PreparedStatement selectStatement = null;
        String sqlExpression = "INSERT INTO lectures (date_and_time, lector_id, group_id, auditorium, name)"
                + " VALUES (?, ?, ?, ?, ?);";

        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            
            selectStatement = connection.prepareStatement(sqlExpression);
            selectStatement.setTimestamp(1, new Timestamp(lecture
                    .getDateAndTime().getTimeInMillis()));
            selectStatement.setInt(2, staffDao.getID(lecture.getLector()));
            selectStatement.setInt(3, groupDao.getID(lecture.getGroup()));
            selectStatement.setInt(4, lecture.getAuditorium().getNumber());
            selectStatement.setString(5, lecture.getName());
            selectStatement.executeUpdate();
            connection.commit();

            connection.setAutoCommit(true);
        } catch (SQLException ex) {
            log.error("Cannot save lecture: " + ex);
            throw new DAOException("Cannot save lecture", ex);
        } finally {
            ClosingUtil.close(selectStatement);
            ClosingUtil.close(connection);
        }

    }

    @Override
    public Lecture get(int lectureID) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sqlExpression = "SELECT name, group_id, auditorium, date_and_time, lector_id FROM"
                + " lectures WHERE lecture_id = ?;";

        String name = "";
        int lectorID = NOT_EXISTING;
        int auditoriumNo = NOT_EXISTING;
        int groupID = NOT_EXISTING;
        Calendar dateAndTime = Calendar.getInstance();
        
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(sqlExpression);
            preparedStatement.setInt(1, lectureID);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                name = resultSet.getString("name");
                groupID = resultSet.getInt("group_id");
                auditoriumNo = resultSet.getInt("auditorium");
                lectorID = resultSet.getInt("lector_id");
                dateAndTime.setTimeInMillis(resultSet.getTimestamp(
                        "date_and_time").getTime());
            }

        } catch (SQLException ex) {
            log.error("Cannot get lecture: " + ex);
            throw new DAOException("Cannot get lecture", ex);
        } finally {
            ClosingUtil.close(resultSet);
            ClosingUtil.close(preparedStatement);
            ClosingUtil.close(connection);
        }

        return new Lecture(name, dateAndTime, professorDao.get(lectorID),
                groupDao.get(groupID), new Auditorium(auditoriumNo));
    }

    @Override
    public void delete(int lectureID) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String sqlExpression = "DELETE FROM lectures WHERE lecture_id = ?;";

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(sqlExpression);
            preparedStatement.setInt(1, lectureID);
            preparedStatement.execute();

        } catch (SQLException ex) {
            log.error("Cannot delete lecture: " + ex);
            throw new DAOException("Cannot delete lecture", ex);
        } finally {
            ClosingUtil.close(preparedStatement);
            ClosingUtil.close(connection);
        }

    }

    @Override
    public int getID(Lecture lecture) throws DAOException {
        Connection connection = null;
        PreparedStatement selectStatement = null;
        ResultSet resultSet = null;
        int lectureID = NOT_EXISTING;
        String sqlExpression = "SELECT lecture_id FROM lectures WHERE lector_id = ? AND date_and_time = ?;";

        try {
            connection = dataSource.getConnection();
            selectStatement = connection.prepareStatement(sqlExpression);
            selectStatement.setInt(1, staffDao.getID(lecture.getLector()));
            selectStatement.setTimestamp(2, new Timestamp(lecture
                    .getDateAndTime().getTimeInMillis()));
            resultSet = selectStatement.executeQuery();

            while (resultSet.next()) {
                lectureID = resultSet.getInt(1);
            }

        } catch (SQLException ex) {
            log.error("Cannot get ID for the lecture: " + ex);
            throw new DAOException("Cannot get ID for the lecture", ex);
        } finally {
            ClosingUtil.close(resultSet);
            ClosingUtil.close(selectStatement);
            ClosingUtil.close(connection);
        }
        return lectureID;
    }

    @Override
    public void update(int lectureID, Lecture lecture) throws DAOException {

        try {
            delete(lectureID);
            save(lecture);
        } catch (DAOException ex) {
            log.error("Cannot update lecture: " + ex);
            throw new DAOException("Cannot update lecture", ex);
        }
    }

    @Override
    public Set<Lecture> getLectures(Staff individual, Calendar start,
            Calendar end) throws DAOException {
        Set<Lecture> result = new HashSet<>();
        Set<Integer> lectureId = new HashSet<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sqlExpression = null;
        int iD = NOT_EXISTING;

        if (individual instanceof Professor) {
            iD = staffDao.getID(individual);
            sqlExpression = "SELECT lecture_id FROM lectures WHERE lector_id = ?"
                    + " AND (date_and_time BETWEEN ? AND ?);";

        } else if (individual instanceof Student) {
            iD = studentDao.getGroupID((Student) individual);
            sqlExpression = "SELECT lecture_id FROM lectures WHERE group_id = ?"
                    + " AND (date_and_time BETWEEN ? AND ?);";
        }

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(sqlExpression);
            preparedStatement.setInt(1, iD);
            preparedStatement.setTimestamp(2,
                    new Timestamp(start.getTimeInMillis()));
            preparedStatement.setTimestamp(3,
                    new Timestamp(end.getTimeInMillis()));
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                lectureId.add(resultSet.getInt("lecture_id"));
            }

        } catch (SQLException ex) {
            log.error("Cannot get lectures list: " + ex);
            throw new DAOException("Cannot get lectures list", ex);
        } finally {
            ClosingUtil.close(resultSet);
            ClosingUtil.close(preparedStatement);
            ClosingUtil.close(connection);
        }

        for (Integer currentId : lectureId) {
            result.add(get(currentId));
        }

        return result;
    }
}

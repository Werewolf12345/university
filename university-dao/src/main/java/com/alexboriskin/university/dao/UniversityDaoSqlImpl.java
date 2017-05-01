package com.alexboriskin.university.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alexboriskin.university.domain.DAOException;
import com.alexboriskin.university.domain.Faculty;
import com.alexboriskin.university.domain.Group;

public class UniversityDaoSqlImpl implements UniversityDao{
    private static final Logger log = LogManager.getLogger();
    private DataSource dataSource;
    private GroupDao groupDao;
    private ProfessorDao professorDao;
    
    
    public void setProfessorDao(ProfessorDao professorDao) {
        this.professorDao = professorDao;
    }

    public void setGroupDao(GroupDao groupDao) {
        this.groupDao = groupDao;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Set<Group> getGroupsSet() throws DAOException {
        Set<Group> result = new HashSet<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sqlExpression = "SELECT group_id FROM groups;";

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(sqlExpression);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                result.add(groupDao.get(resultSet.getInt("group_id")));
            }

        } catch (SQLException ex) {
            log.error("Cannot get groups: " + ex);
            throw new DAOException("Cannot get groups", ex);
        } finally {
            ClosingUtil.close(resultSet);
            ClosingUtil.close(preparedStatement);
            ClosingUtil.close(connection);
        }

        return result;
    }

    @Override
    public Faculty getProfessorsSet() throws DAOException {
        Faculty result = new Faculty();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sqlExpression = "SELECT professor_id FROM professors;";
        
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(sqlExpression);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
               result.add(professorDao.get(resultSet.getInt("professor_id")));
            }

        } catch (SQLException ex) {
            log.error("Cannot get professors: " + ex);
            throw new DAOException("Cannot get professors", ex);
        } finally {
            ClosingUtil.close(resultSet);
            ClosingUtil.close(preparedStatement);
            ClosingUtil.close(connection);
        }

        return result;
    }

}

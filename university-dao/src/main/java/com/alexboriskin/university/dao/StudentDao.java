package com.alexboriskin.university.dao;

import javax.sql.DataSource;

import com.alexboriskin.university.domain.*;

public interface StudentDao {
    public void setDataSource(DataSource dataSource);
    
    public void save(Student student, Group group) throws DAOException;

    public Student get(int studentID) throws DAOException;

    public int getGroupID(Student student) throws DAOException;

    public void setGroupID(Student student, Group group) throws DAOException;
}

package com.alexboriskin.university.dao;

import com.alexboriskin.university.domain.*;

public interface StudentDao {
    public void save(Student student, Group group) throws DAOException;

    public Student get(int studentID) throws DAOException;

    public int getGroupID(Student student) throws DAOException;

    public void setGroupID(Student student, Group group) throws DAOException;
}

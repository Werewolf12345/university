package com.alexboriskin.university.dao;

import java.util.Set;

import com.alexboriskin.university.domain.*;

public interface UniversityDao {
    public Set<Group> getGroupsSet() throws DAOException;

    public Faculty getProfessorsSet() throws DAOException;
}

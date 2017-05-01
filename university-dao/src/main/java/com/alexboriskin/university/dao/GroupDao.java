package com.alexboriskin.university.dao;

import com.alexboriskin.university.domain.DAOException;
import com.alexboriskin.university.domain.Group;

public interface GroupDao {
   
    public int getID(Group group) throws DAOException;

    public void save(Group group) throws DAOException;

    public Group get(int groupID) throws DAOException;
}

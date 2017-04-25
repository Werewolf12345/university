package com.alexboriskin.university.dao;

import com.alexboriskin.university.domain.DAOException;
import com.alexboriskin.university.domain.Staff;

public interface AddressDao {
    public int getID(Staff staffMember) throws DAOException;

    public void save(Staff staffMember) throws DAOException;
}

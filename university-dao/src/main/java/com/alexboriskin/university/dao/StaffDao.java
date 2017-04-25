package com.alexboriskin.university.dao;

import com.alexboriskin.university.domain.*;

public interface StaffDao {
    public int getID(Staff staffMember) throws DAOException;

    public void updateNameAndAddress(Staff staffMemberOldData,
            Staff staffMemberNewData) throws DAOException;

    public void delete(Staff staffMember) throws DAOException;
}

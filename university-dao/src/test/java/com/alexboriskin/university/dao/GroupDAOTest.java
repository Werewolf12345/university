package com.alexboriskin.university.dao;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.alexboriskin.university.domain.DAOException;
import com.alexboriskin.university.domain.Group;

public class GroupDAOTest {

    @Test
    public void testGetID() throws DAOException {
        Group groupInDB = new Group("m93");
        Group groupNotInDB = new Group("f15");
        
        GroupDao groupDao = new GroupDaoSqlImpl();
        
        assertEquals(4, groupDao.getID(groupInDB));
        assertEquals(-1, groupDao.getID(groupNotInDB));
    }

}

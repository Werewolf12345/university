package com.alexboriskin.university.dao;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.alexboriskin.university.domain.Address;
import com.alexboriskin.university.domain.Address.US;
import com.alexboriskin.university.domain.DAOException;
import com.alexboriskin.university.domain.Student;

public class AddressDAOTest {

    @Test
    public void testGetID() throws DAOException {
        Student studentInDB = new Student("Alex", "Bor", new Address(US.ILLINOIS, "920 Cherry Valley RD", 60061));
        Student studentNotInDB = new Student("Azer", "Bajanov", new Address(US.ILLINOIS, "Ghj Arj", 60061));
        
        AddressDao addressDao = new AddressDaoSqlImpl();
        
        assertEquals(1, addressDao.getID(studentInDB));
        assertEquals(-1, addressDao.getID(studentNotInDB));
    }
        
}

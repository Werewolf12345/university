package com.alexboriskin.university.dao;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.alexboriskin.university.domain.Address;
import com.alexboriskin.university.domain.Address.US;
import com.alexboriskin.university.domain.DAOException;
import com.alexboriskin.university.domain.Group;
import com.alexboriskin.university.domain.Student;

public class StudentDAOTest {


    @Test
    public void testGet() throws DAOException {
        StudentDao studentDao = new StudentDaoSqlImpl();
        StaffDao staffDao = new StaffDaoSqlImpl();
        Student studentExpected = new Student("Alex", "Bor", new Address(US.ILLINOIS, "920 Cherry Valley RD", 60061));
        int studentID = staffDao.getID(studentExpected);
        Student studentInDB = studentDao.get(studentID);
        assertEquals(studentExpected, studentInDB);
        
        
        Student studentNotInDB = studentDao.get(470); // non existing ID
        Student studentEmpty = new Student("", "", new Address("", "", 0)); // empty student object
        assertEquals(studentEmpty, studentNotInDB);
        
        studentInDB = new Student("AAA", "BBB", new Address("MO", "120 Elm st Ville", 50051));
        studentDao.save(studentInDB, new Group("m93"));
        assertEquals(studentInDB, studentDao.get(staffDao.getID(studentInDB)));
        staffDao.delete(studentInDB);
    }
    
    @Test
    public void testSaveAndDelete() throws DAOException {
        StudentDao studentDao = new StudentDaoSqlImpl();
        StaffDao staffDao = new StaffDaoSqlImpl();                
        Student studentEmpty = new Student("", "", new Address("", "", 0)); // empty student object
        Student studentToSave = new Student("AAA", "BBB", new Address("MO", "120 Elm st Ville", 50051));
        studentDao.save(studentToSave, new Group("m93"));
        assertEquals(studentToSave, studentDao.get(staffDao.getID(studentToSave)));
        staffDao.delete(studentToSave);
        assertEquals(studentEmpty, studentDao.get(staffDao.getID(studentToSave)));
    }

}

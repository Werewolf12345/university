package com.alexboriskin.university.dao;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.alexboriskin.university.domain.Address;
import com.alexboriskin.university.domain.Address.US;
import com.alexboriskin.university.domain.DAOException;
import com.alexboriskin.university.domain.Professor;
import com.alexboriskin.university.domain.Student;

public class StaffDAOTest {

    @Test
    public void testGetID() throws DAOException {
        Student studentInDB = new Student("Alex", "Bor", new Address(US.ILLINOIS, "920 Cherry Valley RD", 60061));
        Student studentNotInDB = new Student("JOE", "doe", new Address(US.ILLINOIS, "212 elm STREET mettawa", 60061));
        
        Professor professorInDB = new Professor("Adam", "Smith", new Address(US.ILLINOIS, "920 Cherry Valley RD", 60061));
        Professor professorNotInDB = new Professor("JOE", "doe", new Address(US.ILLINOIS, "212 elm STREET mettawa", 60061));
        
        StaffDao staffDao = new StaffDaoSqlImpl();
        
        assertEquals(47, staffDao.getID(studentInDB));
        assertEquals(-1, staffDao.getID(studentNotInDB));
        
        assertEquals(1, staffDao.getID(professorInDB));
        assertEquals(-1, staffDao.getID(professorNotInDB));
    }
    
    @Test
    public void testUpdate() throws DAOException {
        Student studentInDB = new Student("Alex", "Bor", new Address(US.ILLINOIS, "920 Cherry Valley RD", 60061));
        Student studentNewInfo = new Student("Alexey", "Boriskin", new Address(US.ILLINOIS, "212 elm STREET mettawa", 60061));
        
        Professor professorInDB = new Professor("Adam", "Smith", new Address(US.ILLINOIS, "920 Cherry Valley RD", 60061));
        Professor professorNewInfo = new Professor("JOE", "doe", new Address(US.ILLINOIS, "214 xxxxx yyyyyy", 70061));
        
        StaffDao staffDao = new StaffDaoSqlImpl();
        
        staffDao.updateNameAndAddress(studentInDB, studentNewInfo);
        staffDao.updateNameAndAddress(professorInDB, professorNewInfo);
        
        ProfessorDao professorDao = new ProfessorDaoSqlImpl();
        StudentDao studentDao = new StudentDaoSqlImpl();
        
        assertEquals(studentNewInfo, studentDao.get(staffDao.getID(studentNewInfo)));
        assertEquals(professorNewInfo, professorDao.get(staffDao.getID(professorNewInfo)));
        
        staffDao.updateNameAndAddress(studentNewInfo, studentInDB);
        staffDao.updateNameAndAddress(professorNewInfo, professorInDB);
    }


}

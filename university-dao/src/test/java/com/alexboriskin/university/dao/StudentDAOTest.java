package com.alexboriskin.university.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.alexboriskin.university.domain.Address;
import com.alexboriskin.university.domain.Address.US;
import com.alexboriskin.university.domain.Group;
import com.alexboriskin.university.domain.Professor;
import com.alexboriskin.university.domain.Student;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:Spring-Module.xml")
@Transactional
public class StudentDAOTest {
    
    @Autowired
    StudentDao studentDao;

    @Test
    public void testSaveGetDelete() {
                        
        Professor professor1 = new Professor("Adam", "Smith", new Address(US.ILLINOIS, "920 Cherry Valley RD", 60061));
        Professor professor2 = new Professor("JOE", "doe", new Address(US.ILLINOIS, "212 elm STREET mettawa", 66661));
        
        Group group1 = new Group("group1", professor1);
        Group group2 = new Group("group2", professor2);
        
        Address address1 = new Address(US.ALABAMA, "921 Valley RD", 50055);
        Student student1 = new Student("Karl", "Marx", address1);
        group1.add(student1);
        Student student2 = new Student("Fridrich", "Engels", address1);
        group2.add(student2);
        
        studentDao.save(student1);
        studentDao.save(student2);
        int student1Id = student1.getId();
        int student2Id = student2.getId();
        
        Student student1InDb = studentDao.get(student1Id);
        assertEquals(student1, student1InDb);
        
        student1.setFirstName("Vasya");
        student1.setLastName("Pupkin");
        group2.add(student1);
        studentDao.save(student1);
        
        Student student1Expected = new Student("Vasya", "Pupkin", new Address(US.ALABAMA, "921 Valley RD", 50055));
        student1InDb = studentDao.get(student1Id);
        assertEquals(student1Expected, student1InDb);
        
        Address address2 = new Address(US.CALIFORNIA, "666 VHell RD", 70066);
        student1.setAddress(address2);
        studentDao.save(student1);
        student1Expected = new Student("Vasya", "Pupkin", new Address(US.CALIFORNIA, "666 VHell RD", 70066));
        student1InDb = studentDao.get(student1Id);
        assertEquals(student1Expected, student1InDb);
        
        studentDao.delete(student1Id);
        student1InDb = studentDao.get(student1Id);
        assertNull(student1InDb);
        
        studentDao.delete(student2Id);
        Student studentTwoInDb = studentDao.get(student2Id);
        assertNull(studentTwoInDb);
    }
}

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
import com.alexboriskin.university.domain.Professor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:Spring-Module.xml")
@Transactional
public class ProfessorDAOTest {
    
    @Autowired
    ProfessorDao professorDao;

    @Test
    public void testSaveGetDelete() {
                        
        Professor professor1 = new Professor("Aaa", "Bbb", new Address(US.ARIZONA, "Address1", 12345));
        Professor professor2 = new Professor("Ccc", "Ddd", new Address(US.ARKANSAS, "Address2", 67893));
                       
        professorDao.save(professor1);
        professorDao.save(professor2);
        int professor1Id = professor1.getId();
        int professor2Id = professor2.getId();
        
        Professor professor1InDb = professorDao.get(professor1Id);
        assertEquals(professor1, professor1InDb);
        
        professor1.setFirstName("Vasya");
        professor1.setLastName("Pupkin");
        professorDao.save(professor1);
        
        Professor professor1Expected = new Professor("Vasya", "Pupkin", new Address(US.ARIZONA, "Address1", 12345));
        professor1InDb = professorDao.get(professor1Id);
        assertEquals(professor1Expected, professor1InDb);
                
        Address address2 = new Address(US.CALIFORNIA, "666 VHell RD", 70066);
        professor1.setAddress(address2);
        professorDao.save(professor1);
        professor1Expected = new Professor("Vasya", "Pupkin", new Address(US.CALIFORNIA, "666 VHell RD", 70066));
        professor1InDb = professorDao.get(professor1Id);
        assertEquals(professor1Expected, professor1InDb);
        
        professorDao.delete(professor1Id);
        professor1InDb = professorDao.get(professor1Id);
        assertNull(professor1InDb);
        
        professorDao.delete(professor2Id);
        Professor professorTwoInDb = professorDao.get(professor2Id);
        assertNull(professorTwoInDb);
    }
}

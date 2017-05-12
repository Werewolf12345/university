package com.alexboriskin.university.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.GregorianCalendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.alexboriskin.university.domain.Address;
import com.alexboriskin.university.domain.Address.US;
import com.alexboriskin.university.domain.Group;
import com.alexboriskin.university.domain.Lecture;
import com.alexboriskin.university.domain.Professor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:Spring-Module.xml")
@Transactional
public class LectureDAOTest {

    @Autowired
    LectureDao lectureDao;

    @Test
    public void testSaveGetDelete() {

        Professor professor = new Professor("Aaa", "Bbb", new Address(
                US.ARIZONA, "Address1", 12345));
        Group group = new Group("Group", professor);
       

        Lecture lectureToSave = new Lecture("Lecture", new GregorianCalendar(
                2017, 3, 5, 12, 0), professor, group, 13);

        lectureDao.save(lectureToSave);
        int savedLectureID = lectureToSave.getId();
        Lecture lectureInDB = lectureDao.get(savedLectureID);
        assertEquals(lectureToSave, lectureInDB);

        lectureDao.delete(savedLectureID);
        lectureInDB = lectureDao.get(savedLectureID);
        assertNull(lectureInDB);
    }

}

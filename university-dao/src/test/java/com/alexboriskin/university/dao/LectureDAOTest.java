package com.alexboriskin.university.dao;

import static org.junit.Assert.assertEquals;

import java.util.GregorianCalendar;

import org.junit.Test;

import com.alexboriskin.university.domain.Auditorium;
import com.alexboriskin.university.domain.DAOException;
import com.alexboriskin.university.domain.Group;
import com.alexboriskin.university.domain.Lecture;
import com.alexboriskin.university.domain.Professor;

public class LectureDAOTest {

    @Test
    public void testSaveAndDelete() throws DAOException {
        GroupDao groupDao = new GroupDaoSqlImpl();
        Group group = groupDao.get(4);
        ProfessorDao professorDao = new ProfessorDaoSqlImpl();

        Professor professor = professorDao.get(1);
        Lecture lectureToSave = new Lecture("Lecture1", new GregorianCalendar(2017, 3, 5, 12, 0), professor, group, new Auditorium(13));
        
        LectureDao lectureDao = new LectureDaoSqlImpl(); 
        
        lectureDao.save(lectureToSave);
        int savedLectureID = lectureDao.getID(lectureToSave);
        Lecture lectureInDB = lectureDao.get(savedLectureID);
        assertEquals(lectureToSave, lectureInDB);
        
        lectureDao.delete(savedLectureID);
        
        lectureInDB = lectureDao.get(savedLectureID);
        assertEquals(-1, lectureDao.getID(lectureToSave));
       }

}

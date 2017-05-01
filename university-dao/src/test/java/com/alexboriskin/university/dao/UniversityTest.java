package com.alexboriskin.university.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.Set;

import org.junit.Test;

import com.alexboriskin.university.domain.Auditorium;
import com.alexboriskin.university.domain.DAOException;
import com.alexboriskin.university.domain.Faculty;
import com.alexboriskin.university.domain.Group;
import com.alexboriskin.university.domain.Lecture;
import com.alexboriskin.university.domain.Professor;
import com.alexboriskin.university.domain.Student;

public class UniversityTest {

    @Test
    public void testGetSchedule() throws DAOException {
        UniversityDao testUniversity = new UniversityDaoSqlImpl();
   
        Group testGroup = testUniversity.("m93");
        Faculty faculty = testUniversity.getAllProfessors();
        Professor professor1 = faculty.find("Adam", "Smith", 60061);
        Student student1 = testGroup.find("Alex", "Bor", 60061);
        
        
        Lecture lecture1 = new Lecture("Lecture1", new GregorianCalendar(2017, 3, 5, 12, 0), professor1, testGroup, new Auditorium(13));
        Lecture lecture2 = new Lecture("Lecture2", new GregorianCalendar(2017, 3, 5, 14, 0), professor1, testGroup, new Auditorium(14));
        Lecture lecture3 = new Lecture("Lecture3", new GregorianCalendar(2017, 3, 5, 16, 0), professor1, testGroup, new Auditorium(15));
        
        
        testUniversity.addLecture(lecture1);
        testUniversity.addLecture(lecture2);
        testUniversity.addLecture(lecture3);
        
     // Testing creation of schedule for professor
        Set<Lecture> testSchedule = testUniversity.getSchedule(professor1, new GregorianCalendar(2017, 3, 4, 12, 0)
                                                                         , new GregorianCalendar(2017, 3, 6, 12, 0));
        assertTrue(testSchedule.containsAll(Arrays.asList(new Lecture[] { lecture1, lecture2, lecture3})));
        
        testSchedule = testUniversity.getSchedule(professor1, new GregorianCalendar(2017, 3, 6, 12, 0)
                                                            , new GregorianCalendar(2017, 3, 7, 12, 0));
        assertTrue(testSchedule.isEmpty());
        
     // Testing creation of schedule for student
        testSchedule = testUniversity.getSchedule(student1, new GregorianCalendar(2017, 3, 4, 12, 0)
                                                          , new GregorianCalendar(2017, 3, 6, 12, 0));
        assertEquals(3, testSchedule.size());
        assertTrue(testSchedule.containsAll(Arrays.asList(new Lecture[] { lecture1, lecture2, lecture3})));
        
        testUniversity.removeLecture(lecture1);
        testUniversity.removeLecture(lecture2);
        testUniversity.removeLecture(lecture3);
        
        testSchedule = testUniversity.getSchedule(professor1, new GregorianCalendar(2017, 3, 4, 12, 0)
                                                            , new GregorianCalendar(2017, 3, 6, 12, 0));
        assertTrue(testSchedule.isEmpty());
                      
        
       }
}

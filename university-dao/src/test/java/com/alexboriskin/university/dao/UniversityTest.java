package com.alexboriskin.university.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.Set;

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
import com.alexboriskin.university.domain.Student;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:Spring-Module.xml")
@Transactional
public class UniversityTest {

    @Autowired
    UniversityDao universityDao;
    @Autowired
    StudentDao studentDao;
    @Autowired
    LectureDao lectureDao;
    @Autowired
    GroupDao groupDao;
    @Autowired
    ProfessorDao professorDao;
    @Autowired
    AddressDao addressDao;

    @Test
    public void testGetSchedule() {

        Professor professor1 = new Professor("Adam", "Smith", new Address(
                US.ILLINOIS, "920 Cherry Valley RD", 60061));
        Group group1 = new Group("group12", professor1);
        Address address1 = new Address(US.ALABAMA, "921 Valley RD", 50055);
        Student student1 = new Student("Karl", "Marx", address1);
        group1.add(student1);

        studentDao.save(student1);

        Lecture lecture1 = new Lecture("Lecture1", new GregorianCalendar(2017,
                3, 5, 12, 0), professor1, group1, 13);
        Lecture lecture2 = new Lecture("Lecture2", new GregorianCalendar(2017,
                3, 5, 14, 0), professor1, group1, 14);
        Lecture lecture3 = new Lecture("Lecture3", new GregorianCalendar(2017,
                3, 5, 16, 0), professor1, group1, 15);

        lectureDao.save(lecture1);
        lectureDao.save(lecture2);
        lectureDao.save(lecture3);

        // Testing creation of schedule for professor
        Set<Lecture> testSchedule = universityDao.getSchedule(professor1,
                new GregorianCalendar(2017, 3, 4, 12, 0),
                new GregorianCalendar(2017, 3, 6, 12, 0));
        assertTrue(testSchedule.containsAll(Arrays.asList(new Lecture[] {
                lecture1, lecture2, lecture3 })));

        testSchedule = universityDao.getSchedule(professor1,
                new GregorianCalendar(2017, 3, 6, 12, 0),
                new GregorianCalendar(2017, 3, 7, 12, 0));
        assertTrue(testSchedule.isEmpty());

        // Testing creation of schedule for student
        testSchedule = universityDao.getSchedule(student1,
                new GregorianCalendar(2017, 3, 4, 12, 0),
                new GregorianCalendar(2017, 3, 6, 12, 0));
        assertEquals(3, testSchedule.size());
        assertTrue(testSchedule.containsAll(Arrays.asList(new Lecture[] {
                lecture1, lecture2, lecture3 })));

        lectureDao.delete(lecture1.getId());
        lectureDao.delete(lecture2.getId());
        lectureDao.delete(lecture3.getId());

        testSchedule = universityDao.getSchedule(professor1,
                new GregorianCalendar(2017, 3, 4, 12, 0),
                new GregorianCalendar(2017, 3, 6, 12, 0));
        assertTrue(testSchedule.isEmpty());
        studentDao.delete(student1.getId());
        groupDao.delete(group1.getId());
        professorDao.delete(professor1.getId());
    }

    @Test
    public void testGetGroupsSet() {
        Set<Group> groups = universityDao.getGroupsSet();

        assertFalse(groups.isEmpty());
       
    }
}

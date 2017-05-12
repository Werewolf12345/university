package com.alexboriskin.university.dao;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AddressDAOTest.class, GroupDAOTest.class, LectureDAOTest.class,
        ProfessorDAOTest.class, StudentDAOTest.class, UniversityTest.class })
public class AllTests {

}

package com.alexboriskin.university.domain;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AddressTest.class, FacultyTest.class, GroupTest.class,
        ProfessorTest.class, StudentTest.class })
public class AllTests {

}

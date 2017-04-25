package com.alexboriskin.university.domain;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.alexboriskin.university.domain.Address.US;

public class StudentTest {

    @Test
    public void testEqualsObject() {
        Student student1 = new Student("Joe", "Doe", new Address(US.ILLINOIS, "212 Elm st, Mettawa", 60061));
        Student student2 = new Student("JOE", "doe", new Address(US.ILLINOIS, "212 elm STREET mettawa", 60061));
        Student student3 = new Student("Adam", "Smith", new Address(US.ILLINOIS, "214 elm STREET mettawa", 60061));
        
        assertTrue(student1.equals(student1));
        assertTrue(student1.equals(student2));
        assertFalse(student1.equals(student3));
    }


}

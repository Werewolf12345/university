package com.alexboriskin.university.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.alexboriskin.university.domain.Address.US;



public class FacultyTest {

    @Test
    public void testAdd() {
        Faculty faculty = new Faculty();
        Professor professor1 = new Professor("Joe", "Doe", new Address(US.ILLINOIS, "212 Elm st, Mettawa", 60061));
        Professor professor2 = new Professor("JOE", "doe", new Address(US.ILLINOIS, "212 elm ST, mettawa", 60061));
        Professor professor3 = new Professor("Adam", "Smith", new Address(US.ILLINOIS, "214 elm STREET mettawa", 60061));
        
        assertTrue(faculty.add(professor1));
        assertFalse(faculty.add(professor2));
        assertFalse(faculty.add(professor1));
        assertTrue(faculty.add(professor3));
    }

    @Test
    public void testFind() {
        Faculty faculty = new Faculty();
        Professor professor = new Professor("Joe", "Doe", new Address(US.ILLINOIS, "212 Elm st, Mettawa", 60061));
        faculty.add(professor);
                
        assertEquals(professor, faculty.find("joe", "DOE", 60061));

        try {
            faculty.find("joe", "DOE", 99999);
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException ex) {
            assertTrue(ex.getMessage().equals("joe DOE not found"));
        }
    }

    @Test
    public void testRemove() {
        Faculty faculty = new Faculty();
        Professor professor1 = new Professor("Joe", "Doe", new Address(US.ILLINOIS, "212 Elm st, Mettawa", 60061));
        Professor professor3 = new Professor("Adam", "Smith", new Address(US.ILLINOIS, "214 elm STREET mettawa", 60061));
        faculty.add(professor1);
        faculty.add(professor3);
        
                
        assertTrue(faculty.remove("JOE", "doe", 60061));
        assertFalse(faculty.remove("bob", "DOLAN", 60061));
        assertTrue(faculty.remove("ADAM", "smiTh", 60061));
        assertFalse(faculty.remove("ADAM", "smiTh", 60061));
    }


}

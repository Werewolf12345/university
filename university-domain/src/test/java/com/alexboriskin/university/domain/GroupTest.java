package com.alexboriskin.university.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.alexboriskin.university.domain.Address.US;

public class GroupTest {

    @Test
    public void testAdd() {
        Group group = new Group("GRP1");
        Student student1 = new Student("Joe", "Doe", new Address(US.ILLINOIS, "212 Elm st, Mettawa", 60061));
        Student student2 = new Student("JOE", "doe", new Address(US.ILLINOIS, "212 elm STREET mettawa", 60061));
        Student student3 = new Student("Adam", "Smith", new Address(US.ILLINOIS, "214 elm STREET mettawa", 60061));
        
        assertTrue(group.add(student1));
        assertFalse(group.add(student2));
        assertFalse(group.add(student1));
        assertTrue(group.add(student3));
    }

    @Test
    public void testFind() {
        Group group = new Group("GRP1");
        Student student = new Student("Joe", "Doe", new Address(US.ILLINOIS, "212 Elm st, Mettawa", 60061));
        group.add(student);

        assertEquals(student, group.find("joe", "DOE", 60061));
        
        try {
            group.find("joe", "DOE", 99999);
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException ex) {
            assertTrue(ex.getMessage().equals("joe DOE not found"));
        }
    }

    @Test
    public void testRemove() {
        Group group = new Group("GRP1");
        Student student1 = new Student("Joe", "Doe", new Address(US.ILLINOIS, "212 Elm st, Mettawa", 60061));
        Student student3 = new Student("Adam", "Smith", new Address(US.ILLINOIS, "214 elm STREET mettawa", 60061));
        group.add(student1);
        group.add(student3);
        
                
        assertTrue(group.remove("JOE", "doe", 60061));
        assertFalse(group.remove("bob", "DOLAN", 60061));
        assertTrue(group.remove("ADAM", "smiTh", 60061));
        assertFalse(group.remove("ADAM", "smiTh", 60061));
    }
}

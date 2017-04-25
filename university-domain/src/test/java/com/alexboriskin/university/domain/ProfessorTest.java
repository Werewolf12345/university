package com.alexboriskin.university.domain;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.alexboriskin.university.domain.Address.US;

public class ProfessorTest {

    @Test
    public void testEqualsObject() {
        Professor professor1 = new Professor("Joe", "Doe", new Address(US.ILLINOIS, "212 Elm st, Mettawa", 60061));
        Professor professor2 = new Professor("JOE", "doe", new Address(US.ILLINOIS, "212 elm STREET mettawa", 60061));
        Professor professor3 = new Professor("Adam", "Smith", new Address(US.ILLINOIS, "214 elm STREET mettawa", 60061));
        
        assertTrue(professor1.equals(professor1));
        assertTrue(professor1.equals(professor2));
        assertFalse(professor1.equals(professor3));
    }

}

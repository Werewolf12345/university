package com.alexboriskin.university.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.alexboriskin.university.domain.Address.US;

public class AddressTest {

    @Test
    public void testAddress() {
        assertEquals(null, US.parse(""));
        assertEquals(US.ALABAMA, US.parse("al"));
        assertEquals(null, US.parse("xxx"));
        assertEquals(US.ALABAMA, US.parse("ALABAMA"));
        assertEquals(US.ALABAMA, US.parse("us-Al"));
    }

}

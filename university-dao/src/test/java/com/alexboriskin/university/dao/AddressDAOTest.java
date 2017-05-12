package com.alexboriskin.university.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.alexboriskin.university.domain.Address;
import com.alexboriskin.university.domain.Address.US;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:Spring-Module.xml")
@Transactional
public class AddressDAOTest {

    @Autowired
    AddressDao addressDao;

    @Test
    public void testSaveGetDelete() {
        Address address = new Address(US.ALABAMA, "921 Valley RD", 50055);
        addressDao.save(address);
        int addressId = address.getId();
        
        Address addressExpected = new Address(US.ALABAMA, "921 Valley RD", 50055);
        address = addressDao.get(addressId);
        assertEquals(addressExpected, address);
        
        address.setZipCode(60061);
        address.setAddress("920 Cherry");
        address.setState(US.ILLINOIS);
        addressDao.update(address);
        
        addressExpected = new Address(US.ILLINOIS, "920 Cherry", 60061);
        address = addressDao.get(addressId);
        assertEquals(addressExpected, address);
        
        addressDao.delete(addressId);
        address = addressDao.get(addressId);
        assertNull(address);

    }
        
}

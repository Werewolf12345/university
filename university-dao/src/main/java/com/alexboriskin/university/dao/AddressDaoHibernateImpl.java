package com.alexboriskin.university.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate5.HibernateTemplate;

import com.alexboriskin.university.domain.Address;

public class AddressDaoHibernateImpl implements AddressDao {

    private static final Logger log = LogManager.getLogger();

    @Autowired
    private HibernateTemplate template;

    @Override
    public void setTemplate(HibernateTemplate template) {
        this.template = template;
    }

    @Override
    public HibernateTemplate getTemplate() {
        return this.template;
    }

    @Override
    public void save(Address address) {
        try {
            template.saveOrUpdate(address);
        } catch (DataAccessException e) {
            log.error("Cannot save address: " + e);
            throw e;
        }
    }

    @Override
    public Address get(int addressID) {
        try {
            return template.get(Address.class, addressID);
        } catch (DataAccessException e) {
            log.error("Cannot get address: " + e);
            throw e;
        }
    }

    @Override
    public void update(Address address) {
        try {
            template.saveOrUpdate(address);
        } catch (DataAccessException e) {
            log.error("Cannot update address: " + e);
            throw e;
        }
    }

    @Override
    public void delete(int id) {
        try {
            template.delete(get(id));
        } catch (DataAccessException e) {
            log.error("Cannot delete address: " + e);
            throw e;
        }
    }
}

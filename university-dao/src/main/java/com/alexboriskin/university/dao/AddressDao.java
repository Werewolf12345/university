package com.alexboriskin.university.dao;

import org.springframework.orm.hibernate5.HibernateTemplate;

import com.alexboriskin.university.domain.Address;

public interface AddressDao {

    void setTemplate(HibernateTemplate template);

    HibernateTemplate getTemplate();

    void save(Address address);

    Address get(int addressID);

    void update(Address address);

    void delete(int id);

}

package com.alexboriskin.university.dao;

import org.springframework.orm.hibernate5.HibernateTemplate;

import com.alexboriskin.university.domain.Group;

public interface GroupDao {

    void setTemplate(HibernateTemplate template);

    HibernateTemplate getTemplate();

    public void save(Group group);

    public Group get(int groupID);
    
    public Group getByName(String name);

    public void update(Group group);

    public void delete(int id);
}

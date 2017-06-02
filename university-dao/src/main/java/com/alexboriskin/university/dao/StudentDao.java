package com.alexboriskin.university.dao;

import org.springframework.orm.hibernate5.HibernateTemplate;

import com.alexboriskin.university.domain.Group;
import com.alexboriskin.university.domain.Student;

public interface StudentDao {

    void setTemplate(HibernateTemplate template);

    HibernateTemplate getTemplate();

    public void save(Student student);

    public Student get(int studentID);

    void update(Student student);

    public void delete(int i);

    Group getGroup(int studentID);
}

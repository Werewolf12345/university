package com.alexboriskin.university.dao;

import org.springframework.orm.hibernate5.HibernateTemplate;

import com.alexboriskin.university.domain.Professor;

public interface ProfessorDao {

    void setTemplate(HibernateTemplate template);

    HibernateTemplate getTemplate();
    
    public void save(Professor professor);
    
    public Professor get(int professorID);

    void update(Professor professor);

    void delete(int id);
}

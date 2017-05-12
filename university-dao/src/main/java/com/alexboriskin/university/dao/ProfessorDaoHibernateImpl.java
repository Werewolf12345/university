package com.alexboriskin.university.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate5.HibernateTemplate;

import com.alexboriskin.university.domain.Professor;

public class ProfessorDaoHibernateImpl implements ProfessorDao {

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
    public void save(Professor professor) {
        try {
            template.saveOrUpdate(professor);
        } catch (DataAccessException e) {
            log.error("Cannot save professor: " + e);
            throw e;
        }
    }

    @Override
    public void update(Professor professor) {
        try {
            template.saveOrUpdate(professor);
        } catch (DataAccessException e) {
            log.error("Cannot update professor: " + e);
            throw e;
        }
    }

    @Override
    public Professor get(int professorID) {
        try {
            return template.get(Professor.class, professorID);
        } catch (DataAccessException e) {
            log.error("Cannot get professor: " + e);
            throw e;
        }
    }

    @Override
    public void delete(int id) {
        try {
            template.delete(get(id));
        } catch (DataAccessException e) {
            log.error("Cannot delete professor: " + e);
            throw e;
        }
    }
}

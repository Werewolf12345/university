package com.alexboriskin.university.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.alexboriskin.university.domain.Group;
import com.alexboriskin.university.domain.Student;

@Repository
public class StudentDaoHibernateImpl implements StudentDao {

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
    public void save(Student student) {
        try {
            template.saveOrUpdate(student);
        } catch (DataAccessException e) {
            log.error("Cannot save student: " + e);
            throw e;
        }
    }

    @Override
    public void update(Student student) {
        try {
            template.merge(student);
        } catch (DataAccessException e) {
            log.error("Cannot update student: " + e);
            throw e;
        }
    }

    @Override
    public Student get(int studentID) {
        try {
            return template.get(Student.class, studentID);
        } catch (DataAccessException e) {
            log.error("Cannot get student: " + e);
            throw e;
        }
    }

    @Override
    public Group getGroup(int studentID) {
        try {
            Student student = template.get(Student.class, studentID);
            return student.getGroup();
        } catch (DataAccessException e) {
            log.error("Cannot get student: " + e);
            throw e;
        }
    }

    @Override
    public void delete(int id) {
        try {
            template.delete(get(id));
        } catch (DataAccessException e) {
            log.error("Cannot delete student: " + e);
            throw e;
        }
    }
}

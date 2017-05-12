package com.alexboriskin.university.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate5.HibernateTemplate;

import com.alexboriskin.university.domain.Lecture;

public class LectureDaoHibernateImpl implements LectureDao {

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
    public void save(Lecture lecture) {
        try {
            template.saveOrUpdate(lecture);
        } catch (DataAccessException e) {
            log.error("Cannot save lecture: " + e);
            throw e;
        }
    }

    @Override
    public Lecture get(int lectureID) {
        try {
            return template.get(Lecture.class, lectureID);
        } catch (DataAccessException e) {
            log.error("Cannot get lecture: " + e);
            throw e;
        }
    }

    @Override
    public void delete(int lectureID) {
        try {
            template.delete(get(lectureID));
        } catch (DataAccessException e) {
            log.error("Cannot delete lecture: " + e);
            throw e;
        }
    }

    @Override
    public void update(int lectureID, Lecture lecture) {
        try {
            template.saveOrUpdate(lecture);
        } catch (DataAccessException e) {
            log.error("Cannot update lecture: " + e);
            throw e;
        }
    }

}

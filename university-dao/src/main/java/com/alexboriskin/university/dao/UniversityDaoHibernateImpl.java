package com.alexboriskin.university.dao;

import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.alexboriskin.university.domain.Faculty;
import com.alexboriskin.university.domain.Group;
import com.alexboriskin.university.domain.Lecture;
import com.alexboriskin.university.domain.Professor;
import com.alexboriskin.university.domain.Staff;
import com.alexboriskin.university.domain.Student;

public class UniversityDaoHibernateImpl implements UniversityDao {
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

    @SuppressWarnings("unchecked")
    @Transactional
    @Override
    public Set<Group> getGroupsSet() {
        try {
            List<Group> list = (List<Group>) template.find("from Group");
            
            for(Group current : list) {
                Hibernate.initialize(current.getStudents());     
            }

            return new HashSet<Group>(list);

        } catch (DataAccessException e) {
            log.error("Cannot get group by name: " + e);
            throw e;
        }
    }

    @Override
    public Faculty getProfessorsSet() {
        try {
            @SuppressWarnings("unchecked")
            List<Professor> list = (List<Professor>) template
                    .find("from Professor");

            Faculty faculty = new Faculty();

            for (Professor current : list) {
                faculty.add(current);
            }

            return faculty;

        } catch (DataAccessException e) {
            log.error("Cannot get group by name: " + e);
            throw e;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Set<Lecture> getSchedule(Staff member, GregorianCalendar fromDate,
            GregorianCalendar tillDate) {

        List<Lecture> list = null;
        String hqlExpression = null;
        
        if (member instanceof Professor) {

            hqlExpression = "FROM Lecture WHERE lector_fk = " + member.getId()
                    + " AND (date_and_time BETWEEN ? AND ?)";

        } else if (member instanceof Student) {

            hqlExpression = "FROM Lecture WHERE group_fk = "
                    + ((Student) member).getGroupID()
                    + " AND (date_and_time BETWEEN ? AND ?)";
        }

        try {
            list = (List<Lecture>) template.find(hqlExpression, fromDate, tillDate);
        } catch (DataAccessException ex) {
            log.error("Cannot get lectures list: " + ex);
        }
        return new HashSet<Lecture>(list);
    }
}

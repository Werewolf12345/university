package com.alexboriskin.university.dao;

import java.util.GregorianCalendar;
import java.util.Set;

import org.springframework.orm.hibernate5.HibernateTemplate;

import com.alexboriskin.university.domain.Faculty;
import com.alexboriskin.university.domain.Group;
import com.alexboriskin.university.domain.Lecture;
import com.alexboriskin.university.domain.Staff;

public interface UniversityDao {

    void setTemplate(HibernateTemplate template);

    HibernateTemplate getTemplate();

    public Set<Group> getGroupsSet();

    public Faculty getProfessorsSet();

    Set<Lecture> getSchedule(Staff member,
            GregorianCalendar fromDate,
            GregorianCalendar tillDate);
}

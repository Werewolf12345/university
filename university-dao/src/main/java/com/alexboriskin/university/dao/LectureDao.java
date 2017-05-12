package com.alexboriskin.university.dao;

import org.springframework.orm.hibernate5.HibernateTemplate;

import com.alexboriskin.university.domain.Lecture;

public interface LectureDao {

    HibernateTemplate getTemplate();

    void setTemplate(HibernateTemplate template);

    public void save(Lecture lecture);

    public Lecture get(int lectureID);

    public void delete(int lectureID);

    public void update(int lectureID, Lecture lecture);
}

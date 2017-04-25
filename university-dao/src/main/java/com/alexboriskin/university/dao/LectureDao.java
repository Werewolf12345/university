package com.alexboriskin.university.dao;

import java.util.Calendar;
import java.util.Set;

import com.alexboriskin.university.domain.*;

public interface LectureDao {
    public void save(Lecture lecture) throws DAOException;

    public Lecture get(int lectureID) throws DAOException;

    public void delete(int lectureID) throws DAOException;

    public int getID(Lecture lecture) throws DAOException;

    public void update(int lectureID, Lecture lecture) throws DAOException;

    public Set<Lecture> getLectures(Staff individual, Calendar start,
            Calendar end) throws DAOException;
}

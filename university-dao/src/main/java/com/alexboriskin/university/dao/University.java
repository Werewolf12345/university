package com.alexboriskin.university.dao;

import java.util.Calendar;
import java.util.Set;

import com.alexboriskin.university.domain.DAOException;
import com.alexboriskin.university.domain.Faculty;
import com.alexboriskin.university.domain.Group;
import com.alexboriskin.university.domain.Lecture;
import com.alexboriskin.university.domain.Professor;
import com.alexboriskin.university.domain.Staff;


public class University {
    
    public Set<Lecture> getSchedule(Staff individual, Calendar start,
            Calendar end) throws DAOException {
        
        LectureDao lectureDao = new LectureDaoSqlImpl();
       
        return lectureDao.getLectures(individual, start, end);
    }
        
    public Set<Group> getAllGroups() throws DAOException {
        UniversityDao universityDao = new UniversityDaoSqlImpl();
        
        return universityDao.getGroupsSet();
    }
    
    public void addProfessor(Professor newProfessor) throws DAOException {
        ProfessorDao professorDao = new ProfessorDaoSqlImpl();
        
        professorDao.save(newProfessor);
    }
    
    public void removeProfessor(Professor professorToRemove) throws DAOException {
        ProfessorDao professorDao = new ProfessorDaoSqlImpl();
        
        professorDao.delete(professorToRemove);
    }

    public Faculty getAllProfessors() throws DAOException {
        UniversityDao universityDao = new UniversityDaoSqlImpl();
        
        return universityDao.getProfessorsSet();
    }

    public void addGroup(Group newGroup) throws DAOException {
       GroupDao groupDao = new GroupDaoSqlImpl();
       
       groupDao.save(newGroup);
    }
   
    public Group getGroup(String name) throws DAOException {
        GroupDao groupDao = new GroupDaoSqlImpl();
        
        return groupDao.get(groupDao.getID(new Group(name)));
    }
    
    public Group getGroup(int groupId) throws DAOException {
        GroupDao groupDao = new GroupDaoSqlImpl();
        
        return groupDao.get(groupId);
    }
    
    public void addLecture(Lecture newLecture) throws DAOException {
        LectureDao lectureDao = new LectureDaoSqlImpl();
        
        lectureDao.save(newLecture);
    }

    public Lecture getLecture(int lectureId) throws DAOException {
        LectureDao lectureDao = new LectureDaoSqlImpl();
        
        return lectureDao.get(lectureId);
    }
      
    public void removeLecture(Lecture lectureToRemove) throws DAOException {
        LectureDao lectureDao = new LectureDaoSqlImpl();
        lectureDao.delete(lectureDao.getID(lectureToRemove));
    }

}

package com.alexboriskin.university.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alexboriskin.university.domain.Group;
import com.alexboriskin.university.domain.Student;

@Service
public class StudentServiceImpl implements StudentService {
    
    @Autowired
    private StudentDao studentDao;
    
    @Autowired
    private GroupDao groupDao; 

    @Transactional
    @Override
    public void deleteById(int id) {
       studentDao.delete(id);
    }
    
    @Transactional
    @Override
    public void saveNew(Student student, String groupName) {
        Group group = groupDao.getByName(groupName);
        group.add(student);
        studentDao.save(student);
    }

    @Transactional
    @Override
    public void update(Student student, String groupName, int studentId) {
        Group group = groupDao.getByName(groupName);
        Student oldStudent = studentDao.get(studentId);
        group.remove(oldStudent);
        student.setId(studentId);
        group.add(student);
        studentDao.update(student);
    }

}

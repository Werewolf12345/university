package com.alexboriskin.university.dao;

import com.alexboriskin.university.domain.Student;

public interface StudentService {

    void deleteById(int id);
    
    void saveNew(Student student, String groupName);
    
    void update(Student student, String groupName, int studentId);
    
}

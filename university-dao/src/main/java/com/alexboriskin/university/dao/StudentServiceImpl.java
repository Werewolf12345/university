package com.alexboriskin.university.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentServiceImpl implements StudentService {
    
    @Autowired
    private StudentDao studentDao; 

    @Transactional
    @Override
    public void deleteById(int id) {
       studentDao.delete(id);
    }

}

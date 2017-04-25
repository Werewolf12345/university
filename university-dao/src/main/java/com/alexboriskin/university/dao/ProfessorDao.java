package com.alexboriskin.university.dao;

import com.alexboriskin.university.domain.*;

public interface ProfessorDao {
    public void save(Professor professor) throws DAOException;
    
    public void delete(Professor professor) throws DAOException;

    public Professor get(int professorID) throws DAOException;
}

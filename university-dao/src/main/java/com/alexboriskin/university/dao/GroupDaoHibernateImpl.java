package com.alexboriskin.university.dao;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.alexboriskin.university.domain.Group;

public class GroupDaoHibernateImpl implements GroupDao {

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
    public void save(Group group) {
        try {
            template.saveOrUpdate(group);
        } catch (DataAccessException e) {
            log.error("Cannot save group: " + e);
            throw e;
        }
    }

    @Override
    public Group get(int groupID) {
        try {
            return template.get(Group.class, groupID);
        } catch (DataAccessException e) {
            log.error("Cannot get group: " + e);
            throw e;
        }
    }
    
    @Transactional
    @Override
    public Group getByName(String name) {
        try {
            List<?> list = template.find("from Group group where group.name = ?", name);
            if (list.size() != 0) {
                Group result = (Group) list.get(0);
                Hibernate.initialize(result.getStudents());
                return result;
            } else {
                return new Group();
            }
        } catch (DataAccessException e) {
            log.error("Cannot get group by name: " + e);
            throw e;
        }
    }
    
    @Override
    public void update(Group group) {
        try {
            template.saveOrUpdate(group);
        } catch (DataAccessException e) {
            log.error("Cannot save group: " + e);
            throw e;
        }
    }

    @Override
    public void delete(int id) {
        try {
            template.delete(get(id));
        } catch (DataAccessException e) {
            log.error("Cannot delete group: " + e);
            throw e;
        }
    }
}

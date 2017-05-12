package com.alexboriskin.university.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.alexboriskin.university.domain.Address;
import com.alexboriskin.university.domain.Address.US;
import com.alexboriskin.university.domain.Group;
import com.alexboriskin.university.domain.Professor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:Spring-Module.xml")
@Transactional
public class GroupDAOTest {

    @Autowired
    GroupDao groupDao;

    @Test
    public void testSaveGetDelete() {

        Professor professor1 = new Professor("Aaa", "Bbb", new Address(
                US.ARIZONA, "Address1", 12345));
        Professor professor2 = new Professor("Ccc", "Ddd", new Address(
                US.ARKANSAS, "Address2", 67893));

        Group group1 = new Group("Group1", professor1);
        Group group2 = new Group("Group2", professor2);

        groupDao.save(group1);
        groupDao.save(group2);

        int group1id = group1.getId();
        int group2id = group2.getId();

        Group group1expected = new Group("Group1", professor1);
        group1 = groupDao.get(group1id);

        assertEquals(group1expected, group1);

        group2.setName("Group3");
        groupDao.update(group2);
        group2 = groupDao.get(group2id);

        Group group2expected = new Group("Group3", professor2);
        assertEquals(group2expected, group2);
        
        group2 = groupDao.getByName("Group3");
        assertEquals(group2expected, group2);

        groupDao.delete(group1id);
        group1 = groupDao.get(group1id);
        assertNull(group1);

        groupDao.delete(group2id);
        group2 = groupDao.get(group2id);
        assertNull(group2);
    }
}

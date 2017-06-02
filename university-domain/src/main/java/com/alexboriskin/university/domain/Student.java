package com.alexboriskin.university.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@XmlRootElement
@SuppressWarnings("serial")
@Entity
@Table(name = "students")
public class Student extends Staff implements Serializable {
    private Group group;
   
    public Student() {
        super();
    }
    
    public Student(String firstName, String lastName, Address address) {
        super(firstName, lastName, address);
    }
    
    @Override
    public void setId(int id) {
        super.setId(id);
    }

    @Id
    @Column(name = "student_id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setFirstName(String firstName) {
        super.setFirstName(firstName);
    }

    @Column(name = "first_name", nullable = false, length = 20)
    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public void setLastName(String lastName) {
        super.setLastName(lastName);
    }

    @Column(name = "last_name", nullable = false, length = 20)
    @Override
    public String getLastName() {
        return lastName;
    }
    
    
    @ManyToOne(targetEntity = Group.class)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "group_fk", nullable = false, insertable=false, updatable=false)
    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Transient
    public int getGroupID() {
        return group.getId();
    }

    @Override
    public void setAddress(Address address) {
        super.setAddress(address);
    }
    
    
    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    @Cascade(CascadeType.SAVE_UPDATE)
    @Override
    public Address getAddress() {
        return address;
    }
}

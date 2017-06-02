package com.alexboriskin.university.domain;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@SuppressWarnings("serial")

@XmlRootElement
@Entity
@Table(name = "groups", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
public class Group extends Board<Student> implements Serializable {
    
    private int id;
    private String name;
    private Professor counselor;
    
    public Group() {
    }
    
    public Group(String name, Professor counselor) {
        super();
        this.name = name;
        this.counselor = counselor;
    }
    
    @Id
    @Column(name = "group_id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    public int getId() {
        return id ;
    }
        
    @Column(name = "name", nullable = false, length = 20)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne
    @JoinColumn(name = "counselor_fk", nullable = false)
    @Cascade(CascadeType.SAVE_UPDATE)
    public Professor getCounselor() {
        return counselor;
    }
    
    public void setCounselor(Professor counselor) {
        this.counselor = counselor;
    }

    @Override
    public boolean add(Student student) {
        student.setGroup(this);
        return super.add(student);
    }
    
    @XmlTransient
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_fk", nullable = false)
    public Set<Student> getStudents() {
        return members;
    }
    
    public void setStudents(Set<Student> students) {
        this.members = students;
    }
            
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return ((Group) obj).getName().equals(this.getName());
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public void remove(Student student) {
        members.remove(student);
    }

}

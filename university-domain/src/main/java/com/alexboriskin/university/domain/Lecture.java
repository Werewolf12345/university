package com.alexboriskin.university.domain;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@SuppressWarnings("serial")

@Entity
@Table(name = "lectures")
public class Lecture implements Serializable {

    private String name;
    private Calendar dateAndTime;
    private Professor lector;
    private Group group;
    private int auditoriumNo;
    int id;

    public Lecture() {
    }

    public Lecture(String name, Calendar dateAndTime, Professor lector,
            Group group, int auditorium) {
        this.name = name;
        this.dateAndTime = dateAndTime;
        this.lector = lector;
        this.group = group;
        this.auditoriumNo = auditorium;
    }

    @Id
    @Column(name = "lecture_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "name", nullable = false, length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "date_and_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Calendar getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(Calendar dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    @ManyToOne
    @JoinColumn(name = "lector_fk", nullable = false)
    @Cascade(CascadeType.SAVE_UPDATE)
    public Professor getLector() {
        return lector;
    }

    public void setLector(Professor lector) {
        this.lector = lector;
    }

    @ManyToOne(targetEntity = Group.class, fetch = FetchType.LAZY)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "group_fk", nullable = false)
    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Column(name = "auditorium", nullable = false)
    public int getAuditoriumNo() {
        return auditoriumNo;
    }
    
    public void setAuditoriumNo(int auditoriumNo) {
        this.auditoriumNo = auditoriumNo;
    }

    public void setAuditorium(int auditorium) {
        this.auditoriumNo = auditorium;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, dateAndTime, lector, group, auditoriumNo);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return ((Lecture) obj).getName().equalsIgnoreCase(this.getName())
                && ((Lecture) obj).getAuditoriumNo() == this.getAuditoriumNo()
                && ((Lecture) obj).getGroup().equals(this.getGroup())
                && ((Lecture) obj).getDateAndTime().equals(
                        this.getDateAndTime());
    }

}

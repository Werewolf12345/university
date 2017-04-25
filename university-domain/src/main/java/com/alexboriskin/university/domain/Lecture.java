package com.alexboriskin.university.domain;

import java.util.Calendar;
import java.util.Objects;

public class Lecture {
    private String name;
    private Calendar dateAndTime;
    private Professor lector;
    private Group group;
    private Auditorium auditorium;

    @Override
    public int hashCode() {
        
        return Objects.hash(name, dateAndTime, lector, group, auditorium);
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
                && ((Lecture) obj).getAuditorium().getNumber() == this.getAuditorium().getNumber()
                && ((Lecture) obj).getGroup().equals(this.getGroup())
                && ((Lecture) obj).getDateAndTime().equals(this.getDateAndTime());
    }

    public Lecture(String name, Calendar dateAndTime, Professor lector,
            Group group, Auditorium auditorium) {
        this.name = name;
        this.dateAndTime = dateAndTime;
        this.lector = lector;
        this.group = group;
        this.auditorium = auditorium;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Calendar getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(Calendar dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public Professor getLector() {
        return lector;
    }

    public void setLector(Professor lector) {
        this.lector = lector;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Auditorium getAuditorium() {
        return auditorium;
    }

    public void setAuditorium(Auditorium auditorium) {
        this.auditorium = auditorium;
    }
}

package com.alexboriskin.university.domain;

import java.util.Objects;
import java.util.Set;

public class Group extends Board<Student> {
    private String name;
    private Professor counselor;
    
    public Group(String name) {
        super();
        this.name = name;
    }
        
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Professor getCounselor() {
        return counselor;
    }

    public void setCounselor(Professor counselor) {
        this.counselor = counselor;
    }

    public Set<Student> getStudents() {
        return members;
    }
            
    @Override
    public int hashCode() {
        return Objects.hash(name.toLowerCase());
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return ((Group) obj).getName().equalsIgnoreCase(this.getName());
    }
}

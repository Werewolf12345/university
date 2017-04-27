package com.alexboriskin.university.domain;

import java.util.HashSet;
import java.util.Set;

public abstract class Board<T> {
    public Set<T> getMembers() {
        return members;
    }

    protected Set<T> members;

    public Board() {
        this.members = new HashSet<T>();
    }

    public boolean add(T newMember) {
        return members.add(newMember);
    }

    public T find(String firstName, String lastName, int zipCode) {
        if(!contains(firstName, lastName, zipCode)) {
            throw new IllegalArgumentException(firstName + " " + lastName + " not found");
        }
        
        T result = null;
        
        if (!members.isEmpty()) {
            for (T current : members) {
                if (((Staff) current).getFirstName().equalsIgnoreCase(firstName)
                        && ((Staff) current).getLastName().equalsIgnoreCase(lastName)
                        && ((Staff) current).getAddress().zipCode == zipCode) {
                    result = current;
                }
            }
        }
        return result;
    }
    
    public boolean contains(String firstName, String lastName, int zipCode) {
        if (!members.isEmpty()) {
            for (T current : members) {
                if (((Staff) current).getFirstName().equalsIgnoreCase(firstName)
                        && ((Staff) current).getLastName().equalsIgnoreCase(lastName)
                        && ((Staff) current).getAddress().zipCode == zipCode) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean remove(String firstName, String lastName, int zipCode) {
        if (!members.isEmpty()) {
            for (T current : members) {
                if (((Staff) current).getFirstName().equalsIgnoreCase(firstName)
                        && ((Staff) current).getLastName().equalsIgnoreCase(lastName)
                        && ((Staff) current).getAddress().zipCode == zipCode) {
                    return members.remove(current);
                }
            }
        }
        return false;
    }
}

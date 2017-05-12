package com.alexboriskin.university.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "professors")
public class Professor extends Staff implements Serializable {

    public Professor(String firstName, String lastName, Address address) {
        super(firstName, lastName, address);
    }
    
    public Professor() {
        super();
    }
    
    @Override
    public void setId(int id) {
        super.setId(id);
    }

    @Id
    @Column(name = "professor_id")
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
   
    @Override
    public void setAddress(Address address) {
        super.setAddress(address);
    }
    
    @ManyToOne(cascade = {CascadeType.ALL, CascadeType.DETACH })
    @JoinColumn(name = "address_id", nullable = false)
    @Override
    public Address getAddress() {
        return address;
    }
}


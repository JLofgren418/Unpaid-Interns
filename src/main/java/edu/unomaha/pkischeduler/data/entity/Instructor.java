package edu.unomaha.pkischeduler.data.entity;

import javax.persistence.Entity;

@Entity
public class Instructor extends AbstractEntity {
    private String name;

    private String availability;

    public Instructor() {

    }
    public Instructor(String name, String availability) {
        this.name = name;
        this.availability = availability;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvailability()
    {
        return availability;
    }

    public void setAvailability(String availability)
    {
        this.availability = availability;
    }

}
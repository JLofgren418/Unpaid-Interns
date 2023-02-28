package edu.unomaha.pkischeduler.data.entity;

import java.util.LinkedList;
import java.util.List;
import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

@Entity
public class Room extends AbstractEntity {
    @NotBlank
    private String number;
    @OneToMany(mappedBy = "room")
    @Nullable
    private List<Course> courses = new LinkedList<>();

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}


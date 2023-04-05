package edu.unomaha.pkischeduler.data.entity;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Instructor extends AbstractEntity implements Serializable, Cloneable {
    @NotEmpty
    private String name;
    @NotEmpty
    private String availability;

    @OneToMany(mappedBy = "instructor")
    @Nullable
    private List<Course> courses = new LinkedList<>();

    public Instructor()
    {

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

    @Override
    public String toString() {
       //  return name;
        return "Instructor{" +
                "name='" + name + '\'' +
                ", availability='" + availability + '\'' +
                '}';
    }

    @Override
    public Instructor clone() throws CloneNotSupportedException {
        return (Instructor) super.clone();
    }

    @Override
    public boolean equals(Object obj){
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Instructor)) {
            return false;
        }

        Instructor other = (Instructor) obj;

        return this.name.equals(other.name) && this.availability.equals(other.availability);
    }
}
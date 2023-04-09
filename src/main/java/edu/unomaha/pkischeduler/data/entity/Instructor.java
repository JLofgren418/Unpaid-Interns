package edu.unomaha.pkischeduler.data.entity;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * This class represents an Instructor object.
 */
@Entity
public class Instructor extends AbstractEntity implements Serializable, Cloneable {

    /**
     * The instructor's name.
     */
    @NotEmpty
    private String name;

    /**
     * The instructor's availability
     */
    @NotEmpty
    private String availability;

    /**
     * A list of courses that this instructor is assigned to teach.
     */
    @OneToMany(mappedBy = "instructor")
    @Nullable
    private List<Course> courses = new LinkedList<>();

    /**
     * Empty constructor.
     */
    public Instructor()
    {

    }

    /**
     * Default constructor
     * @param name The instructor's name.
     * @param availability The instructor's availability.
     */
    public Instructor(String name, String availability) {
        this.name = name;
        this.availability = availability;
    }

    /**
     * Provides the name of the instructor.
     * @return The name of the instructor.
     */
    public String getName() {
        return name;
    }


    /**
     * Modifies the name of the instructor.
     * @param name The name of the instructor.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Provides the availability of the instructor.
     * @return The availability of the instructor.
     */
    public String getAvailability()
    {
        return availability;
    }


    /**
     * Modifies the availability of the instructor
     * @param availability The availability of the instructor.
     */
    public void setAvailability(String availability)
    {
        this.availability = availability;
    }

    /**
     * Provides a string representation of the instructor.
     * @return A string representation of the instructor.
     */
    @Override
    public String toString() {
       //  return name;
        return "Instructor{" +
                "name='" + name + '\'' +
                ", availability='" + availability + '\'' +
                '}';
    }

    /**
     * Creates a copy of the instructor object.
     * @return A copy of the instructor object.
     * @throws CloneNotSupportedException
     */
    @Override
    public Instructor clone() throws CloneNotSupportedException {
        return (Instructor) super.clone();
    }

    /**
     * Determines equality of instructor objects.
     * @param obj the object being compared
     * @return A boolean value indicating object equality.
     */
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
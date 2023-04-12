package edu.unomaha.pkischeduler.data.entity;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.List;

/**
 * This class represents a Room object.
 */
@Entity
public class Room extends AbstractEntity implements Cloneable {

    //TODO add building field
    /**
     * The room number
     */
    @NotNull
    private int number;

    /**
     * The number of students that the room can accommodate.
     */
    @NotNull
    private int capacity;

    /**
     * A list of courses assigned to this room.
     */
    @OneToMany(mappedBy = "room", fetch = FetchType.EAGER)
    @Nullable
    private List<Course> courses = new LinkedList<>();

    /**
     * The type of the room.
     * Can be either a Laboratory or a Lecture.
     */
    private String roomType = "";

    /**
     * Empty Constructor.
     */
    public Room()
    {

    }

    /**
     * Default constructor
     * @param number The room number
     * @param capacity The number of students the room can accommodate.
     * @param roomType The room type.
     */
    public Room(int number, int capacity, String roomType)
    {
        this.number = number;
        this. capacity = capacity;
        this.roomType = roomType;
    }

    /**
     * Provides the room number
     * @return The room number
     */
    public int getNumber() {
        return number;
    }

    /**
     * Modifies the room number
     * @param number The room number
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * Provides a list of courses assigned to the room.
     * @return A list of courses assigned to the room.
     */
    @Nullable
    public List<Course> getCourses() {
        return courses;
    }

    /**
     * Modifies the list of courses assigned to this room.
     * @param courses The list of courses assigned to this room.
     */
    public void setCourses(@Nullable List<Course> courses) {
        this.courses = courses;
    }

    /**
     * Appends the list of courses which are assigned to this room.
     * @param course A course to be appended to the list of courses which are assigned to this room.
     */
    public void addCourse(Course course) {
        this.courses.add(course);
    }

    /**
     * Provides a string representation of the room.
     * @return A string representation of the room.
     */
    @Override
    public String toString() {
        //  return String.valueOf(number);
        return "Room{" +
                "number=" + number +
                ", capacity=" + capacity +
                ", roomType='" + roomType + '\'' +
                '}';
    }

    /**
     * Provides the number of students that the room can accommodate.
     * @return The number of students that the room can accommodate.
     */
    public int getCapacity()
    {
        return capacity;
    }

    /**
     * Modifies the number of students that the room can accommodate.
     * @param capacity The number of students that the room can accommodate.
     */
    public void setCapacity(int capacity)
    {
        this.capacity = capacity;
    }

    /**
     * Provides the room type.
     * @return The room type.
     */
    public String getRoomType() {
        return roomType;
    }

    /**
     * Modifies the room type.
     * @param roomType The room type.
     */
    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    /**
     * Creates a copy of the room object.
     * @return A copy of the room object.
     * @throws CloneNotSupportedException
     */
    @Override
    public Room clone() throws CloneNotSupportedException {
        return (Room) super.clone();
    }

    /**
     * Determines equality of room objects.
     * @param obj the object being compared
     * @return A boolean value indicating object equality.
     */
    @Override
    public boolean equals(Object obj){
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Room)) {
            return false;
        }

        final Room other = (Room) obj;
        return
                this.number == other.number &&
                this.capacity == other.capacity &&
                this.roomType.equals(other.roomType);
    }

    /**
     * Provides the room number as a string.
     * Used for display in room x room view accordion.
     * @return The room number as a string.
     */
    public String numberToString() {
        return String.valueOf(number);
    }


}


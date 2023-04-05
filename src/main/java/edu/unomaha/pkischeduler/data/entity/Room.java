package edu.unomaha.pkischeduler.data.entity;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Room extends AbstractEntity implements Cloneable {
    @NotNull
    private int number;

    @NotNull
    private int capacity;

    @OneToMany(mappedBy = "room", fetch = FetchType.EAGER)
    @Nullable
    private List<Course> courses = new LinkedList<>();

    private String roomType = "";

    public Room()
    {

    }

    public Room(int number, int capacity, String roomType)
    {
        this.number = number;
        this. capacity = capacity;
        this.roomType = roomType;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Nullable
    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(@Nullable List<Course> courses) {
        this.courses = courses;
    }

    public void addCourse(Course course) {
        this.courses.add(course);
    }
    @Override
    public String toString() {
        //  return String.valueOf(number);
        return "Room{" +
                "number=" + number +
                ", capacity=" + capacity +
                ", roomType='" + roomType + '\'' +
                '}';
    }
    public int getCapacity()
    {
        return capacity;
    }

    public void setCapacity(int capacity)
    {
        this.capacity = capacity;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    @Override
    public Room clone() throws CloneNotSupportedException {
        return (Room) super.clone();
    }

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

    public String numberToString() {
        return String.valueOf(number);
    }


}


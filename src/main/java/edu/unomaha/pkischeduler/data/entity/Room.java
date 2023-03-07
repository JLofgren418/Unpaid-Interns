package edu.unomaha.pkischeduler.data.entity;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Room extends AbstractEntity {
    @NotBlank
    private int number;

    @NotEmpty
    private int capacity;

    @OneToMany(mappedBy = "room")
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

    public List<Course> getSchedules() {
        return courses;
    }

    public void setSchedules(List<Course> courses) {
        this.courses = courses;
    }

    public String toString() {
        return String.valueOf(number);
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



}


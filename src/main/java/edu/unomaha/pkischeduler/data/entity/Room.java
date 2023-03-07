package edu.unomaha.pkischeduler.data.entity;

import java.util.LinkedList;
import java.util.List;
import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Entity
public class Room extends AbstractEntity {
    @NotBlank
    private int number;

    @NotEmpty
    private int capacity;

    @OneToMany(mappedBy = "room")
    @Nullable
    private List<Schedule> schedules = new LinkedList<>();

    private String roomType = "";

    public Room()
    {

    }

    public Room(int number, int capacity, List<Schedule> schedules, String roomType)
    {
        this.number = number;
        this.capacity = capacity;
        this.schedules = schedules;
        this.roomType = roomType;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setCourses(List<Schedule> schedules) {
        this.schedules = schedules;
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


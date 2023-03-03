package edu.unomaha.pkischeduler.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Schedule extends AbstractEntity {

    @NotEmpty
    private String courseCode = "";

    @NotEmpty
    private String courseTitle = "";

    @ManyToOne
    @JoinColumn(name = "room_id")
    @NotNull
    @JsonIgnoreProperties({"courses"})
    private Room room;

    private int roomNumber;
    @NotNull
    @ManyToOne
    private Status status;

    @NotEmpty
    private String meetingDays = "";
    @NotEmpty
    private String meetingTime = "";

    @Override
    public String toString() {
        return courseTitle + " " + meetingDays;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public Status getStatus() {
        return status;
    }

    public String getMeetingDays() {
        return meetingDays;
    }

    public String getMeetingTime() {
        return meetingTime;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setMeetingDays(String meetingDays) {
        this.meetingDays = meetingDays;
    }

    public void setMeetingTime(String meetingTime) {
        this.meetingTime = meetingTime;
    }

}
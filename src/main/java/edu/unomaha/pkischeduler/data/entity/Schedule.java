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

    @NotEmpty
    private String meetingDays = "";
    @NotEmpty
    private String meetingTime = "";

    @NotEmpty
    private String instructor = "";

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

    public String getMeetingDays() {
        return meetingDays;
    }

    public String getMeetingTime() {
        return meetingTime;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public void setMeetingDays(String meetingDays) {
        this.meetingDays = meetingDays;
    }

    public void setMeetingTime(String meetingTime) {
        this.meetingTime = meetingTime;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

}
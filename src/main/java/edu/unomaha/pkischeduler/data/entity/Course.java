package edu.unomaha.pkischeduler.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Course extends AbstractEntity {


    @NotEmpty
    private String courseTitle = "";

    @NotEmpty
    private String courseCode = "";

    private String sectionType = "";

    @NotEmpty
    private String meetingDays = "";

    @NotEmpty
    private String meetingTime = "";

    private String instructor = "";

    private String crossListings = "";

    private int expectedEnrollment;

    @ManyToOne
    @JoinColumn(name = "room_id")
    @NotNull
    @JsonIgnoreProperties({"courses"})
    private Room room;
    @NotNull
    @ManyToOne
    private Status status;

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

    public String getCourseTitle() {
        return courseTitle;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getSectionType() {
        return sectionType;
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

    public String getCrossListings() {
        return crossListings;
    }

    public int getExpectedEnrollment() {
        return expectedEnrollment;
    }

    public Status getStatus() {
        return status;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public void setSectionType(String sectionType) {
        this.sectionType = sectionType;
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

    public void setCrossListings(String crossListings) {
        this.crossListings = crossListings;
    }

    public void setExpectedEnrollment(int expectedEnrollment) {
        this.expectedEnrollment = expectedEnrollment;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}

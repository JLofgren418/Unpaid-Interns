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

    private String crossListings = "";

    private int expectedEnrollment;


    private String sectionNumber;

  /*  @ManyToOne
    @JoinColumn(name = "room_id")
    @NotNull
    @JsonIgnoreProperties({"courses"})
    private Room room;*/

    @ManyToOne
    @NotNull
    private Instructor instructor;

    @Override
    public String toString() {
        return courseTitle + " " + meetingDays;
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

    public String getCrossListings() {
        return crossListings;
    }

    public int getExpectedEnrollment() {
        return expectedEnrollment;
    }

    public String getSectionNumber() {
        return sectionNumber;
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

    public void setCrossListings(String crossListings) {
        this.crossListings = crossListings;
    }

    public void setExpectedEnrollment(int expectedEnrollment) {
        this.expectedEnrollment = expectedEnrollment;
    }

    public void setSectionNumber(String sectionNumber) {
        this.sectionNumber = sectionNumber;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

}

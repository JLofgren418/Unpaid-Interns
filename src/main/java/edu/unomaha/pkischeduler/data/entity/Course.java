package edu.unomaha.pkischeduler.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.text.DecimalFormat;


@Entity
public class Course extends AbstractEntity implements Serializable, Cloneable {
    private static final DecimalFormat decimalFormat = new DecimalFormat("0.00");

    @NotEmpty
    private String courseCode = "";

    @NotEmpty
    private String courseTitle = "";

    @NotEmpty
    private String sectionType = "";

    @NotEmpty
    private String meetingDays = "";

    @NotEmpty
    private String meetingTime = "";

    @NotEmpty
    private String crossListings = "";

    @NotNull
    private double expectedEnrollment = 0;

    @NotEmpty
    private String sectionNumber = "";

    @ManyToOne
    @JoinColumn(name = "room_id")
    @NotNull
    @JsonIgnoreProperties({"courses"})
    private Room room;

    @ManyToOne
    @JoinColumn(name = "instructor_id")
    @NotNull
    @JsonIgnoreProperties({"courses"})
    private Instructor instructor;

    public Course()
    {

    }

    public Course(String courseCode, String courseTitle, String sectionType, String meetingDays,
                  String meetingTime, String crossListings, double expectedEnrollment,
                  String sectionNumber, Instructor instructor, Room room)
    {
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        this.sectionType = sectionType;
        this.meetingDays = meetingDays;
        this.meetingTime = meetingTime;
        this.crossListings = crossListings;
        this.expectedEnrollment = expectedEnrollment;
        this.sectionNumber = sectionNumber;
        this.instructor = instructor;
        this.room = room;

    }

    @Override
    public String toString() {
        //  return courseCode + " " + courseTitle;
        return "Course{" +
                "courseCode='" + courseCode + '\'' +
                ", courseTitle='" + courseTitle + '\'' +
                ", sectionType='" + sectionType + '\'' +
                ", meetingDays='" + meetingDays + '\'' +
                ", meetingTime='" + meetingTime + '\'' +
                ", crossListings='" + crossListings + '\'' +
                ", expectedEnrollment=" + expectedEnrollment +
                ", sectionNumber='" + sectionNumber + '\'' +
                ", instructor=" + instructor.toString() +
                ", room=" + room.toString() +
                '}';
    }


    public String getChangesFrom(Course other) {
        String difference = "";
            difference +=
                    "Course{" +
                            "courseCode='" + courseCode + '\'' +    ( this.courseCode.equals(other.courseCode)  ? "":" [from=" + other.courseCode + "]" )+
                            ", courseTitle='" + courseTitle + '\'' + (this.courseTitle.equals(other.courseTitle)? "":" [from=" + other.courseTitle + "]" )+
                            ", sectionType='" + sectionType + '\'' + (this.sectionType.equals(other.sectionType)?"":" [from=" + other.sectionType + "]")+
                            ", meetingDays='" + meetingDays + '\'' + (this.meetingDays.equals(meetingDays)?"":" [from=" + other.meetingDays + "]")+
                            ", meetingTime='" + meetingTime + '\'' + (this.meetingTime.equals(other.meetingTime)?"":" [from=" + other.meetingTime + "]")+
                            ", crossListings='" + crossListings + '\'' + ( this.crossListings.equals(other.crossListings)?"":" [from=" + other.crossListings + "]" )+
                            ", expectedEnrollment=" + expectedEnrollment + ( this.expectedEnrollment==other.expectedEnrollment?"":" [from=" +  decimalFormat.format(other.expectedEnrollment) + "]")+
                            ", sectionNumber='" + sectionNumber + '\'' + (this.sectionNumber.equals(other.sectionNumber)?"":" [from=" + other.sectionNumber + "]")+
                            ", " + //instructor=" +
                            instructor.toString() +  (this.instructor.equals(other.instructor)?"":"[from="+other.instructor.toString()+"]" )+
                            ", "+ //room=" +
                            room.toString() + (this.room.equals(other.room)?"":"[from="+other.room.toString()+"]" )+
                            '}';
        return difference;
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

    public double getExpectedEnrollment() {
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

    public void setExpectedEnrollment(double expectedEnrollment) {
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

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }


    public String getDetails() {

        return meetingDays + " " + meetingTime + " " +
                courseCode + "-" +sectionNumber + " " +
                courseTitle;

    }

    @Override
    public Course clone() throws CloneNotSupportedException {
        Course cloned =  (Course) super.clone();
        cloned.instructor = instructor.clone();
        cloned.room = room.clone();
        return cloned;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Course)) {
            return false;
        }

        Course other = (Course) obj;

        return
            this.courseCode.equals(other.courseCode) &&
            this.courseTitle.equals(other.courseTitle) &&
            this.sectionType.equals(other.sectionType) &&
            this.meetingDays.equals(other.meetingDays) &&
            this.meetingTime.equals(other.meetingTime) &&
            this.crossListings.equals(other.crossListings) &&
            this.expectedEnrollment == other.expectedEnrollment &&
            this.sectionNumber.equals(other.sectionNumber) &&
            this.room.equals(other.room) &&
            this.instructor.equals(other.instructor);
    }
}

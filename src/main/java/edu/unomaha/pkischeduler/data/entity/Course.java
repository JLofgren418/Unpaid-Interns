package edu.unomaha.pkischeduler.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.text.DecimalFormat;


/**
 * This Class represents a course object.
 */
@Entity
public class Course extends AbstractEntity implements Serializable, Cloneable {

    private static final DecimalFormat decimalFormat = new DecimalFormat("0.00");
    /**
     * The course ID
     * I.e. - CSCI 4970
     */
    @NotEmpty
    private String courseCode = "";

    /**
     * The title of the course
     * I.e. - Capstone Project
     */
    @NotEmpty
    private String courseTitle = "";

    /**
     * The section type of the course.
     * Can be either a Laboratory or a Lecture.
     */
    @NotEmpty
    private String sectionType = "";

    /**
     * The days of the week which the course meets.
     * I.e. MW, TTh...etc.
     */
    @NotEmpty
    private String meetingDays = "";

    /**
     * The time of day which the course meets.
     * I.e. - 1:30pm - 2:45pm
     */
    @NotEmpty
    private String meetingTime = "";


    /**
     * This field contains course(s) that are cross listed
     *  with this particular course.
     * I.e. - See CSCI 8366-001
     */
    @NotEmpty
    private String crossListings = "";

    /**
     * The expected/maximum enrollment of the course.
     * Represents the max amount of students that may enroll in the course.
     */
    @NotNull
    private double expectedEnrollment = 0;

    /**
     * The section number of the course.
     * I.e. - 001, 002...etc.
     */
    @NotEmpty
    private String sectionNumber = "";

    /**
     * The room that the course is assigned to.
     */
    @ManyToOne
    @JoinColumn(name = "room_id")
    @NotNull
    @JsonIgnoreProperties({"courses"})
    private Room room;

    /**
     * The instructor assigned to teach the course.
     */
    @ManyToOne
    @JoinColumn(name = "instructor_id")
    @NotNull
    @JsonIgnoreProperties({"courses"})
    private Instructor instructor;

    /**
     * Empty Constructor
     */
    public Course()
    {

    }

    /**
     * Default constructor.
     * @param courseCode The course code.
     * @param courseTitle The course title.
     * @param sectionType The section type of the course.
     * @param meetingDays The days which the course meets.
     * @param meetingTime The times which the course begins and ends.
     * @param crossListings The course(s) that are cross listed with this course.
     * @param expectedEnrollment The maximum/expected number of students in the course.
     * @param sectionNumber The section number of the course.
     * @param instructor The instructor assigned to teach the course.
     * @param room The room to which the course is assigned.
     */
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

    /**
     * string representation of the course.
     */
    @Override
    public String toString() {
        return courseTitle + " " + meetingDays;
    }


    /**
     * Provides a string representation of the course for logging purposes.
     * @return A string representation of the course.
     */
    public String toStringForLog() {
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

    /**
     * Provides a string representation of the course.
     * @return A string representing difference between the two courses.
     */
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

    /**
     * Provides the title of the course.
     * @return The course title
     */
    public String getCourseTitle() {
        return courseTitle;
    }

    /**
     * Provides the course code.
     * @return The course code.
     */
    public String getCourseCode() {
        return courseCode;
    }

    /**
     * Provides the section type of the course.
     * @return The section type of the course.
     */
    public String getSectionType() {
        return sectionType;
    }

    /**
     * Provides the days that the course meets.
     * @return The days that the course meets.
     */
    public String getMeetingDays() {
        return meetingDays;
    }


    /**
     * Provides the times of the day that the course begins and ends.
     * @return The times of day that the course begins and ends.
     */
    public String getMeetingTime() {
        return meetingTime;
    }


    /**
     * Provides course(s) cross listed with this course.
     * @return Course(s) cross listed with this course.
     */
    public String getCrossListings() {
        return crossListings;
    }


    /**
     * Provides the maximum/expected enrollment of the course.
     * @return The maximum/expected enrollment of the course.
     */
    public double getExpectedEnrollment() {
        return expectedEnrollment;
    }

    /**
     * Provides the section number of the course.
     * @return The section number of the course.
     */
    public String getSectionNumber() {
        return sectionNumber;
    }


    /**
     * Modifies the title of the course.
     * @param courseTitle The title of the course.
     */
    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    /**
     * Modifies the course code.
     * @param courseCode The course code.
     */
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    /**
     * Modifies the section type.
     * @param sectionType The section type.
     */
    public void setSectionType(String sectionType) {
        this.sectionType = sectionType;
    }

    /**
     * Modifies the days of the week that the course meets.
     * @param meetingDays The days of the week that the course meets.
     */
    public void setMeetingDays(String meetingDays) {
        this.meetingDays = meetingDays;
    }

    /**
     * Sets the times that the course begins and ends.
     * @param meetingTime The times that the course begins and ends.
     */
    public void setMeetingTime(String meetingTime) {
        this.meetingTime = meetingTime;
    }

    /**
     * Modifies the course(s) that are cross listed with this course.
     * @param crossListings The course(s) that are cross listed with this course.
     */
    public void setCrossListings(String crossListings) {
        this.crossListings = crossListings;
    }

    /**
     * Modifies the maximum/expected enrollment of the course.
     * @param expectedEnrollment The maximum/expected enrollment of the course
     */
    public void setExpectedEnrollment(double expectedEnrollment) {
        this.expectedEnrollment = expectedEnrollment;
    }

    /**
     * Modifies the section number of the course.
     * @param sectionNumber The section number of the course.
     */
    public void setSectionNumber(String sectionNumber) {
        this.sectionNumber = sectionNumber;
    }


    /**
     * Provides the instructor assigned to teach the course.
     * @return The instructor assigned to teach the course.
     */
    public Instructor getInstructor() {
        return instructor;
    }


    /**
     * Modifies the instructor assigned to teach the course.
     * @param instructor The instructor assigned to teach the course.
     */
    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }


    /**
     * Provides the room that the course is assigned to.
     * @return The room that the course is assigned to.
     */
    public Room getRoom() {
        return room;
    }

    /**
     * Modifies the room that the course is assigned to.
     * @param room The room that the course is assigned to.
     */
    public void setRoom(Room room) {
        this.room = room;
    }


    /**
     * Provides a special string used to display courses in the room x room view accordion.
     * @return A special string used to display courses in the room x room view accordion.
     */
    public String getDetails() {

        return meetingDays + " " + meetingTime + " " +
                courseCode + "-" +sectionNumber + " " +
                courseTitle;

    }

    /**
     * Creates a copy of the course object.
     * @return A copy of the course object.
     * @throws CloneNotSupportedException
     */
    @Override
    public Course clone() throws CloneNotSupportedException {
        Course cloned =  (Course) super.clone();
        cloned.instructor = instructor.clone();
        cloned.room = room.clone();
        return cloned;
    }

    /**
     * Determines equality of course objects.
     * @param obj the object being compared
     * @return A boolean value indicating object equality.
     */
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


    /**
     * Provides the expected/maximum enrollment as an Integer for grid display.
     * @return The expected/maximum enrollment as an Integer
     */
    public int getExpectedAsInt()
    {
        return ((int) expectedEnrollment);
    }
}

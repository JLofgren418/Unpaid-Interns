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
     * Whether special consent is need to join the course
     */
    @NotEmpty
    private String consent = "";

    /**
     * The number of credit hours the class is worth
     */
    @NotEmpty
    private String credits = "";

    /**
     * The MINIMUM number of credit hours the class is worth
     */
    @NotEmpty
    private String minCredits = "";

    /**
     *  The grading mode for the course
     *  ie. is it graded or is it for completion
     */
    @NotEmpty
    private String graded = "";

    /**
     * Special attributes assigned to the course
     */
    @NotNull
    private String attributes = "";

    /**
     * Attributes needed by the assigned room for the course
     */
    @NotNull
    private String roomAttributes = "";

    /**
     * Current Enrollment in the class
     */
    @NotEmpty
    private String enrollment = "";

    /**
     * The Maximum total enrollment for the class
     */
    @NotEmpty
    private String maxEnrollment = "";

    /**
     * The class ID of the course
     */
    @NotEmpty
    private String classID = "";

    /**
     * The SIS ID of the Course
     */
    @NotEmpty
    private String sisID = "";

    /**
     * The term of the course
     */
    @NotEmpty
    private String term = "";

    /**
     * The term code of the Course
     */
    @NotEmpty
    private String term_code = "";

    /**
     * The Department Code of the Course
     */
    @NotEmpty
    private String deptCode = "";

    /**
     * The subject Code of the Course
     */
    @NotEmpty
    private String subjectCode = "";

    /**
     * The catalog number of the Course
     */
    @NotEmpty
    private String catalogNumber = "";

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
     * The past enrollment the course has historically had
     */
    @NotNull
    private String priorEnrollment = "";

    /**
     * The title or topic assoicated with the course.
     */
    @NotNull
    private String title = "";

    /**
     * The integrated partner for the course
     */
    @NotNull

    private String partner = "";
    /**
     * The estimated enrollment the class will have
     */
    @NotNull
    private String projectedEnrollment = "";

    /**
     * The maximum number of students that can waitlist for the class
     */
    @NotEmpty
    private String waitCap = "";

    /**
     * The number of students the course's room needs to be able to hold
     */
    @NotEmpty
    private String roomCap = "";


    /**
     * Link associated with the course
     */
    @NotNull
    private String link = "";

    /**
     * Comments associated with the course
     */
    @NotNull
    private String comments = "";

    /**
     * Notes Related to the course
     */
    @NotNull
    private String notes = "";

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
     * This is the Course's session
     */
    @NotEmpty
    private String session = "";

    /**
     * This is which campus the course is being taught on
     */
    @NotEmpty
    private String campus = "";

    /**
     * The section number of the course.
     * I.e. - 001, 002...etc.
     */
    @NotEmpty
    private String sectionNumber = "";

    /**
     * This is the course status.
     */
    @NotEmpty
    private String status = "";

    /**
     * This is the method by which the course will be instructed
     * ie. in person or online
     */
    @NotEmpty
    private String method = "";

    /**
     * This is the whether the schedule can be printed
     */
    @NotEmpty
    private String printable = "";

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

    public Course(String consent, String credits, String minCredits, String graded, String attributes, String roomAttributes, String enrollment, String maxEnrollment, String classID,
                  String sisID, String term, String term_code, String deptCode, String subjectCode, String catalogNumber, String courseCode, String courseTitle, String sectionType,
                  String meetingDays, String meetingTime, String priorEnrollment, String title, String partner, String waitCap, String roomCap, String link,
                  String comments, String notes, String crossListings, double expectedEnrollment, String session, String campus, String sectionNumber, String status, String method,
                  String printable, Room room, Instructor instructor) {
        this.consent = consent;
        this.credits = credits;
        this.minCredits = minCredits;
        this.graded = graded;
        this.attributes = attributes;
        this.roomAttributes = roomAttributes;
        this.enrollment = enrollment;
        this.maxEnrollment = maxEnrollment;
        this.classID = classID;
        this.sisID = sisID;
        this.term = term;
        this.term_code = term_code;
        this.deptCode = deptCode;
        this.subjectCode = subjectCode;
        this.catalogNumber = catalogNumber;
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        this.sectionType = sectionType;
        this.meetingDays = meetingDays;
        this.meetingTime = meetingTime;
        this.priorEnrollment = priorEnrollment;
        this.title = title;
        this.partner = partner;
        this.waitCap = waitCap;
        this.roomCap = roomCap;
        this.link = link;
        this.comments = comments;
        this.notes = notes;
        this.crossListings = crossListings;
        this.expectedEnrollment = expectedEnrollment;
        this.session = session;
        this.campus = campus;
        this.sectionNumber = sectionNumber;
        this.status = status;
        this.method = method;
        this.printable = printable;
        this.room = room;
        this.instructor = instructor;
    }

    /**
     * Provides a string representation of the course.
     * @return A string representation of the course.
     */
    public String toRow() {
        return courseCode + " - " + courseTitle + "\n ,"
                + classID + ","
                + sisID + ","
                + term + ","
                + term_code + ","
                + deptCode + ","
                + subjectCode + ","
                + catalogNumber + ","
                + courseCode + ","
                + sectionNumber + ","
                + "\"" + courseTitle + "\"" + ","
                + sectionType + ","
                + title + ","
                + meetingDays + " " + meetingTime + ","
                + "\"" + instructor.getName() + "\"" + ","
                + room.toEntry() + ","
                + status + ","
                + session + ","
                + campus + ","
                + method + ","
                + partner + ","
                + printable + ","
                + consent + ","
                + minCredits + ","
                + credits + ","
                + graded + ","
                + attributes + ","
                + roomAttributes + ","
                + enrollment + ","
                + maxEnrollment + ","
                + priorEnrollment + ","
                + projectedEnrollment + ","
                + waitCap + ","
                + roomCap + ","
                + crossListings + ","
                + link + ","
                + "\"" + comments + "\"" + ","
                + "\"" + notes + "\"" + "\n"
                ;
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
     * Auto generated getter methods.
     */
    public String getConsent() {
        return consent;
    }

    public String getCredits() {
        return credits;
    }

    public String getMinCredits() {
        return minCredits;
    }

    public String getGraded() {
        return graded;
    }

    public String getAttributes() {
        return attributes;
    }

    public String getRoomAttributes() {
        return roomAttributes;
    }

    public String getEnrollment() {
        return enrollment;
    }

    public String getMaxEnrollment() {
        return maxEnrollment;
    }

    public String getClassID() {
        return classID;
    }

    public String getSisID() {
        return sisID;
    }

    public String getTerm() {
        return term;
    }

    public String getTerm_code() {
        return term_code;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public String getCatalogNumber() {
        return catalogNumber;
    }

    public String getPriorEnrollment() {
        return priorEnrollment;
    }

    public String getTitle() {
        return title;
    }

    public String getPartner() {
        return partner;
    }

    public String getProjectedEnrollment() {
        return projectedEnrollment;
    }

    public String getWaitCap() {
        return waitCap;
    }

    public String getRoomCap() {
        return roomCap;
    }

    public String getLink() {
        return link;
    }

    public String getComments() {
        return comments;
    }

    public String getNotes() {
        return notes;
    }

    public String getSession() {
        return session;
    }

    public String getCampus() {
        return campus;
    }

    public String getStatus() {
        return status;
    }

    public String getMethod() {
        return method;
    }

    public String getPrintable() {
        return printable;
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

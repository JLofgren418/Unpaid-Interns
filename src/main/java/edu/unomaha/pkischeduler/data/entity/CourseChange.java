package edu.unomaha.pkischeduler.data.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * This class is used to store the changes made to a course. (audit log)
 */
@Entity
public class CourseChange extends AbstractEntity {

        @Transient
        protected static final  DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        @Transient
        protected LocalDateTime dateT;

        @Transient
        protected  Course before=null;
        @Transient
        protected  Course after=null;

        @Transient
        protected  transient boolean changeToBeforeOrAfter = false;

        /**
         * Contains the timestamp of the change
         */
        protected String dateTime;

        /** Store the difference between the before and after course  Example: Course{courseCode='BMI 8080', courseTitle='SEMINAR IN BIOMEDICAL INFO', sectionType='Seminar' [from=Seminar42],....  */
        @NotEmpty
        @Column(length = 1024)  // Database is using VARCHAR so space is not wasted...
        protected String changeCourse ="";


        /**
         * This function should be called before any changes to @Course object are made
         * @param before The course before the changes are made
         */
        public void setBeforeCourseChange(Course before) {
                dateT = LocalDateTime.now();
                this.before = before;
                changeToBeforeOrAfter = true;
        }

        /**
         * This function should be called after any changes to @Course object are made
         * @param after The course after the changes are made
         */
        public void setAfterCourseChange(Course after) {
                dateT = LocalDateTime.now();
                this.after = after;
                changeToBeforeOrAfter = true;
        }

        /**
         * This function should be called when a course is deleted
         * @param deletedCourse The course that was deleted
         */
        public void setDelete(Course deletedCourse) {
                dateT = LocalDateTime.now();
                changeCourse = "Deleted [ " + deletedCourse.toStringForLog() + " ]";
                changeToBeforeOrAfter = false;
        }

        /**
         * Used to add a message to audit log
         * @param message
         */
        public void setLogMessage( String message) {
                dateT = LocalDateTime.now();
                changeCourse = message;
                changeToBeforeOrAfter = false;
        }


        /**
         * Returns to string representation of the CourseChange object
         * @return to string representation of the CourseChange object
         */
        @Override
        public String toString(){
                beforePersist();
                return  dateTime==null?DATE_TIME_FORMATTER.format(dateT):dateTime  + " " + changeCourse==null?"":changeCourse;
        }

        /**
         * Function is called automatically to make sure that all variables are in order before the object is persisted
         */
        @PrePersist
        public void beforePersist() {
                if (changeToBeforeOrAfter) {
                        if (before != null && after != null){
                                // update was made
                                changeCourse = after.getChangesFrom(before);
                        } else if (after!=null){
                                // new course was added
                                changeCourse = "Added [ " + after.toStringForLog() + " ]";
                        }
                        changeToBeforeOrAfter = false;
                }
                dateTime = DATE_TIME_FORMATTER.format(dateT);
        }

        /**
         * @return the line as it will apear in the log file
         */
        public String getAsLogLine() {
                return this.dateTime + " " + this.changeCourse + "\n";
        }

}





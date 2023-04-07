package edu.unomaha.pkischeduler.data.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class CourseChange extends AbstractEntity {

        @Transient
        protected static final  DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        @Transient
        protected LocalDateTime dateT;

        @Transient
        protected  Course before;
        @Transient
        protected  Course after;

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

        public void setBefore(Course before) {
                dateT = LocalDateTime.now();
                this.before = before;
                changeToBeforeOrAfter = true;
        }

        public void setAfter(Course after) {
                dateT = LocalDateTime.now();
                this.after = after;
                changeToBeforeOrAfter = true;
        }


        public void setDelete(Course deletedCourse) {
                dateT = LocalDateTime.now();
                changeCourse = "Deleted [ " + deletedCourse.toString() + " ]";
                changeToBeforeOrAfter = false;
        }



        @Override
        public String toString(){
                beforePersist();
                return dateTimeFormatter.format(dateT)  + " " + changeCourse;
        }

        /**
         * Function is called automatically to make sure that all variables are in order before the object is persisted
         */
        @PrePersist
        public void beforePersist() {
                if (changeToBeforeOrAfter) {
                        changeCourse = after.getChangesFrom(before);
                        changeToBeforeOrAfter = false;
                }
                dateTime = dateTimeFormatter.format(dateT);
        }


}





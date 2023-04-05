package edu.unomaha.pkischeduler.data.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class CourseChange extends AbstractEntity {

        @Transient
        protected static final  DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        @NotEmpty
        protected LocalDateTime dateTime;

        @Transient
        protected  Course before;
        @Transient
        protected  Course after;
        protected  boolean changeToBeforeOrAfter =true;

        /**
         * Store the difference between the before and after course
         */
        @Column(length = 1024)
        protected String changeCourse ="";


        public void setBefore(Course before) {
                dateTime = LocalDateTime.now();
                this.before = before;
                changeToBeforeOrAfter = true;
        }

        public void setAfter(Course after) {
                dateTime = LocalDateTime.now();
                this.after = after;
                changeToBeforeOrAfter = true;
        }



        @Override
        public String toString(){
                beforePersist();
                return dateTimeFormatter.format( dateTime )  + " " + changeCourse;
        }

        /**
         * Function is called automatically to make sure that all variables are in order before the object is persisted
         */
//        @PrePersist
        public void beforePersist() {
                if (changeToBeforeOrAfter) {
                        changeCourse = before.getChangesFrom(after);
                        changeToBeforeOrAfter = false;
                }
        }


}





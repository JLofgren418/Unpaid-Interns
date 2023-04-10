package edu.unomaha.pkischeduler.data.repository;

import edu.unomaha.pkischeduler.data.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * An interface that the defines the repository for database operations on the Course entity.
 */
public interface CourseRepository extends JpaRepository<Course, Long> {

    /**
     * This query filters the course table in the database based on user input
     * @param searchTerm The user input.
     * @return A list of courses that match the user input.
     */
    @Query("select c from Course c " +
            "where lower(c.courseCode) like lower(concat('%', :searchTerm, '%'))")
    List<Course> search(@Param("searchTerm") String searchTerm);

}
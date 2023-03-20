package edu.unomaha.pkischeduler.data.repository;

import edu.unomaha.pkischeduler.data.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("select c from Course c " +
            "where lower(c.courseCode) like lower(concat('%', :searchTerm, '%'))")
    List<Course> search(@Param("searchTerm") String searchTerm);

}
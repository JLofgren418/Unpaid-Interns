package edu.unomaha.pkischeduler.data.repository;


import edu.unomaha.pkischeduler.data.entity.CourseChange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * An interface that the defines the repository for database operations on the CourseChange entity.
 */
@Repository
public interface CourseChangeRepository extends JpaRepository<CourseChange, Long> {


}

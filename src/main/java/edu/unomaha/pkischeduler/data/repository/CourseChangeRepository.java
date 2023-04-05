package edu.unomaha.pkischeduler.data.repository;



import edu.unomaha.pkischeduler.data.entity.CourseChange;
import edu.unomaha.pkischeduler.data.entity.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseChangeRepository extends JpaRepository<CourseChange, Long> {



}

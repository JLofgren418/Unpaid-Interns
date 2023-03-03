package edu.unomaha.pkischeduler.data.repository;


import edu.unomaha.pkischeduler.data.entity.Course;
import edu.unomaha.pkischeduler.data.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("select c from Schedule  c " +
            "where lower(c.courseTitle) like lower(concat('%', :searchTerm, '%'))")
    List<Schedule> search(@Param("searchTerm") String searchTerm);

}
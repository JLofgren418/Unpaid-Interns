package edu.unomaha.pkischeduler.data.repository;

import edu.unomaha.pkischeduler.data.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

        @Query("select r from Room r " +
            "where lower(r.number) like lower(concat('%', :searchTerm, '%'))")
        List<Room> search(@Param("searchTerm") String searchTerm);
}

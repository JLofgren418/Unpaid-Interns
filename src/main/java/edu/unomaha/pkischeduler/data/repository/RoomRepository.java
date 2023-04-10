package edu.unomaha.pkischeduler.data.repository;

import edu.unomaha.pkischeduler.data.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * An interface that the defines the repository for database operations on the Room entity.
 */
public interface RoomRepository extends JpaRepository<Room, Long> {

        /**
         * This query filters the room table in the database based on user input
         * @param searchTerm The user input
         * @return A list of rooms matching the user input.
         */
        @Query("select r from Room r " +
            "where lower(r.number) like lower(concat('%', :searchTerm, '%'))")
        List<Room> search(@Param("searchTerm") String searchTerm);
}

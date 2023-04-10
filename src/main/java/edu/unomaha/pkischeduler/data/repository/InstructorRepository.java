package edu.unomaha.pkischeduler.data.repository;

import edu.unomaha.pkischeduler.data.entity.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * An interface that the defines the repository for database operations on the Instructor entity.
 */
public interface InstructorRepository extends JpaRepository<Instructor, Long> {

    /**
     * Checks the table in the database to determine if an instructor exists by their name.
     * @param name The name of the instructor.
     * @return A boolean value indicating if an instructor by
     * the given name exists in the instructor table.
     */
    Boolean existsByName(String name);

    /**
     * Finds an instructor in the database table by name
     * @param name The name of the instructor.
     * @return The instructor object/entity if it exists.
     */
    Instructor findByName(String name);

}

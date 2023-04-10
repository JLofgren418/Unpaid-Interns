package edu.unomaha.pkischeduler.data.service;

import edu.unomaha.pkischeduler.data.entity.Course;
import edu.unomaha.pkischeduler.data.entity.Instructor;
import edu.unomaha.pkischeduler.data.entity.Room;
import edu.unomaha.pkischeduler.data.repository.CourseRepository;
import edu.unomaha.pkischeduler.data.repository.InstructorRepository;
import edu.unomaha.pkischeduler.data.repository.RoomRepository;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;

import java.util.Collection;
import java.util.List;


/**
 * Service provider which allows the application to access
 * the repositories for Courses, Rooms, and Instructors (CRI)
 */
@Service
public class CRIService implements CrudListener<Course> {

    /**
     * The course repository interface.
     */
    private final CourseRepository courseRepository;

    /**
     * The room repository interface.
     */
    private final RoomRepository roomRepository;

    /**
     * The instructor repository interface.
     */
    private final InstructorRepository instructorRepository;

    /**
     * Default constructor.
     * @param courseRepository The course repository interface.
     * @param roomRepository The room repository interface.
     * @param instructorRepository The instructor repository interface.
     */
    public CRIService(CourseRepository courseRepository,
                      RoomRepository roomRepository,
                      InstructorRepository instructorRepository) {
        this.courseRepository = courseRepository;
        this.roomRepository = roomRepository;
        this.instructorRepository = instructorRepository;
    }


    /**
     * Allows courses to be filtered by user input.
     * This function calls the courseRepository to execute a query.
     * The query executed returns a list of courses that match user input.
     * @param stringFilter The user input.
     * @return A list of courses that match the user input.
     */
    public List<Course> filterCourses(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return courseRepository.findAll();
        } else {
            return courseRepository.search(stringFilter);
        }
    }

    /**
     * Allows rooms to be filtered by user input.
     * This function calls the roomRepository to execute a query.
     * The query executed returns a list of rooms that match user input.
     * @param stringFilter The user input.
     * @return A list of rooms that match the user input.
     */
    public List<Room> filterRooms(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return roomRepository.findAll();
        } else {
            return roomRepository.search(stringFilter);
        }
    }


    /**
     * This function calls the roomRepository
     * to return all rooms in the database.
     * @return A list of all rooms in the database.
     */
    public List<Room> getAllRooms() {

        return roomRepository.findAll();
    }


    /**
     * This function calls the instructorRepository
     * to return all instructors in the database.
     * @return A list of all instructors in the database.
     */
    public List<Instructor> getAllInstructors() {

        return instructorRepository.findAll();
    }

    /**
     * This function calls the courseRepository
     * to return all courses in the database.
     * @return A list of all courses in the database.
     */
    public List<Course> getAllCourses() {

        return courseRepository.findAll();
    }

    /**
     * This function calls the courseRepository
     * Used specifically for the CRUD grid in EditView
     * to return all courses in the database.
     * @return A collection of all courses in the database.
     */
    @Override
    public Collection<Course> findAll() {

        return courseRepository.findAll();
    }

    /**
     * This function calls the courseRepository
     * Used specifically for the CRUD grid in EditView
     * to add a courses to the database.
     * @return A call to the courseRepository to save a course.
     */
    @Override
    public Course add(Course course) {
        return courseRepository.save(course);
    }

    /**
     * This function calls the courseRepository
     * Used specifically for the CRUD grid in EditView
     * to update a courses in the database.
     * @return A call to the courseRepository to update a course.
     */
    @Override
    public Course update(Course course) {

        return courseRepository.save(course);
    }

    /**
     * This function calls the courseRepository
     * Used specifically for the CRUD grid in EditView
     * to delete a courses in the database.
     */
    @Override
    public void delete(Course course) {
        courseRepository.delete(course);
    }


    /**
     * This function calls the roomRepository
     * to add a room to the database.
     * @param room the room to be added.
     * @return A call to the roomRepository to save the room to the database.
     */
    public Room addRoom(Room room)
    {
        return roomRepository.save(room);
    }

    /**
     * This function calls the instructorRepository
     * to add an instructor to the database.
     * @param instructor instructor The instructor to be added
     * @return a call to the instructorRepository to save the instructor.
     */
    public Instructor addInstructor(Instructor instructor)
    {
        return instructorRepository.save(instructor);
    }

    /**
     * Provides the default room from the database.
     * All courses are initially set to this room.
     * @return A call to the roomRepository which gives the default room at ID=0
     */
    public Room getDefaultRoom()
    {
        Long id = 0L;
        return roomRepository.getReferenceById(id);
    }


    /**
     * Checks to see if an instructor exists by name in the database.
     * @param name The name of the instructor.
     * @return If the instructor exists, it is returned.
     * Otherwise, returns null.
     */
    public Instructor exstingInstructor(String name)
    {
            if (instructorRepository.existsByName(name))
            {
                return instructorRepository.findByName(name);
            }

       return null;
    }


    /**
     * Deletes all courses and instructors in the database.
     */
    public void deleteAll()
    {
        courseRepository.deleteAll();
        instructorRepository.deleteAll();
    }


}
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

@Service
public class CourseService implements CrudListener<Course> {

    private final CourseRepository courseRepository;
    private final RoomRepository roomRepository;

    private final InstructorRepository instructorRepository;

    public CourseService(CourseRepository courseRepository,
                           RoomRepository roomRepository,
                           InstructorRepository instructorRepository) {
        this.courseRepository = courseRepository;
        this.roomRepository = roomRepository;
        this.instructorRepository = instructorRepository;
    }


    public List<Course> findAllCourses(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return courseRepository.findAll();
        } else {
            return courseRepository.search(stringFilter);
        }
    }

    public List<Room> findAllRooms() {
        return roomRepository.findAll();
    }

    public List<Instructor> findAllInstructors() {
        return instructorRepository.findAll();
    }


    @Override
    public Collection<Course> findAll() {

        return courseRepository.findAll();
    }

    @Override
    public Course add(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public Course update(Course course) {

        return courseRepository.save(course);
    }

    @Override
    public void delete(Course course) {
        courseRepository.delete(course);
    }
}
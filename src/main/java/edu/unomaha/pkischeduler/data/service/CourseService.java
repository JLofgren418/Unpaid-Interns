package edu.unomaha.pkischeduler.data.service;


import edu.unomaha.pkischeduler.data.entity.Course;
import edu.unomaha.pkischeduler.data.entity.Room;
import edu.unomaha.pkischeduler.data.entity.Status;
import edu.unomaha.pkischeduler.data.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

    @Service
    public class CourseService {

        private final CourseRepository courseRepository;
        private final RoomRepository roomRepository;
        private final StatusRepository statusRepository;

        public CourseService(CourseRepository courseRepository,
                             RoomRepository roomRepository,
                             StatusRepository statusRepository) {
            this.courseRepository = courseRepository;
            this.roomRepository = roomRepository;
            this.statusRepository = statusRepository;
        }

        public List<Course> findAllCourses(String stringFilter) {
            if (stringFilter == null || stringFilter.isEmpty()) {
                return courseRepository.findAll();
            } else {
                return courseRepository.search(stringFilter);
            }
        }

        public long countCourses() {
            return courseRepository.count();
        }

        public void deleteCourse(Course course) {
            courseRepository.delete(course);
        }

        public void saveCourse(Course course) {
            if (course == null) {
                System.err.println("Course is null. Are you sure you have connected your form to the application?");
                return;
            }
            courseRepository.save(course);
        }

        public List<Room> findAllRooms() {
            return roomRepository.findAll();
        }

        public List<Status> findAllStatuses(){
            return statusRepository.findAll();
        }
    }


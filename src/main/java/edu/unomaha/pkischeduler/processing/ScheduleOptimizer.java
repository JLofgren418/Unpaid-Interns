package edu.unomaha.pkischeduler.processing;

import edu.unomaha.pkischeduler.data.entity.Course;
import edu.unomaha.pkischeduler.data.entity.Room;
import edu.unomaha.pkischeduler.data.service.CourseService;

import java.util.List;
import java.util.Random;

public class ScheduleOptimizer {
    CourseService service;
    public ScheduleOptimizer(CourseService service)
    {
        this.service = service;
    }
    public void backtracking_search()
    {
        List<Room> rooms = service.getAllRooms();
        List<Course> courses = service.getAllCourses();
        Random randy = new Random();
        for (Course course : courses)
        {
            course.setRoom(rooms.get(randy.nextInt(23-1)+1));
            service.update(course);
        }
    }

}

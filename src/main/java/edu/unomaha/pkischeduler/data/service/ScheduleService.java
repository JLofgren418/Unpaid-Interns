package edu.unomaha.pkischeduler.data.service;


import edu.unomaha.pkischeduler.data.entity.Course;
import edu.unomaha.pkischeduler.data.entity.Room;
import edu.unomaha.pkischeduler.data.entity.Schedule;
import edu.unomaha.pkischeduler.data.entity.Status;
import edu.unomaha.pkischeduler.data.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
public class ScheduleService {

    private final CourseRepository courseRepository;
    private final RoomRepository roomRepository;
    private final StatusRepository statusRepository;

    private final InstructorRepository instructorRepository;

    private final ScheduleRepository scheduleRepository;

    public ScheduleService(CourseRepository courseRepository,
                           RoomRepository roomRepository,
                           StatusRepository statusRepository,
                           InstructorRepository instructorRepository,
                           ScheduleRepository scheduleRepository) {
        this.courseRepository = courseRepository;
        this.roomRepository = roomRepository;
        this.statusRepository = statusRepository;
        this.instructorRepository = instructorRepository;
        this.scheduleRepository = scheduleRepository;
    }

    public List<Schedule> findAllSchedules(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return scheduleRepository.findAll();
        } else {
            return scheduleRepository.search(stringFilter);
        }
    }

    public long countSchedules() {
        return scheduleRepository.count();
    }

    public void deleteSchedule(Schedule schedule) {
        scheduleRepository.delete(schedule);
    }

    public void saveSchedule(Schedule schedule) {
        if (schedule == null) {
            System.err.println("Schedule is null. Are you sure you have connected your form to the application?");
            return;
        }
        scheduleRepository.save(schedule);
    }

    public List<Room> findAllRooms() {
        return roomRepository.findAll();
    }

    public List<Status> findAllStatuses(){
        return statusRepository.findAll();
    }
}
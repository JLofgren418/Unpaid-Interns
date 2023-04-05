package edu.unomaha.pkischeduler.data.service;


import edu.unomaha.pkischeduler.data.entity.CourseChange;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;
import edu.unomaha.pkischeduler.data.repository.CourseChangeRepository;

import java.util.Collection;

@Service
public class CourseChangeService  implements CrudListener<CourseChange> {


    private  CourseChangeRepository courseChangeRepository;



    public CourseChangeService(CourseChangeRepository courseChangeRepository) {
        this.courseChangeRepository = courseChangeRepository;
    }


    public CourseChange saveCourseChange (CourseChange courseChange) {
       return  courseChangeRepository.save(courseChange);
    }


    @Override
    public Collection<CourseChange> findAll() {
        return courseChangeRepository.findAll();
    }

    @Override
    public CourseChange add(CourseChange course) {
        return courseChangeRepository.save(course);
    }

    @Override
    public CourseChange update(CourseChange course) {
        return courseChangeRepository.save(course);
    }

    @Override
    public void delete(CourseChange course) {
        courseChangeRepository.delete(course);
    }
}

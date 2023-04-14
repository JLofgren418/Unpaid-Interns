package edu.unomaha.pkischeduler.data.service;


import edu.unomaha.pkischeduler.data.entity.CourseChange;
import edu.unomaha.pkischeduler.ui.EditView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;
import edu.unomaha.pkischeduler.data.repository.CourseChangeRepository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * Service to log Changes
 */
@Service
public class CourseChangeService  implements CrudListener<CourseChange> {
    private static final Logger LOG = LoggerFactory.getLogger(EditView.class);

    private  CourseChangeRepository courseChangeRepository;


    /**
     *
     * @param courseChangeRepository
     */
    public CourseChangeService(CourseChangeRepository courseChangeRepository) {
        this.courseChangeRepository = courseChangeRepository;
    }

    /**
     *
     * @param courseChange
     * @return
     */
    public CourseChange saveCourseChange (CourseChange courseChange) {
       LOG.info("saveCourseChange(): " + courseChange.toString());
       return  courseChangeRepository.save(courseChange);
    }

    /**
     * Find all changes
     * @return
     */
    @Override
    public Collection<CourseChange> findAll() {
        return courseChangeRepository.findAll();
    }

    /**
     * Add a change to the course to the log
     * @param course
     * @return
     */
    @Override
    public CourseChange add(CourseChange course) {
        return courseChangeRepository.save(course);
    }

    /**
     * Function has to be implemented because of the @CrudListener interface
     * Audit log should not be modified
     * @param course
     * @return
     */
    @Override
    public CourseChange update(CourseChange course) {
        LOG.error("Items can't be updated.");
        throw new UnsupportedOperationException("Items can't be updated");
    }

    /**
     * Function has to be implemented because of the @CrudListener interface
     * audit log should not be modified
     * @param course
     */
    @Override
    public void delete(CourseChange course) {
        LOG.error("Items can't be deleted from change log.");
        throw new UnsupportedOperationException("Items can't be deleted from change log.");
    }

    /**
     * returns the log as ByteArrayOutputStream
     */
    public void exportLog(ByteArrayOutputStream baos) {
        List<CourseChange> entities = courseChangeRepository.findAll();
        for (CourseChange entity : entities) {
            try {
                baos.write(  entity.getAsLogLine().getBytes() );
            } catch (IOException e) {
                LOG.error(e.toString());
            }
        }
        LOG.trace("exportLog(): bytes written :" + baos.size());
    }


}

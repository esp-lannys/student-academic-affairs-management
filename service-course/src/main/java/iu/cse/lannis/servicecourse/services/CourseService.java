package iu.cse.lannis.servicecourse.services;

import iu.cse.lannis.servicecourse.entities.Course;
import iu.cse.lannis.servicecourse.exceptions.ServiceCourseException;
import iu.cse.lannis.servicecourse.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseService (CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getAllCourses() {
        return this.courseRepository.findAll();
    }

    public Course getCourseDetail(Long courseId) {
        return this.courseRepository.findById(courseId)
                .orElseThrow(() -> new ServiceCourseException("Course Not found", HttpStatus.NOT_FOUND));
    }

//    public Courses registerStudentIntoCourse(Long courseId, Long studentId) {
//
//    }
}

package iu.cse.lannis.servicecourse.controller;

import iu.cse.lannis.servicecourse.entities.Course;
import iu.cse.lannis.servicecourse.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping("/courses")
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok(this.courseService.getAllCourses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseDetail(@PathVariable("id") long courseId) {
        return ResponseEntity.ok(this.courseService.getCourseDetail(courseId));
    }
}

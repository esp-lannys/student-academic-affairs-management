package iu.cse.lannis.servicestudent.controller;

import iu.cse.lannis.servicestudent.dto.StudentDto;
import iu.cse.lannis.servicestudent.entity.Student;
// import iu.cse.lannis.servicestudent.kafka.producer.Sender;
import iu.cse.lannis.servicestudent.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RefreshScope
@CrossOrigin("*")
public class StudentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private final StudentService studentService;

    @GetMapping("/students/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.studentService.getStudentById(id));
    }

    @GetMapping("/students/{email}")
    public ResponseEntity<Student> getStudentByEmail(@PathVariable("email") String email) {
        return ResponseEntity.ok(this.studentService.getStudentByEmail(email));
    }

    @GetMapping("/students")
    public List<Student> getAllStudents() {
        return this.studentService.getAllStudents();
    }

    @PostMapping("/students")
    public ResponseEntity<Student> registerStudent(@RequestBody StudentDto studentDto) {
        LOGGER.info("sending payload='{}'", studentDto);
        Student createdStudent = studentService.registerStudent(studentDto);
        return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
    }
}

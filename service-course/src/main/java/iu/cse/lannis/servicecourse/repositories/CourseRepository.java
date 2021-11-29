package iu.cse.lannis.servicecourse.repositories;

import iu.cse.lannis.servicecourse.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
}

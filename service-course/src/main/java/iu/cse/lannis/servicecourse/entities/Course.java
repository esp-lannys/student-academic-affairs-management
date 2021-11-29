package iu.cse.lannis.servicecourse.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(
        name = "courses",
        schema = "service_course"
)
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", columnDefinition = "varchar(255) not null")
    @NotEmpty(message = "Username should not be empty")
    private String name;

    @Column(name = "instructor_name", columnDefinition = "varchar(255) not null")
    private Long instructor_name;

    @Column(name = "semester", columnDefinition = "int")
    private int semester;

    @Column(name = "department", columnDefinition = "varchar(255) not null")
    @NotEmpty(message = "Department should not be empty")
    private String department;

    @Column(name = "number_of_periods", columnDefinition = "int")
    @NotEmpty(message = "Number of Periods should not be empty")
    private int numberOfPeriods;

    @Column(name = "number_of_students", columnDefinition = "int not null")
    private int numberOfStudents;

    @Column(name = "room", columnDefinition = "varchar(255) not null")
    @NotEmpty(message = "Course room should not be empty")
    private String room;

    @Column(name = "beginning_period", columnDefinition = "int not null")
    @NotEmpty(message = "Beginning period should not be empty")
    private int beginningPeriod;
}

package iu.cse.lannis.servicestudent.entity;

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
        name = "students",
        schema = "service_student"
)
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_name",columnDefinition = "VARCHAR(255) NOT NULL")
    @NotEmpty(message = "Name should not be empty")
    private String studentName;

    @Column(name = "username", columnDefinition = "VARCHAR(255) NOT NULL")
    @NotEmpty(message = "Username should not be empty")
    private String username;

    @Column(name = "password",columnDefinition = "VARCHAR(255) NOT NULL")
    @NotEmpty(message = "Password should not be empty")
    private String password;

    @Column(name = "email",columnDefinition = "VARCHAR(255) NOT NULL")
    @NotEmpty(message = "Email should not be empty")
    private String email;
}

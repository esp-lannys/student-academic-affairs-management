package iu.cse.lannis.serviceretention.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "retentions",
        schema = "service_retention"
)
public class Retention {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "note", columnDefinition = "varchar(255)")
    private String note;

    @Column(name = "student_id", nullable = false, columnDefinition = "bigint")
    private Long studentId;

    @Column(name = "student_name", nullable = false, columnDefinition = "varchar(255)")
    private String studentName;

    @Column(name = "student_email", nullable = false, columnDefinition = "varchar(255)")
    private String studentEmail;

    @Column(name = "reason", nullable = false, columnDefinition = "varchar(255)")
    private String reason;
}

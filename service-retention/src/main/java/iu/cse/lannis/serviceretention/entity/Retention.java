package iu.cse.lannis.serviceretention.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(
        name = "retentions",
        schema = "service_retention"
)
public class Retention {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_retention_generator")
    @GenericGenerator(
            name = "sequence_retention_generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "service_retention.seq_retention"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    private Long id;
    private String note;
    @Column(name = "student_id", nullable = false)
    private Long studentId;
    @Column(name = "student_name", nullable = false)
    private String studentName;
    private String reason;

    public Retention(String note, Long studentId, String studentName, String reason) {
        this.note = note;
        this.studentId = studentId;
        this.studentName = studentName;
        this.reason = reason;
    }
}

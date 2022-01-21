package iu.cse.lannis.eventcentrallog.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(
        name = "events",
        schema = "event_central_log"
)
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "topic", columnDefinition = "varchar(255) not null")
    private String topic;

    @Column(name = "payload", columnDefinition = "json")
    private String payload;
}

package iu.cse.lannis.servicemailsender.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "\"mails\"", schema = "service_mail")
public class Mail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sent_to",columnDefinition = "VARCHAR(255) NOT NULL")
    @NotEmpty(message = "Receiver should not be empty")
    @Email
    @Size(min = 1, message = "Please enter an email")
    private String sentTo;

    @Column(name = "subject",columnDefinition = "VARCHAR(255) NOT NULL")
    @NotEmpty(message = "Subject should not be empty")
    private String subject;

    @Column(name = "text",columnDefinition = "VARCHAR(255) NOT NULL")
    @NotEmpty(message = "Text should not be empty")
    private String text;
}

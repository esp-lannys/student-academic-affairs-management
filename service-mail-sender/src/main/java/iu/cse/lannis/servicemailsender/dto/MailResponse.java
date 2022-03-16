package iu.cse.lannis.servicemailsender.dto;

import iu.cse.lannis.servicemailsender.entity.Mail;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class MailResponse {
    private final HttpStatus status;
    private final String message;
    private final Mail mail;

    public MailResponse(HttpStatus status, String message, Mail mail) {
        this.mail = mail;
        this.status = status;
        this.message = message;
    }
}

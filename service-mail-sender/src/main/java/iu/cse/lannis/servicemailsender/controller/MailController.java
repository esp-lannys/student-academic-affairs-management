package iu.cse.lannis.servicemailsender.controller;

import iu.cse.lannis.servicemailsender.dto.MailResponse;
import iu.cse.lannis.servicemailsender.dto.StudentDto;
import iu.cse.lannis.servicemailsender.entity.Mail;
import iu.cse.lannis.servicemailsender.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mails")
@CrossOrigin("*")
public class MailController {

    private final MailService mailService;

    @Autowired
    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/students")
    public ResponseEntity<MailResponse> sendNewStudentEmail(@RequestBody StudentDto studentDto) {
        var mail = this.mailService.sendSimpleMessage(studentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MailResponse(HttpStatus.CREATED, "Successfully send email", mail));
    }

    @PostMapping("/retentions")
    public ResponseEntity<Mail> sendRetentionEmail(@RequestBody StudentDto studentDto) {
        var mail = this.mailService.sendStudentRetentionMail(studentDto);
        return new ResponseEntity<Mail>(mail, HttpStatus.CREATED);
    }
}

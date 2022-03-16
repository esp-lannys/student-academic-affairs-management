package iu.cse.lannis.servicemailsender.service;

import iu.cse.lannis.servicemailsender.dto.StudentDto;
import iu.cse.lannis.servicemailsender.entity.Mail;
import iu.cse.lannis.servicemailsender.repository.MailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MailRepository mailRepository;

    public Mail sendSimpleMessage(StudentDto studentDto) {
        try {

            Mail newMail = new Mail();
            newMail.setSentTo(studentDto.getEmail());
            newMail.setSubject("TEST MAIL SENT FROM HEAVEN");
            newMail.setText("WELCOME : " + studentDto.getStudentName()
                    + " TO OUR UNIVERSITY.\n"
                    + "WE HOPE YOU WILL HAVE THE MOST MEMORABLE TIME OF YOUR LIFE.");

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(newMail.getSentTo());
            message.setSubject(newMail.getSubject());
            message.setText(newMail.getText());

            mailRepository.save(newMail);
            mailSender.send(message);
            return newMail;
        } catch (MailException exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }

    public Mail sendStudentRetentionMail(StudentDto studentDto) {
        try {
            Mail newMail = new Mail();
            newMail.setSentTo(studentDto.getEmail());
            newMail.setSubject("RETENTION SUCCESS");
            newMail.setText("Dear " + studentDto.getStudentName() + ",\n"
                    + "We would love to inform that your retention request have been successfully approved.\n"
                    + "Maximum retention year: 2 years.");

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(newMail.getSentTo());
            message.setSubject(newMail.getSubject());
            message.setText(newMail.getText());

            mailRepository.save(newMail);
            mailSender.send(message);
            return newMail;
        } catch (MailException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

}

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

    public void sendSimpleMessage(StudentDto studentDto) {
        try {

            Mail newMail = new Mail();
            newMail.setSentTo(studentDto.getEmail());
            newMail.setSubject("TEST MAIL SENT FROM HEAVEN");
            newMail.setText("HAHAHA MINH GUI MAIL DUOC ROI NE CAC BAN, XIN CHAO: " + studentDto.getStudentName() + " DA DEN VOI CHUNG TOI");

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(newMail.getSentTo());
            message.setSubject(newMail.getSubject());
            message.setText(newMail.getText());

            mailRepository.save(newMail);
            mailSender.send(message);
        } catch (MailException exception) {
            exception.printStackTrace();
        }

    }
}

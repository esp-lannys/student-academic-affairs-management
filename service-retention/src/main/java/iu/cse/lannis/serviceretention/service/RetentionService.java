package iu.cse.lannis.serviceretention.service;

import iu.cse.lannis.serviceretention.dto.RetentionRequestDto;
import iu.cse.lannis.serviceretention.dto.StudentDto;
import iu.cse.lannis.serviceretention.dto.VerifyStudentInfoDto;
import iu.cse.lannis.serviceretention.entity.Retention;
import iu.cse.lannis.serviceretention.exception.ServiceRetentionException;
import iu.cse.lannis.serviceretention.kafka.producer.Sender;
import iu.cse.lannis.serviceretention.repository.RetentionRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RetentionService {

    private boolean isStudentVerified;

    private final RetentionRepository retentionRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(RetentionService.class);

    @Value("${spring.kafka.topic.retentionRequested}")
    private String RETENTION_REQUESTED_TOPIC;

    @Value("${spring.kafka.topic.retentionSucceeded}")
    private String RETENTION_SUCCEEDED_TOPIC;

    private final Sender sender;

    private StudentDto studentDto;

    @Autowired
    public RetentionService(RetentionRepository retentionRepository, Sender sender) {
        this.retentionRepository = retentionRepository;
        this.sender = sender;
        this.isStudentVerified = false;
        this.studentDto = new StudentDto();
    }

    public List<Retention> getAllRetentions() {
        return this.retentionRepository.findAll();
    }

    public Retention getRetentionById(Long retentionId) {
        return this.retentionRepository.findById(retentionId)
                .orElseThrow(() -> new ServiceRetentionException("Retention Not found", HttpStatus.NOT_FOUND));
    }

    private void sendVerificationMessage(VerifyStudentInfoDto verifyStudentInfoDto) {
        this.sender.sendStudentVerificationMessage(RETENTION_REQUESTED_TOPIC, verifyStudentInfoDto);
    }

    private void sendRetentionSucceededMessage(StudentDto studentDto) {
        this.sender.sendRetentionSuccessMessage(RETENTION_SUCCEEDED_TOPIC, studentDto);
    }

    @KafkaListener(topics = "${spring.kafka.topic.retentionStudentVerified}",
            containerFactory = "kafkaListenerContainerFactory",
            groupId = "${spring.kafka.consumer.group-id}")
    private void receiveVerificationMessage(StudentDto studentDto) {
        LOGGER.info("Message Received -> '{}'", studentDto);
        this.isStudentVerified = true;
        this.studentDto = studentDto;
    }

    public Retention createRetention(RetentionRequestDto retentionRequestDto) {
        // 1. Send student verification message
        VerifyStudentInfoDto verifyStudentInfoDto = new VerifyStudentInfoDto(retentionRequestDto.getStudentId(),
                retentionRequestDto.getStudentName(), retentionRequestDto.getEmail());
        sendVerificationMessage(verifyStudentInfoDto);

        // 2. Receive verification result
        StudentDto studentInfo = this.studentDto;
        if (!this.isStudentVerified) {
            throw new ServiceRetentionException("Student not found", HttpStatus.BAD_REQUEST);
        } else {
            // 3. Save retention to database
            Retention newRetention = new Retention();
            newRetention.setNote(retentionRequestDto.getNote());
            newRetention.setStudentId(studentInfo.getId());
            newRetention.setStudentName(studentInfo.getStudentName());
            newRetention.setStudentEmail(studentInfo.getEmail());
            newRetention.setReason(retentionRequestDto.getReason());
            this.retentionRepository.save(newRetention);
            if (retentionRepository.existsById(newRetention.getId())) {
                sendRetentionSucceededMessage(this.studentDto);
                return newRetention;
            } else {
                throw new ServiceRetentionException("Cannot create retention", HttpStatus.BAD_REQUEST);
            }
        }
    }
}

package iu.cse.lannis.serviceretention.service;

import iu.cse.lannis.serviceretention.dto.RetentionRequestDto;
import iu.cse.lannis.serviceretention.dto.SendEmailDto;
import iu.cse.lannis.serviceretention.dto.StudentDto;
import iu.cse.lannis.serviceretention.dto.VerifyStudentInfoDto;
import iu.cse.lannis.serviceretention.entity.Retention;
import iu.cse.lannis.serviceretention.exception.ServiceRetentionException;
import iu.cse.lannis.serviceretention.kafka.producer.Sender;
import iu.cse.lannis.serviceretention.repository.RetentionRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Slf4j
public class RetentionService {

    private final RetentionRepository retentionRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(RetentionService.class);
    @Value("${spring.kafka.topic.retentionRequested}")
    private String RETENTION_REQUESTED_TOPIC;
    @Value("${spring.kafka.topic.retentionSucceeded}")
    private String RETENTION_SUCCEEDED_TOPIC;
    private final Sender sender;
    private RetentionRequestDto retentionRequestDto;
    private final RestTemplate restTemplate;

    @Autowired
    public RetentionService(RetentionRepository retentionRepository, Sender sender, RestTemplate restTemplate) {
        this.retentionRepository = retentionRepository;
        this.sender = sender;
        this.restTemplate = restTemplate;
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

    private void sendRetentionSucceededMessage(String uuid, StudentDto studentDto) {
        this.sender.sendRetentionSuccessMessage(RETENTION_SUCCEEDED_TOPIC, uuid, studentDto);
    }

    @KafkaListener(topics = "${spring.kafka.topic.retentionStudentVerified}",
            containerFactory = "kafkaListenerContainerFactory",
            groupId = "${spring.kafka.consumer.group-id}")
    @SneakyThrows
    private void receiveVerificationMessage(@Header(name = KafkaHeaders.RECEIVED_MESSAGE_KEY) String uuid, @Payload StudentDto studentDto) {
        LOGGER.info("Message Received -> {} id {}", studentDto, uuid);
        this.save(studentDto);
        sendRetentionSucceededMessage(uuid, studentDto);
    }

    public void createRetention(RetentionRequestDto retentionRequestDto) {
        VerifyStudentInfoDto verifyStudentInfoDto = new VerifyStudentInfoDto(retentionRequestDto.getStudentId(),
                retentionRequestDto.getStudentName(), retentionRequestDto.getEmail());
        setRetentionRequestDto(retentionRequestDto);
        sendVerificationMessage(verifyStudentInfoDto);
    }

    public Retention createRetentionVer2(RetentionRequestDto retentionRequestDto) {

        // Check if student exists
        var studentDto = restTemplate.getForObject("http://service-student/students/" + retentionRequestDto.getStudentId(), StudentDto.class);
        Assert.notNull(studentDto, "Student not found");

        // Save retention information to database
        setRetentionRequestDto(retentionRequestDto);
        var newRetention = this.save(studentDto);

        // Send email
        if (retentionRepository.existsById(newRetention.getId())) {
            var mailDto = new SendEmailDto(studentDto.getId(), studentDto.getStudentName(), studentDto.getEmail());
            restTemplate.postForObject("http://service-mail-sender/mails/retentions", mailDto,SendEmailDto.class);
        } else {
            throw new ServiceRetentionException("Cannot create retention", HttpStatus.BAD_REQUEST);
        }
        return newRetention;
    }

    public Retention save(StudentDto studentDto) {
        var newRetention = new Retention();
        newRetention.setReason(getRetentionRequestDto().getReason());
        newRetention.setNote(getRetentionRequestDto().getNote());
        newRetention.setStudentId(studentDto.getId());
        newRetention.setStudentName(studentDto.getStudentName());
        newRetention.setStudentEmail(studentDto.getEmail());
        return this.retentionRepository.save(newRetention);
    }

    public RetentionRequestDto getRetentionRequestDto() {
        return retentionRequestDto;
    }

    public void setRetentionRequestDto(RetentionRequestDto retentionRequestDto) {
        this.retentionRequestDto = retentionRequestDto;
    }
}

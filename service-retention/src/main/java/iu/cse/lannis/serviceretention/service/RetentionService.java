package iu.cse.lannis.serviceretention.service;

import iu.cse.lannis.serviceretention.dto.RetentionRequestDto;
import iu.cse.lannis.serviceretention.dto.StudentDto;
import iu.cse.lannis.serviceretention.dto.VerifyStudentInfoDto;
import iu.cse.lannis.serviceretention.entity.Retention;
import iu.cse.lannis.serviceretention.exception.ServiceRetentionException;
import iu.cse.lannis.serviceretention.kafka.producer.Sender;
import iu.cse.lannis.serviceretention.repository.RetentionRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.Asserts;
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
import org.springframework.util.Assert;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

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

    @Autowired
    public RetentionService(RetentionRepository retentionRepository, Sender sender) {
        this.retentionRepository = retentionRepository;
        this.sender = sender;
        this.retentionRequestDto = new RetentionRequestDto();
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
    @SneakyThrows
    private void receiveVerificationMessage(@Header(name = KafkaHeaders.RECEIVED_MESSAGE_KEY) String uuid, @Payload StudentDto studentDto) {
        LOGGER.info("Message Received -> {} id {}", studentDto, uuid);

        Retention newRetention = new Retention();
        newRetention.setNote(this.retentionRequestDto.getNote());
        newRetention.setStudentId(studentDto.getId());
        newRetention.setStudentName(studentDto.getStudentName());
        newRetention.setStudentEmail(studentDto.getEmail());
        newRetention.setReason(this.retentionRequestDto.getReason());
        this.retentionRepository.save(newRetention);
        if (retentionRepository.existsById(newRetention.getId())) {
            sendRetentionSucceededMessage(studentDto);
        } else {
            throw new ServiceRetentionException("Cannot create retention", HttpStatus.BAD_REQUEST);
        }
    }

    public void createRetention(RetentionRequestDto retentionRequestDto) {
        VerifyStudentInfoDto verifyStudentInfoDto = new VerifyStudentInfoDto(retentionRequestDto.getStudentId(),
                retentionRequestDto.getStudentName(), retentionRequestDto.getEmail());
        this.retentionRequestDto = retentionRequestDto;
        sendVerificationMessage(verifyStudentInfoDto);
    }
}

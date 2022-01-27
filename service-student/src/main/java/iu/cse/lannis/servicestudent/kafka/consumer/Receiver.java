package iu.cse.lannis.servicestudent.kafka.consumer;

import iu.cse.lannis.servicestudent.dto.VerifyStudentForRetention;
import iu.cse.lannis.servicestudent.exception.ServiceStudentException;
import iu.cse.lannis.servicestudent.kafka.producer.Sender;
import iu.cse.lannis.servicestudent.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.concurrent.CountDownLatch;

public class Receiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

    private final CountDownLatch latch = new CountDownLatch(1);

    @Value("${spring.kafka.topic.retentionStudentVerified}")
    private String RETENTION_STUDENT_VERIFIED_TOPIC;

    @Autowired
    private StudentService studentService;

    @Autowired
    private Sender sender;

    @KafkaListener(topics = "${spring.kafka.topic.retentionRequested}", containerFactory = "kafkaListenerContainerFactory")
    public void receive(@Header(name = KafkaHeaders.RECEIVED_MESSAGE_KEY) String uuid, @Payload VerifyStudentForRetention payload) {
        try {
            LOGGER.info("Message received => {} uuid => {}", payload, uuid);
            var student = this.studentService.getStudentByEmail(payload.getEmail());
            this.sender.sendWithId(RETENTION_STUDENT_VERIFIED_TOPIC, uuid, student);
            latch.countDown();
        } catch (ServiceStudentException exception) {
            LOGGER.error(exception.getMessage());
        }
    }
}

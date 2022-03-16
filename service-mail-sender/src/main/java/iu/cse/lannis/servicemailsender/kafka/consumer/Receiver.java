package iu.cse.lannis.servicemailsender.kafka.consumer;

import iu.cse.lannis.servicemailsender.dto.StudentDto;
import iu.cse.lannis.servicemailsender.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import java.util.concurrent.CountDownLatch;

public class Receiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

    private final CountDownLatch latch = new CountDownLatch(1);

    @Autowired
    private MailService emailService;

    @KafkaListener(topics = "${spring.kafka.topic.studentCreated}", containerFactory = "kafkaListenerContainerFactory", groupId = "${spring.kafka.consumer.group-id}")
    public void receive(StudentDto payload) {
        LOGGER.info("received payload='{}'", payload);
        emailService.sendSimpleMessage(payload);
        latch.countDown();
    }

    @KafkaListener(topics = "${spring.kafka.topic.retentionSucceeded}", containerFactory = "kafkaListenerContainerFactory", groupId = "${spring.kafka.consumer.group-id}")
    public void receiveRetentionSucceededMessage(@Header(name = KafkaHeaders.RECEIVED_MESSAGE_KEY) String uuid, @Payload StudentDto payload) {
        LOGGER.info("received payload -> {}, key -> {} ", payload, uuid);
        emailService.sendStudentRetentionMail(payload);
        latch.countDown();
    }
}

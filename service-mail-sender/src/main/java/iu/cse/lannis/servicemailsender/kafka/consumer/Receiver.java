package iu.cse.lannis.servicemailsender.kafka.consumer;

import iu.cse.lannis.servicemailsender.dto.StudentDto;
import iu.cse.lannis.servicemailsender.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.concurrent.CountDownLatch;

public class Receiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

    private final CountDownLatch latch = new CountDownLatch(1);

    @Autowired
    private MailService emailService;

    @KafkaListener(topics = "${spring.kafka.topic.studentCreated}", containerFactory = "kafkaListenerContainerFactory")
    public void receive(StudentDto payload) {
        LOGGER.info("received payload='{}'", payload);
        emailService.sendSimpleMessage(payload);
        latch.countDown();
    }

    @KafkaListener(topics = "${spring.kafka.topic.retentionSucceeded}", containerFactory = "kafkaListenerContainerFactory")
    public void receiveRetentionSucceededMessage(StudentDto payload) {
        LOGGER.info("received payload='{}'", payload);
        emailService.sendSimpleMessage(payload);
        latch.countDown();
    }
}

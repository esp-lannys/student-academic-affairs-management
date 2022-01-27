package iu.cse.lannis.serviceretention.kafka.producer;

import iu.cse.lannis.serviceretention.dto.StudentDto;
import iu.cse.lannis.serviceretention.dto.VerifyStudentInfoDto;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Sender {

    private static final Logger LOGGER = LoggerFactory.getLogger(Sender.class);

    @Autowired
    private KafkaTemplate<String, VerifyStudentInfoDto> studentVerificationKafkaTemplate;

    @Autowired
    private KafkaTemplate<String, StudentDto> retentionSucceededKafkaTemplate;

    public void sendStudentVerificationMessage(String topic, VerifyStudentInfoDto verifyStudentInfoDto) {
        String uuid = UUID.randomUUID().toString();
        ListenableFuture<SendResult<String, VerifyStudentInfoDto>> future =
                this.studentVerificationKafkaTemplate.send(new ProducerRecord<>(topic, uuid, verifyStudentInfoDto));
        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult<String, VerifyStudentInfoDto> stringVerifyStudentInfoDtoSendResult) {
                LOGGER.info("sending verification message ={} to topic={} with offset {} id = {}", verifyStudentInfoDto, topic, stringVerifyStudentInfoDtoSendResult.getRecordMetadata().offset(), uuid);
            }

            @Override
            public void onFailure(Throwable throwable) {
                LOGGER.error("Unable to send message to topic: {}, id: {} {}",topic, uuid, throwable.getMessage());
            }
        });
    }

    public void sendRetentionSuccessMessage(String topic, StudentDto studentDto) {
        String uuid = UUID.randomUUID().toString();
        ListenableFuture<SendResult<String, StudentDto>> future =
                this.retentionSucceededKafkaTemplate.send(new ProducerRecord<>(topic, uuid, studentDto));
        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult<String, StudentDto> stringVerifyStudentInfoDtoSendResult) {
                LOGGER.info("sending payload={} to topic={} with id={}", studentDto, topic, uuid);
            }

            @Override
            public void onFailure(Throwable throwable) {
                LOGGER.error("Unable to send message to topic: {}, {}",topic, throwable.getMessage());
            }
        });
    }
}

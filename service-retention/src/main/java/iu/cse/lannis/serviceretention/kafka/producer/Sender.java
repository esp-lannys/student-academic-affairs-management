package iu.cse.lannis.serviceretention.kafka.producer;

import iu.cse.lannis.serviceretention.dto.StudentDto;
import iu.cse.lannis.serviceretention.dto.VerifyStudentInfoDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

public class Sender {

    private static final Logger LOGGER = LoggerFactory.getLogger(Sender.class);

    @Autowired
    private KafkaTemplate<String, VerifyStudentInfoDto> studentVerificationKafkaTemplate;

    @Autowired
    private KafkaTemplate<String, StudentDto> retentionSucceededKafkaTemplate;

    public void sendStudentVerificationMessage(String topic, VerifyStudentInfoDto payload) {
        ListenableFuture<SendResult<String, VerifyStudentInfoDto>> future = this.studentVerificationKafkaTemplate.send(topic, payload);
        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult<String, VerifyStudentInfoDto> stringVerifyStudentInfoDtoSendResult) {
                LOGGER.info("sending payload='{}' to topic='{}' with offset '{}'", payload, topic, stringVerifyStudentInfoDtoSendResult.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(Throwable throwable) {
                LOGGER.error("Unable to send message to topic: " + topic, throwable);
            }
        });
    }

    public void sendRetentionSuccessMessage(String topic, StudentDto studentDto) {
        ListenableFuture<SendResult<String, StudentDto>> future = this.retentionSucceededKafkaTemplate.send(topic, studentDto);
        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult<String, StudentDto> stringVerifyStudentInfoDtoSendResult) {
                LOGGER.info("sending payload='{}' to topic='{}'", studentDto, topic);
            }

            @Override
            public void onFailure(Throwable throwable) {
                LOGGER.error("Unable to send message to topic: " + topic, throwable);
            }
        });
    }
}

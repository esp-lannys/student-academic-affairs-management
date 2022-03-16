package iu.cse.lannis.servicestudent.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Value("${spring.kafka.topic.retentionRequested}")
    private String RETENTION_REQUESTED_TOPIC;
    @Value("${spring.kafka.topic.retentionSucceeded}")
    private String RETENTION_SUCCEEDED_TOPIC;
    @Value("${spring.kafka.topic.studentCreated}")
    private String STUDENT_CREATED_TOPIC;
    @Value("${spring.kafka.topic.retentionStudentVerified}")
    private String RETENTION_STUDENT_VERIFIED_TOPIC;

    @Bean
    public NewTopic studentCreatedTopic() {
        return TopicBuilder.name(STUDENT_CREATED_TOPIC)
                .partitions(10)
                .replicas(3)
                .build();
    }

    @Bean
    public NewTopic retentionRequestedTopic() {
        return TopicBuilder.name(RETENTION_REQUESTED_TOPIC)
                .partitions(10)
                .replicas(3)
                .build();
    }

    @Bean
    public NewTopic retentionStudentVerifiedTopic() {
        return TopicBuilder.name(RETENTION_STUDENT_VERIFIED_TOPIC)
                .partitions(10)
                .replicas(3)
                .build();
    }

    @Bean
    public NewTopic retentionSucceededTopic() {
        return TopicBuilder.name(RETENTION_SUCCEEDED_TOPIC)
                .partitions(10)
                .replicas(3)
                .build();
    }
}

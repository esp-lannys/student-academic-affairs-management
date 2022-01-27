package iu.cse.lannis.servicestudent.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    @Bean
    public NewTopic studentCreatedTopic() {
        return TopicBuilder.name("${spring.kafka.topic.studentCreated}")
                .partitions(10)
                .replicas(3)
                .build();
    }

    @Bean
    public NewTopic retentionRequestedTopic() {
        return TopicBuilder.name("${spring.kafka.topic.retentionRequested}")
                .partitions(10)
                .replicas(3)
                .build();
    }

    @Bean
    public NewTopic retentionStudentVerifiedTopic() {
        return TopicBuilder.name("${spring.kafka.topic.retentionStudentVerified}")
                .partitions(10)
                .replicas(3)
                .build();
    }

    @Bean
    public NewTopic retentionSucceededTopic() {
        return TopicBuilder.name("${spring.kafka.topic.retentionSucceeded}")
                .partitions(10)
                .replicas(3)
                .build();
    }
}

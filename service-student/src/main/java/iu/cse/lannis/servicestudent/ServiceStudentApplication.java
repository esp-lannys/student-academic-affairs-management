package iu.cse.lannis.servicestudent;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;


@SpringBootApplication
@EnableEurekaClient
public class ServiceStudentApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceStudentApplication.class, args);
	}

	@Bean
	public NewTopic studentCreatedTopic() {
		return TopicBuilder.name("STUDENT_CREATED_TOPIC")
				.partitions(1)
				.replicas(1)
				.build();
	}

	@Bean
	public NewTopic retentionRequestedTopic() {
		return TopicBuilder.name("RETENTION_REQUESTED_TOPIC")
				.partitions(1)
				.replicas(1)
				.build();
	}

	@Bean
	public NewTopic retentionSucceededTopic() {
		return TopicBuilder.name("RETENTION_SUCCEEDED_TOPIC")
				.partitions(1)
				.replicas(1)
				.build();
	}

	@Bean
	public NewTopic retentionStudentVerifiedTopic() {
		return TopicBuilder.name("RETENTION_STUDENT_VERIFIED_TOPIC")
				.partitions(1)
				.replicas(1)
				.build();
	}
}

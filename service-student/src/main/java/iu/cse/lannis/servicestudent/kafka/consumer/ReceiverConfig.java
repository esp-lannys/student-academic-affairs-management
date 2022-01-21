package iu.cse.lannis.servicestudent.kafka.consumer;

import iu.cse.lannis.servicestudent.dto.VerifyStudentForRetention;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class ReceiverConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id}")
    private String consumerGroupId;

    @Bean
    public ConsumerFactory<String, VerifyStudentForRetention> studentVerificationFactory() {
        JsonDeserializer<VerifyStudentForRetention> customDeserializer =
                new JsonDeserializer<>(VerifyStudentForRetention.class);
        customDeserializer.setRemoveTypeHeaders(false);
        customDeserializer.addTrustedPackages("*");
        customDeserializer.setUseTypeMapperForKey(true);

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, customDeserializer);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroupId);
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), customDeserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, VerifyStudentForRetention> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, VerifyStudentForRetention> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(studentVerificationFactory());
        return factory;
    }

    @Bean
    public Receiver receiver() {
        return new Receiver();
    }
}

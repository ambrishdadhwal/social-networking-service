package com.social.network.config.kafka;

import com.social.network.domain.SocialNetworkPayload;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigurationExcludeFilter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;

@ConditionalOnProperty(name = "kafka.enabled", havingValue = "true")
@EnableKafka
@Slf4j
@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic socialNetworkTopic() {
        return TopicBuilder.name("social-network-topic")
                .partitions(3)
                .compact()
                .build();
    }

    @Bean
    public NewTopic testTopic() {
        return TopicBuilder.name("test-topic")
                .partitions(3)
                .compact()
                .build();
    }

    @KafkaListener(topics = "test-topic", groupId = "consumer-group", containerFactory = "kafkaListenerContainerFactory")
    public void onEventString(String msg) {
        log.info("Message Received : {}" , msg);
    }

    @KafkaListener(topics = "social-network-topic", groupId = "consumer-group-1", containerFactory = "socialKafkaListenerContainerFactory")
    public void listenWithErrorHandler(ConsumerRecord<String, SocialNetworkPayload> record) {
        System.out.println("Message processed 1: " + record.value());
    }

}

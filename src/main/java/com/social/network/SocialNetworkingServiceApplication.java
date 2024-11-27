package com.social.network;

import com.social.network.domain.SocialNetworkPayload;
import com.social.network.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ConfigurationPropertiesScan
@EnableRetry
@EnableJpaRepositories("com.social.network")
@EntityScan(basePackages =
        {"com.social.network"})
@Slf4j
@RequiredArgsConstructor
@SpringBootApplication(scanBasePackages = {"com.social.network", "com.social.network.config.kafka"})
@EnableRabbit
@EnableTransactionManagement
@EnableKafka
public class SocialNetworkingServiceApplication {

    final UserRepo userRepo;

    public static void main(String[] args) {
        SpringApplication.run(SocialNetworkingServiceApplication.class, args);
    }


    //TODO:: move kafka stuff to kafka package.
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

package com.social.network.service;

import com.social.network.domain.SocialNetworkPayload;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


/*
* TODO:: Currently @KafkaListner is not working in this package.
*  Please refere to SocialNetworkingServiceApplication.java for reference.
* */

@Component
@Slf4j
public class KafkaConsumerService {


    @KafkaListener(topics = "my-topic", groupId = "consumer-group-1", containerFactory = "kafkaListenerContainerFactory")
    public void onEventString(String o) {
        log.info("Message Received: {}" , o);
    }

    @KafkaListener(topics = "my-topic", groupId = "consumer-group-1",containerFactory = "socialKafkaListenerContainerFactory" )
    public void onEvent(SocialNetworkPayload o) {
        log.info("Message Received: {}" , o);
    }

    @KafkaListener(topics = "my-topic", groupId = "consumer-group-1", containerFactory = "socialKafkaListenerContainerFactory")
    public void listenWithErrorHandler(ConsumerRecord<String, SocialNetworkPayload> record) {
        System.out.println("Message processed: " + record.value());
    }

    @KafkaListener(topics = "my-topic", groupId = "consumer-group-1", containerFactory = "socialKafkaListenerContainerFactory")
    public void listenandler(ConsumerRecord<String, SocialNetworkPayload> record) {
        System.out.println("Message processed: " + record.value());
    }

}

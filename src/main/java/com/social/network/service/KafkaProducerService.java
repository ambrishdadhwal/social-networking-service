package com.social.network.service;

import com.social.network.domain.SocialNetworkPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class KafkaProducerService {
    @Autowired
    @Qualifier(value = "kafkaTemplate")
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private KafkaTemplate<String, SocialNetworkPayload> socialKafkaTemplate;

    public void postMessage(final String message) {
        CompletableFuture<SendResult<String, String>>  future = kafkaTemplate.send("test-topic", message);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                System.out.println("Sent message=[" + message +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            } else {
                System.out.println("Unable to send message=[" +
                        message + "] due to : " + ex.getMessage());
            }
        });
    }

    /*public void sendGreetingMessage(Greeting greeting) {
        greetingKafkaTemplate.send("configured-topic", greeting);
    }*/

    public void sendGreetingMessage(final SocialNetworkPayload message) {
        CompletableFuture<SendResult<String, SocialNetworkPayload>>  future = socialKafkaTemplate.send("social-network-topic", message);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                System.out.println("Sent message=[" + message +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            } else {
                System.out.println("Unable to send message=[" +
                        message + "] due to : " + ex.getMessage());
            }
        });
    }
}

package com.social.network.config.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;

/*
*
* TODO:: kafka listners are not working if we keep below Beans as separte in com.social.network.config.kafka package
*  You can see SocialNetworkingServiceApplication.java for reference.
* */

@Configuration
@ConditionalOnProperty(name = "app.kafka.enabled", havingValue = "true")
@EnableKafka
@Slf4j
public class KafkaConfig {

   /* @Bean
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
    }*/

}

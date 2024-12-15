package com.social.network.config.datasource;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import jakarta.annotation.PostConstruct;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfig {

    @Value("${spring.mongo.uri}")
    private String MONGO_URI;

    @Value("${spring.mongo.db}")
    private String DATABASE = "user_events";

    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create(MONGO_URI);
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), DATABASE);
    }

    @EventListener(ContextRefreshedEvent.class)
    public void initDatabase() {
        MongoTemplate mongoTemplate = mongoTemplate();
        if (!mongoTemplate.collectionExists(DATABASE)) {
            mongoTemplate.createCollection(DATABASE);
        }
    }

}

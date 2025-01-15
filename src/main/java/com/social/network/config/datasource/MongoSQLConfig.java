package com.social.network.config.datasource;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Arrays;

@ConditionalOnProperty(name = "spring.mongo.enable", havingValue = "true")
@Configuration
@EnableTransactionManagement
@EnableMongoRepositories(
        basePackages = "com.social.network.repository.mongo",
        mongoTemplateRef = "mongoTemplate"
)
@Profile("!h2")
@RequiredArgsConstructor
@Slf4j
public class MongoSQLConfig {

    @Value("${spring.mongo.uri}")
    private String MONGO_URI;

    @Value("${spring.mongo.db}")
    private String DATABASE;

    @Value("${spring.mongo.collections}")
    private String[] collections;

    @Bean("mongoClient")
    public MongoClient mongoClient() {
        log.info("######## mongoClient created #########");
        return MongoClients.create(MONGO_URI);
    }

    @Bean("mongoTemplate")
    public MongoTemplate mongoTemplate(@Qualifier("mongoClient") MongoClient mongoClient) {
        log.info("######### MongoTemplate started ########");
        MongoTemplate mongoTemplate = new MongoTemplate(mongoClient, DATABASE);
        initDatabase(mongoTemplate);
        return mongoTemplate;
    }

    public void initDatabase(@Qualifier("mongoTemplate") MongoTemplate mongoTemplate) {
        Arrays.stream(collections).forEach(collection -> {
            if (!mongoTemplate.collectionExists(collection)) {
                mongoTemplate.createCollection(collection);
               log.info("Collection created : {}", collection);
            } else {
                log.info("Collection already exists: {} ", collection);
            }
        });
    }
}

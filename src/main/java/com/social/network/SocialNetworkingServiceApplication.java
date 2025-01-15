package com.social.network;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ConfigurationPropertiesScan
@EnableRetry
@Slf4j
@RequiredArgsConstructor
@SpringBootApplication(exclude = {
        LiquibaseAutoConfiguration.class, DataSourceAutoConfiguration.class
})
@EnableRabbit
@EnableTransactionManagement
public class SocialNetworkingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SocialNetworkingServiceApplication.class, args);
    }
}

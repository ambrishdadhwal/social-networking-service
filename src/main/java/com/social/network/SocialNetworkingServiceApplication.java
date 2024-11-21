package com.social.network;

import com.social.network.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ConfigurationPropertiesScan
@EnableRetry
@EnableJpaRepositories("com.social.network")
@EntityScan(basePackages =
        {"com.social.network"})
@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
@EnableRabbit
@EnableTransactionManagement
public class SocialNetworkingServiceApplication {

    final UserRepo userRepo;

    public static void main(String[] args) {
        SpringApplication.run(SocialNetworkingServiceApplication.class, args);
    }


}

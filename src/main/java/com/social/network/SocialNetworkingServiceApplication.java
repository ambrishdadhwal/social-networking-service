package com.social.network;

import com.social.network.domain.Country;
import com.social.network.domain.Gender;
import com.social.network.entity.ProfileE;
import com.social.network.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@ConfigurationPropertiesScan
@EnableRetry
@EnableJpaRepositories("com.social.network")
@EntityScan(basePackages =
        {"com.social.network"})
@ComponentScan(basePackages =
        {"com.social.network"})
@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
@EnableRabbit
@EnableTransactionManagement
public class SocialNetworkingServiceApplication implements CommandLineRunner {

    final UserRepo userRepo;

    public static void main(String[] args) {
        SpringApplication.run(SocialNetworkingServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception
    {
        log.info("Admin User is Created..");

        List<ProfileE> persons = IntStream.iterate(1, n -> n + 1).limit(50)
                .mapToObj(k ->ProfileE.builder()
                        .id(Long.valueOf(k))
                        .firstName("First Name - " + k)
                        .lastName("Last Name - " + k)
                        .email(k + "@social.com")
                        .password("password-" + k)
                        .isActive(true)
                        .dob(LocalDate.now())
                        .country(Country.INDIA)
                        .gender(Gender.NOT_INTERESTED_TO_MENTION)
                        .createDateTime(LocalDateTime.now())
                        .modifiedDateTime(LocalDateTime.now())
                        .build()).collect(Collectors.toList());

        userRepo.saveAll(persons);

    }

}

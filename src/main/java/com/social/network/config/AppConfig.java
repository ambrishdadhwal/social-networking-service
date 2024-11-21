package com.social.network.config;

import com.social.network.domain.Country;
import com.social.network.domain.Gender;
import com.social.network.entity.UserProfileE;
import com.social.network.repository.UserRepo;
import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Configuration
@Slf4j
public class AppConfig {

    @Bean
    public TimedAspect timedAspect(MeterRegistry registry) {
        return new TimedAspect(registry);
    }

    @Bean
    CommandLineRunner commandLineRunner(UserRepo userRepo) {
        return args -> {
            log.info("Admin User is Created..");

            List<UserProfileE> persons = IntStream.iterate(1, n -> n + 1).limit(50)
                    .mapToObj(k -> UserProfileE.builder()
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
        };
    }
}

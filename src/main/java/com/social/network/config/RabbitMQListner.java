package com.social.network.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.social.network.notification.EmailDetailDTO;
import com.social.network.notification.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class RabbitMQListner implements MessageListener {


    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EmailService emailService;

    @Override
    public void onMessage(Message message) {
        System.out.println("received: " + message);
        String msgBodu = new String(message.getBody(), StandardCharsets.UTF_8);

        try {
            EmailDetailDTO emailDetailDTO = objectMapper.readValue(msgBodu, EmailDetailDTO.class);

            emailService.processEmailMessage(emailDetailDTO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

  /*  @RabbitListener(queues = "email_queue", autoStartup = "true", messageConverter = "jsonMessageConverter")
    @RabbitHandler
    public void processEmailMessage(@Payload Message message) {
        System.out.println("RabbitListener --received: " + new String(message.getBody()));
    }*/
}

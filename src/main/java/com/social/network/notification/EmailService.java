package com.social.network.notification;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Configuration
public class EmailService {

    private final JavaMailSender emailSender;

    private final ObjectMapper objectMapper;

    @Value("${application.mail.sent.from}")
    private String fromUsr;


    public void processEmailMessage(EmailDetailDTO emailDetailDTO) throws MessagingException {
        String to = emailDetailDTO.getTo();
        String subject = emailDetailDTO.getSubject();
        String body = generateEmailBody(emailDetailDTO); // Generate body from DTO

        sendEmail(to, subject, body);
    }


    public void sendEmail(String to, String subject, String body) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, true); // Set HTML content to true
        emailSender.send(message);
    }

    public String generateEmailBody(EmailDetailDTO emailDetailDTO) {
        String template = loadEmailTemplate(emailDetailDTO.getTemplateName());
        // Replace placeholders with actual values from EmailDetailDTO
        String body = template;
        // If there are additional dynamic values
        if (emailDetailDTO.getDynamicValue() != null) {
            for (Map.Entry<String, Object> entry : emailDetailDTO.getDynamicValue().entrySet()) {
                body = body.replace("{{" + entry.getKey() + "}}", entry.getValue().toString());
            }
        }
        return body;
    }

    public String loadEmailTemplate(String templateName) {
        ClassPathResource resource = new ClassPathResource("templates/" + templateName + ".html");
        try {
            return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Error loading email template " + templateName, e);
        }
    }
}
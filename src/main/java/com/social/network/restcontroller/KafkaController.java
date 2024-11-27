package com.social.network.restcontroller;

import com.social.network.domain.SocialNetworkPayload;
import com.social.network.service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class KafkaController {

    @Autowired
    KafkaProducerService kafkaProducerService;

    @PostMapping("/send-message")
    public ResponseEntity<String> sendMessage(@RequestBody String message) {
       kafkaProducerService.postMessage(message);
        return new ResponseEntity<>("Message successfully produced..", HttpStatus.OK);
    }

    @PostMapping("/send-message-g")
    public ResponseEntity<String> sendMessageG(@RequestBody SocialNetworkPayload message) {
        kafkaProducerService.sendGreetingMessage(message);
        return new ResponseEntity<>("Message successfully produced..", HttpStatus.OK);
    }
}

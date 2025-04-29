package com.example.demo.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private ProducerTemplate producerTemplate;

    @PostMapping
    public ResponseEntity<String> sendMessage(@RequestBody Map<String, Object> payload) throws JsonProcessingException {
        String jsonMessage = new ObjectMapper().writeValueAsString(payload);
        producerTemplate.sendBody("kafka:my-messages-topic?brokers=PLAINTEXT://localhost:9092", jsonMessage);
        return ResponseEntity.ok("Message sent to Kafka");
    }
}

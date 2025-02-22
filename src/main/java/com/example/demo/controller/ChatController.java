package com.example.demo.controller;

import com.example.demo.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/chat/civil")
public class ChatController {
    @Autowired
    private ChatService chatService;

    @PostMapping("/message")
    public Map<String, String> chat(@RequestBody Map<String, String> request) {
        String userMsg = request.get("message");
        String aiResponse = chatService.getChatResponse(userMsg);
        return Map.of("response", aiResponse);
    }
}

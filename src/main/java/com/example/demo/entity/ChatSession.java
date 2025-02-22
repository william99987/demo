package com.example.demo.entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

public class ChatSession {
    private String sessionId;
    private LocalDateTime createdTime = LocalDateTime.now();
    private List<ChatMessage> messages = new ArrayList<>();

    public ChatSession() {};

    public ChatSession(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionId() { return sessionId; };
    public void setSessionId(String id) { this.sessionId = id; };
    public LocalDateTime getCreateTime() { return createdTime; }
    public List<ChatMessage> getMessages() { return messages; }

    public void addMessage(ChatMessage message) {
        this.messages.add(message);
    }

}

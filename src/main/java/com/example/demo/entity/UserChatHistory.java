package com.example.demo.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "UserChatHistory")
public class UserChatHistory {
    @Id
    private String userId;
    private List<ChatSession> sessions = new ArrayList<>();

    public UserChatHistory(){}

    public UserChatHistory(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public List<ChatSession> getSessions() {
        return sessions;
    }

    public void addSession(ChatSession session) {
        this.sessions.add(session);
    }
}

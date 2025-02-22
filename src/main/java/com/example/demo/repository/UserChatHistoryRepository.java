
package com.example.demo.repository;

import com.example.demo.entity.ChatSession;
import com.example.demo.entity.UserChatHistory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserChatHistoryRepository extends MongoRepository<UserChatHistory, String> {
    Optional<UserChatHistory> findById(String userId);
}
package com.example.demo.service;

import com.example.demo.entity.ChatMessage;
import com.example.demo.entity.ChatSession;
import com.example.demo.entity.User;
import com.example.demo.entity.UserChatHistory;
import com.example.demo.repository.UserChatHistoryRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.print.attribute.standard.Media;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;


@Service
public class ChatService {
    @Value("${OPENAI_API_KEY}")
    private String openAiApiKey;
    private static String OPENAI_API_URL = "wss://api.openai.com/v1/realtime";
//    private final ObjectMapper jacksonObjectMapper = new ObjectMapper();

    @Autowired
    private UserChatHistoryRepository userChatHistoryRepository;
    private ChatSession chatSession;
    private ChatMessage chatMessage;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ObjectMapper jacksonObjectMapper;

//    public ChatSession createNewSession(String userId) {
//        return userChatHistoryRepository.save(new ChatSession(userId));
//    }
//
//    public ChatSession getChatHistory(String sessionId) {
//        return userChatHistoryRepository.findById(sessionId).orElse(null);
//    }

    public void addMessageToSession(String userId, String sessionId, String userMessage, String aiResponse) {
        Optional<UserChatHistory> optionalHistory = userChatHistoryRepository.findById(userId);
        Query query = new Query(Criteria.where("_id").is(userId)
                    .and("sessions.sessionId").is(sessionId));
        if(userMessage == null)
        {
            Update update = new Update()
            .push("sessions.$.messages", new ChatMessage("ai", aiResponse));
            mongoTemplate.updateFirst(query, update, UserChatHistory.class);
        }
        else {
            Update update = new Update() .push("sessions.$.messages", new ChatMessage("user", userMessage))
                    .push("sessions.$.messages", new ChatMessage("ai", aiResponse));
            mongoTemplate.updateFirst(query, update, UserChatHistory.class);
        }
    }

    public String createNewSession(String userId) {
        ChatSession chatSession = new ChatSession();
        Query query = new Query(Criteria.where("_id").is(userId));
        Update update = new Update().push("$.sessions", chatSession);
        mongoTemplate.updateFirst(query, update, UserChatHistory.class);
        return chatSession.getSessionId();
    }

    public String loadChatAvatar() {
        ClassPathResource resource = new ClassPathResource("static/chat-avatar.json");
        try (InputStream inputStream = resource.getInputStream();
             Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8)){
            return scanner.useDelimiter("\\A").next();
        }
        catch (Exception e) {
            System.out.println("Error reading avatar file");
            return "{}";
        }
    }

    public Map<String, Object> parseAvatarJson(String avatarFile) {
        try {
            return jacksonObjectMapper.readValue(avatarFile, new TypeReference<Map<String, Object>>() {});
        } catch (JsonMappingException e) {
            System.err.println("Json mapping error:" + e.getMessage());
        } catch (JsonProcessingException e) {
            System.err.println("JSON processing Error" + e.getMessage());
        }
        return Map.of();
    }

    public String startChatSession(String avatarId, String userId) {

        ChatService chatService = new ChatService();
        //Start new chat session
        String sessionId = chatService.createNewSession(userId);

        // Get avatar description
        String avatarFile = loadChatAvatar();

        Map<String, Object> avatarMap = parseAvatarJson(avatarFile);
        String avatarDescription = (String) avatarMap.get(avatarId);

        //
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authentication", "Bearer" + openAiApiKey);
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-4o-mini-2024-07-18");
        requestBody.put("messages", avatarDescription);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<Map> response = restTemplate.exchange(OPENAI_API_URL, HttpMethod.POST, entity, Map.class);
        String aiResponse = (String) ((List<Map<String, Object>>) response.getBody().get("choices")).get(0).get("message");
        chatService.addMessageToSession(userId, sessionId,null, aiResponse);
        return aiResponse;


    }

    //Process user input
    public String getChatResponse(String userId, String sessionId, String userMessage) {
        ChatService chatService = new ChatService();
        List<ChatMessage> chatMessages = new ArrayList<>();
        UserChatHistory userChatHistory = userChatHistoryRepository.findById(userId).orElse(null);
        if (userChatHistory == null) {
           return "Invalid user ID";
        }
        else {
            // get all sessions out
            List<ChatSession> chatHistory = userChatHistory.getSessions();

            for(ChatSession ss : chatHistory)
            {
                if(sessionId == ss.getSessionId())
                {
                    chatMessages = ss.getMessages();
                }
            }

            // Convert chat history to openAI format
            List<Map<String, String>> chatContext = new ArrayList<>();
            chatContext.add(Map.of("role","system","content","i am st"));

            for(ChatMessage msg : chatMessages) {
                chatContext.add(Map.of("role",msg.getSender(),"content",msg.getMessage()));
            }

            chatContext.add(Map.of("role", "user","content",userMessage));

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization","Bearer" + openAiApiKey );
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "gpt-4o-mini-2024-07-18");
            requestBody.put("messages", chatContext);
            requestBody.put("max_tokens", 400);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<Map> response = restTemplate.exchange(OPENAI_API_URL, HttpMethod.POST, entity, Map.class);

            String aiResponse = (String) ((List<Map<String, Object>>) response.getBody().get("choices")).get(0).get("message");

            chatService.addMessageToSession(userId, sessionId, userMessage, aiResponse);

            return aiResponse;
        }

    }
}

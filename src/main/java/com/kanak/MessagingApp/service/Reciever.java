package com.kanak.MessagingApp.service;

import com.kanak.MessagingApp.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.user.SimpSession;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Service;

@Service
public class Reciever {
    private static final Logger logger= LoggerFactory.getLogger(Reciever.class);
    private final SimpMessageSendingOperations messagingTemplate;
    private final SimpUserRegistry userRegistry;

    public Reciever(SimpMessageSendingOperations messagingTemplate, SimpUserRegistry userRegistry) {
        this.messagingTemplate = messagingTemplate;
        this.userRegistry = userRegistry;
    }

    @KafkaListener(topics = "messaging",groupId = "chat")
    public void consume(Message chatMessage){
        logger.info("Recieved message from kafka: "+chatMessage);
        for(SimpUser user :userRegistry.getUsers()){
            for(SimpSession session: user.getSessions()){
                if(!session.getId().equals(chatMessage.getSessionId())){
                    messagingTemplate.convertAndSendToUser(session.getId(),"/topic/public",chatMessage);
                }
            }
        }
    }
}

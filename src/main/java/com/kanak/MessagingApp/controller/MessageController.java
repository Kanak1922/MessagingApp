package com.kanak.MessagingApp.controller;
import com.kanak.MessagingApp.dto.ChatMessageDTO;
import com.kanak.MessagingApp.service.Sender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import com.kanak.MessagingApp.dto.MessageDTO;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class MessageController {

    private final Sender sender;
    @Autowired
    private final SimpMessagingTemplate messagingTemplate;
    private static final Logger logger =LoggerFactory.getLogger(MessageController.class);

    public MessageController(Sender sender, SimpMessagingTemplate messagingTemplate) {
        this.sender = sender;
        this.messagingTemplate = messagingTemplate;
    }



    @MessageMapping("/sendMessage")
    public void sendMessage(Message<ChatMessageDTO> chatMessage){
       logger.error("Sending message to /topic/public topic ");
        //ConcurrentHashMap<String,String> hashMap= (ConcurrentHashMap<String, String>) chatMessage.getHeaders().get("simpSessionAttributes");
        String userName=chatMessage.getPayload().getSender();
        String content=chatMessage.getPayload().getContent();
        String type=chatMessage.getPayload().getType();
        logger.error("username : "+userName);
        logger.error("Content : "+content);
        logger.error("type : "+type);
//        if(chatMessage.getPayload().getSender()!=null)
//        messagingTemplate.convertAndSendToUser(userName,"/topic/public",content);
//       else
           messagingTemplate.convertAndSend("/topic/public", chatMessage);
       logger.error("Message sent to /topic/public topic ");

    }

    @MessageMapping("/addUser")
    @SendTo("/topic/public")
    public String addUser(Message<ChatMessageDTO> userNameMessage, SimpMessageHeaderAccessor headerAccessor){
        logger.error("inside add user function");
        logger.error("username : "+ userNameMessage.getPayload().getSender());
        logger.error("type : "+userNameMessage.getPayload().getType());
        logger.error("content :"+userNameMessage.getPayload().getContent());
        if(headerAccessor.getSessionAttributes()!=null){
            headerAccessor.getSessionAttributes().put("username",userNameMessage.getPayload().getSender());
            headerAccessor.getSessionAttributes().put("type",userNameMessage.getPayload().getType());
            headerAccessor.getSessionAttributes().put("content",userNameMessage.getPayload().getContent());
        }
        messagingTemplate.convertAndSend("/topic/public",userNameMessage);
        return userNameMessage.getPayload().toString();
    }
}

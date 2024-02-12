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
       logger.info("Sending message to /topic/public topic ");
        String userName=chatMessage.getPayload().getSender();
        String content=chatMessage.getPayload().getContent();
        String type=chatMessage.getPayload().getType();
        logger.info("username : "+userName);
        logger.info("Content : "+content);
        logger.info("type : "+type);
           messagingTemplate.convertAndSend("/topic/public", chatMessage);
       logger.info("Message sent to /topic/public topic ");

    }

    @MessageMapping("/addUser")
    @SendTo("/topic/public")
    public String addUser(Message<ChatMessageDTO> userNameMessage, SimpMessageHeaderAccessor headerAccessor){
        logger.info("inside add user function");
        logger.info("username : "+ userNameMessage.getPayload().getSender());
        logger.info("type : "+userNameMessage.getPayload().getType());
        logger.info("content :"+userNameMessage.getPayload().getContent());
        if(headerAccessor.getSessionAttributes()!=null){
            headerAccessor.getSessionAttributes().put("username",userNameMessage.getPayload().getSender());
            headerAccessor.getSessionAttributes().put("type",userNameMessage.getPayload().getType());
            headerAccessor.getSessionAttributes().put("content",userNameMessage.getPayload().getContent());
        }
        messagingTemplate.convertAndSend("/topic/public",userNameMessage);
        return userNameMessage.getPayload().toString();
    }
}

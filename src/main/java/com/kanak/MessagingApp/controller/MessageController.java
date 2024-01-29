package com.kanak.MessagingApp.controller;

//import com.kanak.MessagingApp.model.Message;

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
//        chatMessage.setSessionId(headerAccessor.getSessionId());
//        sender.send("messaging",chatMessage.getPayload().toString());
       logger.error("Sending message to /topic/public: "+chatMessage);
       //messagingTemplate.convertAndSend(chatMessage.getPayload())
      // messagingTemplate.convertAndSend("/topic/public", chatMessage);
        if(chatMessage.getPayload().getUsername()!=null)
        messagingTemplate.convertAndSendToUser(chatMessage.getPayload().getUsername(),"/topic/public",chatMessage.getPayload().getContent());
       else
            messagingTemplate.convertAndSend("/topic/public", chatMessage);
        logger.error("Message :  "+chatMessage.getPayload());
       logger.error("Message sent to /topic/public: "+chatMessage);

    }

    @MessageMapping("/addUser")
    @SendTo("/topic/public")
    public String addUser(Message<MessageDTO> userNameMessage, SimpMessageHeaderAccessor headerAccessor){
        logger.error("inside add user function");
        if(headerAccessor.getSessionAttributes()!=null){
            headerAccessor.getSessionAttributes().put("username",userNameMessage.getPayload().getSender());
        }
        logger.error("exit add user function");
        return userNameMessage.getPayload().toString();
    }
}

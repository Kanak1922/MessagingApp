package com.kanak.MessagingApp.model;


import com.kanak.MessagingApp.enums.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventListener {

 private static final Logger logger= LoggerFactory.getLogger(WebSocketEventListener.class);
 private final SimpMessageSendingOperations messagingTemplate;

 public WebSocketEventListener(SimpMessageSendingOperations messagingTemplate) {
  this.messagingTemplate = messagingTemplate;
 }
 @EventListener
 public void handleWebSocketConnectListener(SessionConnectedEvent event){
  logger.info("Recieved a new web socket connection");
 }

 public void handleWebSocketDisconnectListner(SessionDisconnectEvent event){
  StompHeaderAccessor headerAccessor=StompHeaderAccessor.wrap(event.getMessage());
  logger.error("Inside handel DIsconnect function");
  String username=(String) headerAccessor.getSessionAttributes().get("username");
  if(username !=null){
   logger.info("User Disconnected: "+ username);
   Message chatMessage=new Message();
   chatMessage.setType(MessageType.DISCONNECT);
   chatMessage.setSender(username);
   messagingTemplate.convertAndSend("/topic/public",chatMessage);
  }
 }

}

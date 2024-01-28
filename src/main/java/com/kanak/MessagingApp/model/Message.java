package com.kanak.MessagingApp.model;


import com.kanak.MessagingApp.enums.MessageType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message {

    private String content;
    private String sender;
    private String sessionId;
    private MessageType type;


}

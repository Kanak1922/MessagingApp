package com.kanak.MessagingApp.dto;

public class ChatMessageDTO {

    private String sender;
    private String content;
    private String type;

    public ChatMessageDTO() {
    }

    public ChatMessageDTO(String sender, String content, String type) {
        this.sender = sender;
        this.content = content;
        this.type = type;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String username) {
        this.sender = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

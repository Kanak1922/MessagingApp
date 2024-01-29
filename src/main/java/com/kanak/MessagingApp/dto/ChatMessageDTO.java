package com.kanak.MessagingApp.dto;

public class ChatMessageDTO {

    private String username;
    private String content;
    private String type;

    public ChatMessageDTO() {
    }

    public ChatMessageDTO(String username, String content, String type) {
        this.username = username;
        this.content = content;
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

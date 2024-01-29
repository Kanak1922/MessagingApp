package com.kanak.MessagingApp.dto;

public class MessageDTO {
    private String sender;
    private String type;
    public MessageDTO() {
    }

    public MessageDTO(String sender, String type) {
        this.sender = sender;
        this.type = type;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

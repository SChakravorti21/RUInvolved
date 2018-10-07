package com.example.shoumyo.ruinvolved.models;

import java.util.Date;

public class ChatMessage {

    private String messageText;
    private String messageUser;
    private long messageTime;
    private long clubId;

    public ChatMessage(String messageText, String messageUser, long clubId) {
        this.messageText = messageText;
        this.messageUser = messageUser;
        this.clubId = clubId;

        // Initialize to current time
        messageTime = new Date().getTime();
    }

    // needed for firebase library to convert the firebase object into a chat message
    public ChatMessage(){

    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }

    public long getClubId() {
        return clubId;
    }
}


package com.example.atharva.chatroomtest;

import java.util.ArrayList;

public class ClubChats {

    private int clubId;
    private ArrayList<ChatMessage> messages;

    public ClubChats() {
        clubId = 100;
        messages = new ArrayList<>();
    }


    public int getClubId() {
        return clubId;
    }

    public ArrayList<ChatMessage> getMessages() {
        return messages;
    }
}

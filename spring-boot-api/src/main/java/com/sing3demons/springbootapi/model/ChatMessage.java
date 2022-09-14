package com.sing3demons.springbootapi.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {
    private String from;
    private String message;
    private Date created;

    public ChatMessage() {
        created = new Date();
    }
}

package com.sing3demons.springbootapi.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sing3demons.springbootapi.business.ChatBusiness;
import com.sing3demons.springbootapi.exception.ChatException;
import com.sing3demons.springbootapi.model.ChatMessageRequest;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatApi {
    private final ChatBusiness business;

    @PostMapping(value = "/message")
    public ResponseEntity<Void> post(@RequestBody ChatMessageRequest request) throws ChatException {
        business.post(request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}

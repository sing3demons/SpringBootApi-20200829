package com.sing3demons.springbootapi.business;

import java.util.Optional;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.sing3demons.springbootapi.exception.ChatException;
import com.sing3demons.springbootapi.model.ChatMessage;
import com.sing3demons.springbootapi.model.ChatMessageRequest;
import com.sing3demons.springbootapi.util.SecurityUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatBusiness {
    private final SimpMessagingTemplate template;

    public void post(ChatMessageRequest request) throws ChatException {
        final String destination = "/topic/chat";

        Optional<String> opt = SecurityUtil.getCurrentUserId();
        if (opt.isEmpty()) {
            throw ChatException.accessDenied();
        }

        ChatMessage payload = new ChatMessage();
        payload.setFrom(opt.get());
        payload.setMessage(request.getMessage());

        template.convertAndSend(destination, payload);

    }
}

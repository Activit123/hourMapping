package com.mihai.Java_2024.features.chatBotFeature.controller;

import com.mihai.Java_2024.features.chatBotFeature.dto.ChatBotDTO;
import com.mihai.Java_2024.features.chatBotFeature.service.ChatBotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/chatbot")
public class ChatBotController {

    private final ChatBotService chatBotService;

    @PostMapping("/response")
    public ResponseEntity<ChatBotDTO> getChatResponse(@RequestBody ChatBotDTO chatBotDTO) {
        return chatBotService.getChatResponse(chatBotDTO);
    }
}

package com.mihai.Java_2024.features.chatBotFeature.service;

import com.mihai.Java_2024.features.chatBotFeature.dto.ChatBotDTO;
import com.mihai.Java_2024.features.chatBotFeature.dto.ChatResponseDTO;
import com.mihai.Java_2024.features.chatBotFeature.dto.HistoryDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;


@RequiredArgsConstructor
@Service
@Slf4j
public class ChatBotService {
    @Value("${ai.url}")
    private String url;
    public ResponseEntity<ChatResponseDTO> getResponse(ChatBotDTO chatBotDTO) {
        ChatResponseDTO responseDTO = callApi(chatBotDTO);
        if (responseDTO == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ChatResponseDTO("An error occurred while getting the response."));
        }
        return ResponseEntity.ok(responseDTO);
    }

    private ChatResponseDTO callApi(ChatBotDTO chatBotDTO) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            HttpPost request = new HttpPost(url);
            request.addHeader("Content-Type", "application/json");
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(chatBotDTO);
            StringEntity entity = new StringEntity(json);
            request.setEntity(entity);

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                int statusCode = response.getStatusLine().getStatusCode();
                String responseBody = EntityUtils.toString(response.getEntity());
                if (statusCode == HttpStatus.OK.value()) {
                    ObjectMapper responseMapper = new ObjectMapper();
                    return responseMapper.readValue(responseBody, ChatResponseDTO.class);
                } else {
                    return new ChatResponseDTO("Error response from API: " + responseBody);
                }
            }
        } catch (IOException e) {
            log.warn(e.getMessage());
            return null;
        }
    }

    public ResponseEntity<ChatBotDTO> getChatResponse(ChatBotDTO chatBotDTO) {
        if (chatBotDTO.getQuestion().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        ChatResponseDTO chatResponseDTO = getResponse(chatBotDTO).getBody();
        chatBotDTO.getHistory().add(new HistoryDTO("assistant", chatResponseDTO.getResponse()));
        chatBotDTO.getHistory().add(new HistoryDTO("user", chatBotDTO.getQuestion()));
        return ResponseEntity.ok(chatBotDTO);
    }

}
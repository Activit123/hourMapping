package com.mihai.Java_2024.features.chatBotFeature.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatBotDTO {
    private String question;
    private List<HistoryDTO> history;
}


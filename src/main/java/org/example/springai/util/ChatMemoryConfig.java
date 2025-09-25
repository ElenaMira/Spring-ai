package org.example.springai.util;

import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class ChatMemoryConfig {
    @Bean
    ChatMemory chatMemory(ChatMemoryRepository chatMemoryRepository)
    {
        return MessageWindowChatMemory
                .builder()
                .maxMessages(2)
                .chatMemoryRepository(chatMemoryRepository)
                .build();
    }
}

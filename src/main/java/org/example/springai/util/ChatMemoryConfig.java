package org.example.springai.util;

import com.alibaba.cloud.ai.memory.redis.RedisChatMemoryRepository;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatMemoryConfig {
    @Value("${spring.ai.memory.redis.host}")
    private String redisHost;
    @Value("${spring.ai.memory.redis.port}")
    private int redisPort;
    @Value("${spring.ai.memory.redis.password}")
    private String redisPassword;
    @Value("${spring.ai.memory.redis.timeout}")
    private int redisTimeout;
//    @Bean
//    ChatMemory chatMemory(ChatMemoryRepository chatMemoryRepository)
//    {
//        return MessageWindowChatMemory
//                .builder()
//                .maxMessages(2)
//                .chatMemoryRepository(chatMemoryRepository)
//                .build();
//    }
    //底层通过spring-ai的jdbc自动装配
//    @Bean
//    ChatMemory chatMemoryJDBC(JdbcChatMemoryRepository jdbcChatMemoryRepository)
//    {
//        return MessageWindowChatMemory
//                .builder()
//                .maxMessages(10)
//                .chatMemoryRepository(jdbcChatMemoryRepository)
//                .build();
//    }
    @Bean
    public RedisChatMemoryRepository redisChatMemoryRepository()
    {
        return RedisChatMemoryRepository.builder()
                .host(redisHost)
                .port(redisPort)
                .password(redisPassword)
                .timeout(redisTimeout)
                .build();
    }
    @Bean
    ChatMemory chatMemoryRedis(RedisChatMemoryRepository redisChatMemoryRepository)
    {
        return MessageWindowChatMemory
                .builder()
                .maxMessages(10)
                .chatMemoryRepository(redisChatMemoryRepository)
                .build();
    }
}

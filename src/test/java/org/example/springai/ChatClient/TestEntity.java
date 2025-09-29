package org.example.springai.ChatClient;

import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
public class TestEntity {
    ChatClient chatClient;
    @BeforeEach
    public  void init(@Autowired DashScopeChatModel dashScopeChatModel,
                      @Autowired ChatMemory chatMemory
    )
    {
        ChatClient.Builder builder = ChatClient.builder(dashScopeChatModel);
        chatClient = builder.defaultAdvisors(
                        new SimpleLoggerAdvisor(),
                        PromptChatMemoryAdvisor
                                .builder(chatMemory)
                                .build())
                .defaultSystem("请判断用户是否需要转接人工,如果判断有意图转接人工,那么请你只回答false,不要多余的输出")
                .build();
    }
    //测试不同角色
    @Test
    public void Test()
    {
        Boolean entity = chatClient.prompt()
                .user("转人工")
                .advisors(p -> p.params(Map.of(ChatMemory.CONVERSATION_ID, "zss")))
                .call()
                .entity(boolean.class);
        if (!Boolean.TRUE.equals(entity)){
            System.out.println("转人工");
        }else {
            System.out.println("不转人工");
        }
    }
}

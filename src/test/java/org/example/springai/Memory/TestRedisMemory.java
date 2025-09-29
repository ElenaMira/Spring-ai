package org.example.springai.Memory;

import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestRedisMemory {
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
                .build();
    }
    @Test
    public void Test()
    {
        chatClient.prompt()
                .user("我是来自沙县")
                .advisors(p-> p.params(java.util.Map.of(ChatMemory.CONVERSATION_ID,"zss")))
                .call()
                .content();
        chatClient.prompt()
                .user("我是一名学生")
                .advisors(p-> p.params(java.util.Map.of(ChatMemory.CONVERSATION_ID,"zss")))
                .call()
                .content();
        chatClient.prompt()
                .user("我叫zss")
                .advisors(p-> p.params(java.util.Map.of(ChatMemory.CONVERSATION_ID,"zss")))
                .call()
                .content();
        String content =chatClient.prompt()
                .user("你知道关于我的哪些信息")
                .advisors(p-> p.params(java.util.Map.of(ChatMemory.CONVERSATION_ID,"zss")))
                .call()
                .content();
        System.out.println(content);
        System.out.println("--------------------------------------------");
        String content1 =chatClient.prompt()
                .user("我叫什么")
                .advisors(p-> p.params(java.util.Map.of(ChatMemory.CONVERSATION_ID,"cj")))
                .call()
                .content();
        System.out.println("--------------------------------------------");
        System.out.println(content1);
    }
}

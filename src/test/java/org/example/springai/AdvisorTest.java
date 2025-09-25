package org.example.springai;

import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import org.example.springai.util.ReReadingAdvisor;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class AdvisorTest {
    @Test
    public void DefaultAdvisorTest(@Autowired DashScopeChatModel dashScopeChatModel)
    {
        SafeGuardAdvisor.builder().sensitiveWords(List.of("周生生")).failureResponse("周生生不能被查").order(0).build();
        //选择传入大模型DashScopeChatModel:是阿里的大模型
        ChatClient chatClient =ChatClient.builder(dashScopeChatModel)
                .defaultAdvisors(new SimpleLoggerAdvisor()
                ,SafeGuardAdvisor
                                .builder()
                                .sensitiveWords(List.of("周生生"))
                                .failureResponse("周生生不能被查")
                                .order(0)
                                .build()
                                )
                .build();
        String content =chatClient.prompt()
                .user("你知道周生生是谁吗")
                .call()
                .content();
        System.out.println(content);
    }
    @Test
    public void ReReadingAdvisorTest(@Autowired DashScopeChatModel dashScopeChatModel)
    {
        //选择传入大模型DashScopeChatModel:是阿里的大模型
        ChatClient chatClient =ChatClient.builder(dashScopeChatModel)
                .defaultAdvisors(new ReReadingAdvisor(),
                        new SimpleLoggerAdvisor()
                )
                .build();
        String content =chatClient.prompt()
                .user("你是谁")
                .call()
                .content();
        System.out.println(content);
    }
    @Test
    public void ChatMemoryTest(@Autowired DashScopeChatModel dashScopeChatModel,
                                @Autowired ChatMemory chatMemory)
    {
        //选择传入大模型DashScopeChatModel:是阿里的大模型
        ChatClient chatClient =ChatClient.builder(dashScopeChatModel)
                .defaultAdvisors(
                        new SimpleLoggerAdvisor(),
                        PromptChatMemoryAdvisor.builder(chatMemory).build()
                )
                .build();
        chatClient.prompt()
                .user("我叫zss")
                .call()
                .content();
        System.out.println("--------------------------------------------");
        String content =chatClient.prompt()
                .user("我叫什么")
                .call()
                .content();
        System.out.println(content);
    }

}

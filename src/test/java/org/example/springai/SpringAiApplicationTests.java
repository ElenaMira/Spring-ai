package org.example.springai;

import com.alibaba.cloud.ai.dashscope.api.DashScopeAgentApi;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;

import java.util.Scanner;

@SpringBootTest
public class SpringAiApplicationTests {

    @Test
    public void testChatClient(@Autowired DashScopeChatModel dashScopeChatModel)
    {
        //构造器注入chatClient对象(明确指定使用dashscopeChatModel)
        ChatClient chatClient = ChatClient.builder(dashScopeChatModel).build();
        String content = chatClient.prompt()
                .user("你是谁")
                .call()
                .content();
        System.out.println(content);
    }
    //基于流式处理(为了让响应更快,提升用户体验)
    @Test
    public void testChatClientStream(@Autowired DashScopeChatModel dashScopeChatModel)
    {
        //选择传入大模型DashScopeChatModel:是阿里的大模型
        ChatClient chatClient =ChatClient.builder(dashScopeChatModel).build();
        chatClient.prompt()
                .user("你是谁")
                .stream()
                .content()
                .toIterable()
                .forEach(System.out::println);
    }

        @Test
        public void testChat(@Autowired OllamaChatModel ollamaChatModel) {

            String text = ollamaChatModel.call("你是谁");
            System.out.println(text);
        }
    @Test
    public void test() {
        SnowflakeIDWorker idWorker = new SnowflakeIDWorker(1, 1);
        for (int i = 0; i < 100; i++) {
            long id = idWorker.nextId();
            System.out.println(id);
        }
    }


}
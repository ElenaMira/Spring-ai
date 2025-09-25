package org.example.springai.Controller;

import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import org.example.springai.AiConfig;
import org.example.springai.pojo.SystemPrompt;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.deepseek.DeepSeekChatModel;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.HashMap;
import java.util.Map;

@RestController
public class MorPlatformAndModel {
    //1. 定义HashMap来存储多个模型
    HashMap<String, ChatModel> platforms=new HashMap<>();

    @Autowired
    private SystemPrompt systemPrompt;

    //2. 通过构造函数注入多个模型
    public  MorPlatformAndModel(
            DashScopeChatModel dashScopeChatModel,
            DeepSeekChatModel deepSeekChatModel,
            OllamaChatModel ollamaChatModel
    ) {
        //HashMap实现唯一标识
        platforms.put("dashscope", dashScopeChatModel);
        platforms.put("deepseek", deepSeekChatModel);
        platforms.put("ollama", ollamaChatModel);
    }

    @RequestMapping(value = "/chat", produces = "text/stream;charset=UTF-8")
    public Flux<String> chat(@RequestParam("question") String question,
                             @ModelAttribute AiConfig option)
    {
        //获取模型
        String platform = option.getPlatform();
        System.out.println("platform:"+platform);
        ChatModel chatModel = platforms.get(platform);
        //创建ChatClient的构建器
        ChatClient.Builder builder = ChatClient.builder(chatModel);
        //通过构建器创建ChatClient
        ChatClient chatClient = builder.defaultOptions(
                ChatOptions.builder()
                        .temperature(option.getTemperature())
                        .model(option.getModel())
                        .build()
        )
                .defaultSystem(systemPrompt.getSystemPrompt())
                .build();
        //通过ChatClient创建prompt
        Flux<String> content = chatClient
                .prompt()
                .system(p-> p.param("name", "zss"))
                .user(question)
                .stream()
                .content();
//        流式返回不能直接System
//        System.out.println("question:"+question);
//        System.out.println("platform:"+platform);
//        System.out.println("model:"+option.getModel());
//        System.out.println("temperature:"+option.getTemperature());
//        System.out.println("content:"+content);
        return content;
    }
}

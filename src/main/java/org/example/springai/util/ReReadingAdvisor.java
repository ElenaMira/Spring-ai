package org.example.springai.util;

import org.jetbrains.annotations.NotNull;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.AdvisorChain;
import org.springframework.ai.chat.client.advisor.api.BaseAdvisor;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.core.io.Resource;

import java.util.Map;

public class ReReadingAdvisor implements BaseAdvisor {

    //    private Resource EnhanceWords;
    //    public ReReadingAdvisor(Resource EnhanceWords) {
    //        this.EnhanceWords = EnhanceWords;
    //    }

    // 在调用ChatClient之前执行
    @NotNull
    @Override
    public ChatClientRequest before(@NotNull ChatClientRequest chatClientRequest, @NotNull AdvisorChain advisorChain) {
        //重写之前的提示词
        //1. 获取原文
        String contents = chatClientRequest.prompt().getContents();
        //2. 自定义提示词模板

        String enhanceWords = "你是一名老师你喜欢做自我介绍";
        String lastPrompt = PromptTemplate.builder().template(enhanceWords).build()
                .render(Map.of("User", contents));
        return chatClientRequest.mutate().prompt(Prompt.builder().content(lastPrompt).build()).build();
    }
    //
    @NotNull
    @Override
    public ChatClientResponse after(@NotNull ChatClientResponse chatClientResponse, @NotNull AdvisorChain advisorChain) {
        return chatClientResponse;
    }
    //执行的顺序
    @Override
    public int getOrder() {
        return 0;
    }
}

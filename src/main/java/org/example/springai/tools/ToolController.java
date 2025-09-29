package org.example.springai.tools;

import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(("/tools"))
public class ToolController {
    private final ChatClient chatClient;

    public ToolController(ToolService toolService,
                          DashScopeChatModel dashScopeChatModel,
                          ToolCallbackProvider toolCallbackProvider
                            ) {
         this.chatClient = ChatClient.builder(dashScopeChatModel)
                 .defaultTools(toolService)
                 .defaultToolCallbacks(toolCallbackProvider)
                 .build();
    }
    @GetMapping("/ticket")
    public String Ticket(@RequestParam(value = "message",defaultValue = "你是谁") String message) {
       return chatClient.prompt()
                .user(message)
                .call()
                .content();
    }
}
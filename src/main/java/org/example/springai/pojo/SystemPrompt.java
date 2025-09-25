package org.example.springai.pojo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class SystemPrompt {
    @Value("classpath:/prompt/systemPrompt-test.st")
    private Resource systemPrompt;


    public String getSystemPrompt() {
        try {
            return new String(systemPrompt.getInputStream().readAllBytes());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

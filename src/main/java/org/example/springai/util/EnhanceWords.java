package org.example.springai.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

public class EnhanceWords {
    @Value("classpath:/prompt/EnhanceWords.st")
    private Resource enhanceWords;
    public String getEnhanceWords() {
        try {
            return new String(enhanceWords.getInputStream().readAllBytes());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

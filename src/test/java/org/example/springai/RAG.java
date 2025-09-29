package org.example.springai;

import com.alibaba.cloud.ai.dashscope.embedding.DashScopeEmbeddingModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
public class RAG {
    @Test
    public void test(@Autowired DashScopeEmbeddingModel  model) {
        float[] embed = model.embed("hello world");
        System.out.println(embed.length);
        System.out.println(Arrays.toString(embed));
    }
}

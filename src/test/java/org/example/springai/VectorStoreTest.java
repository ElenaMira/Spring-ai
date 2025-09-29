package org.example.springai;

import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.alibaba.cloud.ai.dashscope.embedding.DashScopeEmbeddingModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.SimpleVectorStoreContent;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootTest
public class VectorStoreTest {
    @Test
    public void test(@Autowired VectorStore vectorStore) {
        Document document1 = Document.builder().text(
                "今天早上我在公司食堂点了一杯拿铁咖啡，味道比昨天的美式更顺滑。喝完后我感觉精神好多了，就开始处理积压的邮件。"
        ).build();
        Document document2 = Document.builder().text(
                "昨天加班到很晚，早上起来有点疲惫。我在楼下的咖啡馆买了一杯美式咖啡，虽然有点苦，但正好让我清醒过来，继续赶项目进度。"
        ).build();
        Document document3 = Document.builder().text(
                "周末的时候，我没有去喝咖啡，而是约了朋友去图书馆。我们在自习室里看了一整天的书，感觉比在咖啡馆学习更安静高效。"
        ).build();

        List<Document> documents = List.of(document1, document2, document3);

        vectorStore.add(documents);

        List<Document> documentList = vectorStore.similaritySearch("喝什么可以提升精神");
        Assertions.assertNotNull(documentList);
        for (Document document : documentList)
        {
            System.out.println(document.getText());
            System.out.println(document.getScore());
        }
    }
    @Test
    public void test2(@Autowired SimpleVectorStore vectorStore,
                        @Autowired DashScopeChatModel  model){
        ChatClient chatClient = ChatClient.builder(model)
                .build();
        chatClient.prompt()
                .user("喝什么可以提升精神")
                .advisors(
                        new SimpleLoggerAdvisor(),
                        QuestionAnswerAdvisor.builder(vectorStore)
                        .order(0)
                        .searchRequest(SearchRequest.builder()
                                .topK(1)
                                .similarityThreshold(0.38)
                                .build())
                        .build())
                .stream()
                .content()
                .toIterable()
                .forEach(System.out::println)
        ;
    }
    @TestConfiguration
    static class TestConfig{
        @Bean
        public VectorStore vectorStore(DashScopeEmbeddingModel model){
            return SimpleVectorStore.builder(model).build();
        }

    }
}

package org.example.springai;

import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;

import java.util.List;

@SpringBootTest
public class TokenSplitterTest {
    @Test
    public void test(@Value("classpath:/RAG/tokenSplitterTest")Resource  resource) {
        TextReader textReader = new TextReader(resource);
        List<Document> documents = textReader.read();
        for (Document document : documents)
        {
            System.out.println("--------------");
            System.out.println(document.getText());
        }
        TokenTextSplitter tokenTextSplitter = new TokenTextSplitter(100, 10, 5, 10, false);
        List<Document> applied = tokenTextSplitter.apply(documents);
        for (Document document : applied)
        {
            System.out.println(document.getText());
            System.out.println("--------------");
        }
    }
}

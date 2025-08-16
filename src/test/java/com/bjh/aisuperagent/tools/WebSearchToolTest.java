package com.bjh.aisuperagent.tools;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;


@SpringBootTest
class WebSearchToolTest {

    @MockitoBean
    private VectorStore pgVectorVectorStore;

    @Value("${search-api.api-key}")
    private String searchApikey;

    @Test
    void searchWeb() {
        WebSearchTool webSearchTool = new WebSearchTool(searchApikey);
        String result = webSearchTool.searchWeb("怎么学编程啊");
        System.out.println("Search result" + result);
        Assertions.assertNotNull(result);
    }
}
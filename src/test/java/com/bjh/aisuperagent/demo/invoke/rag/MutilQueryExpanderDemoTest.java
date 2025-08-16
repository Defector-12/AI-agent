package com.bjh.aisuperagent.demo.invoke.rag;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.ai.rag.Query;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MutilQueryExpanderDemoTest {

    @Resource
    private MutilQueryExpanderDemo multiQueryExpanderDemo;

    @Test
    void expand() {
        List<Query> queires = multiQueryExpanderDemo.expand("怎么学编程啊啊啊啊啊啊？谁来教教我啊啊啊啊啊啊");
        Assertions.assertNotNull(queires);
    }
}
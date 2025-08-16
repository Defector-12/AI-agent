package com.bjh.aisuperagent.demo.invoke;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


@Component
@Profile("!test")  // 测试环境不运行
public class OllamaAiAiInvoke implements CommandLineRunner {

    @Resource
    private ChatModel ollamaChatModel;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("OllamaAiAiInvoke is running!"); // 添加这行

        AssistantMessage assistantMessage = ollamaChatModel.call(new Prompt("你好,你是谁"))
                .getResult()
                .getOutput();
        System.out.println(assistantMessage.getText());
    }
}
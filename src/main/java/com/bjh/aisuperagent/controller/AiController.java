package com.bjh.aisuperagent.controller;


import com.bjh.aisuperagent.agent.BoManus;
import com.bjh.aisuperagent.app.LoveApp;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;

import java.io.IOException;

@RestController
@RequestMapping("/ai")
public class AiController {

    @Resource
    private LoveApp loveApp;

    @Resource
    private ToolCallback[] allTools;

    @Resource
    private ChatModel dashscopeChatModel;

    /**
     * 同步调用 AI 恋爱大师应用
     * @param message
     * @param chatid
     * @return
     */

    @GetMapping("/love_app/chat/sync")
    public String doChatWithLoveApp(String message, String chatid) {
        return loveApp.doChat(message, chatid);
    }

    /**
     * SSE 流式调用 AI 恋爱大师应用
     * @param message
     * @param chatid
     * @return
     */
    @GetMapping(value = "/love_app/chat/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> doChatWithLoveAppSSE(String message, String chatid) {
        return loveApp.doChatByStream(message, chatid);
    }

    /**
     * SSE 流式调用 AI 恋爱大师应用
     * @param message
     * @param chatid
     * @return
     */
    @GetMapping(value = "/love_app/chat/server_sent_event")
    public Flux<ServerSentEvent<String>> doChatWithLoveAppServerSentEvent(String message, String chatid) {
        return loveApp.doChatByStream(message, chatid)
                .map(chunk -> ServerSentEvent
                .builder(chunk)
                .build());
    }

    /**
     * SSE 流式调用 AI 恋爱大师应用
     * @param message
     * @param chatid
     * @return
     */
    @GetMapping(value = "/love_app/chat/sse_emitter")
    public SseEmitter doChatWithLoveAppServerSseEmitter(String message, String chatid) {
        SseEmitter sseEmitter = new SseEmitter(18000L);
        // 获取 Flux 响应式数据流并且直接通过订阅推送给 SseEmitter
        loveApp.doChatByStream(message,chatid)
                .subscribe(chunk -> {
                    try {
                        sseEmitter.send(chunk);
                    } catch (IOException e) {
                        sseEmitter.completeWithError(e);
                }
            }, sseEmitter::completeWithError, sseEmitter::complete);

        return sseEmitter;
    }

    /**
     * 流式调用 Manus 超级智能体
     *
     * @param message
     * @return
     */
    @GetMapping("/manus/chat")
    public SseEmitter doChatWithManus(String message) {
        BoManus boManus = new BoManus(allTools, dashscopeChatModel);
        return boManus.runStream(message);
    }

}


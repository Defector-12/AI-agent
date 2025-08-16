<template>
  <div>
    <h2>AI 恋爱大师</h2>
    <p class="desc">进入页面自动生成聊天室 ID：{{ chatId }}</p>
    <ChatWindow
      :messages="messages"
      :loading="loading"
      placeholder="对恋爱/情感相关的问题说点什么..."
      :onSend="handleSend"
    />
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import ChatWindow, { type ChatMessage } from '../components/ChatWindow.vue';
import { openSse } from '../utils/sse';

function generateChatId() {
  return 'love_' + Math.random().toString(36).slice(2) + Date.now().toString(36);
}

const chatId = ref(generateChatId());
const messages = ref<ChatMessage[]>([]);
const loading = ref(false);
let es: EventSource | null = null;

const handleSend = (text: string) => {
  messages.value.push({ id: crypto.randomUUID(), role: 'user', content: text });
  loading.value = true;
  // 关闭上一个 SSE
  if (es) { es.close(); es = null; }

  const url = `/api/ai/love_app/chat/sse?message=${encodeURIComponent(text)}&chatid=${encodeURIComponent(chatId.value)}`;
  // 服务端：produces = text/event-stream
  es = openSse(url, {
    onMessage: (chunk) => {
      if (messages.value[messages.value.length - 1]?.role === 'ai') {
        messages.value[messages.value.length - 1].content += chunk;
      } else {
        messages.value.push({ id: crypto.randomUUID(), role: 'ai', content: chunk });
      }
    },
    onError: () => {
      loading.value = false;
      es?.close();
      es = null;
    }
  });
  // 当连接结束（有些后端会发送 [DONE] 或断流）, 这里用超时兜底
  setTimeout(() => { loading.value = false; }, 80_000);
};
</script>

<style scoped>
.desc { margin: 6px 0 12px; color: #666; font-size: 13px; }
</style>


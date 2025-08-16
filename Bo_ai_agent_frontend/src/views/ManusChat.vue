<template>
  <div>
    <h2>AI 超级智能体</h2>
    <ChatWindow
      :messages="messages"
      :loading="loading"
      placeholder="让智能体来帮你完成复杂任务..."
      :onSend="handleSend"
    />
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import ChatWindow, { type ChatMessage } from '../components/ChatWindow.vue';
import { openSse } from '../utils/sse';

const messages = ref<ChatMessage[]>([]);
const loading = ref(false);
let es: EventSource | null = null;

const handleSend = (text: string) => {
  messages.value.push({ id: crypto.randomUUID(), role: 'user', content: text });
  loading.value = true;
  if (es) { es.close(); es = null; }

  // 后端 Manus 提供 SseEmitter，GET /ai/manus/chat?message=xxx
  const url = `/api/ai/manus/chat?message=${encodeURIComponent(text)}`;
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
  setTimeout(() => { loading.value = false; }, 80_000);
};
</script>

<style scoped></style>


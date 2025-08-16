<template>
  <div class="chat-window">
    <div class="messages" ref="listRef">
      <div v-for="m in messages" :key="m.id" class="message" :class="m.role">
        <div class="bubble">{{ m.content }}</div>
      </div>
    </div>
    <div class="composer">
      <input
        v-model="input"
        class="input"
        type="text"
        :placeholder="placeholder"
        @keydown.enter="handleSend"
      />
      <button class="send" :disabled="loading || !input.trim()" @click="handleSend">发送</button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, watch, nextTick, onBeforeUnmount } from 'vue';

type Role = 'user' | 'ai';

export interface ChatMessage {
  id: string;
  role: Role;
  content: string;
}

const props = defineProps<{
  placeholder?: string;
  onSend: (text: string) => void;
  loading?: boolean;
  messages: ChatMessage[];
}>();

const input = ref('');
const listRef = ref<HTMLDivElement | null>(null);

const handleSend = () => {
  const text = input.value.trim();
  if (!text || props.loading) return;
  props.onSend(text);
  input.value = '';
};

watch(
  () => props.messages.length,
  async () => {
    await nextTick();
    const el = listRef.value;
    if (el) el.scrollTop = el.scrollHeight;
  }
);

onBeforeUnmount(() => {
  // 组件销毁前确保输入状态清理
  input.value = '';
});
</script>

<style scoped>
.chat-window { display: flex; flex-direction: column; height: calc(100vh - 140px); border: 1px solid #eee; border-radius: 8px; overflow: hidden; }
.messages { flex: 1; overflow: auto; padding: 16px; background: #fafafa; }
.message { display: flex; margin-bottom: 12px; }
.message.user { justify-content: flex-end; }
.message.ai { justify-content: flex-start; }
.bubble { max-width: 70%; padding: 10px 12px; border-radius: 10px; line-height: 1.6; white-space: pre-wrap; word-break: break-word; }
.message.user .bubble { background: #1677ff; color: #fff; border-top-right-radius: 2px; }
.message.ai .bubble { background: #fff; border: 1px solid #eee; border-top-left-radius: 2px; }
.composer { display: flex; gap: 8px; padding: 10px; border-top: 1px solid #eee; background: #fff; }
.input { flex: 1; height: 36px; padding: 0 12px; border: 1px solid #ddd; border-radius: 6px; }
.send { height: 36px; padding: 0 16px; background: #1677ff; color: #fff; border: none; border-radius: 6px; cursor: pointer; }
.send:disabled { background: #aac8ff; cursor: not-allowed; }
</style>


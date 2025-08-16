<template>
  <div class="home">
    <h2>请选择要体验的应用</h2>
    <div class="cards">
      <RouterLink class="card" to="/love">
        <h3>AI 恋爱大师</h3>
        <p>情感建议、话术润色与关系维护</p>
      </RouterLink>
      <RouterLink class="card" to="/manus">
        <h3>AI 超级智能体</h3>
        <p>工具调用、任务编排与多步推理</p>
      </RouterLink>
    </div>
  </div>
</template>

<script setup lang="ts"></script>

<style scoped>
.home { padding: 12px; }
.cards { display: grid; grid-template-columns: repeat(auto-fit, minmax(240px, 1fr)); gap: 16px; margin-top: 12px; }
.card { display: block; border: 1px solid #eee; border-radius: 8px; padding: 16px; text-decoration: none; color: inherit; transition: box-shadow .2s ease; }
.card:hover { box-shadow: 0 6px 18px rgba(0,0,0,0.08); }
.card h3 { margin: 0 0 8px; }
.card p { margin: 0; color: #666; }
</style>


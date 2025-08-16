<template>
  <div class="app-container">
    <header class="app-header">
      <h1>AI 超级智能体 Demo</h1>
      <nav>
        <RouterLink to="/">首页</RouterLink>
        <RouterLink to="/love">AI 恋爱大师</RouterLink>
        <RouterLink to="/manus">AI 超级智能体</RouterLink>
      </nav>
    </header>
    <main class="app-main">
      <RouterView />
    </main>
  </div>
</template>

<script setup lang="ts">
</script>

<style scoped>
.app-container { padding: 16px; }
.app-header { display: flex; align-items: center; justify-content: space-between; gap: 16px; }
nav { display: flex; gap: 12px; }
nav a { color: #1677ff; text-decoration: none; }
nav a.router-link-active { font-weight: 600; }
.app-main { margin-top: 16px; }
</style>


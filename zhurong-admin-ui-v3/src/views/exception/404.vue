<template>
  <div class="exception-container">
    <div class="exception-content">
      <div class="exception-icon">
        <ContainerOutlined />
      </div>
      <h1 class="exception-code">404</h1>
      <h2 class="exception-title">页面不存在</h2>
      <p class="exception-message">抱歉，你访问的页面不存在或已被移除。</p>
      <div class="exception-actions">
        <a-button type="primary" @click="goBack">
          <template #icon><ArrowLeftOutlined /></template>
          返回
        </a-button>
        <a-button @click="goHome">
          <template #icon><HomeOutlined /></template>
          首页
        </a-button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
import { ContainerOutlined, ArrowLeftOutlined, HomeOutlined } from '@ant-design/icons-vue'

const router = useRouter()

function goBack() {
  router.back()
}

function goHome() {
  router.push('/dashboard/workbench')
}
</script>

<style scoped>
.exception-container {
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
  padding: 24px;
  background: var(--zhurong-color-bg-layout);
}

.exception-content {
  text-align: center;
  max-width: 400px;
  padding: 48px;
  background: var(--zhurong-color-bg-container);
  border-radius: var(--zhurong-border-radius-lg);
  box-shadow: var(--zhurong-box-shadow-lg);
  border: 1px solid var(--zhurong-color-border);
}

.exception-icon {
  font-size: 64px;
  color: var(--zhurong-color-warning);
  margin-bottom: 24px;
}

.exception-code {
  font-size: 96px;
  font-weight: 700;
  color: var(--zhurong-color-text-heading);
  line-height: 1;
  margin: 0 0 16px;
  font-family: var(--zhurong-font-family-mono);
}

.exception-title {
  font-size: 24px;
  font-weight: 600;
  color: var(--zhurong-color-text-heading);
  margin: 0 0 12px;
}

.exception-message {
  color: var(--zhurong-color-text-secondary);
  font-size: 15px;
  line-height: 1.6;
  margin: 0 0 32px;
}

.exception-actions {
  display: flex;
  gap: 16px;
  justify-content: center;
}
</style>
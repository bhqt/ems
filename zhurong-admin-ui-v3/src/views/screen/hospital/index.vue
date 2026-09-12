<template>
  <div class="page-container">
    <a-card :bordered="false">
      <a-result
        status="info"
        :title="t('hospital.screen.title')"
        sub-title="该页面正在开发中，敬请期待..."
      >
        <template #extra>
          <a-button type="primary" @click="router.back()">返回</a-button>
        </template>
      </a-result>
    </a-card>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
import { useI18n } from 'vue-i18n'

const router = useRouter()
const { t } = useI18n()
</script>

<style scoped>
.page-container {
  padding: 24px;
}
</style>

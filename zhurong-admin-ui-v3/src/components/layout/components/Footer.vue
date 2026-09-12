<template>
  <div class="footer">
    <div class="footer-content">
      <span class="copyright">
        {{ t('common.copyright') }} © 2024-{{ currentYear }} {{ t('common.appTitle') }}
      </span>
      <span class="version">v{{ version }}</span>
      <span class="powered-by" v-if="showPoweredBy">
        {{ t('common.poweredBy') }} <a href="https://github.com" target="_blank" rel="noopener">Zhurong Team</a>
      </span>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed, onMounted } from 'vue'
import { useI18n } from 'vue-i18n'
import { useAppStore } from '@/stores/modules/app'

const { t } = useI18n()
const appStore = useAppStore()

const currentYear = computed(() => new Date().getFullYear())
const version = computed(() => import.meta.env.VITE_APP_VERSION || '3.0.0')
const showPoweredBy = computed(() => import.meta.env.VITE_SHOW_POWERED_BY !== 'false')
</script>

<style scoped>
.footer {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 48px;
  padding: 0 24px;
  background: var(--zhurong-color-bg-container);
  border-top: 1px solid var(--zhurong-color-border);
  color: var(--zhurong-color-text-secondary);
  font-size: var(--zhurong-font-size-sm);
}

.footer-content {
  display: flex;
  align-items: center;
  gap: 16px;
  flex-wrap: wrap;
  justify-content: center;
}

.copyright {
  white-space: nowrap;
}

.version {
  padding: 2px 8px;
  background: var(--zhurong-color-primary-bg);
  color: var(--zhurong-color-primary);
  border-radius: 4px;
  font-family: var(--zhurong-font-family-mono);
  font-size: 11px;
}

.powered-by a {
  color: var(--zhurong-color-primary);
  text-decoration: none;
  transition: color 0.2s;
}

.powered-by a:hover {
  color: var(--zhurong-color-primary-hover);
  text-decoration: underline;
}

@media (max-width: 768px) {
  .footer-content {
    gap: 8px;
  }
}
</style>
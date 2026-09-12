<template>
  <a-layout :class="layoutClass">
    <!-- 侧边栏 -->
    <a-sider
      v-model:collapsed="collapsed"
      :width="siderWidth"
      :collapsed-width="collapsedWidth"
      :trigger="null"
      :collapsible="true"
      :zero-width-trigger-style="{ bottom: 20 }"
      class="sider"
    >
      <SiderMenu :collapsed="collapsed" @toggle="toggleCollapse" />
    </a-sider>

    <a-layout>
      <!-- 顶部导航 -->
      <a-layout-header class="header" :style="{ paddingLeft: collapsed ? '24px' : '0' }">
        <TopNav :collapsed="collapsed" @toggle="toggleCollapse" />
      </a-layout-header>

      <!-- 标签页导航 -->
      <TagsView v-if="showTagsView" @close="handleCloseTag" />

      <!-- 主内容区 -->
      <a-layout-content class="content" :style="contentStyle">
        <router-view v-slot="{ Component }">
          <transition name="fade" mode="out-in">
            <component :is="Component" />
          </transition>
        </router-view>
      </a-layout-content>

      <!-- 页脚 -->
      <a-layout-footer class="footer">
        <Footer />
      </a-layout-footer>
    </a-layout>
  </a-layout>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useAppStore } from '@/stores/modules/app'
import { usePermissionStore } from '@/stores/modules/permission'
import SiderMenu from '@/components/layout/components/SiderMenu.vue'
import TopNav from '@/components/layout/components/TopNav.vue'
import TagsView from '@/components/layout/components/TagsView.vue'
import Footer from '@/components/layout/components/Footer.vue'

const route = useRoute()
const router = useRouter()
const appStore = useAppStore()
const permissionStore = usePermissionStore()

const collapsed = computed(() => appStore.sidebar.collapsed)
const siderWidth = computed(() => appStore.sidebarWidth)
const collapsedWidth = computed(() => 80)
const showTagsView = computed(() => appStore.tagsView)

const layoutClass = computed(() => [
  'default-layout',
  { 'sider-collapsed': collapsed.value },
  { 'mobile': appStore.device === 'mobile' },
])

const contentStyle = computed(() => ({
  marginTop: showTagsView.value ? '44px' : '0',
  minHeight: `calc(100vh - ${showTagsView.value ? '104px' : '60px'} - 48px)`,
}))

function toggleCollapse() {
  appStore.toggleCollapse()
}

function handleCloseTag(path: string) {
  // 关闭标签页逻辑
  permissionStore.removeTag(path)
}
</script>

<style scoped>
.default-layout {
  height: 100vh;
  overflow: hidden;
}

.sider {
  height: 100vh;
  position: relative;
  background: var(--zhurong-color-bg-container);
  border-right: 1px solid var(--zhurong-color-border);
  transition: width 0.2s, background 0.2s;
}

.header {
  height: 60px;
  padding: 0 24px;
  background: var(--zhurong-color-bg-container);
  border-bottom: 1px solid var(--zhurong-color-border);
  display: flex;
  align-items: center;
  justify-content: space-between;
  position: sticky;
  top: 0;
  z-index: var(--zhurong-z-index-sticky);
  box-shadow: var(--zhurong-box-shadow-sm);
  transition: all 0.2s;
}

.content {
  background: var(--zhurong-color-bg-layout);
  overflow-y: auto;
  transition: margin-top 0.2s;
}

.footer {
  height: 48px;
  padding: 0 24px;
  background: var(--zhurong-color-bg-container);
  border-top: 1px solid var(--zhurong-color-border);
  display: flex;
  align-items: center;
  justify-content: center;
  color: var(--zhurong-color-text-secondary);
  font-size: var(--zhurong-font-size-sm);
}

@media (max-width: 768px) {
  .header {
    padding: 0 16px;
  }

  .footer {
    padding: 0 16px;
  }
}

/* 动画 */
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.2s, transform 0.2s;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
  transform: translateY(8px);
}
</style>
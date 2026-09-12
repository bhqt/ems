<template>
  <div class="tags-view" ref="tagsContainer">
    <div class="tags-nav" ref="tagsNav" :style="{ transform: `translateX(${translateX}px)` }">
      <router-link
        v-for="tag in tags"
        :key="tag.path"
        :to="tag.path"
        class="tag"
        :class="{ active: isActive(tag), affix: tag.affix }"
        @click="handleTagClick(tag)"
        @contextmenu.prevent="handleContextMenu(tag, $event)"
      >
        <span class="tag-title">{{ getTagTitle(tag) }}</span>
        <a-icon
          v-if="!tag.affix"
          type="close"
          class="tag-close"
          @click.stop="handleClose(tag)"
        />
      </router-link>
    </div>

    <div class="tags-operations">
      <a-button type="text" @click="scrollLeft" :disabled="translateX >= 0" class="scroll-btn">
        <template #icon><LeftOutlined /></template>
      </a-button>
      <a-button type="text" @click="scrollRight" :disabled="!canScrollRight" class="scroll-btn">
        <template #icon><RightOutlined /></template>
      </a-button>
      <a-dropdown :menu="{ items: moreMenuItems }" placement="bottomRight">
        <a-button type="text" class="more-btn">
          <template #icon><MoreOutlined /></template>
        </a-button>
      </a-dropdown>
    </div>

    <!-- 右键菜单 -->
    <a-dropdown
      v-model:open="contextMenuVisible"
      :menu="{ items: contextMenuItems }"
      placement="bottomRight"
    >
      <div v-show="false" />
    </a-dropdown>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, onUnmounted, nextTick } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useAppStore } from '@/stores/modules/app'
import { usePermissionStore } from '@/stores/modules/permission'
import { useI18n } from 'vue-i18n'
import {
  LeftOutlined,
  RightOutlined,
  MoreOutlined,
  ReloadOutlined,
  CloseOutlined,
  CloseCircleOutlined,
  MenuUnfoldOutlined,
  MenuFoldOutlined,
} from '@ant-design/icons-vue'

const router = useRouter()
const route = useRoute()
const appStore = useAppStore()
const permissionStore = usePermissionStore()
const { t } = useI18n()

const tagsContainer = ref<HTMLDivElement>()
const tagsNav = ref<HTMLDivElement>()

const tags = computed(() => permissionStore.tags)
const activeTag = computed(() => route.path)

const translateX = ref(0)
const contextMenuVisible = ref(false)
const contextMenuTag = ref<any>(null)

const contextMenuItems = computed(() => [
  { label: t('common.refresh'), key: 'refresh', icon: ReloadOutlined },
  { type: 'divider' },
  { label: t('common.close'), key: 'close', icon: CloseOutlined, danger: !contextMenuTag.value?.affix, disabled: contextMenuTag.value?.affix },
  { label: t('common.closeOthers'), key: 'closeOthers', icon: CloseCircleOutlined },
  { label: t('common.closeAll'), key: 'closeAll', icon: CloseCircleOutlined },
  { type: 'divider' },
  { label: t('common.closeLeft'), key: 'closeLeft', icon: MenuUnfoldOutlined },
  { label: t('common.closeRight'), key: 'closeRight', icon: MenuFoldOutlined },
])

const moreMenuItems = computed(() => [
  { label: t('common.refresh'), key: 'refresh', icon: ReloadOutlined },
  { type: 'divider' },
  { label: t('common.closeOthers'), key: 'closeOthers', icon: CloseCircleOutlined },
  { label: t('common.closeAll'), key: 'closeAll', icon: CloseCircleOutlined },
])

const canScrollRight = computed(() => {
  if (!tagsNav.value || !tagsContainer.value) return false
  return Math.abs(translateX.value) + tagsContainer.value.clientWidth < tagsNav.value.scrollWidth
})

function getTagTitle(tag: any) {
  return t(tag.meta?.title as string || '') || tag.name || '未命名'
}

function isActive(tag: any) {
  return tag.path === activeTag.value
}

function handleTagClick(tag: any) {
  if (tag.path !== route.path) {
    router.push(tag.path)
  }
}

function handleClose(tag: any) {
  if (tag.affix) return
  permissionStore.removeTag(tag.path)
  updateTranslateX()
}

function handleContextMenu(tag: any, event: MouseEvent) {
  contextMenuTag.value = tag
  contextMenuVisible.value = true
}

function handleContextMenuClick({ key }: { key: string }) {
  if (!contextMenuTag.value) return

  const tag = contextMenuTag.value
  const index = tags.value.findIndex((t: any) => t.path === tag.path)

  switch (key) {
    case 'refresh':
      router.replace({ path: '/redirect' + tag.path })
      break
    case 'close':
      permissionStore.removeTag(tag.path)
      break
    case 'closeOthers':
      permissionStore.closeOtherTags(tag.path)
      break
    case 'closeAll':
      permissionStore.closeAllTags()
      break
    case 'closeLeft':
      permissionStore.closeTagsOnSide(index, 'left')
      break
    case 'closeRight':
      permissionStore.closeTagsOnSide(index, 'right')
      break
  }

  contextMenuVisible.value = false
  contextMenuTag.value = null
  updateTranslateX()
}

function scrollLeft() {
  translateX.value = Math.min(translateX.value + 200, 0)
}

function scrollRight() {
  if (!tagsNav.value || !tagsContainer.value) return
  const maxScroll = tagsNav.value.scrollWidth - tagsContainer.value.clientWidth
  translateX.value = Math.max(translateX.value - 200, -maxScroll)
}

function updateTranslateX() {
  nextTick(() => {
    if (!tagsNav.value || !tagsContainer.value) return

    const activeTagEl = tagsNav.value.querySelector('.tag.active')
    if (!activeTagEl) return

    const containerWidth = tagsContainer.value.clientWidth
    const tagLeft = (activeTagEl as HTMLElement).offsetLeft
    const tagWidth = (activeTagEl as HTMLElement).offsetWidth

    if (tagLeft + tagWidth > containerWidth + Math.abs(translateX.value)) {
      translateX.value = -(tagLeft + tagWidth - containerWidth)
    } else if (tagLeft < Math.abs(translateX.value)) {
      translateX.value = -tagLeft
    }

    // 限制范围
    const maxScroll = tagsNav.value.scrollWidth - containerWidth
    translateX.value = Math.max(Math.min(translateX.value, 0), -maxScroll)
  })
}

onMounted(() => {
  updateTranslateX()
  window.addEventListener('resize', updateTranslateX)
})

onUnmounted(() => {
  window.removeEventListener('resize', updateTranslateX)
})
</script>

<style scoped>
.tags-view {
  height: 44px;
  background: var(--zhurong-color-bg-container);
  border-bottom: 1px solid var(--zhurong-color-border);
  display: flex;
  align-items: center;
  position: sticky;
  top: 60px;
  z-index: var(--zhurong-z-index-sticky);
  box-shadow: var(--zhurong-box-shadow-sm);
}

.tags-nav {
  flex: 1;
  display: flex;
  align-items: center;
  overflow: hidden;
  padding: 0 12px;
  height: 100%;
  will-change: transform;
}

.tag {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 0 12px;
  height: 32px;
  border-radius: 6px;
  background: var(--zhurong-color-bg-layout);
  border: 1px solid var(--zhurong-color-border);
  color: var(--zhurong-color-text-secondary);
  font-size: var(--zhurong-font-size-sm);
  white-space: nowrap;
  cursor: pointer;
  transition: all 0.2s;
  flex-shrink: 0;
  text-decoration: none;
}

.tag:hover {
  background: var(--zhurong-color-bg-hover);
  border-color: var(--zhurong-color-border-hover);
  color: var(--zhurong-color-text);
}

.tag.active {
  background: var(--zhurong-color-primary-bg);
  border-color: var(--zhurong-color-primary);
  color: var(--zhurong-color-primary);
}

.tag.affix {
  background: var(--zhurong-color-primary-bg);
  border-color: var(--zhurong-color-primary);
}

.tag-close {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 16px;
  height: 16px;
  border-radius: 50%;
  color: var(--zhurong-color-text-tertiary);
  transition: all 0.2s;
  flex-shrink: 0;
}

.tag-close:hover {
  background: var(--zhurong-color-error);
  color: #fff;
}

.tags-operations {
  display: flex;
  align-items: center;
  gap: 4px;
  padding: 0 8px;
  height: 100%;
  flex-shrink: 0;
}

.scroll-btn,
.more-btn {
  width: 32px;
  height: 32px;
  padding: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  color: var(--zhurong-color-text-secondary);
  background: transparent;
  border: none;
}

.scroll-btn:hover:not(:disabled),
.more-btn:hover {
  background: var(--zhurong-color-bg-hover);
  color: var(--zhurong-color-text);
}

.scroll-btn:disabled {
  color: var(--zhurong-color-text-quaternary);
  cursor: not-allowed;
}
</style>
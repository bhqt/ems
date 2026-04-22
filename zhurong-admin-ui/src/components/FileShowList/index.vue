<!--// 仿照ImageShowList组件实现的文件列表展示组件 - 通过链接显示文件（兼容OSS ID和直接URL）-->
<template>
  <div class="file-list-container">
    <!-- 加载状态 -->
    <div v-if="loading" class="file-loading">
      <el-icon class="loading-icon"> </el-icon>
      <span>文件加载中...</span>
    </div>

    <!-- 加载完成 -->
    <template v-else>
      <!-- 正常文件 -->
      <div v-for="(file, index) in validFiles" :key="index" class="file-item">
        <el-link type="primary" :href="file.url" target="_blank" download>
          {{ file.originalName || file.url.split('/').pop() }}
        </el-link>
      </div>

      <!-- 加载失败的提示 -->
      <el-tooltip
        v-for="(file, index) in errorFiles"
        :key="'error-' + index"
        :content="`文件加载失败 (ID: ${file.ossId})`"
        placement="top"
      >
        <div class="file-error">
          <el-icon> </el-icon>
          <span>加载失败</span>
        </div>
      </el-tooltip>
    </template>
  </div>
</template>

<script>
import { loadFiles } from '@/utils/file-helper';

export default {
  name: 'FileShowList',
  props: {
    files: {
      type: [String, Array],
      default: ''
    },
    gap: {
      type: Number,
      default: 10
    }
  },
  data() {
    return {
      loading: false,
      loadedUrls: []
    };
  },
  computed: {
    // 有效文件（加载成功）
    validFiles() {
      // 确保 loadedUrls 始终是数组
      return (Array.isArray(this.loadedUrls) ? this.loadedUrls : []).filter(item => !item.error && item.url);
    },
    // 错误文件（加载失败）
    errorFiles() {
      // 确保 loadedUrls 始终是数组
      return (Array.isArray(this.loadedUrls) ? this.loadedUrls : []).filter(item => item.error);
    }
  },
  watch: {
    files: {
      immediate: true,
      async handler(newVal) {
        await this.handleLoadFiles(newVal);
      }
    }
  },
  methods: {
    async handleLoadFiles(files) {
      if (!files) {
        this.loadedUrls = [];
        return;
      }

      try {
        this.loading = true;
        const result = await loadFiles(files);
        this.loadedUrls = Array.isArray(result) ? result : [];
      } catch (error) {
        console.error('文件加载失败:', error);
        this.loadedUrls = [];
      } finally {
        this.loading = false;
      }
    }
  }
};
</script>

<style scoped>
.file-list-container {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
}

.file-item {
  margin-right: v-bind('gap + "px"');
  margin-bottom: v-bind('gap + "px"');
}

.file-loading {
  display: flex;
  align-items: center;
  color: var(--el-color-info);
}

.loading-icon {
  animation: rotating 2s linear infinite;
  margin-right: 5px;
}

.file-error {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background: var(--el-fill-color-light);
  color: var(--el-color-error);
  margin-right: v-bind('gap + "px"');
  margin-bottom: v-bind('gap + "px"');
  border-radius: 4px;
  padding: 8px;
}

.file-error span {
  font-size: 12px;
  margin-top: 2px;
}

@keyframes rotating {
  from { transform: rotate(0deg); }
  to { transform: rotate(360deg); }
}
</style>
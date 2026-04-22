
<!--// dongbei 2025-9-13  图片列表展示组件 -  通过图片预览组件显示图片（兼容OSS ID和直接URL）-->
<template>
  <div class="image-list-container">
    <!-- 加载状态 -->
    <div v-if="loading" class="image-loading">
      <el-icon class="loading-icon"> </el-icon>
      <span>图片加载中...</span>
    </div>

    <!-- 加载完成 -->
    <template v-else>
      <!-- 正常图片 -->
      <image-preview
        v-for="(image, index) in validImages"
        :key="index"
        :src="image.url"
        :width="size"
        :height="size"
        :preview-src-list="previewList"
      />

      <!-- 加载失败的提示 -->
      <el-tooltip
        v-for="(image, index) in errorImages"
        :key="'error-' + index"
        :content="`图片加载失败 (ID: ${image.ossId})`"
        placement="top"
      >
        <div class="image-error">
          <el-icon> </el-icon>
          <span>加载失败</span>
        </div>
      </el-tooltip>
    </template>
  </div>
</template>

<script>
import { loadImages } from '@/utils/image-helper';

export default {
  name: 'ImageList',
  props: {
    images: {
      type: [String, Array],
      default: ''
    },
    size: {
      type: Number,
      default: 50
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
    // 有效图片（加载成功）
    validImages() {
      return this.loadedUrls.filter(item => !item.error && item.url);
    },
    // 错误图片（加载失败）
    errorImages() {
      return this.loadedUrls.filter(item => item.error);
    },
    // 预览图列表
    previewList() {
      return this.validImages.map(item => item.url);
    }
  },
  watch: {
    images: {
      immediate: true,
      async handler(newVal) {
        await this.loadImages(newVal);
      }
    }
  },
  methods: {
    async loadImages(images) {
      if (!images) {
        this.loadedUrls = [];
        return;
      }

      try {
        this.loading = true;
        this.loadedUrls = await loadImages(images);
      } catch (error) {
        console.error('图片加载失败:', error);
        this.loadedUrls = [];
      } finally {
        this.loading = false;
      }
    }
  }
};
</script>

<style scoped>
.image-list-container {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
}

.image-item {
  margin-right: v-bind('gap + "px"');
  margin-bottom: v-bind('gap + "px"');
  cursor: pointer;
}

.image-loading {
  display: flex;
  align-items: center;
  color: var(--el-color-info);
}

.loading-icon {
  animation: rotating 2s linear infinite;
  margin-right: 5px;
}

.image-error {
  width: v-bind('size + "px"');
  height: v-bind('size + "px"');
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background: var(--el-fill-color-light);
  color: var(--el-color-error);
  margin-right: v-bind('gap + "px"');
  margin-bottom: v-bind('gap + "px"');
  border-radius: 4px;
}

.image-error span {
  font-size: 12px;
  margin-top: 2px;
}

@keyframes rotating {
  from { transform: rotate(0deg); }
  to { transform: rotate(360deg); }
}
</style>

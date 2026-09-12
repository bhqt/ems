<template>
  <ProCard
    :title="title"
    :extra="extra"
    :loading="loading"
    :bordered="bordered"
    :size="size"
  >
    <div class="pro-chart-container" ref="chartContainer" :style="{ height: height || '300px' }">
      <ECharts
        v-if="chartType === 'echarts'"
        :options="options"
        :theme="theme"
        :auto-resize="autoResize"
        :loading="loading"
        @init="onChartInit"
      />
      <G2Chart
        v-else-if="chartType === 'g2'"
        :options="options"
        :theme="theme"
        :auto-fit="autoFit"
        :loading="loading"
      />
      <div v-else class="chart-placeholder">
        <a-empty description="暂无图表配置" />
      </div>
    </div>
  </ProCard>
</template>

<script setup lang="ts>
import { ref, computed, watch, onMounted, onUnmounted, nextTick } from 'vue'
import { useI18n } from 'vue-i18n'
import ProCard from './ProCard.vue'
import ECharts from '@/components/charts/ECharts.vue'
import G2Chart from '@/components/charts/G2Chart.vue'

const props = defineProps<{
  /** 图表类型 */
  chartType?: 'echarts' | 'g2'
  /** 图表标题 */
  title?: string
  /** 额外操作 */
  extra?: any
  /** ECharts/G2 配置 */
  options?: any
  /** 主题 */
  theme?: 'light' | 'dark' | string
  /** 高度 */
  height?: string
  /** 自动调整大小 */
  autoResize?: boolean
  /** 自动适配容器 */
  autoFit?: boolean
  /** 加载状态 */
  loading?: boolean
  /** 是否有边框 */
  bordered?: boolean
  /** 尺寸 */
  size?: 'default' | 'small'
}>()

const emit = defineEmits<{
  init: [chart: any]
  click: [params: any]
}>()

const { t } = useI18n()

const chartContainer = ref<HTMLElement>()
const chartRef = ref()

const mergedTheme = computed(() => {
  if (props.theme) return props.theme
  return document.documentElement.classList.contains('dark') ? 'dark' : 'light'
})

function onChartInit(chart: any) {
  chartRef.value = chart
  emit('init', chart)
}

function handleResize() {
  if (chartRef.value && props.chartType === 'echarts') {
    chartRef.value.resize()
  }
}

onMounted(() => {
  window.addEventListener('resize', handleResize)
})

onUnmounted(() => {
  window.removeEventListener('resize', handleResize)
})
</script>

<style scoped>
.pro-chart-container {
  width: 100%;
  min-height: 200px;
}

.chart-placeholder {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>
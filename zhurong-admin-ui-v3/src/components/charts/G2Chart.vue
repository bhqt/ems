<template>
  <div ref="chartRef" class="g2-chart" :style="{ width: '100%', height: '100%' }">
    <a-spin v-if="loading" size="large" :tip="loadingTip" class="chart-loading" />
    <div v-else-if="error" class="chart-error">
      <a-empty description="图表加载失败" />
    </div>
  </div>
</template>

<script setup lang="ts>
import { ref, watch, onMounted, onUnmounted, nextTick, computed } from 'vue'
import { Chart } from '@antv/g2'
import type { ChartOptions } from '@antv/g2'

const props = defineProps<{
  /** G2 配置 */
  options?: ChartOptions | (() => ChartOptions)
  /** 主题 */
  theme?: 'light' | 'dark' | string
  /** 自动适配容器 */
  autoFit?: boolean
  /** 加载状态 */
  loading?: boolean
  /** 加载提示 */
  loadingTip?: string
}>()

const emit = defineEmits<{
  init: [chart: Chart]
  click: [event: any]
  dblclick: [event: any]
  mousedown: [event: any]
  mouseup: [event: any]
  mouseover: [event: any]
  mouseout: [event: any]
  plotEnter: [event: any]
  plotLeave: [event: any]
  plotClick: [event: any]
  plotDblclick: [event: any]
  tooltipChange: [event: any]
  tooltipShow: [event: any]
  tooltipHide: [event: any]
}>()

const chartRef = ref<HTMLDivElement>()
let chartInstance: Chart | null = null

const mergedTheme = computed(() => {
  if (props.theme === 'dark') return 'dark'
  if (props.theme === 'light') return 'light'
  return document.documentElement.classList.contains('dark') ? 'dark' : 'light'
}

function initChart() {
  if (!chartRef.value || chartInstance) return

  try {
    const options = typeof props.options === 'function' ? props.options() : props.options
    if (!options) return

    chartInstance = new Chart({
      container: chartRef.value,
      autoFit: props.autoFit !== false,
      theme: mergedTheme.value,
      ...options,
    })

    chartInstance.on('click', (event: any) => emit('click', event))
    chartInstance.on('dblclick', (event: any) => emit('dblclick', event))
    chartInstance.on('mousedown', (event: any) => emit('mousedown', event))
    chartInstance.on('mouseup', (event: any) => emit('mouseup', event))
    chartInstance.on('mouseover', (event: any) => emit('mouseover', event))
    chartInstance.on('mouseout', (event: any) => emit('mouseout', event))
    chartInstance.on('plot:enter', (event: any) => emit('plotEnter', event))
    chartInstance.on('plot:leave', (event: any) => emit('plotLeave', event))
    chartInstance.on('plot:click', (event: any) => emit('plotClick', event))
    chartInstance.on('plot:dblclick', (event: any) => emit('plotDblclick', event))
    chartInstance.on('tooltip:change', (event: any) => emit('tooltipChange', event))
    chartInstance.on('tooltip:show', (event: any) => emit('tooltipShow', event))
    chartInstance.on('tooltip:hide', (event: any) => emit('tooltipHide', event))

    chartInstance.render()
    emit('init', chartInstance)
  } catch (error) {
    console.error('G2 Chart init error:', error)
  }
}

function updateChart() {
  if (!chartInstance) return

  try {
    const options = typeof props.options === 'function' ? props.options() : props.options
    if (!options) return

    chartInstance.update(options)
  } catch (error) {
    console.error('G2 Chart update error:', error)
  }
}

function resize() {
  chartInstance?.changeSize()
}

function changeTheme(theme: 'light' | 'dark') {
  if (chartInstance) {
    chartInstance.update({ theme })
  }
}

function destroy() {
  if (chartInstance) {
    chartInstance.destroy()
    chartInstance = null
  }
}

watch(
  () => props.options,
  updateChart,
  { deep: true }
)

watch(mergedTheme, (theme) => {
  changeTheme(theme)
})

onMounted(() => {
  nextTick(() => {
    initChart()
  })
})

onUnmounted(() => {
  destroy()
})

defineExpose({
  getInstance: () => chartInstance,
  update: updateChart,
  resize,
  changeTheme,
  destroy,
})
</script>

<style scoped>
.g2-chart {
  width: 100%;
  height: 100%;
  position: relative;
}

.chart-loading {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  background: var(--zhurong-color-bg-container);
  z-index: 10;
}

.chart-error {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  background: var(--zhurong-color-bg-container);
  z-index: 10;
}
</style>
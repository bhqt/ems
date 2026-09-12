<template>
  <div ref="chartRef" class="echarts-chart" :style="{ width: '100%', height: '100%' }">
    <a-spin v-if="loading" size="large" :tip="loadingTip" class="chart-loading" />
    <div v-else-if="error" class="chart-error">
      <a-empty description="图表加载失败" />
    </div>
  </div>
</template>

<script setup lang="ts>
import { ref, watch, onMounted, onUnmounted, nextTick } from 'vue'
import * as echarts from 'echarts/core'
import {
  CanvasRenderer,
} from 'echarts/renderers'
import {
  LineChart,
  BarChart,
  PieChart,
  ScatterChart,
  RadarChart,
  FunnelChart,
  GaugeChart,
  GraphChart,
  HeatmapChart,
  LinesChart,
  MapChart,
  ParallelChart,
  SankeyChart,
  SunburstChart,
  TreeChart,
  TreemapChart,
  BoxplotChart,
  CandlestickChart,
  EffectScatterChart,
  ThemeRiverChart,
  CustomChart,
  PictorialBarChart,
} from 'echarts/charts'
import {
  TitleComponent,
  TooltipComponent,
  LegendComponent,
  GridComponent,
  PolarComponent,
  GeoComponent,
  SingleAxisComponent,
  ParallelAxisComponent,
  CalendarComponent,
  GraphicComponent,
  ToolboxComponent,
  DataZoomComponent,
  VisualMapComponent,
  AriaComponent,
  BrushComponent,
  MarkPointComponent,
  MarkLineComponent,
  MarkAreaComponent,
  TimelineComponent,
  DatasetComponent,
} from 'echarts/components'

// 注册所有组件
echarts.use([
  CanvasRenderer,
  LineChart,
  BarChart,
  PieChart,
  ScatterChart,
  RadarChart,
  FunnelChart,
  GaugeChart,
  GraphChart,
  HeatmapChart,
  LinesChart,
  MapChart,
  ParallelChart,
  SankeyChart,
  SunburstChart,
  TreeChart,
  TreemapChart,
  BoxplotChart,
  CandlestickChart,
  EffectScatterChart,
  ThemeRiverChart,
  CustomChart,
  PictorialBarChart,
  TitleComponent,
  TooltipComponent,
  LegendComponent,
  GridComponent,
  PolarComponent,
  GeoComponent,
  SingleAxisComponent,
  ParallelAxisComponent,
  CalendarComponent,
  GraphicComponent,
  ToolboxComponent,
  DataZoomComponent,
  VisualMapComponent,
  AriaComponent,
  BrushComponent,
  MarkPointComponent,
  MarkLineComponent,
  MarkAreaComponent,
  TimelineComponent,
  DatasetComponent,
])

const props = defineProps<{
  /** 图表配置 */
  options?: any
  /** 主题 */
  theme?: 'light' | 'dark' | string | object
  /** 自动调整大小 */
  autoResize?: boolean
  /** 加载状态 */
  loading?: boolean
  /** 加载提示 */
  loadingTip?: string
  /** 不合并配置 */
  notMerge?: boolean
  /** 懒加载更新 */
  lazyUpdate?: boolean
  /** 渲染器 */
  renderer?: 'canvas' | 'svg'
  /** 区域设置 */
  locale?: 'ZH' | 'EN' | object
}()

const emit = defineEmits<{
  init: [chart: echarts.ECharts]
  click: [params: any]
  dblclick: [params: any]
  mousedown: [params: any]
  mouseup: [params: any]
  mouseover: [params: any]
  mouseout: [params: any]
  globalout: [params: any]
  contextmenu: [params: any]
  legendselectchanged: [params: any]
  legendselected: [params: any]
  legendunselected: [params: any]
  legendscroll: [params: any]
  datazoom: [params: any]
  datarange: [params: any]
  timelinechanged: [params: any]
  timelineplaychanged: [params: any]
  restore: [params: any]
  dataviewchanged: [params: any]
  magictypechanged: [params: any]
  geoselectchanged: [params: any]
  geoselected: [params: any]
  geounselected: [params: any]
  pixelselect: [params: any]
  brush: [params: any]
  brushselected: [params: any]
  axisareaselect: [params: any]
  focusnodeadjacency: [params: any]
  unfocusnodeadjacency: [params: any]
  renderComplete: [params: any]
  finished: [params: any]
}>()

const chartRef = ref<HTMLDivElement>()
let chartInstance: echarts.ECharts | null = null
let resizeObserver: ResizeObserver | null = null

const mergedTheme = computed(() => {
  if (props.theme === 'dark' || (typeof props.theme === 'string' && props.theme.includes('dark'))) {
    return 'dark'
  }
  if (props.theme === 'light' || typeof props.theme === 'object') {
    return props.theme
  }
  return document.documentElement.classList.contains('dark') ? 'dark' : 'light'
})

function initChart() {
  if (!chartRef.value || chartInstance) return

  try {
    chartInstance = echarts.init(chartRef.value, mergedTheme.value, {
      renderer: props.renderer || 'canvas',
      locale: props.locale,
    })

    chartInstance.on('click', handleEvent('click'))
    chartInstance.on('dblclick', handleEvent('dblclick'))
    chartInstance.on('mousedown', handleEvent('mousedown'))
    chartInstance.on('mouseup', handleEvent('mouseup'))
    chartInstance.on('mouseover', handleEvent('mouseover'))
    chartInstance.on('mouseout', handleEvent('mouseout'))
    chartInstance.on('globalout', handleEvent('globalout'))
    chartInstance.on('contextmenu', handleEvent('contextmenu'))

    emit('init', chartInstance)

    setOptions(props.options)
  } catch (error) {
    console.error('ECharts init error:', error)
  }
}

function setOptions(options: any) {
  if (!chartInstance || !options) return

  try {
    chartInstance.setOption(options, {
      notMerge: props.notMerge ?? false,
      lazyUpdate: props.lazyUpdate ?? false,
    })
  } catch (error) {
    console.error('ECharts setOption error:', error)
  }
}

function handleEvent(eventName: string) {
  return (params: any) => {
    emit(eventName, params)
  }
}

function resize() {
  chartInstance?.resize()
}

function dispose() {
  if (resizeObserver) {
    resizeObserver.disconnect()
    resizeObserver = null
  }
  if (chartInstance) {
    chartInstance.dispose()
    chartInstance = null
  }
}

function updateTheme() {
  if (chartInstance) {
    const newTheme = mergedTheme.value
    const currentOptions = chartInstance.getOption()
    dispose()
    chartInstance = echarts.init(chartRef.value!, newTheme, {
      renderer: props.renderer || 'canvas',
      locale: props.locale,
    })
    setOptions(currentOptions)
  }
}

watch(
  () => props.options,
  (newOptions) => {
    setOptions(newOptions)
  },
  { deep: true }
)

watch(
  () => props.loading,
  (loading) => {
    if (chartInstance) {
      if (loading) {
        chartInstance.showLoading({ text: props.loadingTip })
      } else {
        chartInstance.hideLoading()
      }
    }
  }
)

watch(mergedTheme, updateTheme)

onMounted(() => {
  nextTick(() => {
    initChart()

    if (props.autoResize !== false) {
      resizeObserver = new ResizeObserver(() => {
        resize()
      })
      resizeObserver.observe(chartRef.value!)
    }
  })
})

onUnmounted(() => {
  dispose()
})

defineExpose({
  getInstance: () => chartInstance,
  setOptions,
  resize,
  dispose,
  getOption: () => chartInstance?.getOption(),
  getWidth: () => chartInstance?.getWidth(),
  getHeight: () => chartInstance?.getHeight(),
  getDom: () => chartInstance?.getDom(),
  on: chartInstance?.on.bind(chartInstance),
  off: chartInstance?.off.bind(chartInstance),
})
</script>

<style scoped>
.echarts-chart {
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
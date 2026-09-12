<template>
  <ProCard :title="title" :extra="extra" :loading="loading" :bordered="bordered">
    <div class="energy-flow">
      <!-- 桑基图 -->
      <ProChart
        :chart-type="'echarts'"
        :options="sankeyOptions"
        :height="height || '400px'"
        :loading="loading"
      />

      <!-- 关键指标 -->
      <a-row :gutter="16" class="flow-metrics">
        <a-col :span="6" v-for="metric in metrics" :key="metric.key">
          <ProStatistic
            :title="metric.title"
            :value="metric.value"
            :unit="metric.unit"
            :trend="metric.trend"
            :trend-label="metric.trendLabel"
            :precision="metric.precision"
          />
        </a-col>
      </a-row>
    </div>
  </ProCard>
</template>

<script setup lang="ts>
import { ref, computed } from 'vue'
import ProCard from '../pro/ProCard.vue'
import ProChart from '../pro/ProChart.vue'
import ProStatistic from '../pro/ProStatistic.vue'

const props = defineProps<{
  title?: string
  extra?: any
  loading?: boolean
  bordered?: boolean
  height?: string
  flowData?: any
}>()

const emit = defineEmits<{}>

const title = computed(() => props.title || '能量流向')

const height = computed(() => props.height || '400px')

const sankeyOptions = computed(() => ({
  tooltip: {
    trigger: 'item',
    formatter: '{b}: {c}',
  },
  series: [
    {
      type: 'sankey',
      layout: 'none',
      emphasis: {
        focus: 'adjacency',
      },
      data: props.flowData?.nodes || getMockNodes(),
      links: props.flowData?.links || getMockLinks(),
      lineStyle: {
        color: 'source',
        curveness: 0.5,
      },
    },
  ],
}))

const metrics = computed(() => [
  { key: 'totalInput', title: '总输入', value: 125680, unit: 'kWh', trend: 2.3, trendLabel: '同比' },
  { key: 'totalOutput', title: '总输出', value: 118450, unit: 'kWh', trend: 1.8, trendLabel: '同比' },
  { key: 'loss', title: '损耗', value: 7230, unit: 'kWh', trend: -5.2, trendLabel: '同比' },
  { key: 'efficiency', title: '传输效率', value: 94.2, unit: '%', trend: 0.5, trendLabel: '同比' },
])

function getMockNodes() {
  return [
    { name: '外部电网', value: 100000, itemStyle: { color: '#1890ff' } },
    { name: '光伏发电', value: 15000, itemStyle: { color: '#faad14' } },
    { name: '储能放电', value: 10680, itemStyle: { color: '#52c41a' } },
    { name: '变压器站', value: 80000, itemStyle: { color: '#722ed1' } },
    { name: '配电柜A', value: 40000, itemStyle: { color: '#13c2c2' } },
    { name: '配电柜B', value: 40000, itemStyle: { color: '#13c2c2' } },
    { name: '放射科', value: 25000, itemStyle: { color: '#eb2f96' } },
    { key: '检验科', value: 20000, itemStyle: { color: '#f5222d' } },
    { name: '超声科', value: 15000, itemStyle: { color: '#fa8c16' } },
    { name: '其他科室', value: 20000, itemStyle: { color: '#a0d911' } },
    { name: '损耗', value: 7230, itemStyle: { color: '#bfbfbf' } },
  ]
}

function getMockLinks() {
  return [
    { source: '外部电网', target: '变压器站', value: 80000 },
    { source: '光伏发电', target: '变压器站', value: 15000 },
    { source: '储能放电', target: '变压器站', value: 10680 },
    { source: '变压器站', target: '配电柜A', value: 40000 },
    { source: '变压器站', target: '配电柜B', value: 40000 },
    { source: '配电柜A', target: '放射科', value: 25000 },
    { source: '配电柜A', target: '检验科', value: 15000 },
    { source: '配电柜B', target: '超声科', value: 15000 },
    { source: '配电柜B', target: '其他科室', value: 25000 },
    { source: '配电柜A', target: '损耗', value: 2230 },
    { source: '配电柜B', target: '损耗', value: 5000 },
  ]
}
</script>

<style scoped>
.energy-flow {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.flow-metrics {
  margin-top: 8px;
}
</style>
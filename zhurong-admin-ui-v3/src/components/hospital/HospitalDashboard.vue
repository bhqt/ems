<template>
  <ProCard :title="title" :extra="extra" :loading="loading" :bordered="bordered">
    <div class="hospital-dashboard">
      <!-- 顶部统计卡片 -->
      <a-row :gutter="16" class="dashboard-stats">
        <a-col :span="6" v-for="stat in stats" :key="stat.key">
          <ProStatistic
            :title="stat.title"
            :value="stat.value"
            :unit="stat.unit"
            :trend="stat.trend"
            :trend-label="stat.trendLabel"
            :prefix="stat.prefix"
            :precision="stat.precision"
          />
        </a-col>
      </a-row>

      <!-- 图表区域 -->
      <a-row :gutter="16" class="dashboard-charts">
        <a-col :span="12" v-for="chart in charts" :key="chart.key">
          <ProChart
            :title="chart.title"
            :chart-type="chart.chartType"
            :options="chart.options"
            :height="chart.height"
            :loading="loading"
          />
        </a-col>
      </a-row>

      <!-- 设备状态列表 -->
      <ProTable
        v-if="showDeviceList"
        :columns="deviceColumns"
        :request="deviceRequest"
        :search-form="deviceSearchForm"
        :toolbar="['refresh']"
        :row-key="'id'"
        :pagination="{ pageSize: 10 }"
      />
    </div>
  </ProCard>
</template>

<script setup lang="ts>
import { ref, computed } from 'vue'
import ProCard from '../pro/ProCard.vue'
import ProStatistic from '../pro/ProStatistic.vue'
import ProChart from '../pro/ProChart.vue'
import ProTable from '../pro/ProTable.vue'

const props = defineProps<{
  /** 标题 */
  title?: string
  /** 额外操作 */
  extra?: any
  /** 加载状态 */
  loading?: boolean
  /** 是否有边框 */
  bordered?: boolean
  /** 显示设备列表 */
  showDeviceList?: boolean
  /** 统计数据 */
  stats?: any[]
  /** 图表配置 */
  charts?: any[]
}>()

const emit = defineEmits<{}>

const title = computed(() => props.title || '医院能源概览')

const stats = computed(() => props.stats || [
  { key: 'totalConsumption', title: '总能耗', value: 125680, unit: 'kWh', trend: 2.3, trendLabel: '同比增长' },
  { key: 'totalCost', title: '总费用', value: 89450, unit: '元', trend: -1.2, trendLabel: '同比下降' },
  { key: 'carbonEmission', title: '碳排放', value: 75.6, unit: '吨', trend: 0.8, trendLabel: '同比增长' },
  { key: 'savingRate', title: '节能率', value: 12.5, unit: '%', trend: 3.1, trendLabel: '同比提升' },
])

const charts = computed(() => props.charts || [
  {
    key: 'energyTrend',
    title: '能耗趋势',
    chartType: 'echarts' as const,
    height: '300px',
    options: getEnergyTrendOptions(),
  },
  {
    key: 'energyDistribution',
    title: '能耗分布',
    chartType: 'echarts' as const,
    height: '300px',
    options: getEnergyDistributionOptions(),
  },
])

const deviceColumns = [
  { title: '设备名称', dataIndex: 'name', width: 180 },
  { title: '设备类型', dataIndex: 'type', width: 120 },
  { title: '科室', dataIndex: 'department', width: 120 },
  { title: '状态', dataIndex: 'status', width: 100, slots: { customRender: 'status' } },
  { title: '实时功率', dataIndex: 'power', width: 120 },
  { title: '今日电量', dataIndex: 'todayEnergy', width: 120 },
  { title: '运行时长', dataIndex: 'runtime', width: 120 },
]

const deviceSearchForm = [
  { field: 'name', label: '设备名称', type: 'input', placeholder: '请输入设备名称' },
  { field: 'type', label: '设备类型', type: 'select', options: [] },
  { field: 'department', label: '所属科室', type: 'select', options: [] },
  { field: 'status', label: '运行状态', type: 'select', options: [] },
]

async function deviceRequest(params: any) {
  // 模拟请求
  return {
    data: [],
    total: 0,
  }
}

function getEnergyTrendOptions() {
  return {
    tooltip: { trigger: 'axis' },
    legend: { data: ['电力', '水', '燃气'] },
    grid: { left: '3%', right: '4%', bottom: '3%', containLabel: true },
    xAxis: { type: 'category', boundaryGap: false, data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'] },
    yAxis: { type: 'value' },
    series: [
      { name: '电力', type: 'line', data: [120, 132, 101, 134, 90, 230, 210] },
      { name: '水', type: 'line', data: [220, 182, 191, 234, 290, 330, 310] },
      { name: '燃气', type: 'line', data: [150, 232, 201, 154, 190, 330, 410] },
    ],
  }
}

function getEnergyDistributionOptions() {
  return {
    tooltip: { trigger: 'item' },
    legend: { orient: 'vertical', left: 'left' },
    series: [
      {
        name: '能源分布',
        type: 'pie',
        radius: '50%',
        data: [
          { value: 1048, name: '电力' },
          { value: 735, name: '水' },
          { value: 580, name: '燃气' },
          { value: 484, name: '蒸汽' },
          { value: 300, name: '其他' },
        ],
        emphasis: {
          itemStyle: { shadowBlur: 10, shadowOffsetX: 0, shadowColor: 'rgba(0, 0, 0, 0.5)' },
        },
      },
    ],
  }
}
</script>

<style scoped>
.hospital-dashboard {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.dashboard-stats {
  margin-bottom: 8px;
}

.dashboard-charts {
  margin-bottom: 16px;
}
</style>
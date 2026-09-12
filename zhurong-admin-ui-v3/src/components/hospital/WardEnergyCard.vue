<template>
  <BaseCard
    :title="ward.name"
    :extra="extra"
    :bordered="bordered"
    :loading="loading"
    :hoverable="true"
    :size="size"
  >
    <div class="ward-energy-card">
      <!-- 科室信息 -->
      <div class="ward-header">
        <div class="ward-basic">
          <span class="ward-code">{{ ward.code }}</span>
          <span class="ward-type">{{ ward.type }}</span>
          <span class="ward-area">{{ ward.area }}㎡</span>
        </div>
        <div class="ward-status" :class="ward.status">
          <span class="status-dot" :class="ward.status" />
          {{ getStatusText(ward.status) }}
        </div>
      </div>

      <!-- 能耗指标 -->
      <div class="ward-metrics">
        <div class="metric-group">
          <ProStatistic
            :title="t('hospital.electricity')"
            :value="ward.electricity"
            :unit="t('common.kWh')"
            :trend="ward.electricityTrend"
            :trend-label="t('common.monthOnMonth')"
            :precision="1"
          />
          <ProStatistic
            :title="t('hospital.water')"
            :value="ward.water"
            :unit="t('common.ton')"
            :trend="ward.waterTrend"
            :trend-label="t('common.monthOnMonth')"
            :precision="1"
          />
        </div>
        <div class="metric-group">
          <ProStatistic
            :title="t('hospital.gas')"
            :value="ward.gas"
            :unit="t('common.m3')"
            :trend="ward.gasTrend"
            :trend-label="t('common.monthOnMonth')"
            :precision="1"
          />
          <ProStatistic
            :title="t('hospital.cost')"
            :value="ward.cost"
            :unit="t('common.yuan')"
            :trend="ward.costTrend"
            :trend-label="t('common.monthOnMonth')"
            :precision="0"
          />
        </div>
      </div>

      <!-- 趋势迷你图 -->
      <div class="ward-trends">
        <ProChart
          :chart-type="'echarts'"
          :options="getTrendOptions('electricity')"
          :height="'120px'"
        />
        <ProChart
          :chart-type="'echarts'"
          :options="getTrendOptions('water')"
          :height="'120px'"
        />
      </div>

      <!-- 设备数量与效率 -->
      <div class="ward-summary">
        <div class="summary-item">
          <span class="summary-label">{{ t('hospital.deviceCount') }}</span>
          <span class="summary-value">{{ ward.deviceCount }}</span>
        </div>
        <div class="summary-item">
          <span class="summary-label">{{ t('hospital.avgEfficiency') }}</span>
          <span class="summary-value">{{ ward.avgEfficiency }}%</span>
        </div>
        <div class="summary-item">
          <span class="summary-label">{{ t('hospital.carbonEmission') }}</span>
          <span class="summary-value">{{ ward.carbonEmission }}t</span>
        </div>
      </div>
    </div>
  </BaseCard>
</template>

<script setup lang="ts>
import { computed } from 'vue'
import { useI18n } from 'vue-i18n'
import BaseCard from '@/components/common/BaseCard.vue'
import ProStatistic from '../pro/ProStatistic.vue'
import ProChart from '../pro/ProChart.vue'

const props = defineProps<{
  /** 病区数据 */
  ward: {
    name: string
    code: string
    type: string
    area: number
    status: 'normal' | 'warning' | 'critical' | 'offline'
    electricity: number
    water: number
    gas: number
    cost: number
    electricityTrend: number
    waterTrend: number
    gasTrend: number
    costTrend: number
    deviceCount: number
    avgEfficiency: number
    carbonEmission: number
    trendData?: {
      electricity: number[]
      water: number[]
      gas: number[]
    }
  }
  /** 额外操作 */
  extra?: any
  /** 是否有边框 */
  bordered?: boolean
  /** 加载状态 */
  loading?: boolean
  /** 尺寸 */
  size?: 'default' | 'small'
}>()

const emit = defineEmits<{}>

const { t } = useI18n()

function getStatusText(status: string) {
  const map: Record<string, string> = {
    normal: '正常',
    warning: '预警',
    critical: '严重',
    offline: '离线',
  }
  return map[status] || status
}

function getTrendOptions(type: 'electricity' | 'water' | 'gas') {
  const data = props.ward.trendData?.[type] || getMockTrendData()
  return {
    grid: { top: 10, right: 10, bottom: 10, left: 10 },
    xAxis: { show: false },
    yAxis: { show: false },
    series: [
      {
        type: 'line',
        data,
        smooth: true,
        showSymbol: false,
        lineStyle: { width: 2, color: getTrendColor(type) },
        areaStyle: {
          color: {
            type: 'linear',
            x: 0, y: 0, x2: 0, y2: 1,
            colorStops: [
              { offset: 0, color: `${getTrendColor(type)}80` },
              { offset: 1, color: `${getTrendColor(type)}00` },
            ],
          },
        },
      },
    ],
  })
}

function getTrendColor(type: string) {
  const colors: Record<string, string> = {
    electricity: '#1890ff',
    water: '#13c2c2',
    gas: '#faad14',
  }
  return colors[type] || '#1890ff'
}

function getMockTrendData() {
  return Array.from({ length: 30 }, () => Math.random() * 100 + 50)
}
</script>

<style scoped>
.ward-energy-card {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.ward-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding-bottom: 8px;
  border-bottom: 1px solid var(--zhurong-color-border-secondary);
}

.ward-basic {
  display: flex;
  align-items: center;
  gap: 12px;
}

.ward-code {
  font-family: var(--zhurong-font-family-mono);
  font-size: 13px;
  color: var(--zhurong-color-text-secondary);
}

.ward-type {
  padding: 2px 8px;
  background: var(--zhurong-color-primary-bg);
  color: var(--zhurong-color-primary);
  border-radius: 4px;
  font-size: 12px;
}

.ward-area {
  font-size: 12px;
  color: var(--zhurong-color-text-tertiary);
}

.ward-status {
  display: flex;
  align-items: center;
  gap: 6px;
  padding: 4px 10px;
  border-radius: 4px;
  font-size: 12px;
  font-weight: 500;
}

.ward-status.normal {
  background: var(--zhurong-color-success-bg);
  color: var(--zhurong-color-success);
}

.ward-status.warning {
  background: var(--zhurong-color-warning-bg);
  color: var(--zhurong-color-warning);
}

.ward-status.critical {
  background: var(--zhurong-color-error-bg);
  color: var(--zhurong-color-error);
}

.ward-status.offline {
  background: var(--zhurong-color-gray-100);
  color: var(--zhurong-color-text-tertiary);
}

.status-dot {
  width: 6px;
  height: 6px;
  border-radius: 50%;
  background: currentColor;
}

.ward-metrics {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 12px;
}

.metric-group {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.ward-trends {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 12px;
  margin-top: 8px;
}

.ward-summary {
  display: flex;
  justify-content: space-around;
  padding-top: 12px;
  border-top: 1px solid var(--zhurong-color-border-secondary);
}

.summary-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 4px;
}

.summary-label {
  font-size: 12px;
  color: var(--zhurong-color-text-tertiary);
}

.summary-value {
  font-size: 16px;
  font-weight: 600;
  color: var(--zhurong-color-text-heading);
}
</style>
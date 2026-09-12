<template>
  <ProCard :title="title" :extra="extra" :loading="loading" :bordered="bordered">
    <div class="device-wall">
      <!-- 筛选器 -->
      <div class="device-wall-filters">
        <a-space :size="16" :wrap="true">
          <a-select
            v-model:value="filters.type"
            :options="typeOptions"
            placeholder="设备类型"
            style="width: 160px"
            allow-clear
          />
          <a-select
            v-model:value="filters.department"
            :options="departmentOptions"
            placeholder="所属科室"
            style="width: 160px"
            allow-clear
          />
          <a-select
            v-model:value="filters.status"
            :options="statusOptions"
            placeholder="运行状态"
            style="width: 140px"
            allow-clear
          />
          <a-input
            v-model:value="filters.keyword"
            placeholder="搜索设备名称/编码"
            allow-clear
            style="width: 200px"
          />
        </a-space>
      </div>

      <!-- 设备墙网格 -->
      <div class="device-wall-grid">
        <a-card
          v-for="device in filteredDevices"
          :key="device.id"
          :title="device.name"
          :extra="deviceExtra(device)"
          :bordered="true"
          :hoverable="true"
          class="device-card"
        >
          <div class="device-info">
            <div class="device-status" :class="device.status">
              <span class="status-dot" :class="device.status" />
              <span>{{ getStatusText(device.status) }}</span>
            </div>
            <div class="device-meta">
              <span><strong>编码:</strong> {{ device.code }}</div>
              <div><strong>科室:</strong> {{ device.department }}</div>
              <div><strong>类型:</strong> {{ device.type }}</div>
              <div><strong>位置:</strong> {{ device.location }}</div>
            </div>
            <div class="device-metrics">
              <div class="metric">
                <span class="metric-label">功率</span>
                <span class="metric-value">{{ device.power }} kW</span>
              </div>
              <div class="metric">
                <span class="metric-label">今日电量</span>
                <span class="metric-value">{{ device.todayEnergy }} kWh</span>
              </div>
              <div class="metric">
                <span class="metric-label">运行时长</span>
                <span class="metric-value">{{ device.runtime }} h</span>
              </div>
              <div class="metric">
                <span class="metric-label">效率</span>
                <span class="metric-value">{{ device.efficiency }}%</span>
              </div>
            </div>
          </div>
        </a-card>
      </div>

      <!-- 分页 -->
      <a-pagination
        v-model:current="pagination.current"
        v-model:page-size="pagination.pageSize"
        :total="pagination.total"
        :show-size-changer="true"
        :show-quick-jumper="true"
        @change="handlePageChange"
        @show-size-change="handlePageSizeChange"
      />
    </div>
  </ProCard>
</template>

<script setup lang="ts>
import { ref, computed } from 'vue'
import { useI18n } from 'vue-i18n'
import ProCard from '../pro/ProCard.vue'

const props = defineProps<{
  title?: string
  extra?: any
  loading?: boolean
  bordered?: boolean
  devices?: any[]
}>()

const emit = defineEmits<{}>

const { t } = useI18n()

const title = computed(() => props.title || '设备墙')

const filters = ref({
  type: '',
  department: '',
  status: '',
  keyword: '',
})

const typeOptions = [
  { label: 'CT', value: 'CT' },
  { label: 'MRI', value: 'MRI' },
  { label: 'DR', value: 'DR' },
  { label: '超声', value: 'ULTRASOUND' },
  { label: '检验流水线', value: 'LAB_LINE' },
  { label: 'DSA', value: 'DSA' },
]

const departmentOptions = [
  { label: '放射科', value: 'radiology' },
  { label: '检验科', value: 'laboratory' },
  { label: '超声科', value: 'ultrasound' },
  { label: '介入科', value: 'interventional' },
]

const statusOptions = [
  { label: '运行中', value: 'running' },
  { label: '待机', value: 'standby' },
  { label: '离线', value: 'offline' },
  { label: '故障', value: 'fault' },
  { label: '维护', value: 'maintenance' },
]

const pagination = ref({
  current: 1,
  pageSize: 20,
  total: 0,
})

const filteredDevices = computed(() => {
  let devices = props.devices || getMockDevices()
  
  if (filters.value.type) {
    devices = devices.filter(d => d.type === filters.value.type)
  }
  if (filters.value.department) {
    devices = devices.filter(d => d.department === filters.value.department)
  }
  if (filters.value.status) {
    devices = devices.filter(d => d.status === filters.value.status)
  }
  if (filters.value.keyword) {
    const kw = filters.value.keyword.toLowerCase()
    devices = devices.filter(d => 
      d.name.toLowerCase().includes(kw) || d.code.toLowerCase().includes(kw)
    )
  }
  
  pagination.value.total = devices.length
  const start = (pagination.value.current - 1) * pagination.value.pageSize
  return devices.slice(start, start + pagination.value.pageSize)
})

function getMockDevices() {
  return Array.from({ length: 50 }, (_, i) => ({
    id: i + 1,
    name: `设备-${String(i + 1).padStart(3, '0')}`,
    code: `DEV-${String(i + 1).padStart(4, '0')}`,
    type: ['CT', 'MRI', 'DR', 'ULTRASOUND', 'LAB_LINE', 'DSA'][i % 6],
    department: ['放射科', '检验科', '超声科', '介入科'][i % 4],
    location: `${['A', 'B', 'C', 'D'][i % 4]}座${Math.floor(i / 4) + 1}楼`,
    status: ['running', 'standby', 'offline', 'fault', 'maintenance'][i % 5] as any,
    power: (Math.random() * 50 + 10).toFixed(1),
    todayEnergy: (Math.random() * 200 + 50).toFixed(1),
    runtime: (Math.random() * 24).toFixed(1),
    efficiency: (Math.random() * 20 + 80).toFixed(1),
  }))
}

function deviceExtra(device: any) {
  return (
    <a-space>
      <a-button type="text" size="small" icon={<EyeOutlined />}>详情</a-button>
      <a-button type="text" size="small" icon={<HistoryOutlined />}>历史</a-button>
    </a-space>
  )
}

function getStatusText(status: string) {
  const map: Record<string, string> = {
    running: '运行中',
    standby: '待机',
    offline: '离线',
    fault: '故障',
    maintenance: '维护',
  }
  return map[status] || status
}

function handlePageChange(page: number) {
  pagination.value.current = page
}

function handlePageSizeChange(pageSize: number) {
  pagination.value.pageSize = pageSize
  pagination.value.current = 1
}
</script>

<style scoped>
.device-wall {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.device-wall-filters {
  padding: 16px;
  background: var(--zhurong-color-bg-container);
  border-radius: 8px;
  border: 1px solid var(--zhurong-color-border);
}

.device-wall-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(320px, 1fr));
  gap: 16px;
}

.device-card {
  flex: 1;
  min-height: 280px;
}

.device-info {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.device-status {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 8px 12px;
  border-radius: 6px;
  font-weight: 500;
}

.device-status.running {
  background: var(--zhurong-color-success-bg);
  color: var(--zhurong-color-success);
}

.device-status.standby {
  background: var(--zhurong-color-warning-bg);
  color: var(--zhurong-color-warning);
}

.device-status.offline {
  background: var(--zhurong-color-error-bg);
  color: var(--zhurong-color-error);
}

.device-status.fault {
  background: var(--zhurong-color-error-bg);
  color: var(--zhurong-color-error);
}

.device-status.maintenance {
  background: var(--zhurong-color-info-bg);
  color: var(--zhurong-color-info);
}

.status-dot {
  width: 8px;
  height: 8px;
  border-radius: 50%;
  background: currentColor;
  animation: pulse 2s infinite;
}

.status-dot.running {
  animation: pulse 2s infinite;
}

@keyframes pulse {
  0%, 100% { opacity: 1; }
  50% { opacity: 0.5; }
}

.device-meta {
  display: flex;
  flex-direction: column;
  gap: 4px;
  font-size: 13px;
  color: var(--zhurong-color-text-secondary);
}

.device-metrics {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 8px;
  padding-top: 8px;
  border-top: 1px solid var(--zhurong-color-border-secondary);
}

.metric {
  display: flex;
  flex-direction: column;
  gap: 2px;
}

.metric-label {
  font-size: 12px;
  color: var(--zhurong-color-text-tertiary);
}

.metric-value {
  font-size: 14px;
  font-weight: 500;
  color: var(--zhurong-color-text-heading);
}
</style>
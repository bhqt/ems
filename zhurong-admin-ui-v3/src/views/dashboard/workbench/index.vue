<template>
  <div class="workbench-container">
    <a-row :gutter="24">
      <a-col :span="24">
        <a-card :bordered="false" class="welcome-card">
          <div class="welcome-content">
            <div class="welcome-text">
              <h2>{{ t('dashboard.workbench.welcome') }}, {{ userStore.userInfo.nickName || 'Admin' }}!</h2>
              <p>{{ t('dashboard.workbench.description') }}</p>
            </div>
            <div class="welcome-stats">
              <div class="stat-item">
                <div class="stat-value">{{ stats.deviceCount }}</div>
                <div class="stat-label">{{ t('dashboard.workbench.totalDevices') }}</div>
              </div>
              <div class="stat-item">
                <div class="stat-value">{{ stats.onlineDevices }}</div>
                <div class="stat-label">{{ t('dashboard.workbench.onlineDevices') }}</div>
              </div>
              <div class="stat-item">
                <div class="stat-value">{{ stats.totalEnergy }}</div>
                <div class="stat-label">{{ t('dashboard.workbench.totalEnergy') }}</div>
              </div>
            </div>
          </div>
        </a-card>
      </a-col>
    </a-row>

    <a-row :gutter="24" style="margin-top: 24px">
      <a-col :span="16">
        <a-card :title="t('dashboard.workbench.energyTrend')" :bordered="false">
          <div ref="energyChartRef" style="height: 300px"></div>
        </a-card>
      </a-col>
      <a-col :span="8">
        <a-card :title="t('dashboard.workbench.quickActions')" :bordered="false">
          <div class="quick-actions">
            <a-button type="primary" size="large" block @click="router.push('/system/user')">
              <template #icon><UserOutlined /></template>
              {{ t('dashboard.workbench.userManagement') }}
            </a-button>
            <a-button size="large" block @click="router.push('/energy/overview')">
              <template #icon><DashboardOutlined /></template>
              {{ t('dashboard.workbench.energyOverview') }}
            </a-button>
            <a-button size="large" block @click="router.push('/maintenance/workorder')">
              <template #icon><ToolOutlined /></template>
              {{ t('dashboard.workbench.workOrder') }}
            </a-button>
            <a-button size="large" block @click="router.push('/hospital/dashboard')">
              <template #icon><MedicineBoxOutlined /></template>
              {{ t('dashboard.workbench.hospitalOverview') }}
            </a-button>
          </div>
        </a-card>
      </a-col>
    </a-row>

    <a-row :gutter="24" style="margin-top: 24px">
      <a-col :span="12">
        <a-card :title="t('dashboard.workbench.recentAlerts')" :bordered="false">
          <a-list :data-source="recentAlerts" size="small">
            <template #renderItem="{ item }">
              <a-list-item>
                <a-list-item-meta>
                  <template #avatar>
                    <a-tag :color="item.level === 'high' ? 'red' : item.level === 'medium' ? 'orange' : 'blue'">
                      {{ item.levelText }}
                    </a-tag>
                  </template>
                  <template #title>
                    <span>{{ item.title }}</span>
                  </template>
                  <template #description>
                    <span>{{ item.time }}</span>
                  </template>
                </a-list-item-meta>
              </a-list-item>
            </template>
          </a-list>
        </a-card>
      </a-col>
      <a-col :span="12">
        <a-card :title="t('dashboard.workbench.deviceStatus')" :bordered="false">
          <div ref="deviceChartRef" style="height: 200px"></div>
        </a-card>
      </a-col>
    </a-row>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, onBeforeUnmount } from 'vue'
import { useRouter } from 'vue-router'
import { useI18n } from 'vue-i18n'
import { UserOutlined, DashboardOutlined, ToolOutlined, MedicineBoxOutlined } from '@ant-design/icons-vue'
import { useUserStore } from '@/stores/modules/user'
import * as echarts from 'echarts'

const router = useRouter()
const { t } = useI18n()
const userStore = useUserStore()

const energyChartRef = ref<HTMLElement>()
const deviceChartRef = ref<HTMLElement>()
let energyChart: echarts.ECharts | null = null
let deviceChart: echarts.ECharts | null = null

const stats = reactive({
  deviceCount: 128,
  onlineDevices: 112,
  totalEnergy: '1,234,567 kWh',
})

const recentAlerts = ref([
  { title: 'MRI-001 冷却系统温度过高', level: 'high', levelText: '高', time: '5 分钟前' },
  { title: 'UPS-002 电池电量低于 20%', level: 'medium', levelText: '中', time: '15 分钟前' },
  { title: '空调系统-003 运行异常', level: 'low', levelText: '低', time: '1 小时前' },
  { title: '照明系统-004 故障', level: 'low', levelText: '低', time: '2 小时前' },
])

function initEnergyChart() {
  if (!energyChartRef.value) return
  energyChart = echarts.init(energyChartRef.value)
  
  const option = {
    tooltip: { trigger: 'axis' },
    legend: { data: [t('dashboard.workbench.electricity'), t('dashboard.workbench.water'), t('dashboard.workbench.gas')] },
    grid: { left: '3%', right: '4%', bottom: '3%', containLabel: true },
    xAxis: { type: 'category', data: ['1月', '2月', '3月', '4月', '5月', '6月'] },
    yAxis: { type: 'value' },
    series: [
      { name: t('dashboard.workbench.electricity'), type: 'line', data: [120, 132, 101, 134, 90, 230] },
      { name: t('dashboard.workbench.water'), type: 'line', data: [220, 182, 191, 234, 290, 330] },
      { name: t('dashboard.workbench.gas'), type: 'line', data: [150, 232, 201, 154, 190, 330] },
    ],
  }
  energyChart.setOption(option)
}

function initDeviceChart() {
  if (!deviceChartRef.value) return
  deviceChart = echarts.init(deviceChartRef.value)
  
  const option = {
    tooltip: { trigger: 'item' },
    series: [
      {
        type: 'pie',
        radius: ['40%', '70%'],
        data: [
          { value: 112, name: t('dashboard.workbench.online') },
          { value: 10, name: t('dashboard.workbench.offline') },
          { value: 6, name: t('dashboard.workbench.fault') },
        ],
        emphasis: { itemStyle: { shadowBlur: 10, shadowOffsetX: 0, shadowColor: 'rgba(0, 0, 0, 0.5)' } },
      },
    ],
  }
  deviceChart.setOption(option)
}

function handleResize() {
  energyChart?.resize()
  deviceChart?.resize()
}

onMounted(() => {
  initEnergyChart()
  initDeviceChart()
  window.addEventListener('resize', handleResize)
})

onBeforeUnmount(() => {
  window.removeEventListener('resize', handleResize)
  energyChart?.dispose()
  deviceChart?.dispose()
})
</script>

<style scoped>
.workbench-container {
  padding: 24px;
}

.welcome-card {
  background: linear-gradient(135deg, var(--zhurong-color-primary), var(--zhurong-color-primary-hover));
}

.welcome-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.welcome-text h2 {
  color: #fff;
  font-size: 24px;
  margin: 0 0 8px;
}

.welcome-text p {
  color: rgba(255, 255, 255, 0.85);
  font-size: 14px;
  margin: 0;
}

.welcome-stats {
  display: flex;
  gap: 48px;
}

.stat-item {
  text-align: center;
}

.stat-value {
  color: #fff;
  font-size: 28px;
  font-weight: 600;
}

.stat-label {
  color: rgba(255, 255, 255, 0.85);
  font-size: 13px;
  margin-top: 4px;
}

.quick-actions {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.quick-actions .ant-btn {
  height: 48px;
  font-size: 15px;
}
</style>
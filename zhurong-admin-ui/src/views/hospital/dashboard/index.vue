<template>
  <div class="dash">
    <div class="dash-header">
      <div>
        <div class="dash-title">{{ $t('hospital.dashTitle') }}</div>
        <div class="dash-sub">{{ $t('hospital.dashSub') }}</div>
      </div>
      <div class="dash-meta">
        <el-tag size="small" type="info" class="dash-role-tag">
          {{ $t('hospital.currentRole') }}：{{ roleLabel }}
        </el-tag>
        <span class="dash-time">{{ currentTime }}</span>
      </div>
    </div>

    <el-row :gutter="16" class="dash-grid">
      <el-col
        v-for="m in modules"
        :key="m.key"
        :xs="24" :sm="12" :lg="m.span || 12"
        class="dash-cell"
      >
        <el-card shadow="hover" class="dash-card">
          <div slot="header" class="dash-card-head">
            <span class="dash-card-title">{{ $t(m.title) }}</span>
            <el-button v-if="m.link" type="text" size="mini" @click="go(m.link)">
              {{ $t('hospital.viewMore') }}
            </el-button>
          </div>

          <!-- 能耗概览（通用） -->
          <el-table
            v-if="m.key === 'overview'"
            :data="overviewList" size="small"
            @row-click="goDrill"
          >
            <el-table-column :label="$t('hospital.dimension')" prop="dimName" show-overflow-tooltip>
              <template slot-scope="scope">
                <el-link type="primary" :underline="false">{{ scope.row.dimName }}</el-link>
              </template>
            </el-table-column>
            <el-table-column :label="$t('hospital.kwh')" prop="kwh" width="110" align="right">
              <template slot-scope="scope">{{ scope.row.kwh }} kWh</template>
            </el-table-column>
            <el-table-column :label="$t('hospital.avgPower')" prop="avgPower" width="100" align="right">
              <template slot-scope="scope">{{ scope.row.avgPower }} kW</template>
            </el-table-column>
            <el-table-column :label="$t('hospital.chainRatio')" width="90" align="right">
              <template slot-scope="scope">
                <span :class="{ up: scope.row.chainRatio != null && scope.row.chainRatio > 0 }">
                  {{ scope.row.chainRatio == null ? '-' : scope.row.chainRatio + '%' }}
                </span>
              </template>
            </el-table-column>
          </el-table>

          <!-- 院区能耗对比（院区管理员） -->
          <el-table v-if="m.key === 'areaCompare'" :data="areaCompareList" size="small">
            <el-table-column type="index" width="50" align="center" />
            <el-table-column :label="$t('hospital.areaName')" prop="dimName" show-overflow-tooltip />
            <el-table-column :label="$t('hospital.kwh')" prop="kwh" width="110" align="right">
              <template slot-scope="scope">{{ scope.row.kwh }} kWh</template>
            </el-table-column>
            <el-table-column :label="$t('hospital.deviceCount')" prop="deviceCount" width="90" align="right" />
          </el-table>

          <!-- 设备状态（院区/设备管理员） -->
          <div v-if="m.key === 'deviceStatus'" class="status-grid">
            <div v-for="s in devStatList" :key="s.key" class="status-item">
              <div class="status-num" :style="{ color: s.color }">{{ s.value }}</div>
              <div class="status-label">{{ s.label }}</div>
            </div>
          </div>

          <!-- 分项（能源管理员） -->
          <div v-if="m.key === 'category'" ref="catChart" class="dash-chart" />

          <!-- 趋势（能源管理员） -->
          <div v-if="m.key === 'trend'" ref="trendChart" class="dash-chart dash-chart-tall" />

          <!-- 耗电排名（能源管理员） -->
          <el-table v-if="m.key === 'rank'" :data="rankList" size="small">
            <el-table-column type="index" width="45" align="center" />
            <el-table-column :label="$t('hospital.deviceName')" prop="deviceName" show-overflow-tooltip />
            <el-table-column :label="$t('hospital.kwh')" prop="kwh" width="110" align="right">
              <template slot-scope="scope">{{ scope.row.kwh }} kWh</template>
            </el-table-column>
          </el-table>

          <!-- 单位工作量能效（能源/设备管理员） -->
          <el-table v-if="m.key === 'efficiency'" :data="effList" size="small">
            <el-table-column :label="$t('hospital.deviceName')" prop="deviceName" show-overflow-tooltip />
            <el-table-column :label="$t('hospital.workloadCount')" prop="workload" width="90" align="right" />
            <el-table-column :label="$t('hospital.unitEnergy')" prop="unitEnergy" width="100" align="right">
              <template slot-scope="scope">{{ scope.row.unitEnergy }} kWh/次</template>
            </el-table-column>
          </el-table>

          <!-- 最近报警（通用） -->
          <el-table v-if="m.key === 'alarm'" :data="alarmList" size="small">
            <el-table-column :label="$t('hospital.alarmRecordManage')" prop="deviceName" show-overflow-tooltip />
            <el-table-column :label="$t('hospital.level')" width="80" align="center">
              <template slot-scope="scope">{{ levelLabel(scope.row) }}</template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import * as echarts from 'echarts'
import { checkPermi } from '@/utils/permission'
import { getEnergyOverview, getEnergyCategory, getEnergyTrend, getEnergyRank, getEfficiency } from '@/api/hospital/energy'
import { getMonitorOverview } from '@/api/hospital/monitor'
import { listAlarmRecord } from '@/api/hospital/alarm'

const MODULES = [
  { key: 'overview', span: 12, title: 'hospital.dashOverview', permission: 'hospital:energy:list', roles: ['area-admin', 'energy-admin'], common: true, link: '/hospital/energy' },
  { key: 'areaCompare', span: 12, title: 'hospital.dashAreaCompare', permission: 'hospital:energy:list', roles: ['area-admin'] },
  { key: 'deviceStatus', span: 12, title: 'hospital.dashDeviceStatus', permission: 'hospital:monitor:list', roles: ['area-admin', 'device-admin'], link: '/hospital/monitor' },
  { key: 'category', span: 12, title: 'hospital.dashCategory', permission: 'hospital:energy:list', roles: ['energy-admin'] },
  { key: 'trend', span: 12, title: 'hospital.dashTrend', permission: 'hospital:energy:list', roles: ['energy-admin'] },
  { key: 'rank', span: 12, title: 'hospital.dashRank', permission: 'hospital:energy:list', roles: ['energy-admin'] },
  { key: 'efficiency', span: 12, title: 'hospital.dashEfficiency', permission: 'hospital:energy:list', roles: ['energy-admin', 'device-admin'], link: '/hospital/efficiency' },
  { key: 'alarm', span: 12, title: 'hospital.dashAlarm', permission: 'hospital:alarmRecord:list', roles: ['area-admin', 'device-admin'], common: true, link: '/hospital/alarmRecord' }
]

export default {
  name: 'HospitalDashboard',
  data() {
    return {
      currentTime: '',
      clock: null,
      query: null,
      overviewList: [],
      areaCompareList: [],
      rankList: [],
      effList: [],
      alarmList: [],
      devStatList: [],
      catChart: null,
      trendChart: null
    }
  },
  computed: {
    modules() {
      return MODULES.filter(m => this.canShow(m))
    },
    roleKeys() {
      return this.$store.getters.roles || []
    },
    roleLabel() {
      const map = {
        admin: this.$t('hospital.roleAdmin'),
        'area-admin': this.$t('hospital.roleAreaAdmin'),
        'energy-admin': this.$t('hospital.roleEnergyAdmin'),
        'device-admin': this.$t('hospital.roleDeviceAdmin')
      }
      if (!this.roleKeys.length || this.roleKeys.includes('ROLE_DEFAULT')) {
        return this.$t('hospital.roleCommon')
      }
      return this.roleKeys.map(r => map[r] || r).join(', ')
    }
  },
  created() {
    this.clock = setInterval(this.tick, 1000)
    const end = new Date()
    const start = new Date(end.getTime() - 7 * 24 * 3600 * 1000)
    this.query = { startTime: this.fmt(start), endTime: this.fmt(end) }
    this.loadData()
  },
  mounted() {
    this.$nextTick(() => {
      if (this.canShowKey('category') && this.$refs.catChart) {
        this.catChart = echarts.init(this.$refs.catChart)
      }
      if (this.canShowKey('trend') && this.$refs.trendChart) {
        this.trendChart = echarts.init(this.$refs.trendChart)
      }
      window.addEventListener('resize', this.resizeCharts)
    })
  },
  beforeDestroy() {
    clearInterval(this.clock)
    window.removeEventListener('resize', this.resizeCharts)
    if (this.catChart) { this.catChart.dispose(); this.catChart = null }
    if (this.trendChart) { this.trendChart.dispose(); this.trendChart = null }
  },
  methods: {
    fmt(d) {
      const pad = (n) => (n < 10 ? '0' + n : n)
      return d.getFullYear() + '-' + pad(d.getMonth() + 1) + '-' + pad(d.getDate()) + ' ' + pad(d.getHours()) + ':' + pad(d.getMinutes()) + ':' + pad(d.getSeconds())
    },
    tick() {
      this.currentTime = this.fmt(new Date())
    },
    go(path) {
      this.$router.push(path)
    },
    goDrill(row) {
      this.$router.push('/hospital/energy')
    },
    moduleOf(key) {
      return MODULES.find(m => m.key === key)
    },
    canShowKey(key) {
      const m = this.moduleOf(key)
      return m ? this.canShow(m) : false
    },
    canShow(m) {
      if (m.permission && !checkPermi([m.permission])) return false
      if (this.roleKeys.includes('admin')) return true
      if (!this.roleKeys.length || this.roleKeys.includes('ROLE_DEFAULT')) {
        return !!m.common
      }
      return m.roles.some(r => this.roleKeys.includes(r))
    },
    resizeCharts() {
      if (this.catChart) this.catChart.resize()
      if (this.trendChart) this.trendChart.resize()
    },
    loadData() {
      if (this.canShowKey('overview')) {
        getEnergyOverview({ ...this.query, level: 'AREA' }).then(r => {
          this.overviewList = (r.data || []).slice(0, 6)
        }).catch(() => {})
      }
      if (this.canShowKey('areaCompare')) {
        getEnergyOverview({ ...this.query, level: 'AREA' }).then(r => {
          this.areaCompareList = r.data || []
        }).catch(() => {})
      }
      if (this.canShowKey('category')) {
        getEnergyCategory({ ...this.query }).then(r => {
          this.renderCategory(r.data || [])
        }).catch(() => {})
      }
      if (this.canShowKey('trend')) {
        getEnergyTrend({ ...this.query, granularity: 'DAY' }).then(r => {
          this.renderTrend(r.data || [])
        }).catch(() => {})
      }
      if (this.canShowKey('rank')) {
        getEnergyRank({ ...this.query, limit: 6 }).then(r => {
          this.rankList = r.data || []
        }).catch(() => {})
      }
      if (this.canShowKey('efficiency')) {
        getEfficiency({ ...this.query }).then(r => {
          this.effList = (r.data || []).slice(0, 6)
        }).catch(() => {})
      }
      if (this.canShowKey('deviceStatus')) {
        getMonitorOverview({}).then(r => {
          this.devStatList = this.aggDeviceStatus(r.data || [])
        }).catch(() => {})
      }
      if (this.canShowKey('alarm')) {
        listAlarmRecord({}).then(r => {
          this.alarmList = (r.data || []).slice(0, 6)
        }).catch(() => {})
      }
    },
    aggDeviceStatus(list) {
      let online = 0, running = 0, standby = 0, fault = 0
      list.forEach(d => {
        if (d.online === false || d.online === 0) return
        online++
        if (d.runStatus === '1') running++
        else if (d.runStatus === '0') standby++
        else fault++
      })
      return [
        { key: 'online', label: this.$t('hospital.deviceOnline'), value: online, color: '#0e9f6e' },
        { key: 'running', label: this.$t('hospital.running'), value: running, color: '#2563eb' },
        { key: 'standby', label: this.$t('hospital.standby'), value: standby, color: '#e08a3c' },
        { key: 'fault', label: this.$t('hospital.deviceFault'), value: fault, color: '#d84a4a' }
      ]
    },
    renderCategory(list) {
      if (!this.catChart) return
      this.catChart.setOption({
        tooltip: { trigger: 'item', formatter: '{b}: {c} kWh ({d}%)' },
        legend: { bottom: 0, type: 'scroll' },
        series: [{
          type: 'pie',
          radius: ['35%', '65%'],
          center: ['50%', '42%'],
          data: list.map(i => ({ name: i.categoryName, value: i.kwh }))
        }]
      }, true)
    },
    renderTrend(list) {
      if (!this.trendChart) return
      this.trendChart.setOption({
        tooltip: { trigger: 'axis' },
        legend: { data: [this.$t('hospital.kwh')] },
        grid: { left: '6%', right: '4%', bottom: '6%', containLabel: true },
        xAxis: { type: 'category', data: list.map(i => i.label) },
        yAxis: { type: 'value', name: 'kWh' },
        series: [{ name: this.$t('hospital.kwh'), type: 'line', smooth: true, areaStyle: { opacity: 0.25 }, data: list.map(i => i.kwh) }]
      }, true)
    },
    levelLabel(row) {
      const lv = row.escalateLevel != null ? row.escalateLevel : row.level
      const map = { '0': this.$t('hospital.levelNormal'), '1': this.$t('hospital.levelSerious'), '2': this.$t('hospital.levelUrgent') }
      return map[String(lv)] || lv
    }
  }
}
</script>

<style scoped>
.dash {
  padding: 8px;
}
.dash-header {
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  padding: 16px 8px 12px;
  flex-wrap: wrap;
  gap: 8px;
}
.dash-title {
  font-size: 20px;
  font-weight: 700;
  color: #303133;
}
.dash-sub {
  font-size: 13px;
  color: #909399;
  margin-top: 4px;
}
.dash-meta {
  display: flex;
  align-items: center;
  gap: 12px;
}
.dash-role-tag {
  margin-right: 8px;
}
.dash-time {
  font-size: 13px;
  color: #909399;
}
.dash-grid {
  margin-top: 4px;
}
.dash-cell {
  margin-bottom: 16px;
}
.dash-card-head {
  display: flex;
  align-items: center;
  justify-content: space-between;
}
.dash-card-title {
  font-weight: 600;
  color: #303133;
}
.dash-chart {
  height: 280px;
}
.dash-chart-tall {
  height: 320px;
}
.status-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 12px;
}
.status-item {
  text-align: center;
}
.status-num {
  font-size: 26px;
  font-weight: 700;
}
.status-label {
  font-size: 12px;
  color: #909399;
  margin-top: 4px;
}
.dash-card /deep/ .el-table__row {
  cursor: pointer;
}
.up {
  color: #d84a4a;
}
</style>

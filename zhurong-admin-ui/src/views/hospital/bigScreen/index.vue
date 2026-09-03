<template>
  <div class="screen">
    <div class="screen-header">
      <div class="screen-title">{{ $t('hospital.screenTitle') }}</div>
      <div class="screen-time">{{ currentTime }}</div>
    </div>

    <div class="screen-body">
      <!-- 左侧：概览 + 分项 -->
      <div class="screen-col screen-left">
        <div class="panel">
          <div class="panel-title">{{ $t('hospital.energyOverview') }}</div>
          <el-table :data="overviewList" size="small" class="dark-table">
            <el-table-column :label="$t('hospital.dimension')" prop="dimName" show-overflow-tooltip />
            <el-table-column :label="$t('hospital.kwh')" prop="kwh" width="110" align="right">
              <template slot-scope="scope">{{ scope.row.kwh }}</template>
            </el-table-column>
          </el-table>
        </div>
        <div class="panel">
          <div class="panel-title">{{ $t('hospital.energyCategory') }}</div>
          <div ref="catChart" class="chart" />
        </div>
      </div>

      <!-- 中间：趋势大图 -->
      <div class="screen-col screen-center">
        <div class="panel center-panel">
          <div class="panel-title">{{ $t('hospital.energyTrend') }}</div>
          <div ref="trendChart" class="chart big-chart" />
        </div>
      </div>

      <!-- 右侧：排名 + 报警 -->
      <div class="screen-col screen-right">
        <div class="panel">
          <div class="panel-title">{{ $t('hospital.energyRank') }}</div>
          <el-table :data="rankList" size="small" class="dark-table">
            <el-table-column type="index" width="50" align="center" />
            <el-table-column :label="$t('hospital.deviceName')" prop="deviceName" show-overflow-tooltip />
            <el-table-column :label="$t('hospital.kwh')" prop="kwh" width="100" align="right">
              <template slot-scope="scope">{{ scope.row.kwh }}</template>
            </el-table-column>
          </el-table>
        </div>
        <div class="panel">
          <div class="panel-title">{{ $t('hospital.recentAlarm') }}</div>
          <el-table :data="alarmList" size="small" class="dark-table">
            <el-table-column :label="$t('hospital.deviceName')" prop="deviceName" show-overflow-tooltip />
            <el-table-column :label="$t('hospital.alarmType')" width="80" align="center">
              <template slot-scope="scope">
                <el-tag :type="scope.row.alarmType === 'OFFLINE' ? 'warning' : 'danger'" size="mini">
                  {{ scope.row.alarmType === 'OFFLINE' ? $t('hospital.offlineAlarm') : $t('hospital.overload') }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column :label="$t('hospital.level')" width="80" align="center">
              <template slot-scope="scope">
                <el-tag :type="levelTagType(scope.row.escalateLevel != null ? scope.row.escalateLevel : scope.row.level)" size="mini">
                  {{ levelLabel(scope.row.escalateLevel != null ? scope.row.escalateLevel : scope.row.level) }}
                </el-tag>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import * as echarts from 'echarts'
import { getEnergyOverview, getEnergyTrend, getEnergyRank, getEnergyCategory } from '@/api/hospital/energy'
import { listAlarmRecord } from '@/api/hospital/alarm'

export default {
  name: 'HospitalScreen',
  data() {
    return {
      currentTime: '',
      timer: null,
      overviewList: [],
      rankList: [],
      alarmList: [],
      catChart: null,
      trendChart: null
    }
  },
  created() {
    this.tick()
    this.timer = setInterval(this.tick, 1000)
    const end = new Date()
    const start = new Date(end.getTime() - 7 * 24 * 3600 * 1000)
    this.query = {
      startTime: this.fmt(start),
      endTime: this.fmt(end)
    }
    this.loadData()
  },
  mounted() {
    this.catChart = echarts.init(this.$refs.catChart)
    this.trendChart = echarts.init(this.$refs.trendChart)
    window.addEventListener('resize', this.resizeCharts)
  },
  beforeDestroy() {
    clearInterval(this.timer)
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
    resizeCharts() {
      if (this.catChart) this.catChart.resize()
      if (this.trendChart) this.trendChart.resize()
    },
    loadData() {
      getEnergyOverview(this.query).then(r => {
        this.overviewList = r.data || []
      }).catch(() => {})
      getEnergyCategory({ startTime: this.query.startTime, endTime: this.query.endTime }).then(r => {
        this.renderCategory(r.data || [])
      }).catch(() => {})
      getEnergyTrend({ startTime: this.query.startTime, endTime: this.query.endTime, granularity: 'DAY' }).then(r => {
        this.renderTrend(r.data || [])
      }).catch(() => {})
      getEnergyRank({ startTime: this.query.startTime, endTime: this.query.endTime, limit: 5 }).then(r => {
        this.rankList = r.data || []
      }).catch(() => {})
      listAlarmRecord({ level: undefined, alarmType: undefined }).then(r => {
        this.alarmList = (r.data || []).slice(0, 5)
      }).catch(() => {})
    },
    renderCategory(list) {
      if (!this.catChart) return
      this.catChart.setOption({
        tooltip: { trigger: 'item', formatter: '{b}: {c} kWh ({d}%)' },
        legend: { textStyle: { color: '#c8d6e5' }, bottom: 0 },
        series: [{
          type: 'pie',
          radius: ['35%', '65%'],
          center: ['50%', '45%'],
          data: list.map(i => ({ name: i.categoryName, value: i.kwh })),
          label: { color: '#c8d6e5' }
        }]
      }, true)
    },
    renderTrend(list) {
      if (!this.trendChart) return
      this.trendChart.setOption({
        backgroundColor: 'transparent',
        tooltip: { trigger: 'axis' },
        legend: { data: [this.$t('hospital.kwh'), this.$t('hospital.avgPower')], textStyle: { color: '#c8d6e5' } },
        grid: { left: '4%', right: '4%', bottom: '6%', containLabel: true },
        xAxis: { type: 'category', data: list.map(i => i.label), axisLabel: { color: '#c8d6e5' } },
        yAxis: [{ type: 'value', name: 'kWh', axisLabel: { color: '#c8d6e5' } }, { type: 'value', name: 'kW', axisLabel: { color: '#c8d6e5' } }],
        series: [
          { name: this.$t('hospital.kwh'), type: 'line', smooth: true, areaStyle: { opacity: 0.3 }, data: list.map(i => i.kwh), itemStyle: { color: '#2b8cbe' } },
          { name: this.$t('hospital.avgPower'), type: 'line', smooth: true, yAxisIndex: 1, data: list.map(i => i.avgPower), itemStyle: { color: '#e08a3c' } }
        ]
      }, true)
    },
    levelLabel(level) {
      const map = { '0': this.$t('hospital.levelNormal'), '1': this.$t('hospital.levelSerious'), '2': this.$t('hospital.levelUrgent') }
      return map[String(level)] || level
    },
    levelTagType(level) {
      const map = { '0': 'info', '1': 'warning', '2': 'danger' }
      return map[String(level)] || 'info'
    }
  }
}
</script>

<style scoped>
.screen {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: radial-gradient(ellipse at center, #0c1c33 0%, #060d1a 100%);
  color: #c8d6e5;
  overflow: auto;
}
.screen-header {
  height: 64px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 24px;
  background: rgba(28, 48, 84, 0.6);
  border-bottom: 1px solid rgba(67, 130, 210, 0.4);
}
.screen-title {
  font-size: 24px;
  font-weight: 700;
  background: linear-gradient(90deg, #43a2d6, #7dd3fc);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
}
.screen-time {
  font-size: 14px;
  color: #9fc3e8;
}
.screen-body {
  display: flex;
  gap: 12px;
  padding: 12px;
  min-height: calc(100% - 64px);
}
.screen-col {
  display: flex;
  flex-direction: column;
  gap: 12px;
}
.screen-left { flex: 0 0 320px; }
.screen-center { flex: 1; }
.screen-right { flex: 0 0 320px; }
.panel {
  background: rgba(16, 34, 60, 0.85);
  border: 1px solid rgba(67, 130, 210, 0.35);
  border-radius: 6px;
  padding: 12px;
}
.panel-title {
  font-size: 15px;
  font-weight: 600;
  color: #7dd3fc;
  margin-bottom: 10px;
  border-left: 4px solid #2b8cbe;
  padding-left: 8px;
}
.chart { height: 220px; }
.big-chart { height: 560px; }
.center-panel { flex: 1; }
.dark-table /deep/ .el-table {
  background: transparent;
  color: #c8d6e5;
}
.dark-table /deep/ .el-table__header th {
  background: rgba(43, 90, 150, 0.35);
  color: #9fc3e8;
}
.dark-table /deep/ .el-table__body td {
  background: transparent;
  color: #c8d6e5;
  border-bottom: 1px solid rgba(67, 130, 210, 0.15);
}
.dark-table /deep/ .el-table__row:hover > td {
  background: rgba(43, 90, 150, 0.2) !important;
}
</style>

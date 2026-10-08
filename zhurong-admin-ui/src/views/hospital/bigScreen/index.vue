<template>
  <div class="screen">
    <!-- 顶部标题栏 -->
    <HeaderBar class="screen-hdr" />

    <!-- 主体 -->
    <div class="screen-body">
      <!-- 左列 -->
      <div class="col col-left">
        <div class="panel">
          <div class="panel-title"><i class="el-icon-office-building" /> 能耗概览</div>
          <div class="kpi-grid">
            <KpiCard
              v-for="k in kpiCards"
              :key="k.label"
              class="kpi-item"
              :label="k.label"
              :value="k.value"
              :unit="k.unit"
              :icon-class="k.icon"
              :icon="k.color"
              :extra="k.extra || ''"
              :danger="!!k.danger"
              :precision="k.precision || 0"
            />
          </div>
        </div>

        <div class="panel full">
          <div class="panel-title"><i class="el-icon-pie-chart" /> 分项能耗占比</div>
          <div class="chart-area tall">
            <CategoryChart :data="categoryList" />
          </div>
        </div>

        <div class="panel full">
          <div class="panel-title"><i class="el-icon-odometer" /> 实时功率曲线（小时）</div>
          <div class="chart-area tall">
            <PowerLineChart :data="hourPowerList" />
          </div>
        </div>
      </div>

      <!-- 中列 -->
      <div class="col col-center">
        <div class="panel chart-panel">
          <div class="panel-title center-title"><i class="el-icon-data-line" /> 近 7 天用电量 / 平均功率趋势</div>
          <div class="chart-area grow">
            <TrendChart :data="trendList" />
          </div>
        </div>

        <div class="row-two">
          <div class="panel">
            <div class="panel-title"><i class="el-icon-rank" /> 设备耗电排行榜</div>
            <div class="chart-area grow">
              <RankChart :data="rankList" />
            </div>
          </div>
          <div class="panel">
            <div class="panel-title"><i class="el-icon-country" /> 院区用电对比</div>
            <div class="chart-area grow">
              <AreaCompareChart :data="areaCompareList" />
            </div>
          </div>
        </div>
      </div>

      <!-- 右列 -->
      <div class="col col-right">
        <div class="panel full">
          <div class="panel-title"><i class="el-icon-monitor" /> 设备运行状态</div>
          <div class="chart-area tall">
            <DeviceStatusChart :data="deviceStatusList" />
          </div>
        </div>

        <div class="panel full">
          <div class="panel-title"><i class="el-icon-warning-outline" /> 实时告警</div>
          <div class="alarm-area">
            <AlarmScroll :data="alarmList" />
          </div>
        </div>

        <div class="panel full">
          <div class="panel-title"><i class="el-icon-s-data" /> 用能要点</div>
          <div class="tip-list">
            <div v-for="(t, i) in tips" :key="i" class="tip-item">
              <span class="tip-num">{{ String(i + 1).padStart(2, '0') }}</span>
              <span class="tip-text">{{ t }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import HeaderBar from './components/HeaderBar.vue'
import KpiCard from './components/KpiCard.vue'
import TrendChart from './components/TrendChart.vue'
import CategoryChart from './components/CategoryChart.vue'
import RankChart from './components/RankChart.vue'
import DeviceStatusChart from './components/DeviceStatusChart.vue'
import AreaCompareChart from './components/AreaCompareChart.vue'
import PowerLineChart from './components/PowerLineChart.vue'
import AlarmScroll from './components/AlarmScroll.vue'
import { getEnergyOverview, getEnergyTrend, getEnergyRank, getEnergyCategory } from '@/api/hospital/energy'
import { getMonitorOverview } from '@/api/hospital/monitor'
import { listAlarmRecord } from '@/api/hospital/alarm'

export default {
  name: 'HospitalScreen',
  components: {
    HeaderBar, KpiCard, TrendChart, CategoryChart, RankChart,
    DeviceStatusChart, AreaCompareChart, PowerLineChart, AlarmScroll
  },
  data() {
    return {
      kpiCards: [],
      categoryList: [],
      trendList: [],
      rankList: [],
      areaCompareList: [],
      hourPowerList: [],
      deviceStatusList: [],
      alarmList: [],
      tips: [],
      overview: [],
      query: null,
      loadTimer: null
    }
  },
  created() {
    const end = new Date()
    const start = new Date(end.getFullYear(), end.getMonth(), end.getDate() - 7)
    this.query = {
      startTime: this.fmt(start),
      endTime: this.fmt(end)
    }
    this.loadData()
    this.loadTimer = setInterval(this.loadData, 15000)
  },
  beforeDestroy() {
    clearInterval(this.loadTimer)
  },
  methods: {
    fmt(d) {
      const pad = n => (n < 10 ? '0' + n : '' + n)
      return d.getFullYear() + '-' + pad(d.getMonth() + 1) + '-' + pad(d.getDate()) + ' ' + pad(d.getHours()) + ':' + pad(d.getMinutes()) + ':' + pad(d.getSeconds())
    },
    loadData() {
      const q = this.query
      getEnergyOverview(q).then(r => {
        this.overview = r.data || []
        this.buildKpi()
      }).catch(() => {})
      getEnergyOverview({ ...q, level: 'AREA' }).then(r => {
        this.areaCompareList = r.data || []
      }).catch(() => {})
      getEnergyCategory(q).then(r => {
        this.categoryList = r.data || []
      }).catch(() => {})
      getEnergyTrend({ ...q, granularity: 'DAY' }).then(r => {
        this.trendList = r.data || []
      }).catch(() => {})
      getEnergyTrend({ ...q, granularity: 'HOUR' }).then(r => {
        this.hourPowerList = r.data || []
      }).catch(() => {})
      getEnergyRank({ ...q, limit: 8 }).then(r => {
        this.rankList = r.data || []
      }).catch(() => {})
      getMonitorOverview({}).then(r => {
        const list = r.data || []
        this.deviceStatusList = list
        this.buildTips(list)
        this.buildKpi()
      }).catch(() => {})
      listAlarmRecord({ pageNum: 1, pageSize: 10 }).then(r => {
        this.alarmList = (r.data && r.data.rows) ? r.data.rows : (r.data || [])
        this.buildKpi()
      }).catch(() => {})
    },
    buildKpi() {
      const list = this.overview || []
      const sum = arr => arr.reduce((s, i) => s + Number(i.kwh || 0), 0)
      const totalKwh = sum(list)
      let avgPower = 0
      const p = list.filter(i => Number(i.avgPower || 0) > 0)
      if (p.length) avgPower = p.reduce((s, i) => s + Number(i.avgPower), 0) / p.length
      const chain = list[0] && Number(list[0].chainRatio)
      const gap = p.length ? Math.max(p[0].avgPower - avgPower, 0) : 0
      const openAlarms = this.alarmList.filter(a => String(a.status) === '0').length
      const deviceCount = this.deviceStatusList.length
      const online = this.deviceStatusList.filter(i => i.online).length
      this.kpiCards = [
        { label: '设备总数', value: deviceCount || list.length, unit: '台', icon: 'el-icon-monitor', color: '#59c6ff' },
        { label: '在线设备', value: online, unit: '台', icon: 'el-icon-success', color: '#63e0a0' },
        { label: '用电总量', value: totalKwh.toFixed(1), unit: 'kWh', icon: 'el-icon-lightning', color: '#ffd76b' },
        { label: '平均功率', value: avgPower.toFixed(2), unit: 'kW', icon: 'el-icon-data-analysis', color: '#b07fff' },
        { label: '环比变化', value: Math.abs(chain || 0).toFixed(1), unit: '%', icon: 'el-icon-top-right', color: (chain || 0) >= 0 ? '#ff7f8f' : '#63e0a0', extra: (chain || 0) >= 0 ? '较上期上升' : '较上期下降', danger: (chain || 0) >= 0 },
        { label: '待处理告警', value: openAlarms, unit: '条', icon: 'el-icon-warning', color: '#ff7f8f', danger: openAlarms > 0, extra: (gap > 0 ? '峰值' + Number(gap).toFixed(1) + 'kW' : '') }
      ]
    },
    buildTips(deviceList) {
      const t = []
      const offlineList = deviceList.filter(i => !i.online)
      if (offlineList.length) {
        t.push('当前有 ' + offlineList.length + ' 台设备离线，建议尽快核查供电与网络状态')
      }
      const open = this.alarmList.filter(a => String(a.status) === '0')
      if (open.length) {
        t.push('存在 ' + open.length + ' 条待处理告警，请优先处理紧急级别告警')
      }
      const idx = Math.floor(Math.random() * 2)
      t.push(idx === 0 ? '建议错峰启停大型影像设备，规避尖峰电价时段' : '夜间时段降低非应急照明与空调负荷，节能约 30%')
      if (!this.tips.length || JSON.stringify(t) !== JSON.stringify(this.tips)) {
        this.tips = t
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.screen {
  position: fixed;
  inset: 0;
  z-index: 100;
  display: flex;
  flex-direction: column;
  background:
    radial-gradient(ellipse at 20% 20%, rgba(17, 74, 152, 0.28) 0%, transparent 45%),
    radial-gradient(ellipse at 80% 30%, rgba(89, 198, 255, 0.15) 0%, transparent 40%),
    radial-gradient(ellipse at 50% 100%, rgba(47, 127, 224, 0.2) 0%, transparent 50%),
    #060d1f;
  color: #c8d6e5;
  overflow: hidden;
  box-sizing: border-box;

  &::before {
    content: '';
    position: absolute;
    inset: 0;
    background-image:
      linear-gradient(rgba(64, 158, 255, 0.045) 1px, transparent 1px),
      linear-gradient(90deg, rgba(64, 158, 255, 0.045) 1px, transparent 1px);
    background-size: 40px 40px;
    pointer-events: none;
  }
}

.screen-hdr {
  flex: 0 0 88px;
}

.screen-body {
  flex: 1;
  display: flex;
  gap: 16px;
  padding: 16px 20px 20px;
  min-height: 0;
}

.col {
  display: flex;
  flex-direction: column;
  gap: 14px;
  min-height: 0;
}

.col-left, .col-right {
  width: 26%;
}

.col-center {
  flex: 1;
}

.panel {
  position: relative;
  display: flex;
  flex-direction: column;
  padding: 12px 14px;
  box-sizing: border-box;
  min-height: 0;
  overflow: hidden;
  background: linear-gradient(160deg, rgba(13, 30, 58, 0.82), rgba(18, 45, 92, 0.5));
  border: 1px solid rgba(64, 158, 255, 0.22);
  box-shadow: 0 0 14px rgba(21, 64, 128, 0.2), inset 0 0 22px rgba(32, 96, 160, 0.06);

  &.full {
    flex: 1 1 0;
  }
}

.panel-title {
  flex: 0 0 auto;
  font-size: 16px;
  font-weight: 600;
  color: #eaf6ff;
  letter-spacing: 2px;
  margin-bottom: 10px;
  display: flex;
  align-items: center;
  gap: 6px;

  i {
    color: #59c6ff;
    text-shadow: 0 0 8px rgba(89, 198, 255, 0.6);
  }

  &::after {
    content: '';
    flex: 0 0 4px;
    height: 4px;
    width: 4px;
    margin-left: auto;
    background: #2f7fe0;
    transform: rotate(45deg);
  }
}

.center-title {
  font-size: 18px;
  justify-content: center;
}

.kpi-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  grid-auto-rows: 1fr;
  gap: 12px;
}

.kpi-item {
  height: 96px;
}

.chart-area {
  width: 100%;
  flex: 1 1 auto;
  min-height: 0;
  overflow: hidden;
}

.chart-panel {
  flex: 1;
}

.row-two {
  display: flex;
  gap: 14px;
  flex: 1;
  min-height: 0;

  & > .panel {
    flex: 1;
  }
}

.alarm-area {
  flex: 1;
  min-height: 0;
  overflow: hidden;
}

.tip-list {
  display: flex;
  flex-direction: column;
  gap: 10px;
  padding: 4px 2px;
  flex: 1 1 auto;
  min-height: 0;
  overflow: hidden;
}

.tip-item {
  display: flex;
  align-items: center;
  padding: 8px 10px;
  background: rgba(64, 158, 255, 0.07);
  border-left: 2px solid #59c6ff;

  .tip-num {
    flex: 0 0 26px;
    color: #ffd76b;
    font-family: 'Consolas', monospace;
    font-size: 13px;
  }

  .tip-text {
    font-size: 13px;
    color: #cfdeee;
    line-height: 1.5;
  }
}
</style>

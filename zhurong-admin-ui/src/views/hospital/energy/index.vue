<template>
  <div class="app-container">
    <el-form v-show="showSearch" ref="queryForm" :model="queryParams" size="small" :inline="true" label-width="90px">
      <el-form-item :label="$t('hospital.timeRange')" prop="dateRange">
        <el-date-picker
          v-model="dateRange"
          type="datetimerange"
          :range-separator="$t('common.to')"
          :start-placeholder="$t('common.startTime')"
          :end-placeholder="$t('common.endTime')"
          value-format="yyyy-MM-dd HH:mm:ss"
          style="width:380px"
        />
      </el-form-item>
      <el-form-item :label="$t('hospital.drillLevel')" prop="level">
        <el-select v-model="queryParams.level" :placeholder="$t('common.pleaseSelect')" style="width:140px">
          <el-option :label="$t('hospital.levelArea')" value="AREA" />
          <el-option :label="$t('hospital.levelDept')" value="DEPT" />
          <el-option :label="$t('hospital.levelDevice')" value="DEVICE" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">{{ $t('button.search') }}</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">{{ $t('button.reset') }}</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="12" class="mb8">
      <el-col v-for="card in summaryCards" :key="card.key" :xs="24" :sm="8">
        <el-card shadow="hover" class="summary-card">
          <div class="summary-label">{{ card.label }}</div>
          <div class="summary-value">{{ card.value }}</div>
        </el-card>
      </el-col>
    </el-row>

    <el-tabs v-model="activeTab" class="mb8">
      <el-tab-pane :label="$t('hospital.energyOverview')" name="overview">
        <el-card class="mb8" shadow="never">
          <el-table v-loading="loading" :data="overviewList">
            <el-table-column :label="$t('hospital.dimension')" align="center" prop="dimName" />
            <el-table-column :label="$t('hospital.kwh')" align="center" prop="kwh" width="150" />
            <el-table-column :label="$t('hospital.avgPower')" align="center" prop="avgPower" width="150" />
            <el-table-column :label="$t('hospital.deviceCount')" align="center" prop="deviceCount" width="110" />
            <el-table-column :label="$t('hospital.chainRatio')" align="center" prop="chainRatio" width="150">
              <template slot-scope="scope">
                <el-tag v-if="scope.row.chainRatio != null" :type="scope.row.chainRatio > 0 ? 'danger' : 'success'" size="mini">
                  {{ scope.row.chainRatio > 0 ? '+' : '' }}{{ scope.row.chainRatio }}%
                </el-tag>
                <span v-else>-</span>
              </template>
            </el-table-column>
          </el-table>
        </el-card>

        <el-row :gutter="12">
          <el-col :xs="24" :md="14">
            <el-card shadow="never">
              <div slot="header"><span>{{ $t('hospital.energyTrend') }}</span></div>
              <div ref="trendChart" v-loading="trendLoading" style="height:320px" />
            </el-card>
          </el-col>
          <el-col :xs="24" :md="10">
            <el-card shadow="never">
              <div slot="header"><span>{{ $t('hospital.energyRank') }}</span></div>
              <el-table v-loading="rankLoading" :data="rankList">
                <el-table-column type="index" :label="$t('hospital.rankNo')" align="center" width="60" />
                <el-table-column :label="$t('hospital.deviceName')" align="center" prop="deviceName" :show-overflow-tooltip="true" />
                <el-table-column :label="$t('hospital.kwh')" align="center" prop="kwh" width="110" />
                <el-table-column :label="$t('hospital.avgPower')" align="center" prop="avgPower" width="110" />
              </el-table>
            </el-card>
          </el-col>
        </el-row>
      </el-tab-pane>

      <el-tab-pane :label="$t('hospital.energyCategory')" name="category">
        <el-row :gutter="12">
          <el-col :xs="24" :md="12">
            <el-card shadow="never">
              <div slot="header"><span>{{ $t('hospital.energyCategory') }}</span></div>
              <el-table v-loading="catLoading" :data="categoryList">
                <el-table-column :label="$t('hospital.dimension')" align="center" prop="categoryName" />
                <el-table-column :label="$t('hospital.kwh')" align="center" prop="kwh" width="150" />
                <el-table-column :label="$t('hospital.percent')" align="center" prop="percent" width="110">
                  <template slot-scope="scope">
                    <span v-if="scope.row.percent != null">{{ scope.row.percent }}%</span>
                    <span v-else>-</span>
                  </template>
                </el-table-column>
              </el-table>
            </el-card>
          </el-col>
          <el-col :xs="24" :md="12">
            <el-card shadow="never">
              <div slot="header"><span>{{ $t('hospital.energyCategoryTrend') }}</span></div>
              <div ref="catChart" v-loading="catLoading" style="height:360px" />
            </el-card>
          </el-col>
        </el-row>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import * as echarts from 'echarts'
import { getEnergyOverview, getEnergyTrend, getEnergyRank, getEnergyCategory, getEnergyCategoryTrend } from '@/api/hospital/energy'

export default {
  name: 'HospitalEnergy',
  data() {
    return {
      loading: true,
      trendLoading: false,
      rankLoading: false,
      catLoading: false,
      showSearch: true,
      overviewList: [],
      rankList: [],
      categoryList: [],
      trendChart: null,
      catChart: null,
      activeTab: 'overview',
      dateRange: [],
      queryParams: {
        level: 'AREA',
        startTime: undefined,
        endTime: undefined
      }
    }
  },
  computed: {
    summaryCards() {
      let totalKwh = 0
      let totalDevices = 0
      this.overviewList.forEach(o => {
        totalKwh += Number(o.kwh || 0)
        totalDevices += Number(o.deviceCount || 0)
      })
      return [
        { key: 'kwh', label: this.$t('hospital.totalKwh'), value: totalKwh.toFixed(2) + ' kWh' },
        { key: 'dev', label: this.$t('hospital.deviceCount'), value: totalDevices },
        { key: 'dim', label: this.$t('hospital.dimension'), value: this.overviewList.length }
      ]
    }
  },
  created() {
    const end = new Date()
    const start = new Date(end.getTime() - 7 * 24 * 3600 * 1000)
    this.dateRange = [this.formatDate(start), this.formatDate(end)]
    this.handleQuery()
  },
  mounted() {
    this.trendChart = echarts.init(this.$refs.trendChart)
    this.catChart = echarts.init(this.$refs.catChart)
    window.addEventListener('resize', this.resizeChart)
  },
  beforeDestroy() {
    window.removeEventListener('resize', this.resizeChart)
    if (this.trendChart) {
      this.trendChart.dispose()
      this.trendChart = null
    }
    if (this.catChart) {
      this.catChart.dispose()
      this.catChart = null
    }
  },
  methods: {
    formatDate(d) {
      const pad = (n) => (n < 10 ? '0' + n : n)
      return d.getFullYear() + '-' + pad(d.getMonth() + 1) + '-' + pad(d.getDate()) + ' ' + pad(d.getHours()) + ':' + pad(d.getMinutes()) + ':' + pad(d.getSeconds())
    },
    syncRange() {
      if (this.dateRange && this.dateRange.length === 2) {
        this.queryParams.startTime = this.dateRange[0]
        this.queryParams.endTime = this.dateRange[1]
      } else {
        this.queryParams.startTime = undefined
        this.queryParams.endTime = undefined
      }
    },
    handleQuery() {
      this.syncRange()
      this.getOverview()
      this.getTrend()
      this.getRank()
      this.getCategory()
      this.getCategoryTrend()
    },
    resetQuery() {
      this.resetForm('queryForm')
      this.dateRange = []
      this.handleQuery()
    },
    resizeChart() {
      if (this.trendChart) {
        this.trendChart.resize()
      }
      if (this.catChart) {
        this.catChart.resize()
      }
    },
    getOverview() {
      this.loading = true
      getEnergyOverview(this.queryParams).then(response => {
        this.overviewList = response.data || []
        this.loading = false
      }).catch(() => {
        this.loading = false
      })
    },
    getTrend() {
      this.trendLoading = true
      getEnergyTrend({ startTime: this.queryParams.startTime, endTime: this.queryParams.endTime, granularity: 'DAY' }).then(response => {
        this.renderTrend(response.data || [])
        this.trendLoading = false
      }).catch(() => {
        this.trendLoading = false
      })
    },
    getRank() {
      this.rankLoading = true
      getEnergyRank({ startTime: this.queryParams.startTime, endTime: this.queryParams.endTime, limit: 10 }).then(response => {
        this.rankList = response.data || []
        this.rankLoading = false
      }).catch(() => {
        this.rankLoading = false
      })
    },
    getCategory() {
      this.catLoading = true
      getEnergyCategory({ startTime: this.queryParams.startTime, endTime: this.queryParams.endTime }).then(response => {
        this.categoryList = response.data || []
        this.catLoading = false
      }).catch(() => {
        this.catLoading = false
      })
    },
    getCategoryTrend() {
      getEnergyCategoryTrend({ startTime: this.queryParams.startTime, endTime: this.queryParams.endTime }).then(response => {
        this.renderCatChart(response.data || {})
      }).catch(() => {})
    },
    renderCatChart(map) {
      if (!this.catChart) {
        return
      }
      const names = Object.keys(map || {})
      const dates = []
      names.forEach(n => {
        const byDate = map[n] || {}
        Object.keys(byDate).forEach(d => {
          if (dates.indexOf(d) === -1) {
            dates.push(d)
          }
        })
      })
      dates.sort()
      const series = names.map(n => ({
        name: n,
        type: 'line',
        smooth: true,
        data: dates.map(d => (map[n][d] != null ? map[n][d] : 0))
      }))
      this.catChart.setOption({
        tooltip: { trigger: 'axis' },
        legend: { data: names },
        grid: { left: '3%', right: '4%', bottom: '3%', containLabel: true },
        xAxis: { type: 'category', data: dates },
        yAxis: { type: 'value', name: 'kWh' },
        series
      }, true)
    },
    renderTrend(list) {
      if (!this.trendChart) {
        return
      }
      const option = {
        tooltip: { trigger: 'axis' },
        legend: { data: [this.$t('hospital.kwh'), this.$t('hospital.avgPower')] },
        grid: { left: '3%', right: '4%', bottom: '3%', containLabel: true },
        xAxis: { type: 'category', data: list.map(i => i.label) },
        yAxis: [{ type: 'value', name: 'kWh' }, { type: 'value', name: 'kW' }],
        series: [
          { name: this.$t('hospital.kwh'), type: 'bar', data: list.map(i => i.kwh), itemStyle: { color: '#2b8cbe' }},
          { name: this.$t('hospital.avgPower'), type: 'line', yAxisIndex: 1, data: list.map(i => i.avgPower), itemStyle: { color: '#e08a3c' }}
        ]
      }
      this.trendChart.setOption(option, true)
    }
  }
}
</script>

<style scoped>
.summary-card {
  text-align: center;
  margin-bottom: 4px;
}
.summary-label {
  font-size: 13px;
  color: #909399;
  margin-bottom: 6px;
}
.summary-value {
  font-size: 22px;
  font-weight: 600;
  color: #1f6fb2;
}
</style>

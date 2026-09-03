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
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">{{ $t('button.search') }}</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">{{ $t('button.reset') }}</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['hospital:energy:export']"
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExportEfficiency"
        >{{ $t('hospital.exportEfficiency') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['hospital:energy:export']"
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExportSuggestions"
        >{{ $t('hospital.exportSuggestions') }}</el-button>
      </el-col>
      <right-toolbar :show-search.sync="showSearch" @queryTable="handleQuery" />
    </el-row>

    <el-card class="mb8" shadow="never">
      <div slot="header"><span>{{ $t('hospital.efficiencyTitle') }}</span></div>
      <el-table v-loading="effLoading" :data="efficiencyList">
        <el-table-column :label="$t('hospital.deviceName')" align="center" prop="deviceName" />
        <el-table-column :label="$t('hospital.deviceCode')" align="center" prop="deviceCode" width="130" />
        <el-table-column :label="$t('hospital.deviceType')" align="center" prop="deviceType" width="90" />
        <el-table-column :label="$t('hospital.kwh')" align="center" prop="kwh" width="120" />
        <el-table-column :label="$t('hospital.avgPower')" align="center" prop="avgPower" width="120" />
        <el-table-column :label="$t('hospital.standbyRatio')" align="center" prop="standbyRatio" width="120">
          <template slot-scope="scope">
            <span v-if="scope.row.standbyRatio != null">{{ scope.row.standbyRatio }}%</span>
            <span v-else>-</span>
          </template>
        </el-table-column>
        <el-table-column :label="$t('hospital.effScore')" align="center" prop="score" width="110">
          <template slot-scope="scope">
            <el-progress v-if="scope.row.score != null" :percentage="Number(scope.row.score)" :color="scoreColor(scope.row.score)" :show-text="true" :stroke-width="12" />
            <span v-else>-</span>
          </template>
        </el-table-column>
        <el-table-column :label="$t('hospital.effLevel')" align="center" prop="level" width="100">
          <template slot-scope="scope">
            <el-tag :type="levelTagType(scope.row.level)" size="mini">{{ scope.row.level }}</el-tag>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-card shadow="never">
      <div slot="header"><span>{{ $t('hospital.suggestionTitle') }}</span></div>
      <div v-loading="sugLoading">
        <el-alert
          v-for="(item, idx) in suggestionList"
          :key="idx"
          :title="'【' + item.typeName + '】' + (item.deviceName ? item.deviceName + '：' : '') + item.content"
          :description="item.action"
          :type="suggestionAlertType(item.type)"
          :closable="false"
          show-icon
          class="sug-alert"
        />
        <el-empty v-if="suggestionList.length === 0" :description="$t('hospital.noSuggestion')" />
      </div>
    </el-card>
  </div>
</template>

<script>
import { getEfficiency, getSuggestions } from '@/api/hospital/energy'

export default {
  name: 'HospitalEfficiency',
  data() {
    return {
      effLoading: true,
      sugLoading: false,
      showSearch: true,
      efficiencyList: [],
      suggestionList: [],
      dateRange: [],
      queryParams: {
        startTime: undefined,
        endTime: undefined
      }
    }
  },
  created() {
    const end = new Date()
    const start = new Date(end.getTime() - 7 * 24 * 3600 * 1000)
    this.dateRange = [this.formatDate(start), this.formatDate(end)]
    this.handleQuery()
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
      this.getEfficiency()
      this.getSuggestions()
    },
    resetQuery() {
      this.resetForm('queryForm')
      this.dateRange = []
      this.handleQuery()
    },
    getEfficiency() {
      this.effLoading = true
      getEfficiency(this.queryParams).then(response => {
        this.efficiencyList = response.data || []
        this.effLoading = false
      }).catch(() => {
        this.effLoading = false
      })
    },
    getSuggestions() {
      this.sugLoading = true
      getSuggestions(this.queryParams).then(response => {
        this.suggestionList = response.data || []
        this.sugLoading = false
      }).catch(() => {
        this.sugLoading = false
      })
    },
    scoreColor(score) {
      const s = Number(score)
      if (s >= 80) return '#67c23a'
      if (s >= 60) return '#e6a23c'
      return '#f56c6c'
    },
    levelTagType(level) {
      const map = { '优': 'success', '良': 'warning', '待改进': 'danger', '未评估': 'info' }
      return map[level] || 'info'
    },
    suggestionAlertType(type) {
      const map = { 'STANDBY': 'warning', 'PEAK': 'info', 'ABNORMAL': 'error' }
      return map[type] || 'info'
    },
    handleExportEfficiency() {
      this.download('hospital/energy/efficiency/export', {
        ...this.queryParams
      }, `hospital_efficiency_${new Date().getTime()}.xlsx`)
    },
    handleExportSuggestions() {
      this.download('hospital/energy/suggestions/export', {
        ...this.queryParams
      }, `hospital_suggestions_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>

<style scoped>
.sug-alert {
  margin-bottom: 10px;
}
</style>

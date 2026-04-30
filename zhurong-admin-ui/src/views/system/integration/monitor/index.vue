<template>
  <div class="app-container">
    <el-row :gutter="20" class="mb16">
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="card-panel">
            <div class="card-panel-icon-wrapper icon-success">
              <i class="el-icon-success card-panel-icon"></i>
            </div>
            <div class="card-panel-description">
              <div class="card-panel-text">成功次数</div>
              <div class="card-panel-num">{{ statistics.success || 0 }}</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="card-panel">
            <div class="card-panel-icon-wrapper icon-fail">
              <i class="el-icon-error card-panel-icon"></i>
            </div>
            <div class="card-panel-description">
              <div class="card-panel-text">失败次数</div>
              <div class="card-panel-num">{{ statistics.fail || 0 }}</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="card-panel">
            <div class="card-panel-icon-wrapper icon-total">
              <i class="el-icon-s-order card-panel-icon"></i>
            </div>
            <div class="card-panel-description">
              <div class="card-panel-text">总执行次数</div>
              <div class="card-panel-num">{{ statistics.total || 0 }}</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="card-panel">
            <div class="card-panel-icon-wrapper icon-rate">
              <i class="el-icon-data-line card-panel-icon"></i>
            </div>
            <div class="card-panel-description">
              <div class="card-panel-text">成功率</div>
              <div class="card-panel-num">{{ statistics.successRate ? statistics.successRate.toFixed(2) + '%' : '0%' }}</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="90px">
      <el-form-item label="任务编码" prop="taskCode">
        <el-input
          v-model="queryParams.taskCode"
          :placeholder="$t('common.pleaseInput')"
          clearable
          style="width: 180px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="执行状态" prop="status">
        <el-select v-model="queryParams.status" :placeholder="$t('common.pleaseSelect')" clearable style="width: 120px">
          <el-option label="执行中" :value="1" />
          <el-option label="成功" :value="2" />
          <el-option label="失败" :value="3" />
          <el-option label="部分成功" :value="4" />
        </el-select>
      </el-form-item>
      <el-form-item label="执行时间">
        <el-date-picker
          v-model="dateRange"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">{{ $t('common.search') }}</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">{{ $t('common.reset') }}</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-refresh"
          size="mini"
          @click="handleRefresh"
        >刷新</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="executionList">
      <el-table-column label="执行ID" align="center" prop="id" width="80" />
      <el-table-column label="任务编码" align="center" prop="taskCode" width="150" />
      <el-table-column label="开始时间" align="center" prop="startTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="结束时间" align="center" prop="endTime" width="180">
        <template slot-scope="scope">
          <span>{{ scope.row.endTime ? parseTime(scope.row.endTime) : '-' }}</span>
        </template>
      </el-table-column>
      <el-table-column label="耗时" align="center" width="100">
        <template slot-scope="scope">
          <span v-if="scope.row.endTime && scope.row.startTime">
            {{ (new Date(scope.row.endTime) - new Date(scope.row.startTime)) / 1000 }}s
          </span>
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column label="总条数" align="center" prop="totalCount" width="80" />
      <el-table-column label="成功" align="center" prop="successCount" width="80">
        <template slot-scope="scope">
          <span class="success-color">{{ scope.row.successCount || 0 }}</span>
        </template>
      </el-table-column>
      <el-table-column label="失败" align="center" prop="failCount" width="80">
        <template slot-scope="scope">
          <span class="fail-color">{{ scope.row.failCount || 0 }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('common.status')" align="center" prop="status" width="100">
        <template slot-scope="scope">
          <el-tag :type="getStatusType(scope.row.status)">
            {{ getStatusLabel(scope.row.status) }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="错误信息" align="center" prop="errorMessage" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <span v-if="scope.row.errorMessage" class="error-message">{{ scope.row.errorMessage }}</span>
          <span v-else>-</span>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
  </div>
</template>

<script>
import { listSyncExecution, getRecentSyncExecutions, getSyncStatistics } from '@/api/system/integration/sync'

export default {
  name: 'SyncMonitor',
  data() {
    return {
      loading: true,
      showSearch: true,
      total: 0,
      executionList: [],
      statistics: {},
      dateRange: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        taskCode: undefined,
        status: undefined
      }
    }
  },
  created() {
    this.getList()
    this.getStatistics()
  },
  methods: {
    getList() {
      this.loading = true
      getRecentSyncExecutions(100).then(response => {
        this.executionList = response.data || []
        this.total = this.executionList.length
        this.loading = false
      })
    },
    getStatistics() {
      getSyncStatistics().then(response => {
        this.statistics = response.data || {}
      })
    },
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    resetQuery() {
      this.dateRange = []
      this.queryParams = {
        pageNum: 1,
        pageSize: 10,
        taskCode: undefined,
        status: undefined
      }
      this.getList()
    },
    handleRefresh() {
      this.getList()
      this.getStatistics()
      this.$message.success('刷新成功')
    },
    getStatusType(status) {
      const statusMap = {
        1: 'warning',
        2: 'success',
        3: 'danger',
        4: 'info'
      }
      return statusMap[status] || 'info'
    },
    getStatusLabel(status) {
      const statusMap = {
        1: '执行中',
        2: '成功',
        3: '失败',
        4: '部分成功'
      }
      return statusMap[status] || '未知'
    }
  }
}
</script>

<style scoped>
.card-panel {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 10px;
}

.card-panel-icon-wrapper {
  float: left;
  width: 60px;
  height: 60px;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.icon-success {
  background: #f0f9ff;
}

.icon-success .card-panel-icon {
  color: #67c23a;
  font-size: 30px;
}

.icon-fail {
  background: #fef0f0;
}

.icon-fail .card-panel-icon {
  color: #f56c6c;
  font-size: 30px;
}

.icon-total {
  background: #fdf6ec;
}

.icon-total .card-panel-icon {
  color: #e6a23c;
  font-size: 30px;
}

.icon-rate {
  background: #ecf5ff;
}

.icon-rate .card-panel-icon {
  color: #409eff;
  font-size: 30px;
}

.card-panel-description {
  float: right;
  text-align: right;
}

.card-panel-text {
  line-height: 18px;
  color: rgba(0, 0, 0, 0.45);
  font-size: 14px;
  margin-bottom: 12px;
}

.card-panel-num {
  font-size: 24px;
  font-weight: bold;
}

.success-color {
  color: #67c23a;
}

.fail-color {
  color: #f56c6c;
}

.error-message {
  color: #f56c6c;
  font-size: 12px;
}
</style>

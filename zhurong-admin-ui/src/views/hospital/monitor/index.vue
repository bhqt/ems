<template>
  <div class="app-container hospital-monitor">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="90px">
      <el-form-item :label="$t('hospital.deviceType')" prop="deviceType">
        <el-select v-model="queryParams.deviceType" :placeholder="$t('common.pleaseSelect')" clearable>
          <el-option
            v-for="dict in deviceTypeOptions"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('hospital.deviceName')" prop="keyword">
        <el-input
          v-model="queryParams.keyword"
          :placeholder="$t('common.pleaseInput')"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('hospital.onlineStatus')" prop="online">
        <el-select v-model="queryParams.online" :placeholder="$t('common.pleaseSelect')" clearable>
          <el-option :label="$t('hospital.online')" :value="true" />
          <el-option :label="$t('hospital.offline')" :value="false" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">{{ $t('button.search') }}</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">{{ $t('button.reset') }}</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-refresh"
          size="mini"
          @click="getList"
          v-hasPermi="['hospital:monitor:list']"
        >{{ $t('button.refresh') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-checkbox v-model="autoRefresh" @change="toggleAutoRefresh">{{ $t('hospital.autoRefresh') }}(30s)</el-checkbox>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="monitorList">
      <el-table-column :label="$t('hospital.deviceName')" align="center" prop="deviceName" />
      <el-table-column :label="$t('hospital.deviceCode')" align="center" prop="deviceCode" />
      <el-table-column :label="$t('hospital.deviceType')" align="center" prop="deviceType" width="90">
        <template slot-scope="scope">
          <span>{{ deviceTypeLabel(scope.row.deviceType) }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('hospital.onlineStatus')" align="center" width="100">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.lastTs == null" type="info" size="mini">{{ $t('hospital.noData') }}</el-tag>
          <el-tag v-else :type="scope.row.online ? 'success' : 'danger'" size="mini">
            {{ scope.row.online ? $t('hospital.online') : $t('hospital.offline') }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column :label="$t('hospital.runStatus')" align="center" width="100">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.runStatus === '1'" type="success" size="mini">{{ $t('hospital.running') }}</el-tag>
          <el-tag v-else-if="scope.row.runStatus === '0'" type="warning" size="mini">{{ $t('hospital.standby') }}</el-tag>
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('hospital.lastPower')" align="center" prop="power" width="130" />
      <el-table-column :label="$t('hospital.lastElectricity')" align="center" prop="electricity" width="140" />
      <el-table-column :label="$t('hospital.openAlarm')" align="center" prop="openAlarmCount" width="110">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.openAlarmCount > 0" type="danger" size="mini">{{ scope.row.openAlarmCount }}</el-tag>
          <span v-else>0</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('hospital.lastUpdate')" align="center" prop="lastTs" width="170">
        <template slot-scope="scope">
          <span>{{ scope.row.lastTs ? parseTime(scope.row.lastTs) : '-' }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('table.operate')" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleViewTrend(scope.row)"
            v-hasPermi="['hospital:monitor:list']"
          >{{ $t('hospital.viewTrend') }}</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 趋势数据对话框 -->
    <el-dialog :title="trendTitle" :visible.sync="trendOpen" width="760px" append-to-body>
      <el-form :inline="true" size="small" class="data-query-form">
        <el-form-item :label="$t('hospital.metricCode')">
          <el-select v-model="trendQuery.metricCode" :placeholder="$t('common.pleaseSelect')" clearable style="width:180px" @change="getTrendList">
            <el-option label="power" value="power" />
            <el-option label="electricity" value="electricity" />
            <el-option label="current" value="current" />
            <el-option label="voltage" value="voltage" />
            <el-option label="run_status" value="run_status" />
          </el-select>
        </el-form-item>
      </el-form>
      <el-table v-loading="trendLoading" :data="trendList" height="400">
        <el-table-column :label="$t('hospital.metricCode')" align="center" prop="metricCode" />
        <el-table-column :label="$t('hospital.metricName')" align="center" prop="metricName" />
        <el-table-column :label="$t('hospital.metricValue')" align="center" prop="metricValue" width="120" />
        <el-table-column :label="$t('hospital.unit')" align="center" prop="unit" width="80" />
        <el-table-column :label="$t('hospital.ts')" align="center" prop="ts" width="170">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.ts) }}</span>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>

<script>
import { getMonitorOverview, getMonitorTrend } from "@/api/hospital/monitor";

export default {
  name: "HospitalMonitor",
  data() {
    return {
      loading: true,
      showSearch: true,
      autoRefresh: false,
      refreshTimer: null,
      monitorList: [],
      trendOpen: false,
      trendTitle: "",
      trendLoading: false,
      trendList: [],
      trendQuery: {
        deviceId: undefined,
        metricCode: 'power',
        limit: 100
      },
      deviceTypeOptions: [
        { value: 'CT', label: 'CT' },
        { value: 'MRI', label: 'MRI' },
        { value: 'DR', label: 'DR' },
        { value: 'US', label: '超声' },
        { value: 'LAB', label: '检验流水线' },
        { value: 'DSA', label: 'DSA' },
        { value: 'OTHER', label: '其他' }
      ],
      queryParams: {
        deviceType: undefined,
        keyword: undefined,
        online: undefined
      }
    };
  },
  created() {
    this.getList();
  },
  beforeDestroy() {
    this.clearAutoRefresh();
  },
  methods: {
    getList() {
      this.loading = true;
      getMonitorOverview(this.queryParams).then(response => {
        let list = response.data || [];
        if (this.queryParams.online !== undefined && this.queryParams.online !== null && this.queryParams.online !== '') {
          const want = this.queryParams.online === true || this.queryParams.online === 'true';
          list = list.filter(item => item.lastTs != null && item.online === want);
        }
        this.monitorList = list;
        this.loading = false;
      }).catch(() => {
        this.loading = false;
      });
    },
    deviceTypeLabel(type) {
      const item = this.deviceTypeOptions.find(o => o.value === type);
      return item ? item.label : type;
    },
    handleQuery() {
      this.getList();
    },
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    toggleAutoRefresh() {
      this.clearAutoRefresh();
      if (this.autoRefresh) {
        this.refreshTimer = setInterval(() => {
          this.getList();
        }, 30000);
      }
    },
    clearAutoRefresh() {
      if (this.refreshTimer) {
        clearInterval(this.refreshTimer);
        this.refreshTimer = null;
      }
    },
    handleViewTrend(row) {
      this.trendQuery.deviceId = row.deviceId;
      this.trendQuery.metricCode = 'power';
      this.trendTitle = this.$t('hospital.trendData') + ' - ' + row.deviceName;
      this.trendOpen = true;
      this.getTrendList();
    },
    getTrendList() {
      this.trendLoading = true;
      getMonitorTrend(this.trendQuery.deviceId, this.trendQuery.metricCode, this.trendQuery.limit).then(response => {
        this.trendList = response.data || [];
        this.trendLoading = false;
      }).catch(() => {
        this.trendLoading = false;
      });
    }
  }
};
</script>

<style scoped>
.hospital-monitor {
  background: linear-gradient(180deg, #f2f7fb 0%, #ffffff 240px);
}
</style>

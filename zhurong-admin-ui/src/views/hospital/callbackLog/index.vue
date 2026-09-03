<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="90px">
      <el-form-item :label="$t('hospital.requestId')" prop="requestId">
        <el-input
          v-model="queryParams.requestId"
          :placeholder="$t('common.pleaseInput')"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('hospital.sourceIp')" prop="sourceIp">
        <el-input
          v-model="queryParams.sourceIp"
          :placeholder="$t('common.pleaseInput')"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('hospital.callbackStatus')" prop="status">
        <el-select v-model="queryParams.status" :placeholder="$t('common.pleaseSelect')" clearable>
          <el-option
            v-for="dict in statusOptions"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">{{ $t('button.search') }}</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">{{ $t('button.reset') }}</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="logList">
      <el-table-column :label="$t('hospital.requestId')" align="center" prop="requestId" width="180" show-overflow-tooltip />
      <el-table-column :label="$t('hospital.sourceIp')" align="center" prop="sourceIp" width="130" />
      <el-table-column :label="$t('hospital.deviceCount')" align="center" prop="deviceCount" width="90" />
      <el-table-column :label="$t('hospital.pointCount')" align="center" prop="pointCount" width="90" />
      <el-table-column :label="$t('hospital.callbackStatus')" align="center" prop="status" width="100">
        <template slot-scope="scope">
          <el-tag :type="statusTagType(scope.row.status)" size="mini">{{ statusLabel(scope.row.status) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column :label="$t('hospital.errorMsg')" align="center" prop="errorMsg" show-overflow-tooltip>
        <template slot-scope="scope">
          <span v-if="scope.row.errorMsg">{{ scope.row.errorMsg }}</span>
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('hospital.costTime')" align="center" prop="costTime" width="100">
        <template slot-scope="scope">
          <span>{{ scope.row.costTime }} ms</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('hospital.receiveTime')" align="center" prop="receiveTime" width="170">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.receiveTime) }}</span>
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
import { listCallbackLog } from "@/api/hospital/callbackLog";

export default {
  name: "HospitalCallbackLog",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示查询条件
      showSearch: true,
      // 总条数
      total: 0,
      // 日志表格数据
      logList: [],
      // 状态选项
      statusOptions: [
        { value: 'success', label: this.$t('hospital.callbackSuccess') },
        { value: 'auth_fail', label: this.$t('hospital.callbackAuthFail') },
        { value: 'parse_fail', label: this.$t('hospital.callbackParseFail') },
        { value: 'fail', label: this.$t('hospital.callbackFail') }
      ],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        requestId: undefined,
        sourceIp: undefined,
        status: undefined
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询回调日志列表 */
    getList() {
      this.loading = true;
      listCallbackLog(this.queryParams).then(response => {
        this.logList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    statusLabel(status) {
      const item = this.statusOptions.find(o => o.value === status);
      return item ? item.label : status;
    },
    statusTagType(status) {
      const map = { success: 'success', auth_fail: 'warning', parse_fail: 'warning', fail: 'danger' };
      return map[status] || 'info';
    },
    /** 查询按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    }
  }
};
</script>

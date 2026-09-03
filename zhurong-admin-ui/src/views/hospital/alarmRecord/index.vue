<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="90px">
      <el-form-item :label="$t('hospital.alarmType')" prop="alarmType">
        <el-select v-model="queryParams.alarmType" :placeholder="$t('common.pleaseSelect')" clearable>
          <el-option :label="$t('hospital.overload')" value="OVERLOAD" />
          <el-option :label="$t('hospital.offlineAlarm')" value="OFFLINE" />
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('hospital.handleStatus')" prop="status">
        <el-select v-model="queryParams.status" :placeholder="$t('common.pleaseSelect')" clearable>
          <el-option :label="$t('hospital.pending')" value="0" />
          <el-option :label="$t('hospital.closed')" value="1" />
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('hospital.level')" prop="level">
        <el-select v-model="queryParams.level" :placeholder="$t('common.pleaseSelect')" clearable>
          <el-option :label="$t('hospital.levelNormal')" value="0" />
          <el-option :label="$t('hospital.levelSerious')" value="1" />
          <el-option :label="$t('hospital.levelUrgent')" value="2" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">{{ $t('button.search') }}</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">{{ $t('button.reset') }}</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="recordList">
      <el-table-column :label="$t('hospital.deviceName')" align="center" prop="deviceName" width="140" />
      <el-table-column :label="$t('hospital.alarmType')" align="center" prop="alarmType" width="100">
        <template slot-scope="scope">
          <el-tag :type="scope.row.alarmType === 'OFFLINE' ? 'warning' : 'danger'" size="mini">
            {{ scope.row.alarmType === 'OFFLINE' ? $t('hospital.offlineAlarm') : $t('hospital.overload') }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column :label="$t('hospital.level')" align="center" prop="level" width="90">
        <template slot-scope="scope">
          <el-tag :type="levelTagType(scope.row.level)" size="mini">{{ levelLabel(scope.row.level) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column :label="$t('hospital.alarmContent')" align="center" prop="content" :show-overflow-tooltip="true" />
      <el-table-column :label="$t('hospital.alarmVal')" align="center" prop="alarmVal" width="110" />
      <el-table-column :label="$t('hospital.handleStatus')" align="center" prop="status" width="100">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status === '0' ? 'danger' : 'success'" size="mini">
            {{ scope.row.status === '0' ? $t('hospital.pending') : $t('hospital.closed') }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column :label="$t('hospital.startTime')" align="center" prop="startTime" width="170">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('table.operate')" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            v-if="scope.row.status === '0'"
            size="mini"
            type="text"
            icon="el-icon-check"
            @click="handleRecord(scope.row)"
            v-hasPermi="['hospital:alarmRecord:handle']"
          >{{ $t('hospital.handle') }}</el-button>
          <span v-else>{{ scope.row.handleBy || '-' }}</span>
        </template>
      </el-table-column>
    </el-table>

    <!-- 处理报警对话框 -->
    <el-dialog :title="$t('hospital.handle')" :visible.sync="open" width="480px" append-to-body>
      <el-form ref="form" :model="form" label-width="100px">
        <el-form-item :label="$t('hospital.alarmContent')">
          <span>{{ form.content }}</span>
        </el-form-item>
        <el-form-item :label="$t('hospital.handleRemark')" prop="handleRemark">
          <el-input v-model="form.handleRemark" type="textarea" :placeholder="$t('common.pleaseInput')" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitHandle">{{ $t('button.submit') }}</el-button>
        <el-button @click="open = false">{{ $t('button.cancel') }}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listAlarmRecord, handleAlarmRecord } from "@/api/hospital/alarm";

export default {
  name: "HospitalAlarmRecord",
  data() {
    return {
      loading: true,
      showSearch: true,
      recordList: [],
      open: false,
      form: {},
      queryParams: {
        deviceId: undefined,
        alarmType: undefined,
        status: '0',
        level: undefined
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {
      this.loading = true;
      listAlarmRecord(this.queryParams).then(response => {
        this.recordList = response.data || [];
        this.loading = false;
      }).catch(() => {
        this.loading = false;
      });
    },
    levelLabel(level) {
      const map = { '0': this.$t('hospital.levelNormal'), '1': this.$t('hospital.levelSerious'), '2': this.$t('hospital.levelUrgent') };
      return map[String(level)] || level;
    },
    levelTagType(level) {
      const map = { '0': 'info', '1': 'warning', '2': 'danger' };
      return map[String(level)] || 'info';
    },
    handleQuery() {
      this.getList();
    },
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    handleRecord(row) {
      this.form = { id: row.id, content: row.content, handleRemark: undefined };
      this.open = true;
    },
    submitHandle() {
      handleAlarmRecord(this.form.id, this.form.handleRemark).then(response => {
        this.$modal.msgSuccess(this.$t('message.editSuccess'));
        this.open = false;
        this.getList();
      });
    }
  }
};
</script>

<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="90px">
      <el-form-item :label="$t('hospital.alarmType')" prop="alarmType">
        <el-select v-model="queryParams.alarmType" :placeholder="$t('common.pleaseSelect')" clearable>
          <el-option :label="$t('hospital.overload')" value="OVERLOAD" />
          <el-option :label="$t('hospital.offlineAlarm')" value="OFFLINE" />
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('hospital.handleStatus')" prop="handleStatus">
        <el-select v-model="queryParams.handleStatus" :placeholder="$t('common.pleaseSelect')" clearable>
          <el-option :label="$t('hospital.hsNone')" value="0" />
          <el-option :label="$t('hospital.hsConfirmed')" value="1" />
          <el-option :label="$t('hospital.hsProcessing')" value="2" />
          <el-option :label="$t('hospital.hsDone')" value="3" />
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

    <el-table v-loading="loading" :data="recordList">
      <el-table-column :label="$t('hospital.deviceName')" align="center" prop="deviceName" width="140" />
      <el-table-column :label="$t('hospital.alarmType')" align="center" prop="alarmType" width="100">
        <template slot-scope="scope">
          <el-tag :type="scope.row.alarmType === 'OFFLINE' ? 'warning' : 'danger'" size="mini">
            {{ scope.row.alarmType === 'OFFLINE' ? $t('hospital.offlineAlarm') : $t('hospital.overload') }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column :label="$t('hospital.level')" align="center" width="120">
        <template slot-scope="scope">
          <el-tag :type="levelTagType(scope.row.escalateLevel != null ? scope.row.escalateLevel : scope.row.level)" size="mini">
            {{ levelLabel(scope.row.escalateLevel != null ? scope.row.escalateLevel : scope.row.level) }}
            <span v-if="scope.row.escalateCount > 0">({{ $t('hospital.escalated') }}×{{ scope.row.escalateCount }})</span>
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column :label="$t('hospital.alarmContent')" align="center" prop="content" :show-overflow-tooltip="true" />
      <el-table-column :label="$t('hospital.alarmVal')" align="center" prop="alarmVal" width="110" />
      <el-table-column :label="$t('hospital.handleStatus')" align="center" width="100">
        <template slot-scope="scope">
          <el-tag :type="handleStatusTagType(scope.row.handleStatus)" size="mini">{{ handleStatusLabel(scope.row.handleStatus) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column :label="$t('hospital.startTime')" align="center" prop="startTime" width="160">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('table.operate')" align="center" class-name="small-padding fixed-width" width="210">
        <template slot-scope="scope">
          <template v-if="String(scope.row.handleStatus) !== '3'">
            <el-button
              v-if="String(scope.row.handleStatus) === '0'"
              size="mini"
              type="text"
              icon="el-icon-check"
              @click="handleAction(scope.row, 'confirm')"
              v-hasPermi="['hospital:alarmRecord:handle']"
            >{{ $t('hospital.confirm') }}</el-button>
            <el-button
              v-if="String(scope.row.handleStatus) === '1'"
              size="mini"
              type="text"
              icon="el-icon-setting"
              @click="handleAction(scope.row, 'process')"
              v-hasPermi="['hospital:alarmRecord:handle']"
            >{{ $t('hospital.process') }}</el-button>
            <el-button
              v-if="String(scope.row.handleStatus) === '2'"
              size="mini"
              type="text"
              icon="el-icon-finished"
              @click="handleAction(scope.row, 'done')"
              v-hasPermi="['hospital:alarmRecord:handle']"
            >{{ $t('hospital.handle') }}</el-button>
          </template>
          <span v-else>{{ scope.row.handleBy || '-' }}</span>
        </template>
      </el-table-column>
    </el-table>

    <!-- 报警处理对话框 -->
    <el-dialog :title="actionTitle" :visible.sync="open" width="480px" append-to-body>
      <el-form ref="form" :model="form" label-width="100px">
        <el-form-item :label="$t('hospital.alarmContent')">
          <span>{{ form.content }}</span>
        </el-form-item>
        <el-form-item :label="$t('hospital.handleRemark')" prop="handleRemark">
          <el-input v-model="form.handleRemark" type="textarea" :placeholder="$t('common.pleaseInput')" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitAction">{{ $t('button.submit') }}</el-button>
        <el-button @click="open = false">{{ $t('button.cancel') }}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listAlarmRecord, actionAlarmRecord } from "@/api/hospital/alarm";

export default {
  name: "HospitalAlarmRecord",
  data() {
    return {
      loading: true,
      showSearch: true,
      recordList: [],
      open: false,
      form: {},
      currentAction: 'done',
      queryParams: {
        alarmType: undefined,
        handleStatus: undefined,
        level: undefined
      }
    };
  },
  computed: {
    actionTitle() {
      return this.$t('hospital.action_' + this.currentAction)
    }
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
    handleStatusLabel(s) {
      s = String(s)
      const map = {
        '0': this.$t('hospital.hsNone'),
        '1': this.$t('hospital.hsConfirmed'),
        '2': this.$t('hospital.hsProcessing'),
        '3': this.$t('hospital.hsDone')
      };
      return map[s] || '-';
    },
    handleStatusTagType(s) {
      const map = { '0': 'danger', '1': 'warning', '2': 'primary', '3': 'success' };
      return map[String(s)] || 'info';
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
    handleAction(row, action) {
      this.form = { id: row.id, content: row.content, handleRemark: undefined };
      this.currentAction = action;
      this.open = true;
    },
    submitAction() {
      actionAlarmRecord(this.form.id, this.currentAction, this.form.handleRemark).then(response => {
        this.$modal.msgSuccess(this.$t('message.editSuccess'));
        this.open = false;
        this.getList();
      });
    }
  }
};
</script>

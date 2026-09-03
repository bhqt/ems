<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="90px">
      <el-form-item :label="$t('hospital.ruleName')" prop="ruleName">
        <el-input
          v-model="queryParams.ruleName"
          :placeholder="$t('common.pleaseInput')"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('hospital.ruleType')" prop="ruleType">
        <el-select v-model="queryParams.ruleType" :placeholder="$t('common.pleaseSelect')" clearable>
          <el-option :label="$t('hospital.thresholdRule')" value="THRESHOLD" />
          <el-option :label="$t('hospital.offlineRule')" value="OFFLINE" />
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('common.status')" prop="status">
        <el-select v-model="queryParams.status" :placeholder="$t('common.pleaseSelect')" clearable>
          <el-option :label="$t('hospital.enabled')" value="0" />
          <el-option :label="$t('hospital.disabled')" value="1" />
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
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['hospital:alarmRule:add']"
        >{{ $t('button.add') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['hospital:alarmRule:edit']"
        >{{ $t('button.edit') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['hospital:alarmRule:remove']"
        >{{ $t('button.delete') }}</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="ruleList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column :label="$t('hospital.ruleName')" align="center" prop="ruleName" />
      <el-table-column :label="$t('hospital.ruleType')" align="center" prop="ruleType" width="120">
        <template slot-scope="scope">
          <el-tag :type="scope.row.ruleType === 'OFFLINE' ? 'warning' : 'primary'" size="mini">
            {{ scope.row.ruleType === 'OFFLINE' ? $t('hospital.offlineRule') : $t('hospital.thresholdRule') }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column :label="$t('hospital.deviceName')" align="center" prop="deviceName" width="140">
        <template slot-scope="scope">
          <span>{{ scope.row.deviceName || $t('hospital.allDevices') }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('hospital.metricCode')" align="center" prop="metricCode" width="110" />
      <el-table-column :label="$t('hospital.thresholdValue')" align="center" width="150">
        <template slot-scope="scope">
          <span v-if="scope.row.ruleType === 'THRESHOLD'">{{ scope.row.condition }} {{ scope.row.thresholdValue }}</span>
          <span v-else>{{ scope.row.offlineTimeoutMin }}min</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('hospital.level')" align="center" prop="level" width="90">
        <template slot-scope="scope">
          <el-tag :type="levelTagType(scope.row.level)" size="mini">{{ levelLabel(scope.row.level) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column :label="$t('hospital.escalation')" align="center" width="150">
        <template slot-scope="scope">
          <span v-if="scope.row.escalateMin != null">
            {{ scope.row.escalateMin }}{{ $t('hospital.min') }}→{{ levelLabel(scope.row.escalateLevel) }}
          </span>
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('common.status')" align="center" prop="status" width="90">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status === '0' ? 'success' : 'info'" size="mini">
            {{ scope.row.status === '0' ? $t('hospital.enabled') : $t('hospital.disabled') }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column :label="$t('table.operate')" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['hospital:alarmRule:edit']"
          >{{ $t('button.edit') }}</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['hospital:alarmRule:remove']"
          >{{ $t('button.delete') }}</el-button>
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

    <!-- 添加或修改报警规则对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="580px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="130px">
        <el-form-item :label="$t('hospital.ruleName')" prop="ruleName">
          <el-input v-model="form.ruleName" :placeholder="$t('common.pleaseInput')" />
        </el-form-item>
        <el-form-item :label="$t('hospital.ruleType')" prop="ruleType">
          <el-select v-model="form.ruleType" :placeholder="$t('common.pleaseSelect')" style="width:100%">
            <el-option :label="$t('hospital.thresholdRule')" value="THRESHOLD" />
            <el-option :label="$t('hospital.offlineRule')" value="OFFLINE" />
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('hospital.deviceName')" prop="deviceId">
          <el-select v-model="form.deviceId" :placeholder="$t('hospital.allDevices')" clearable style="width:100%">
            <el-option
              v-for="dev in deviceOptions"
              :key="dev.id"
              :label="dev.deviceName + '(' + dev.deviceCode + ')'"
              :value="dev.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item v-if="form.ruleType === 'THRESHOLD'" :label="$t('hospital.metricCode')" prop="metricCode">
          <el-select v-model="form.metricCode" :placeholder="$t('common.pleaseSelect')" style="width:100%" filterable allow-create>
            <el-option label="power" value="power" />
            <el-option label="electricity" value="electricity" />
            <el-option label="current" value="current" />
            <el-option label="voltage" value="voltage" />
            <el-option label="temperature" value="temperature" />
          </el-select>
        </el-form-item>
        <el-form-item v-if="form.ruleType === 'THRESHOLD'" :label="$t('hospital.condition')" prop="condition">
          <el-select v-model="form.condition" :placeholder="$t('common.pleaseSelect')" style="width:100%">
            <el-option :label="$t('hospital.conditionG')" value="G" />
            <el-option :label="$t('hospital.conditionGE')" value="GE" />
            <el-option :label="$t('hospital.conditionL')" value="L" />
            <el-option :label="$t('hospital.conditionLE')" value="LE" />
            <el-option :label="$t('hospital.conditionE')" value="E" />
          </el-select>
        </el-form-item>
        <el-form-item v-if="form.ruleType === 'THRESHOLD'" :label="$t('hospital.thresholdValue')" prop="thresholdValue">
          <el-input-number v-model="form.thresholdValue" :precision="4" style="width:100%" />
        </el-form-item>
        <el-form-item v-if="form.ruleType === 'OFFLINE'" :label="$t('hospital.offlineTimeoutMin')" prop="offlineTimeoutMin">
          <el-input-number v-model="form.offlineTimeoutMin" :min="1" :precision="0" style="width:100%" />
        </el-form-item>
        <el-form-item :label="$t('hospital.level')" prop="level">
          <el-select v-model="form.level" :placeholder="$t('common.pleaseSelect')" style="width:100%">
            <el-option :label="$t('hospital.levelNormal')" value="0" />
            <el-option :label="$t('hospital.levelSerious')" value="1" />
            <el-option :label="$t('hospital.levelUrgent')" value="2" />
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('hospital.notifyEmail')" prop="notifyEmail">
          <el-input v-model="form.notifyEmail" :placeholder="$t('hospital.notifyEmailPlaceholder')" />
        </el-form-item>
        <el-form-item :label="$t('hospital.escalateMin')" prop="escalateMin">
          <el-input-number v-model="form.escalateMin" :min="0" :precision="0" style="width:100%" placeholder="0=不升级" />
        </el-form-item>
        <el-form-item :label="$t('hospital.escalateLevel')" prop="escalateLevel">
          <el-select v-model="form.escalateLevel" :placeholder="$t('common.pleaseSelect')" style="width:100%">
            <el-option :label="$t('hospital.levelNormal')" value="0" />
            <el-option :label="$t('hospital.levelSerious')" value="1" />
            <el-option :label="$t('hospital.levelUrgent')" value="2" />
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('common.status')" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio label="0">{{ $t('hospital.enabled') }}</el-radio>
            <el-radio label="1">{{ $t('hospital.disabled') }}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item :label="$t('common.remark')" prop="remark">
          <el-input v-model="form.remark" type="textarea" :placeholder="$t('common.pleaseInput')" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">{{ $t('button.submit') }}</el-button>
        <el-button @click="cancel">{{ $t('button.cancel') }}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listAlarmRule, getAlarmRule, delAlarmRule, addAlarmRule, updateAlarmRule } from "@/api/hospital/alarm";
import { listDevice } from "@/api/hospital/device";

export default {
  name: "HospitalAlarmRule",
  data() {
    return {
      loading: true,
      ids: [],
      single: true,
      multiple: true,
      showSearch: true,
      total: 0,
      ruleList: [],
      deviceOptions: [],
      title: "",
      open: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        ruleName: undefined,
        ruleType: undefined,
        status: undefined
      },
      form: {},
      rules: {
        ruleName: [
          { required: true, message: this.$t('hospital.ruleNameRequired'), trigger: "blur" }
        ],
        ruleType: [
          { required: true, message: this.$t('hospital.ruleTypeRequired'), trigger: "change" }
        ]
      }
    };
  },
  created() {
    this.getList();
    listDevice({ pageNum: 1, pageSize: 100 }).then(response => {
      this.deviceOptions = response.rows || [];
    });
  },
  methods: {
    getList() {
      this.loading = true;
      listAlarmRule(this.queryParams).then(response => {
        this.ruleList = response.rows;
        this.total = response.total;
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
    cancel() {
      this.open = false;
      this.reset();
    },
    reset() {
      this.form = {
        id: undefined,
        ruleName: undefined,
        deviceId: undefined,
        deviceType: undefined,
        metricCode: undefined,
        ruleType: 'THRESHOLD',
        condition: 'G',
        thresholdValue: undefined,
        offlineTimeoutMin: 30,
        level: '0',
        status: '0',
        notifyEmail: undefined,
        escalateMin: 0,
        escalateLevel: '0',
        remark: undefined
      };
      this.resetForm("form");
    },
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = this.$t('hospital.addRule');
    },
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getAlarmRule(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = this.$t('hospital.editRule');
      });
    },
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateAlarmRule(this.form).then(response => {
              this.$modal.msgSuccess(this.$t('message.editSuccess'));
              this.open = false;
              this.getList();
            });
          } else {
            addAlarmRule(this.form).then(response => {
              this.$modal.msgSuccess(this.$t('message.addSuccess'));
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm(this.$t('confirm.delete')).then(function() {
        return delAlarmRule(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess(this.$t('message.deleteSuccess'));
      }).catch(() => {});
    }
  }
};
</script>

<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item :label="$t('hospital.deviceName')" prop="deviceName">
        <el-input
          v-model="queryParams.deviceName"
          :placeholder="$t('common.pleaseInput')"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('hospital.workloadDate')" prop="statDate">
        <el-date-picker
          v-model="queryParams.statDate"
          type="date"
          :placeholder="$t('common.pleaseSelect')"
          value-format="yyyy-MM-dd"
          clearable
          style="width: 150px"
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
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['hospital:workload:add']"
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
          v-hasPermi="['hospital:workload:edit']"
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
          v-hasPermi="['hospital:workload:remove']"
        >{{ $t('button.delete') }}</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="workloadList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column :label="$t('hospital.deviceName')" align="center" prop="deviceName" />
      <el-table-column :label="$t('hospital.workloadCount')" align="center" prop="workloadCount" />
      <el-table-column :label="$t('hospital.workloadDate')" align="center" prop="statDate" width="120" />
      <el-table-column :label="$t('common.remark')" align="center" prop="remark" :show-overflow-tooltip="true" />
      <el-table-column :label="$t('table.operate')" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['hospital:workload:edit']"
          >{{ $t('button.edit') }}</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['hospital:workload:remove']"
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

    <!-- 添加或修改工作量对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="520px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-form-item :label="$t('hospital.device')" prop="deviceId">
          <el-select v-model="form.deviceId" :placeholder="$t('common.pleaseSelect')" filterable style="width:100%">
            <el-option
              v-for="item in deviceOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('hospital.workloadCount')" prop="workloadCount">
          <el-input-number v-model="form.workloadCount" :min="0" :precision="0" style="width:100%" />
        </el-form-item>
        <el-form-item :label="$t('hospital.workloadDate')" prop="statDate">
          <el-date-picker
            v-model="form.statDate"
            type="date"
            :placeholder="$t('common.pleaseSelect')"
            value-format="yyyy-MM-dd"
            style="width:100%"
          />
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
import { listWorkload, getWorkload, delWorkload, addWorkload, updateWorkload } from "@/api/hospital/workload";
import { listDevice } from "@/api/hospital/device";

export default {
  name: "HospitalWorkload",
  data() {
    return {
      loading: true,
      ids: [],
      single: true,
      multiple: true,
      showSearch: true,
      total: 0,
      workloadList: [],
      deviceOptions: [],
      title: "",
      open: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        deviceName: undefined,
        statDate: undefined
      },
      form: {},
      rules: {
        deviceId: [
          { required: true, message: this.$t('hospital.deviceRequired'), trigger: "change" }
        ],
        statDate: [
          { required: true, message: this.$t('hospital.workloadDateRequired'), trigger: "change" }
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getDevices();
  },
  methods: {
    getDevices() {
      listDevice({ pageNum: 1, pageSize: 200 }).then(response => {
        this.deviceOptions = response.rows.map(d => ({
          value: d.id,
          label: d.deviceName
        }));
      });
    },
    getList() {
      this.loading = true;
      listWorkload(this.queryParams).then(response => {
        this.workloadList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    cancel() {
      this.open = false;
      this.reset();
    },
    reset() {
      this.form = {
        id: undefined,
        deviceId: undefined,
        workloadCount: 0,
        statDate: undefined,
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
      this.title = this.$t('hospital.addWorkload');
    },
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getWorkload(id).then(response => {
        this.form = response.data;
        this.form.statDate = this.form.statDate ? this.form.statDate.substring(0, 10) : undefined;
        this.open = true;
        this.title = this.$t('hospital.editWorkload');
      });
    },
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateWorkload(this.form).then(response => {
              this.$modal.msgSuccess(this.$t('message.editSuccess'));
              this.open = false;
              this.getList();
            });
          } else {
            addWorkload(this.form).then(response => {
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
        return delWorkload(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess(this.$t('message.deleteSuccess'));
      }).catch(() => {});
    }
  }
};
</script>

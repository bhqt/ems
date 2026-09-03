<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="90px">
      <el-form-item :label="$t('hospital.metricCode')" prop="metricCode">
        <el-input
          v-model="queryParams.metricCode"
          :placeholder="$t('common.pleaseInput')"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('hospital.metricName')" prop="metricName">
        <el-input
          v-model="queryParams.metricName"
          :placeholder="$t('common.pleaseInput')"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('common.status')" prop="status">
        <el-select v-model="queryParams.status" :placeholder="$t('common.pleaseSelect')" clearable>
          <el-option
            v-for="dict in dict.type.sys_normal_disable"
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

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['hospital:metric:add']"
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
          v-hasPermi="['hospital:metric:edit']"
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
          v-hasPermi="['hospital:metric:remove']"
        >{{ $t('button.delete') }}</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="metricList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column :label="$t('hospital.metricCode')" align="center" prop="metricCode" />
      <el-table-column :label="$t('hospital.metricName')" align="center" prop="metricName" />
      <el-table-column :label="$t('hospital.unit')" align="center" prop="unit" width="100" />
      <el-table-column :label="$t('hospital.dataType')" align="center" prop="dataType" width="100">
        <template slot-scope="scope">
          <span>{{ dataTypeLabel(scope.row.dataType) }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('hospital.highFreq')" align="center" prop="highFreq" width="90">
        <template slot-scope="scope">
          <el-tag :type="scope.row.highFreq === '1' ? 'warning' : 'info'" size="mini">
            {{ scope.row.highFreq === '1' ? $t('hospital.yes') : $t('hospital.no') }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column :label="$t('common.status')" align="center" prop="status" width="90">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column :label="$t('table.operate')" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['hospital:metric:edit']"
          >{{ $t('button.edit') }}</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['hospital:metric:remove']"
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

    <!-- 添加或修改指标对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="520px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item :label="$t('hospital.metricCode')" prop="metricCode">
          <el-input v-model="form.metricCode" :placeholder="$t('common.pleaseInput')" />
        </el-form-item>
        <el-form-item :label="$t('hospital.metricName')" prop="metricName">
          <el-input v-model="form.metricName" :placeholder="$t('common.pleaseInput')" />
        </el-form-item>
        <el-form-item :label="$t('hospital.unit')" prop="unit">
          <el-input v-model="form.unit" :placeholder="$t('common.pleaseInput')" />
        </el-form-item>
        <el-form-item :label="$t('hospital.dataType')" prop="dataType">
          <el-select v-model="form.dataType" :placeholder="$t('common.pleaseSelect')" style="width:100%">
            <el-option
              v-for="dict in dataTypeOptions"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('hospital.highFreq')" prop="highFreq">
          <el-radio-group v-model="form.highFreq">
            <el-radio :label="'1'">{{ $t('hospital.yes') }}</el-radio>
            <el-radio :label="'0'">{{ $t('hospital.no') }}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item :label="$t('common.status')" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in dict.type.sys_normal_disable"
              :key="dict.value"
              :label="dict.value"
            >{{dict.label}}</el-radio>
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
import { listMetric, getMetric, delMetric, addMetric, updateMetric } from "@/api/hospital/metric";

export default {
  name: "HospitalMetric",
  dicts: ['sys_normal_disable'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示查询条件
      showSearch: true,
      // 总条数
      total: 0,
      // 指标表格数据
      metricList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 数据类型选项
      dataTypeOptions: [
        { value: 'number', label: this.$t('hospital.dataTypeNumber') },
        { value: 'status', label: this.$t('hospital.dataTypeStatus') },
        { value: 'string', label: this.$t('hospital.dataTypeString') }
      ],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        metricCode: undefined,
        metricName: undefined,
        status: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        metricCode: [
          { required: true, message: this.$t('hospital.metricCodeRequired'), trigger: "blur" }
        ],
        metricName: [
          { required: true, message: this.$t('hospital.metricNameRequired'), trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询指标列表 */
    getList() {
      this.loading = true;
      listMetric(this.queryParams).then(response => {
        this.metricList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    dataTypeLabel(type) {
      const item = this.dataTypeOptions.find(o => o.value === type);
      return item ? item.label : type;
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: undefined,
        metricCode: undefined,
        metricName: undefined,
        unit: undefined,
        dataType: "number",
        highFreq: "0",
        status: "0",
        remark: undefined
      };
      this.resetForm("form");
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
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = this.$t('hospital.addMetric');
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getMetric(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = this.$t('hospital.editMetric');
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateMetric(this.form).then(response => {
              this.$modal.msgSuccess(this.$t('message.editSuccess'));
              this.open = false;
              this.getList();
            });
          } else {
            addMetric(this.form).then(response => {
              this.$modal.msgSuccess(this.$t('message.addSuccess'));
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm(this.$t('confirm.delete')).then(function() {
        return delMetric(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess(this.$t('message.deleteSuccess'));
      }).catch(() => {});
    }
  }
};
</script>

<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item :label="$t('managementSystemModule.eventName')" prop="eventName">
        <el-input
          v-model="queryParams.eventName"
          :placeholder="$t('managementSystemModule.eventName')"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('managementSystemModule.touchTime')" prop="touchTime">
        <el-date-picker clearable
                        v-model="queryParams.touchTime"
                        type="date"
                        value-format="yyyy-MM-dd"
                        :placeholder="$t('managementSystemModule.touchTime')">
        </el-date-picker>
      </el-form-item>
<!--      <el-form-item label="处理结果" prop="handleResult">-->
<!--        <el-input-->
<!--          v-model="queryParams.handleResult"-->
<!--          :placeholder="$t('common.pleaseInput')"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
      <el-form-item :label="$t('managementSystemModule.handlePerson')" prop="handlePerson">
        <el-input
          v-model="queryParams.handlePerson"
          :placeholder="$t('managementSystemModule.handlePerson')"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">{{ $t('common.search') }}</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">{{ $t('common.reset') }}</el-button>
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
          v-hasPermi="['system:process:add']"
        >{{ $t('common.add') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:process:edit']"
        >{{ $t('common.edit') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:process:remove']"
        >{{ $t('common.delete') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:process:export']"
        >{{ $t('common.export') }}</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="processList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column type="index" width="55" align="center" />
<!--      <el-table-column label="主键" align="center" prop="processId" v-if="true"/>-->
      <el-table-column :label="$t('managementSystemModule.eventName')" align="center" prop="eventName" />
      <el-table-column :label="$t('managementSystemModule.eventType')" align="center" prop="eventType" />
      <el-table-column :label="$t('managementSystemModule.touchTime')" align="center" prop="touchTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.touchTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('managementSystemModule.handleResult')" align="center" prop="handleResult" />
      <el-table-column :label="$t('managementSystemModule.handlePerson')" align="center" prop="handlePerson" />
      <el-table-column :label="$t('common.remark')" align="center" prop="remark" />
      <el-table-column :label="$t('common.operation')" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:process:edit']"
          >{{ $t('common.edit') }}</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:process:remove']"
          >{{ $t('common.delete') }}</el-button>
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

    <!-- 添加或修改流程管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item :label="$t('managementSystemModule.eventName')" prop="eventName">
          <el-input v-model="form.eventName" :placeholder="$t('common.pleaseInput')" />
        </el-form-item>
        <el-form-item :label="$t('managementSystemModule.eventType')" prop="eventType">
          <el-input v-model="form.eventType" :placeholder="$t('common.pleaseInput')" />
        </el-form-item>
        <el-form-item :label="$t('managementSystemModule.touchTime')" prop="touchTime">
          <el-date-picker clearable
                          v-model="form.touchTime"
                          type="datetime"
                          value-format="yyyy-MM-dd HH:mm:ss"
                          :placeholder="$t('common.pleaseSelect')">
          </el-date-picker>
        </el-form-item>
        <el-form-item :label="$t('managementSystemModule.handleResult')" prop="handleResult">
          <el-input v-model="form.handleResult" :placeholder="$t('common.pleaseInput')" />
        </el-form-item>
        <el-form-item :label="$t('managementSystemModule.handlePerson')" prop="handlePerson">
          <el-input v-model="form.handlePerson" :placeholder="$t('common.pleaseInput')" />
        </el-form-item>
        <el-form-item :label="$t('common.remark')" prop="remark">
          <el-input v-model="form.remark" :placeholder="$t('common.pleaseInput')" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button :loading="buttonLoading" type="primary" @click="submitForm">{{ $t('button.submit') }}</el-button>
        <el-button @click="cancel">{{ $t('button.cancel') }}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listProcess, getProcess, delProcess, addProcess, updateProcess } from "@/api/system/process";

export default {
  name: "Process",
  data() {
    return {
      // 按钮loading
      buttonLoading: false,
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
      // 流程管理表格数据
      processList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        eventName: undefined,
        eventType: undefined,
        touchTime: undefined,
        handleResult: undefined,
        handlePerson: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        processId: [
          { required: true, message: "主键不能为空", trigger: "blur" }
        ],
        eventName: [
          { required: true, message: "事件名称不能为空", trigger: "blur" }
        ],
        eventType: [
          { required: true, message: "事件类型不能为空", trigger: "change" }
        ],
        touchTime: [
          { required: true, message: "触发时间不能为空", trigger: "blur" }
        ],
        /* handleResult: [
          { required: true, message: "处理结果不能为空", trigger: "blur" }
        ], */
        /* handlePerson: [
          { required: true, message: "处理人不能为空", trigger: "blur" }
        ], */
        // remark: [
        //   { required: true, message: "备注不能为空", trigger: "blur" }
        // ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询流程管理列表 */
    getList() {
      this.loading = true;
      listProcess(this.queryParams).then(response => {
        this.processList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        processId: undefined,
        eventName: undefined,
        eventType: undefined,
        touchTime: undefined,
        handleResult: undefined,
        handlePerson: undefined,
        remark: undefined,
        createBy: undefined,
        createTime: undefined,
        updateBy: undefined,
        updateTime: undefined
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
      this.ids = selection.map(item => item.processId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加流程管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const processId = row.processId || this.ids
      getProcess(processId).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改流程管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.processId != null) {
            updateProcess(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addProcess(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const processIds = row.processId || this.ids;
      this.$modal.confirm('是否确认删除？').then(() => {
        this.loading = true;
        return delProcess(processIds);
      }).then(() => {
        this.loading = false;
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      }).finally(() => {
        this.loading = false;
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/process/export', {
        ...this.queryParams
      }, `process_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>


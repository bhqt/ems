<template>
  <div class="app-container">
    <el-form :model="queryParams" :inline="true" v-show="showSearch" label-width="90px">
      <el-form-item label="指令编号" prop="commandCode">
        <el-input v-model="queryParams.commandCode" placeholder="请输入指令编号" clearable size="small" @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="指令类型" prop="commandType">
        <el-select v-model="queryParams.commandType" placeholder="请选择" clearable size="small">
          <el-option label="开机" :value="1" />
          <el-option label="关机" :value="2" />
          <el-option label="调节" :value="3" />
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择" clearable size="small">
          <el-option label="待执行" :value="1" />
          <el-option label="执行中" :value="2" />
          <el-option label="已执行" :value="3" />
          <el-option label="失败" :value="4" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete">删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="commandList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="指令ID" prop="commandId" width="80" />
      <el-table-column label="指令编号" prop="commandCode" width="150" />
      <el-table-column label="指令类型" prop="commandType" width="100">
        <template slot-scope="scope">
          <el-tag :type="getCommandTypeTag(scope.row.commandType)">{{ getCommandTypeLabel(scope.row.commandType) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="目标设备" prop="targetDevice" :show-overflow-tooltip="true" />
      <el-table-column label="指令参数" prop="parameter" :show-overflow-tooltip="true" />
      <el-table-column label="计划执行时间" prop="scheduledTime" width="160" />
      <el-table-column label="实际执行时间" prop="actualTime" width="160" />
      <el-table-column label="状态" prop="status" width="100">
        <template slot-scope="scope">
          <el-tag :type="getStatusTag(scope.row.status)">{{ getStatusLabel(scope.row.status) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="150">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-caret-right" @click="handleExecute(scope.row)" v-if="scope.row.status === 1">执行</el-button>
          <el-button size="mini" type="text" icon="el-icon-view" @click="handleView(scope.row)">详情</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />

    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="指令编号" prop="commandCode">
              <el-input v-model="form.commandCode" placeholder="请输入指令编号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="指令类型" prop="commandType">
              <el-select v-model="form.commandType" placeholder="请选择" style="width: 100%">
                <el-option label="开机" :value="1" />
                <el-option label="关机" :value="2" />
                <el-option label="调节" :value="3" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="目标设备" prop="targetDevice">
              <el-input v-model="form.targetDevice" placeholder="请输入目标设备" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="计划时间" prop="scheduledTime">
              <el-date-picker v-model="form.scheduledTime" type="datetime" placeholder="选择计划执行时间" style="width: 100%" value-format="yyyy-MM-dd HH:mm:ss" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="指令参数" prop="parameter">
          <el-input v-model="form.parameter" type="textarea" :rows="2" placeholder="请输入指令参数" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listDispatchCommand, getDispatchCommand, addDispatchCommand, updateDispatchCommand, delDispatchCommand, executeCommand } from "@/api/dispatch/dispatchCommand";

export default {
  name: "DispatchCommand",
  data() {
    return {
      loading: true, ids: [], single: true, multiple: true, showSearch: true,
      total: 0, commandList: [], title: "", open: false,
      queryParams: { pageNum: 1, pageSize: 10 },
      form: {},
      rules: {
        commandCode: [{ required: true, message: "指令编号不能为空", trigger: "blur" }],
        commandType: [{ required: true, message: "指令类型不能为空", trigger: "change" }]
      }
    };
  },
  created() { this.getList(); },
  methods: {
    getList() {
      this.loading = true;
      listDispatchCommand(this.queryParams).then(res => { this.commandList = res.data || []; this.loading = false; });
    },
    handleQuery() { this.queryParams.pageNum = 1; this.getList(); },
    resetQuery() { this.resetForm("queryForm"); this.handleQuery(); },
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.commandId);
      this.single = selection.length !== 1; this.multiple = !selection.length;
    },
    handleAdd() { this.reset(); this.open = true; this.title = "添加调度指令"; },
    handleUpdate(row) {
      this.reset(); const commandId = row.commandId || this.ids;
      getDispatchCommand(commandId).then(res => { this.form = res.data; this.open = true; this.title = "修改调度指令"; });
    },
    handleView(row) {
      getDispatchCommand(row.commandId).then(res => { this.form = res.data; this.open = true; this.title = "调度指令详情"; });
    },
    handleExecute(row) {
      this.$modal.confirm('确认执行该指令？').then(() => {
        executeCommand(row.commandId).then(() => { this.$modal.msgSuccess("执行成功"); this.getList(); });
      });
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.commandId) {
            updateDispatchCommand(this.form).then(() => { this.$modal.msgSuccess("修改成功"); this.open = false; this.getList(); });
          } else {
            addDispatchCommand(this.form).then(() => { this.$modal.msgSuccess("新增成功"); this.open = false; this.getList(); });
          }
        }
      });
    },
    handleDelete(row) {
      const commandIds = row.commandId || this.ids;
      this.$modal.confirm('是否确认删除调度指令？').then(() => {
        delDispatchCommand(commandIds).then(() => { this.$modal.msgSuccess("删除成功"); this.getList(); });
      });
    },
    cancel() { this.open = false; this.reset(); },
    reset() {
      this.form = { commandId: null, commandCode: null, commandType: 1, targetDevice: null, parameter: null, scheduledTime: null };
      this.resetForm("form");
    },
    getCommandTypeLabel(val) { return { 1: "开机", 2: "关机", 3: "调节" }[val] || val; },
    getCommandTypeTag(val) { return { 1: "success", 2: "danger", 3: "warning" }[val] || "info"; },
    getStatusLabel(val) { return { 1: "待执行", 2: "执行中", 3: "已执行", 4: "失败" }[val] || val; },
    getStatusTag(val) { return { 1: "info", 2: "warning", 3: "success", 4: "danger" }[val] || "info"; }
  }
};
</script>

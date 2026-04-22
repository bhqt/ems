<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="90px">
      <el-form-item label="计划编号" prop="planCode">
        <el-input v-model="queryParams.planCode" placeholder="请输入计划编号" clearable size="small" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="计划类型" prop="planType">
        <el-select v-model="queryParams.planType" placeholder="请选择计划类型" clearable size="small">
          <el-option label="短期" :value="1" />
          <el-option label="中期" :value="2" />
          <el-option label="长期" :value="3" />
        </el-select>
      </el-form-item>
      <el-form-item label="计划状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable size="small">
          <el-option label="草稿" :value="1" />
          <el-option label="审批中" :value="2" />
          <el-option label="已批准" :value="3" />
          <el-option label="执行中" :value="4" />
          <el-option label="已完成" :value="5" />
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

    <el-table v-loading="loading" :data="energyPlanList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="计划ID" align="center" prop="planId" width="80" />
      <el-table-column label="计划编号" align="center" prop="planCode" width="150" />
      <el-table-column label="计划名称" align="center" prop="planName" :show-overflow-tooltip="true" />
      <el-table-column label="计划类型" align="center" prop="planType" width="100">
        <template slot-scope="scope">
          <el-tag :type="getPlanTypeTag(scope.row.planType)">{{ getPlanTypeLabel(scope.row.planType) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="开始时间" align="center" prop="startDate" width="160">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startDate) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="结束时间" align="center" prop="endDate" width="160">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endDate) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status" width="100">
        <template slot-scope="scope">
          <el-tag :type="getStatusTag(scope.row.status)">{{ getStatusLabel(scope.row.status) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="160">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="180">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-view" @click="handleView(scope.row)">详情</el-button>
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />

    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="计划编号" prop="planCode">
              <el-input v-model="form.planCode" placeholder="请输入计划编号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="计划名称" prop="planName">
              <el-input v-model="form.planName" placeholder="请输入计划名称" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="计划类型" prop="planType">
              <el-select v-model="form.planType" placeholder="请选择计划类型" style="width: 100%">
                <el-option label="短期" :value="1" />
                <el-option label="中期" :value="2" />
                <el-option label="长期" :value="3" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态" prop="status">
              <el-select v-model="form.status" placeholder="请选择状态" style="width: 100%">
                <el-option label="草稿" :value="1" />
                <el-option label="审批中" :value="2" />
                <el-option label="已批准" :value="3" />
                <el-option label="执行中" :value="4" />
                <el-option label="已完成" :value="5" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="开始时间" prop="startDate">
              <el-date-picker v-model="form.startDate" type="datetime" placeholder="选择开始时间" style="width: 100%" value-format="yyyy-MM-dd HH:mm:ss" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="结束时间" prop="endDate">
              <el-date-picker v-model="form.endDate" type="datetime" placeholder="选择结束时间" style="width: 100%" value-format="yyyy-MM-dd HH:mm:ss" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="计划内容" prop="content">
          <el-input v-model="form.content" type="textarea" :rows="4" placeholder="请输入计划内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog title="计划详情" :visible.sync="detailOpen" width="700px" append-to-body>
      <el-descriptions :column="2" border v-if="detailData">
        <el-descriptions-item label="计划编号">{{ detailData.planCode }}</el-descriptions-item>
        <el-descriptions-item label="计划名称">{{ detailData.planName }}</el-descriptions-item>
        <el-descriptions-item label="计划类型">{{ getPlanTypeLabel(detailData.planType) }}</el-descriptions-item>
        <el-descriptions-item label="状态">
          <el-tag :type="getStatusTag(detailData.status)">{{ getStatusLabel(detailData.status) }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="开始时间">{{ parseTime(detailData.startDate) }}</el-descriptions-item>
        <el-descriptions-item label="结束时间">{{ parseTime(detailData.endDate) }}</el-descriptions-item>
        <el-descriptions-item label="创建时间">{{ parseTime(detailData.createTime) }}</el-descriptions-item>
        <el-descriptions-item label="更新时间">{{ parseTime(detailData.updateTime) }}</el-descriptions-item>
        <el-descriptions-item label="计划内容" :span="2">{{ detailData.content }}</el-descriptions-item>
      </el-descriptions>
      <div slot="footer" class="dialog-footer">
        <el-button @click="detailOpen = false">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listEnergyPlan, getEnergyPlan, addEnergyPlan, updateEnergyPlan, delEnergyPlan } from "@/api/dispatch/energyPlan";

export default {
  name: "EnergyPlan",
  data() {
    return {
      loading: true,
      ids: [],
      single: true,
      multiple: true,
      showSearch: true,
      total: 0,
      energyPlanList: [],
      title: "",
      open: false,
      detailOpen: false,
      detailData: null,
      queryParams: { pageNum: 1, pageSize: 10 },
      form: {},
      rules: {
        planCode: [{ required: true, message: "计划编号不能为空", trigger: "blur" }],
        planName: [{ required: true, message: "计划名称不能为空", trigger: "blur" }],
        planType: [{ required: true, message: "计划类型不能为空", trigger: "change" }]
      }
    };
  },
  created() { this.getList(); },
  methods: {
    getList() {
      this.loading = true;
      listEnergyPlan(this.queryParams).then(response => {
        this.energyPlanList = response.data || [];
        this.loading = false;
      });
    },
    handleQuery() { this.queryParams.pageNum = 1; this.getList(); },
    resetQuery() { this.resetForm("queryForm"); this.handleQuery(); },
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.planId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    handleAdd() { this.reset(); this.open = true; this.title = "添加能源计划"; },
    handleUpdate(row) {
      this.reset();
      const planId = row.planId || this.ids;
      getEnergyPlan(planId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改能源计划";
      });
    },
    handleView(row) {
      getEnergyPlan(row.planId).then(response => {
        this.detailData = response.data;
        this.detailOpen = true;
      });
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.planId) {
            updateEnergyPlan(this.form).then(() => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addEnergyPlan(this.form).then(() => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    handleDelete(row) {
      const planIds = row.planId || this.ids;
      this.$modal.confirm('是否确认删除能源计划编号为"' + planIds + '"的数据项？').then(() => {
        return delEnergyPlan(planIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    cancel() { this.open = false; this.reset(); },
    reset() {
      this.form = { planId: null, planCode: null, planName: null, planType: 1, status: 1, startDate: null, endDate: null, content: null };
      this.resetForm("form");
    },
    getPlanTypeLabel(type) {
      const map = { 1: "短期", 2: "中期", 3: "长期" };
      return map[type] || type;
    },
    getPlanTypeTag(type) {
      const map = { 1: "success", 2: "warning", 3: "info" };
      return map[type] || "info";
    },
    getStatusLabel(status) {
      const map = { 1: "草稿", 2: "审批中", 3: "已批准", 4: "执行中", 5: "已完成" };
      return map[status] || status;
    },
    getStatusTag(status) {
      const map = { 1: "info", 2: "warning", 3: "success", 4: "primary", 5: "success" };
      return map[status] || "info";
    }
  }
};
</script>

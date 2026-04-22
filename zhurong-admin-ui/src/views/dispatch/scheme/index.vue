<template>
  <div class="app-container">
    <el-form :model="queryParams" :inline="true" v-show="showSearch" label-width="90px">
      <el-form-item label="方案编号" prop="schemeCode">
        <el-input v-model="queryParams.schemeCode" placeholder="请输入方案编号" clearable size="small" @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="优化类型" prop="optimizationType">
        <el-select v-model="queryParams.optimizationType" placeholder="请选择" clearable size="small">
          <el-option label="成本最小化" :value="1" />
          <el-option label="排放最小化" :value="2" />
          <el-option label="多目标优化" :value="3" />
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择" clearable size="small">
          <el-option label="设计中" :value="1" />
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

    <el-table v-loading="loading" :data="schemeList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="方案ID" prop="schemeId" width="80" />
      <el-table-column label="方案编号" prop="schemeCode" width="150" />
      <el-table-column label="方案名称" prop="schemeName" :show-overflow-tooltip="true" />
      <el-table-column label="优化类型" prop="optimizationType" width="120">
        <template slot-scope="scope">
          <el-tag :type="getTypeTag(scope.row.optimizationType)">{{ getTypeLabel(scope.row.optimizationType) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="使用算法" prop="algorithm" width="120" />
      <el-table-column label="目标函数值" prop="objectiveValue" width="120" />
      <el-table-column label="状态" prop="status" width="100">
        <template slot-scope="scope">
          <el-tag :type="getStatusTag(scope.row.status)">{{ getStatusLabel(scope.row.status) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" prop="createTime" width="160">
        <template slot-scope="scope"><span>{{ parseTime(scope.row.createTime) }}</span></template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="200">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-view" @click="handleView(scope.row)">详情</el-button>
          <el-button size="mini" type="text" icon="el-icon-caret-right" @click="handleExecute(scope.row)" v-if="scope.row.status === 3">执行</el-button>
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
            <el-form-item label="方案编号" prop="schemeCode">
              <el-input v-model="form.schemeCode" placeholder="请输入方案编号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="方案名称" prop="schemeName">
              <el-input v-model="form.schemeName" placeholder="请输入方案名称" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="优化类型" prop="optimizationType">
              <el-select v-model="form.optimizationType" placeholder="请选择" style="width: 100%">
                <el-option label="成本最小化" :value="1" />
                <el-option label="排放最小化" :value="2" />
                <el-option label="多目标优化" :value="3" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="使用算法" prop="algorithm">
              <el-select v-model="form.algorithm" placeholder="请选择" style="width: 100%">
                <el-option label="线性规划(LP)" value="LP" />
                <el-option label="混合整数规划(MILP)" value="MILP" />
                <el-option label="遗传算法(GA)" value="GA" />
                <el-option label="粒子群优化(PSO)" value="PSO" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="约束条件" prop="constraintsText">
          <el-input v-model="form.constraintsText" type="textarea" :rows="3" placeholder="请输入约束条件" />
        </el-form-item>
        <el-form-item label="优化结果" prop="resultText">
          <el-input v-model="form.resultText" type="textarea" :rows="3" placeholder="请输入优化结果" />
        </el-form-item>
        <el-row>
          <el-col :span="12">
            <el-form-item label="目标函数值" prop="objectiveValue">
              <el-input-number v-model="form.objectiveValue" :precision="2" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态" prop="status">
              <el-select v-model="form.status" placeholder="请选择" style="width: 100%">
                <el-option label="设计中" :value="1" />
                <el-option label="审批中" :value="2" />
                <el-option label="已批准" :value="3" />
                <el-option label="执行中" :value="4" />
                <el-option label="已完成" :value="5" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog title="方案详情" :visible.sync="detailOpen" width="700px" append-to-body>
      <el-descriptions :column="2" border v-if="detailData">
        <el-descriptions-item label="方案编号">{{ detailData.schemeCode }}</el-descriptions-item>
        <el-descriptions-item label="方案名称">{{ detailData.schemeName }}</el-descriptions-item>
        <el-descriptions-item label="优化类型">{{ getTypeLabel(detailData.optimizationType) }}</el-descriptions-item>
        <el-descriptions-item label="使用算法">{{ detailData.algorithm }}</el-descriptions-item>
        <el-descriptions-item label="目标函数值">{{ detailData.objectiveValue }}</el-descriptions-item>
        <el-descriptions-item label="状态">
          <el-tag :type="getStatusTag(detailData.status)">{{ getStatusLabel(detailData.status) }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="约束条件" :span="2">{{ detailData.constraintsText }}</el-descriptions-item>
        <el-descriptions-item label="优化结果" :span="2">{{ detailData.resultText }}</el-descriptions-item>
      </el-descriptions>
      <div slot="footer"><el-button @click="detailOpen = false">关 闭</el-button></div>
    </el-dialog>
  </div>
</template>

<script>
import { listOptimizationScheme, getOptimizationScheme, addOptimizationScheme, updateOptimizationScheme, delOptimizationScheme, executeOptimization } from "@/api/dispatch/optimizationScheme";

export default {
  name: "OptimizationScheme",
  data() {
    return {
      loading: true, ids: [], single: true, multiple: true, showSearch: true,
      total: 0, schemeList: [], title: "", open: false, detailOpen: false, detailData: null,
      queryParams: { pageNum: 1, pageSize: 10 },
      form: {},
      rules: {
        schemeCode: [{ required: true, message: "方案编号不能为空", trigger: "blur" }],
        schemeName: [{ required: true, message: "方案名称不能为空", trigger: "blur" }]
      }
    };
  },
  created() { this.getList(); },
  methods: {
    getList() {
      this.loading = true;
      listOptimizationScheme(this.queryParams).then(res => { this.schemeList = res.data || []; this.loading = false; });
    },
    handleQuery() { this.queryParams.pageNum = 1; this.getList(); },
    resetQuery() { this.resetForm("queryForm"); this.handleQuery(); },
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.schemeId);
      this.single = selection.length !== 1; this.multiple = !selection.length;
    },
    handleAdd() { this.reset(); this.open = true; this.title = "添加优化方案"; },
    handleUpdate(row) {
      this.reset(); const schemeId = row.schemeId || this.ids;
      getOptimizationScheme(schemeId).then(res => { this.form = res.data; this.open = true; this.title = "修改优化方案"; });
    },
    handleView(row) {
      getOptimizationScheme(row.schemeId).then(res => { this.detailData = res.data; this.detailOpen = true; });
    },
    handleExecute(row) {
      this.$modal.confirm('确认执行该优化方案？').then(() => {
        executeOptimization(row.schemeId).then(() => { this.$modal.msgSuccess("执行成功"); this.getList(); });
      });
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.schemeId) {
            updateOptimizationScheme(this.form).then(() => { this.$modal.msgSuccess("修改成功"); this.open = false; this.getList(); });
          } else {
            addOptimizationScheme(this.form).then(() => { this.$modal.msgSuccess("新增成功"); this.open = false; this.getList(); });
          }
        }
      });
    },
    handleDelete(row) {
      const schemeIds = row.schemeId || this.ids;
      this.$modal.confirm('是否确认删除优化方案？').then(() => {
        delOptimizationScheme(schemeIds).then(() => { this.$modal.msgSuccess("删除成功"); this.getList(); });
      });
    },
    cancel() { this.open = false; this.reset(); },
    reset() {
      this.form = { schemeId: null, schemeCode: null, schemeName: null, optimizationType: 1, algorithm: "LP", status: 1 };
      this.resetForm("form");
    },
    getTypeLabel(val) { return { 1: "成本最小化", 2: "排放最小化", 3: "多目标优化" }[val] || val; },
    getTypeTag(val) { return { 1: "success", 2: "warning", 3: "info" }[val] || "info"; },
    getStatusLabel(val) { return { 1: "设计中", 2: "审批中", 3: "已批准", 4: "执行中", 5: "已完成" }[val] || val; },
    getStatusTag(val) { return { 1: "info", 2: "warning", 3: "success", 4: "primary", 5: "success" }[val] || "info"; }
  }
};
</script>

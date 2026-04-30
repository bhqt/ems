<template>
  <div class="app-container">
    <el-form :model="queryParams" :inline="true" v-show="showSearch" label-width="90px">
      <el-form-item :label="$t('dispatchModule.scheme.schemeCode')" prop="schemeCode">
        <el-input v-model="queryParams.schemeCode" :placeholder="$t('dispatchModule.scheme.placeholder.inputSchemeCode')" clearable size="small" @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item :label="$t('dispatchModule.scheme.optimizationType')" prop="optimizationType">
        <el-select v-model="queryParams.optimizationType" :placeholder="$t('dispatchModule.scheme.placeholder.selectOptimizationType')" clearable size="small">
          <el-option :label="$t('dispatchModule.scheme.costMinimization')" :value="1" />
          <el-option :label="$t('dispatchModule.scheme.emissionMinimization')" :value="2" />
          <el-option :label="$t('dispatchModule.scheme.multiObjective')" :value="3" />
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('dispatchModule.scheme.status')" prop="status">
        <el-select v-model="queryParams.status" :placeholder="$t('dispatchModule.scheme.placeholder.selectStatus')" clearable size="small">
          <el-option :label="$t('dispatchModule.scheme.designing')" :value="1" />
          <el-option :label="$t('dispatchModule.scheme.approving')" :value="2" />
          <el-option :label="$t('dispatchModule.scheme.approved')" :value="3" />
          <el-option :label="$t('dispatchModule.scheme.executing')" :value="4" />
          <el-option :label="$t('dispatchModule.scheme.completed')" :value="5" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">{{ $t('common.search') }}</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">{{ $t('common.reset') }}</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd">{{ $t('common.add') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate">{{ $t('common.edit') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete">{{ $t('common.delete') }}</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="schemeList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column :label="$t('dispatchModule.scheme.schemeId')" prop="schemeId" width="80" />
      <el-table-column :label="$t('dispatchModule.scheme.schemeCode')" prop="schemeCode" width="150" />
      <el-table-column :label="$t('dispatchModule.scheme.schemeName')" prop="schemeName" :show-overflow-tooltip="true" />
      <el-table-column :label="$t('dispatchModule.scheme.optimizationType')" prop="optimizationType" width="120">
        <template slot-scope="scope">
          <el-tag :type="getTypeTag(scope.row.optimizationType)">{{ getTypeLabel(scope.row.optimizationType) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column :label="$t('dispatchModule.scheme.algorithm')" prop="algorithm" width="120" />
      <el-table-column :label="$t('dispatchModule.scheme.objectiveValue')" prop="objectiveValue" width="120" />
      <el-table-column :label="$t('dispatchModule.scheme.status')" prop="status" width="100">
        <template slot-scope="scope">
          <el-tag :type="getStatusTag(scope.row.status)">{{ getStatusLabel(scope.row.status) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column :label="$t('common.createTime')" prop="createTime" width="160">
        <template slot-scope="scope"><span>{{ parseTime(scope.row.createTime) }}</span></template>
      </el-table-column>
      <el-table-column :label="$t('common.operation')" align="center" width="200">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-view" @click="handleView(scope.row)">{{ $t('common.detail') }}</el-button>
          <el-button size="mini" type="text" icon="el-icon-caret-right" @click="handleExecute(scope.row)" v-if="scope.row.status === 3">{{ $t('dispatchModule.scheme.execute') }}</el-button>
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)">{{ $t('common.edit') }}</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)">{{ $t('common.delete') }}</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />

    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item :label="$t('dispatchModule.scheme.schemeCode')" prop="schemeCode">
              <el-input v-model="form.schemeCode" :placeholder="$t('dispatchModule.scheme.placeholder.inputSchemeCode')" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('dispatchModule.scheme.schemeName')" prop="schemeName">
              <el-input v-model="form.schemeName" :placeholder="$t('dispatchModule.scheme.placeholder.inputSchemeName')" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item :label="$t('dispatchModule.scheme.optimizationType')" prop="optimizationType">
              <el-select v-model="form.optimizationType" :placeholder="$t('dispatchModule.scheme.placeholder.selectOptimizationType')" style="width: 100%">
                <el-option :label="$t('dispatchModule.scheme.costMinimization')" :value="1" />
                <el-option :label="$t('dispatchModule.scheme.emissionMinimization')" :value="2" />
                <el-option :label="$t('dispatchModule.scheme.multiObjective')" :value="3" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('dispatchModule.scheme.algorithm')" prop="algorithm">
              <el-select v-model="form.algorithm" :placeholder="$t('dispatchModule.scheme.placeholder.selectAlgorithm')" style="width: 100%">
                <el-option :label="$t('dispatchModule.scheme.linearProgramming')" value="LP" />
                <el-option :label="$t('dispatchModule.scheme.milp')" value="MILP" />
                <el-option :label="$t('dispatchModule.scheme.ga')" value="GA" />
                <el-option :label="$t('dispatchModule.scheme.pso')" value="PSO" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item :label="$t('dispatchModule.scheme.constraintsText')" prop="constraintsText">
          <el-input v-model="form.constraintsText" type="textarea" :rows="3" :placeholder="$t('common.pleaseInput')" />
        </el-form-item>
        <el-form-item :label="$t('dispatchModule.scheme.resultText')" prop="resultText">
          <el-input v-model="form.resultText" type="textarea" :rows="3" :placeholder="$t('common.pleaseInput')" />
        </el-form-item>
        <el-row>
          <el-col :span="12">
            <el-form-item :label="$t('dispatchModule.scheme.objectiveValue')" prop="objectiveValue">
              <el-input-number v-model="form.objectiveValue" :precision="2" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('dispatchModule.scheme.status')" prop="status">
              <el-select v-model="form.status" :placeholder="$t('dispatchModule.scheme.placeholder.selectStatus')" style="width: 100%">
                <el-option :label="$t('dispatchModule.scheme.designing')" :value="1" />
                <el-option :label="$t('dispatchModule.scheme.approving')" :value="2" />
                <el-option :label="$t('dispatchModule.scheme.approved')" :value="3" />
                <el-option :label="$t('dispatchModule.scheme.executing')" :value="4" />
                <el-option :label="$t('dispatchModule.scheme.completed')" :value="5" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">{{ $t('button.submit') }}</el-button>
        <el-button @click="cancel">{{ $t('button.cancel') }}</el-button>
      </div>
    </el-dialog>

    <el-dialog :title="$t('dispatchModule.scheme.detail')" :visible.sync="detailOpen" width="700px" append-to-body>
      <el-descriptions :column="2" border v-if="detailData">
        <el-descriptions-item :label="$t('dispatchModule.scheme.schemeCode')">{{ detailData.schemeCode }}</el-descriptions-item>
        <el-descriptions-item :label="$t('dispatchModule.scheme.schemeName')">{{ detailData.schemeName }}</el-descriptions-item>
        <el-descriptions-item :label="$t('dispatchModule.scheme.optimizationType')">{{ getTypeLabel(detailData.optimizationType) }}</el-descriptions-item>
        <el-descriptions-item :label="$t('dispatchModule.scheme.algorithm')">{{ detailData.algorithm }}</el-descriptions-item>
        <el-descriptions-item :label="$t('dispatchModule.scheme.objectiveValue')">{{ detailData.objectiveValue }}</el-descriptions-item>
        <el-descriptions-item :label="$t('dispatchModule.scheme.status')">
          <el-tag :type="getStatusTag(detailData.status)">{{ getStatusLabel(detailData.status) }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item :label="$t('dispatchModule.scheme.constraintsText')" :span="2">{{ detailData.constraintsText }}</el-descriptions-item>
        <el-descriptions-item :label="$t('dispatchModule.scheme.resultText')" :span="2">{{ detailData.resultText }}</el-descriptions-item>
      </el-descriptions>
      <div slot="footer"><el-button @click="detailOpen = false">{{ $t('button.close') }}</el-button></div>
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

<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item :label="$t('reportModule.templateType')" prop="templateType">
        <el-select
          v-model="queryParams.templateType"
          :placeholder="$t('common.pleaseSelect')"
          clearable
          style="width: 160px"
          @change="handleTemplateTypeChange"
        >
          <el-option
            v-for="item in templateTypeOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('reportModule.templateName')" prop="templateId">
        <el-select
          v-model="queryParams.templateId"
          :placeholder="$t('common.pleaseSelect')"
          style="width: 240px"
        >
          <el-option
            v-for="item in templateList"
            :key="item.templateId"
            :label="item.templateName"
            :value="item.templateId"
          />
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('reportModule.energyType')" prop="energyType">
        <el-select v-model="queryParams.energyType" :placeholder="$t('common.pleaseSelect')" style="width: 120px">
          <el-option
            v-for="item in dict.type.energy_type"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('reportModule.dateType')" prop="dateType">
        <el-select v-model="queryParams.dateType" :placeholder="$t('common.pleaseSelect')" style="width: 120px">
          <el-option :label="$t('reportModule.day')" value="date" />
          <el-option :label="$t('reportModule.week')" value="week" />
          <el-option :label="$t('reportModule.month')" value="month" />
          <el-option :label="$t('reportModule.year')" value="year" />
          <el-option :label="$t('reportModule.custom')" value="daterange" />
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('reportModule.dateRange')">
        <el-date-picker
          v-model="dateRange"
          style="width: 300px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          :start-placeholder="$t('common.startDate')"
          :end-placeholder="$t('common.endDate')"
        ></el-date-picker>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-view"
          size="mini"
          @click="previewReport"
        >{{ $t('reportModule.previewReport') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-document"
          size="mini"
          @click="generateReport"
        >{{ $t('reportModule.generateReport') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="exportExcel"
        >{{ $t('reportModule.exportExcel') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-download"
          size="mini"
          @click="exportPdf"
        >{{ $t('reportModule.exportPdf') }}</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getTemplateList" />
    </el-row>

    <!-- 报表预览区域 -->
    <div class="report-preview" v-if="previewData">
      <h2 class="report-title">{{ previewData.title }}</h2>
      <el-table :data="previewData.data" border style="width: 100%">
        <el-table-column
          v-for="(key, index) in Object.keys(previewData.data[0] || {})"
          :key="index"
          :prop="key"
          :label="key"
        />
      </el-table>
      <div class="report-statistics" v-if="previewData.statistics">
        <h3>统计信息</h3>
        <div v-for="(value, key) in previewData.statistics" :key="key" class="stat-item">
          {{ key }}: {{ value }}
        </div>
      </div>
    </div>

    <!-- 报表生成对话框 -->
    <el-dialog :title="'生成报表'" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="报表名称" prop="reportName">
          <el-input v-model="form.reportName" :placeholder="$t('common.pleaseInput')" />
        </el-form-item>
        <el-form-item label="模板类型" prop="templateType">
          <el-select v-model="form.templateType" :placeholder="$t('common.pleaseSelect')">
            <el-option
              v-for="item in templateTypeOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="模板名称" prop="templateId">
          <el-select v-model="form.templateId" :placeholder="$t('common.pleaseSelect')">
            <el-option
              v-for="item in templateList"
              :key="item.templateId"
              :label="item.templateName"
              :value="item.templateId"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="时间范围" prop="dateRange">
          <el-date-picker
            v-model="form.dateRange"
            style="width: 100%"
            value-format="yyyy-MM-dd"
            type="daterange"
            range-separator="-"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
          ></el-date-picker>
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
import { listTemplate } from "@/api/report/template";
import { generateReport, previewReport, exportExcel, exportPdf } from "@/api/report/engine";

export default {
  name: "GenerateReport",
  dicts: ['energy_type'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 报表模板列表
      templateList: [],
      // 预览数据
      previewData: null,
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        templateType: null,
        templateId: null,
        energyType: '0',
        dateType: 'date',
        startTime: '',
        endTime: ''
      },
      // 日期范围
      dateRange: [],
      // 表单参数
      form: {
        reportName: '',
        templateType: null,
        templateId: null,
        dateRange: []
      },
      // 表单校验
      rules: {
        reportName: [
          { required: true, message: "报表名称不能为空", trigger: "blur" }
        ],
        templateId: [
          { required: true, message: "模板不能为空", trigger: "blur" }
        ]
      },
      // 模板类型选项
      templateTypeOptions: [
        { label: "能耗报表", value: "energy" },
        { label: "费用报表", value: "expense" },
        { label: "损耗报表", value: "loss" },
        { label: "碳排放报表", value: "carbon" }
      ]
    };
  },
  created() {
    this.getTemplateList();
  },
  methods: {
    /** 查询报表模板列表 */
    getTemplateList() {
      this.loading = true;
      listTemplate({ templateType: this.queryParams.templateType }).then(response => {
        this.templateList = response.data;
        this.loading = false;
      });
    },
    /** 模板类型变化 */
    handleTemplateTypeChange() {
      this.queryParams.templateId = null;
      this.getTemplateList();
    },
    /** 预览报表 */
    previewReport() {
      if (!this.queryParams.templateId) {
        this.$message.warning("请选择报表模板");
        return;
      }
      if (!this.dateRange || this.dateRange.length === 0) {
        this.$message.warning("请选择时间范围");
        return;
      }
      
      const params = {
        templateId: this.queryParams.templateId,
        startTime: this.dateRange[0] + " 00:00:00",
        endTime: this.dateRange[1] + " 23:59:59",
        energyType: this.queryParams.energyType,
        dateType: this.queryParams.dateType
      };
      
      previewReport(params).then(response => {
        this.previewData = response.data;
      });
    },
    /** 生成报表 */
    generateReport() {
      this.open = true;
      this.title = "生成报表";
    },
    /** 导出Excel */
    exportExcel() {
      if (!this.queryParams.templateId) {
        this.$message.warning("请选择报表模板");
        return;
      }
      if (!this.dateRange || this.dateRange.length === 0) {
        this.$message.warning("请选择时间范围");
        return;
      }
      
      const params = {
        templateId: this.queryParams.templateId,
        startTime: this.dateRange[0] + " 00:00:00",
        endTime: this.dateRange[1] + " 23:59:59",
        energyType: this.queryParams.energyType,
        dateType: this.queryParams.dateType,
        exportFormat: "excel"
      };
      
      exportExcel(params).then(() => {
        this.$message.success("导出成功");
      });
    },
    /** 导出PDF */
    exportPdf() {
      if (!this.queryParams.templateId) {
        this.$message.warning("请选择报表模板");
        return;
      }
      if (!this.dateRange || this.dateRange.length === 0) {
        this.$message.warning("请选择时间范围");
        return;
      }
      
      const params = {
        templateId: this.queryParams.templateId,
        startTime: this.dateRange[0] + " 00:00:00",
        endTime: this.dateRange[1] + " 23:59:59",
        energyType: this.queryParams.energyType,
        dateType: this.queryParams.dateType,
        exportFormat: "pdf"
      };
      
      exportPdf(params).then(() => {
        this.$message.success("导出成功");
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          const params = {
            templateId: this.form.templateId,
            startTime: this.form.dateRange[0] + " 00:00:00",
            endTime: this.form.dateRange[1] + " 23:59:59",
            reportName: this.form.reportName
          };
          
          generateReport(params).then(response => {
            this.msgSuccess("生成成功");
            this.open = false;
            this.previewData = response.data;
          });
        }
      });
    },
    /** 取消按钮 */
    cancel() {
      this.open = false;
      this.reset();
    },
    /** 重置表单 */
    reset() {
      this.form = {
        reportName: '',
        templateType: null,
        templateId: null,
        dateRange: []
      };
      this.resetForm("form");
    }
  }
};
</script>

<style scoped>
.report-preview {
  margin-top: 20px;
  padding: 20px;
  background: #f5f7fa;
  border-radius: 4px;
}

.report-title {
  text-align: center;
  margin-bottom: 20px;
  color: #303133;
}

.report-statistics {
  margin-top: 20px;
  padding: 10px;
  background: #fff;
  border-radius: 4px;
}

.stat-item {
  margin: 5px 0;
}
</style>

<template>
  <div class="app-container">
    <!-- 导入对话框 -->
    <el-dialog :title="pageData.title" :visible.sync="pageData.open" width="400px" append-to-body>
      <el-row style="padding-bottom: 10px">
        <el-text type="warning" size="small">仅允许导入xls、xlsx格式文件。</el-text>
        <el-button type="text" size="small" @click="importTemplate">下载模板</el-button>
      </el-row>
      <el-row style="padding-bottom: 0px">
        <el-checkbox v-model="pageData.importData.updateSupport">
          <el-text type="danger" size="small">直接更新已经存在的数据</el-text>
        </el-checkbox>
      </el-row>
      <el-upload
        ref="importRef"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="pageData.importData.headers"
        :action="pageData.importData.url + '?updateSupport=' + pageData.importData.updateSupport + pageData.importData.actionUrlExtend"
        :disabled="pageData.importData.isImporting"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :on-change="handleFileChange"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip text-center" slot="tip"></div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" :loading="pageData.loadingImportData" :disabled="pageData.loadingImportData" @click="submitFileForm">确 定</el-button>
        <el-button @click="pageData.open = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getToken } from "@/utils/auth";
import patrolPoint from "@/api/autoee/patrolPoint";
import patrolPointExtend from "@/api/autoee/patrolPointExtend";
import moment from 'moment';

export default {
  name: "importDialog",
  dicts: ['sys_user', 'sys_dept'],

  data() {
    return {
      pageData: {
        open: false,
        loadingImportData: false,
        title: "",
        vueAppBaseApi: process.env.VUE_APP_BASE_API,
        importData: {
          open: false,
          title: "",
          isImporting: false,
          updateSupport: true,
          headers: { Authorization: "Bearer " + getToken() },
          url: process.env.VUE_APP_BASE_API + "/autoee/patrolPoint/importPatrolPointData",
          actionUrlExtend: "",
          fileSelected: false
        }
      }
    };
  },

  methods: {
    /** 导入按钮操作 */
    openPatrolPointImportDialog(parentQueryParams) {

      if (patrolPointExtend.openImportDialogExtend(this, parentQueryParams)) {
        this.pageData.title = "导入数据";
        this.pageData.open = true;
      }
    },

    /** 下载模板操作 */
    importTemplate() {
      let fileExt = moment().format('YYYYMMDD_HHmmss');
      this.download('autoee/patrolPoint/downLoadImportTemplatePatrolPoint', {},
        `巡更点位_导入模版_${fileExt}.xlsx`)
    },

    // 选择的文件变化事件
    handleFileChange(file, fileList) {
      this.pageData.importData.fileSelected = fileList.length > 0;
    },

    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.pageData.importData.isImporting = true;
    },

    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.pageData.open = false;
      this.pageData.loadingImportData = false;
      this.pageData.importData.isImporting = false;
      this.$refs.importRef.clearFiles();
      this.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + response.msg + "</div>",
        "导入结果",
        {
          dangerouslyUseHTMLString: true,
          customClass: 'importAlertClass'
        }
      ).then(() => {
        this.$emit("importSubmitCallback");
      }).catch(() => {});
    },

    // 提交上传文件
    submitFileForm() {
      if (this.pageData.importData.fileSelected) {
        this.pageData.loadingImportData = true;
        this.$refs.importRef.submit();
      } else {
        this.$modal.msgWarning("请先选择导入的文件！");
      }
    }
  },

  mounted() {
    // mounted扩展方法
    patrolPointExtend.importMountedStartExtend(this);
  }
};
</script>

<style>
</style>

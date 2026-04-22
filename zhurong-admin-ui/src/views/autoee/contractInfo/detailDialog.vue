<template>
  <div class="app-container">
    <!--查看合同信息管理详细对话框 去掉高度设置  style="height: 800px"-->
    <el-dialog class="ruoyi_dialog" :title="pageData.title" :visible.sync="pageData.open" width="1000px" style="" append-to-body>
      <!-- 使用Element UI栅格系统优化布局 -->
      <el-form ref="detailFormRef" :model="pageData.detailForm" label-width="150px" style="padding-right: 30px">
        <el-row :gutter="20">
		        <el-col v-if="true" :span="12">
          <el-form-item label="合同编号(新)：" prop="contractNoNew">
              <div>{{ pageData.detailForm.contractNoNew }}</div>
          </el-form-item>
        </el-col>
		        <el-col v-if="true" :span="12">
          <el-form-item label="续签编号(老)：" prop="contractNoOld">
              <div>{{ pageData.detailForm.contractNoOld }}</div>
          </el-form-item>
        </el-col>
		        <el-col v-if="true" :span="12">
          <el-form-item label="所属客户：" prop="belongCustomer">
              <div>{{ pageData.detailForm.belongCustomer }}</div>
          </el-form-item>
        </el-col>
		        <el-col v-if="true" :span="12">
          <el-form-item label="客户方联系人：" prop="customerContact">
              <div>{{ pageData.detailForm.customerContact }}</div>
          </el-form-item>
        </el-col>
		        <el-col v-if="true" :span="12">
          <el-form-item label="合同类型：" prop="contractType">
            <div>{{ pageData.detailForm.contractTypeExtend }}</div>
          </el-form-item>
        </el-col>
		        <el-col v-if="true" :span="12">
          <el-form-item label="合同子类型：" prop="contractSubtype">
            <div>{{ pageData.detailForm.contractSubtypeExtend }}</div>
          </el-form-item>
        </el-col>
		        <el-col v-if="true" :span="12">
          <el-form-item label="签约公司：" prop="signCompany">
              <div>{{ pageData.detailForm.signCompany }}</div>
          </el-form-item>
        </el-col>
		        <el-col v-if="true" :span="12">
          <el-form-item label="业务员：" prop="salesmanId">
              <div>{{ pageData.detailForm.salesmanId }}</div>
          </el-form-item>
        </el-col>
		        <el-col v-if="true" :span="12">
          <el-form-item label="技术支持：" prop="techSupport">
              <div>{{ pageData.detailForm.techSupport }}</div>
          </el-form-item>
        </el-col>
		        <el-col v-if="true" :span="12">
          <el-form-item label="报价单号：" prop="quoteNo">
              <div>{{ pageData.detailForm.quoteNo }}</div>
          </el-form-item>
        </el-col>
		        <el-col v-if="true" :span="12">
          <el-form-item label="合同总价：" prop="contractTotal">
              <div>{{ pageData.detailForm.contractTotal }}</div>
          </el-form-item>
        </el-col>
		        <el-col v-if="true" :span="12">
          <el-form-item label="已收金额：" prop="receivedAmount">
              <div>{{ pageData.detailForm.receivedAmount }}</div>
          </el-form-item>
        </el-col>
		        <el-col v-if="true" :span="12">
          <el-form-item label="签约日期：" prop="signDate">
            <div>{{$common.formatDate(pageData.detailForm.signDate, "yyyy-MM-DD")}}</div>
          </el-form-item>
        </el-col>
		        <el-col v-if="true" :span="12">
          <el-form-item label="附件：" prop="attachmentFiles">
            <file-show-list :files="pageData.detailForm.attachmentFiles" />
          </el-form-item>
        </el-col>
		        <el-col v-if="true" :span="24">
          <el-form-item label="备注：" prop="remark">
            <div>{{ pageData.detailForm.remark }}</div>
          </el-form-item>
        </el-col>
        </el-row>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getToken } from "@/utils/auth";
import contractInfo from "@/api/autoee/contractInfo";
import moment from 'moment';

export default {
  name: "detailDialog",
  dicts: ['sys_user', 'contract_type', 'contract_subtype', 'sys_dept'],

  data() {
    return {
      pageData: {
        open: false,
        title: "",
        vueAppBaseApi: process.env.VUE_APP_BASE_API,
        detailForm: {}
      },
    };
  },

  methods: {
    /** 打开详细窗口 */
    openContractInfoDetailDialog(row) {
      contractInfo.selectDetailByPkContractInfo(row.id).then(response => {
        this.pageData.detailForm = response.data;
        this.pageData.open = true;
        this.pageData.title = "查看合同信息管理";
      });
    },

    // 关闭按钮
    cancel() {
      this.pageData.open = false;
    }
  }
};
</script>

<style>
</style>

<template>
  <div class="app-container">
    <!--查看维修工单详细对话框 去掉高度设置  style="height: 800px"-->
    <el-dialog class="ruoyi_dialog" :title="pageData.title" :visible.sync="pageData.open" width="1000px" style="" append-to-body>
      <!-- form中el-form-item加上display: inline-block;控制一行两列、一行一列 -->
      <el-form ref="detailFormRef" :model="pageData.detailForm" label-width="150px" style="padding-right: 30px">
        <el-form-item v-if="true" label="工单编号：" prop="orderNo" style="display: inline-block;width: 45%;">
          <div>{{ pageData.detailForm.orderNo }}</div>
        </el-form-item>
        <el-form-item v-if="true" label="问题描述：" prop="description"  style="display: inline-block;width: 90%;">
		  <div>{{ pageData.detailForm.description }}</div>
		</el-form-item>
        <el-form-item v-if="true" label="故障类型：" prop="orderFaultType" style="display: inline-block;width: 45%;">
          <div>{{ pageData.detailForm.orderFaultTypeExtend }}</div>
        </el-form-item>
        <el-form-item v-if="true" label="优先级：" prop="orderPriority" style="display: inline-block;width: 45%;">
          <div>{{ pageData.detailForm.orderPriorityExtend }}</div>
        </el-form-item>
        <el-form-item v-if="true" label="工单状态：" prop="repairOrderStatus" style="display: inline-block;width: 45%;">
          <div>{{ pageData.detailForm.repairOrderStatusExtend }}</div>
        </el-form-item>
        <el-form-item v-if="true" label="故障位置：" prop="location" style="display: inline-block;width: 45%;">
          <div>{{ pageData.detailForm.location }}</div>
        </el-form-item>
        <el-form-item v-if="true" label="报修人：" prop="reporterId" style="display: inline-block;width: 45%;">
          <div >
			<dict-tag :options="dict.type.sys_user" :value="pageData.detailForm.reporterId" :show-css="false"/>
		  </div>
        </el-form-item>
        <el-form-item v-if="true" label="报修人电话：" prop="reporterContact" style="display: inline-block;width: 45%;">
          <div>{{ pageData.detailForm.reporterContact }}</div>
        </el-form-item>
        <el-form-item v-if="true" label="报修时间：" prop="reportTime" style="display: inline-block;width: 45%;">
          <div>{{$common.formatDateTime(pageData.detailForm.reportTime, "yyyy-MM-DD HH:mm:ss")}}</div>
        </el-form-item>
        <el-form-item v-if="true" label="维修人：" prop="assigneeId" style="display: inline-block;width: 45%;">
          <div >
			<dict-tag :options="dict.type.sys_user" :value="pageData.detailForm.assigneeId" :show-css="false"/>
		  </div>
        </el-form-item>
        <el-form-item v-if="true" label="维修结果：" prop="repairResult"  style="display: inline-block;width: 90%;">
		  <div>{{ pageData.detailForm.repairResult }}</div>
		</el-form-item>
        <el-form-item v-if="true" label="维修图片：" prop="repairImages"  style="display: inline-block;width: 90%">
		  <!-- ems工程中-图片列表展示-兼容OSS ID和直接URL -->
		  <image-show-list :images="pageData.detailForm.repairImages"  :size="60"  />
        </el-form-item>
        <el-form-item v-if="true" label="完成时间：" prop="completionTime" style="display: inline-block;width: 45%;">
          <div>{{$common.formatDateTime(pageData.detailForm.completionTime, "yyyy-MM-DD HH:mm:ss")}}</div>
        </el-form-item>
        <el-form-item v-if="true" label="备注：" prop="remark"  style="display: inline-block;width: 90%;">
		  <div>{{ pageData.detailForm.remark }}</div>
		</el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getToken } from "@/utils/auth";
import maintainOrder from "@/api/autoee/maintainOrder";
import moment from 'moment';

export default {
  name: "detailDialog",
  dicts: ['repair_order_status', 'order_priority', 'sys_user', 'order_fault_type', 'sys_dept'],

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
    openMaintainOrderDetailDialog(row) {
      maintainOrder.selectDetailByPkMaintainOrder(row.id).then(response => {
        this.pageData.detailForm = response.data;
        this.pageData.open = true;
        this.pageData.title = "查看维修工单";
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

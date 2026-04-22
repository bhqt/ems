<template>
  <div class="app-container">
    <!--查看巡更报警详细对话框 去掉高度设置  style="height: 800px"-->
    <el-dialog class="ruoyi_dialog" :title="pageData.title" :visible.sync="pageData.open" width="1000px" style="" append-to-body>
      <!-- form中el-form-item加上display: inline-block;控制一行两列、一行一列 -->
      <el-form ref="detailFormRef" :model="pageData.detailForm" label-width="150px" style="padding-right: 30px">
        <el-form-item v-if="true" label="报警编号：" prop="alarmNo" style="display: inline-block;width: 45%;">
          <div>{{ pageData.detailForm.alarmNo }}</div>
        </el-form-item>
        <el-form-item v-if="true" label="巡更计划：" prop="patrolPlanId" style="display: inline-block;width: 45%;">
          <div >
			<dict-tag :options="dict.type.a_patrol_plan" :value="pageData.detailForm.patrolPlanId" :show-css="false"/>
		  </div>
        </el-form-item>
        <el-form-item v-if="true" label="巡更任务：" prop="patrolTaskId" style="display: inline-block;width: 45%;">
          <div >
			<dict-tag :options="dict.type.a_patrol_task" :value="pageData.detailForm.patrolTaskId" :show-css="false"/>
		  </div>
        </el-form-item>
        <el-form-item v-if="true" label="巡更人员：" prop="patrolUserId" style="display: inline-block;width: 45%;">
          <div >
			<dict-tag :options="dict.type.sys_user" :value="pageData.detailForm.patrolUserId" :show-css="false"/>
		  </div>
        </el-form-item>
        <el-form-item v-if="true" label="报警类型：" prop="patrolAlarmType" style="display: inline-block;width: 45%;">
          <div>{{ pageData.detailForm.patrolAlarmTypeExtend }}</div>
        </el-form-item>
        <el-form-item v-if="true" label="报警时间：" prop="patrolAlarmTime" style="display: inline-block;width: 45%;">
          <div>{{$common.formatDateTime(pageData.detailForm.patrolAlarmTime, "yyyy-MM-DD HH:mm:ss")}}</div>
        </el-form-item>
        <el-form-item v-if="true" label="报警内容：" prop="patrolAlarmContent"  style="display: inline-block;width: 90%;">
		  <div>{{ pageData.detailForm.patrolAlarmContent }}</div>
		</el-form-item>
        <el-form-item v-if="true" label="报警状态：" prop="patrolAlarmStatus" style="display: inline-block;width: 45%;">
          <div>{{ pageData.detailForm.patrolAlarmStatusExtend }}</div>
        </el-form-item>
        <el-form-item v-if="true" label="处理人：" prop="handleUserId" style="display: inline-block;width: 45%;">
          <div >
			<dict-tag :options="dict.type.sys_user" :value="pageData.detailForm.handleUserId" :show-css="false"/>
		  </div>
        </el-form-item>
        <el-form-item v-if="true" label="处理时间：" prop="handleTime" style="display: inline-block;width: 45%;">
          <div>{{$common.formatDateTime(pageData.detailForm.handleTime, "yyyy-MM-DD HH:mm:ss")}}</div>
        </el-form-item>
        <el-form-item v-if="true" label="处理描述：" prop="handleDesc"  style="display: inline-block;width: 90%;">
		  <div>{{ pageData.detailForm.handleDesc }}</div>
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
import patrolAlarm from "@/api/autoee/patrolAlarm";
import moment from 'moment';

export default {
  name: "detailDialog",
  dicts: ['patrol_alarm_status', 'a_patrol_plan', 'a_patrol_task', 'patrol_alarm_type', 'sys_user', 'sys_dept'],

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
    openPatrolAlarmDetailDialog(row) {
      patrolAlarm.selectDetailByPkPatrolAlarm(row.id).then(response => {
        this.pageData.detailForm = response.data;
        this.pageData.open = true;
        this.pageData.title = "查看巡更报警";
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

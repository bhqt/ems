<template>
  <div class="app-container">
    <!--查看巡更任务详细对话框 去掉高度设置  style="height: 800px"-->
    <el-dialog class="ruoyi_dialog" :title="pageData.title" :visible.sync="pageData.open" width="1000px" style="" append-to-body>
      <!-- 使用Element UI栅格系统优化布局 -->
      <el-form ref="detailFormRef" :model="pageData.detailForm" label-width="150px" style="padding-right: 30px">
        <el-row :gutter="20">
		        <el-col v-if="true" :span="24">
          <el-form-item label="任务名称：" prop="patrolTaskName">
              <div>{{ pageData.detailForm.patrolTaskName }}</div>
          </el-form-item>
        </el-col>
		        <el-col v-if="true" :span="12">
          <el-form-item label="巡更计划：" prop="patrolPlanId">
            <div >
              <dict-tag :options="dict.type.a_patrol_plan" :value="pageData.detailForm.patrolPlanId" :show-css="false"/>
            </div>
          </el-form-item>
        </el-col>
		        <el-col v-if="true" :span="12">
          <el-form-item label="巡更路线：" prop="patrolPathId">
            <div >
              <dict-tag :options="dict.type.a_patrol_path" :value="pageData.detailForm.patrolPathId" :show-css="false"/>
            </div>
          </el-form-item>
        </el-col>
		        <el-col v-if="true" :span="12">
          <el-form-item label="巡更人员：" prop="patrolUserId">
            <div >
              <dict-tag :options="dict.type.sys_user" :value="pageData.detailForm.patrolUserId" :show-css="false"/>
            </div>
          </el-form-item>
        </el-col>
		        <el-col v-if="true" :span="12">
          <el-form-item label="巡更日期：" prop="patrolDate">
            <div>{{$common.formatDate(pageData.detailForm.patrolDate, "yyyy-MM-DD")}}</div>
          </el-form-item>
        </el-col>
		        <el-col v-if="true" :span="12">
          <el-form-item label="开始时间：" prop="startTime">
            <div>{{$common.formatTime(pageData.detailForm.startTime, "HH:mm:ss")}}</div>
          </el-form-item>
        </el-col>
		        <el-col v-if="true" :span="12">
          <el-form-item label="结束时间：" prop="endTime">
            <div>{{$common.formatTime(pageData.detailForm.endTime, "HH:mm:ss")}}</div>
          </el-form-item>
        </el-col>
		        <el-col v-if="true" :span="12">
          <el-form-item label="任务状态：" prop="patrolTaskStatus">
            <div>{{ pageData.detailForm.patrolTaskStatusExtend }}</div>
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
import patrolTask from "@/api/autoee/patrolTask";
import moment from 'moment';

export default {
  name: "detailDialog",
  dicts: ['a_patrol_plan', 'patrol_task_status', 'sys_user', 'a_patrol_path', 'sys_dept'],

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
    openPatrolTaskDetailDialog(row) {
      patrolTask.selectDetailByPkPatrolTask(row.id).then(response => {
        this.pageData.detailForm = response.data;
        this.pageData.open = true;
        this.pageData.title = "查看巡更任务";
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

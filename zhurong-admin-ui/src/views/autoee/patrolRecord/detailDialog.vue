<template>
  <div class="app-container">
    <!--查看巡更记录详细对话框 去掉高度设置  style="height: 800px"-->
    <el-dialog class="ruoyi_dialog" :title="pageData.title" :visible.sync="pageData.open" width="1000px" style="" append-to-body>
      <!-- form中el-form-item加上display: inline-block;控制一行两列、一行一列 -->
      <el-form ref="detailFormRef" :model="pageData.detailForm" label-width="150px" style="padding-right: 30px">
        <el-form-item v-if="true" label="巡更计划：" prop="patrolPlanId" style="display: inline-block;width: 45%;">
          <div >
			<dict-tag :options="dict.type.a_patrol_plan" :value="pageData.detailForm.patrolPlanId" :show-css="false"/>
		  </div>
        </el-form-item>
        <el-form-item v-if="true" label="巡更路线：" prop="patrolPathId" style="display: inline-block;width: 45%;">
          <div >
			<dict-tag :options="dict.type.a_patrol_path" :value="pageData.detailForm.patrolPathId" :show-css="false"/>
		  </div>
        </el-form-item>
        <el-form-item v-if="true" label="巡更点位：" prop="patrolPointId" style="display: inline-block;width: 45%;">
          <div >
			<dict-tag :options="dict.type.a_patrol_point" :value="pageData.detailForm.patrolPointId" :show-css="false"/>
		  </div>
        </el-form-item>
        <el-form-item v-if="true" label="巡更人员：" prop="patrolUserId" style="display: inline-block;width: 45%;">
          <div >
			<dict-tag :options="dict.type.sys_user" :value="pageData.detailForm.patrolUserId" :show-css="false"/>
		  </div>
        </el-form-item>
        <el-form-item v-if="true" label="巡更任务：" prop="patrolTaskId" style="display: inline-block;width: 45%;">
          <div >
			<dict-tag :options="dict.type.a_patrol_task" :value="pageData.detailForm.patrolTaskId" :show-css="false"/>
		  </div>
        </el-form-item>
        <el-form-item v-if="true" label="点位顺序：" prop="pointOrder"  style="display: inline-block;width: 45%;">
          <div>{{ pageData.detailForm.pointOrder }}</div>
        </el-form-item>
        <el-form-item v-if="true" label="点位巡更时间：" prop="arriveTime" style="display: inline-block;width: 45%;">
          <div>{{$common.formatDateTime(pageData.detailForm.arriveTime, "yyyy-MM-DD HH:mm:ss")}}</div>
        </el-form-item>
        <el-form-item v-if="true" label="巡更结果：" prop="patrolResult" style="display: inline-block;width: 45%;">
          <div>{{ pageData.detailForm.patrolResultExtend }}</div>
        </el-form-item>
        <el-form-item v-if="true" label="结果描述：" prop="resultDesc"  style="display: inline-block;width: 90%;">
		  <div>{{ pageData.detailForm.resultDesc }}</div>
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
import patrolRecord from "@/api/autoee/patrolRecord";
import moment from 'moment';

export default {
  name: "detailDialog",
  dicts: ['a_patrol_plan', 'a_patrol_task', 'sys_user', 'a_patrol_path', 'a_patrol_point', 'patrol_result', 'sys_dept'],

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
    openPatrolRecordDetailDialog(row) {
      patrolRecord.selectDetailByPkPatrolRecord(row.id).then(response => {
        this.pageData.detailForm = response.data;
        this.pageData.open = true;
        this.pageData.title = "查看巡更记录";
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

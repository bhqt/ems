<template>
  <div class="app-container">
    <!--查看巡更计划详细对话框 去掉高度设置  style="height: 800px"-->
    <el-dialog class="ruoyi_dialog" :title="pageData.title" :visible.sync="pageData.open" width="80%" style="" append-to-body>
      <!-- 使用Element UI栅格系统优化布局 -->
      <el-form ref="detailFormRef" :model="pageData.detailForm" label-width="150px" style="padding-right: 30px">
        <el-row :gutter="20">
		        <el-col v-if="true" :span="12">
          <el-form-item label="巡更计划名称：" prop="patrolPlanName">
              <div>{{ pageData.detailForm.patrolPlanName }}</div>
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
          <el-form-item label="开始时间：" prop="startTime">
            <div>{{$common.formatDateTime(pageData.detailForm.startTime, "HH:mm")}}</div>
          </el-form-item>
        </el-col>
		        <el-col v-if="true" :span="12">
          <el-form-item label="结束时间：" prop="endTime">
            <div>{{$common.formatDateTime(pageData.detailForm.endTime, "HH:mm")}}</div>
          </el-form-item>
        </el-col>
		        <el-col v-if="true" :span="12">
          <el-form-item label="巡更周期：" prop="patrolCycleType">
            <div>{{ pageData.detailForm.patrolCycleTypeExtend }}</div>
          </el-form-item>
        </el-col>
		        <el-col v-if="pageData.detailForm.patrolCycleType === 'week'" :span="24">
          <el-form-item label="巡更周期值：" prop="patrolCycleValue">
            <div>{{ formatWeekCycleValue(pageData.detailForm.patrolCycleValue) }}</div>
          </el-form-item>
        </el-col>
		        <el-col v-if="true" :span="12">
          <el-form-item label="计划开始日期：" prop="startDate">
            <div>{{$common.formatDate(pageData.detailForm.startDate, "yyyy-MM-DD")}}</div>
          </el-form-item>
        </el-col>
		        <el-col v-if="true" :span="12">
          <el-form-item label="计划结束日期：" prop="endDate">
            <div>{{$common.formatDate(pageData.detailForm.endDate, "yyyy-MM-DD")}}</div>
          </el-form-item>
        </el-col>
		        <el-col v-if="true" :span="12">
          <el-form-item label="计划状态：" prop="patrolPlanStatus">
            <div>{{ pageData.detailForm.patrolPlanStatusExtend }}</div>
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
import patrolPlan from "@/api/autoee/patrolPlan";
import moment from 'moment';

export default {
  name: "detailDialog",
  dicts: ['patrol_cycle_type', 'sys_user', 'a_patrol_path', 'patrol_plan_status', 'sys_dept'],

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
    openPatrolPlanDetailDialog(row) {
      patrolPlan.selectDetailByPkPatrolPlan(row.id).then(response => {
        this.pageData.detailForm = response.data;
        this.pageData.open = true;
        this.pageData.title = "查看巡更计划";
      });
    },

    // 关闭按钮
    cancel() {
      this.pageData.open = false;
    },
    
    // 格式化星期周期值
    formatWeekCycleValue(value) {
      if (!value) return '';
      
      // 星期映射表
      const weekMap = {1: '周一', 2: '周二', 3: '周三', 4: '周四', 5: '周五', 6: '周六', 7: '周日'};
      
      // 确保value是字符串
      const valueStr = String(value);
      
      if (valueStr && valueStr.trim()) {
        // 将字符串"1,2,3"转换为数组并映射为星期名称
        return valueStr.split(',').map(day => {
          const dayNum = parseInt(day.trim(), 10);
          return weekMap[dayNum] || day; // 如果映射不存在，保留原始值
        }).filter(day => day).join('、'); // 用顿号连接
      }
      
      return '';
    }
  }
};
</script>

<style>
</style>

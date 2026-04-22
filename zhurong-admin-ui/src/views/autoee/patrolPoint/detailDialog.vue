<template>
  <div class="app-container">
    <!--查看巡更点位详细对话框 去掉高度设置  style="height: 800px"-->
    <el-dialog class="ruoyi_dialog" :title="pageData.title" :visible.sync="pageData.open" width="1000px" style="" append-to-body>
      <!-- form中el-form-item加上display: inline-block;控制一行两列、一行一列 -->
      <el-form ref="detailFormRef" :model="pageData.detailForm" label-width="150px" style="padding-right: 30px">
        <el-form-item v-if="true" label="点位名称：" prop="pointName" style="display: inline-block;width: 90%;">
          <div>{{ pageData.detailForm.pointName }}</div>
        </el-form-item>
        <el-form-item v-if="true" label="点位地点：" prop="pointLocation" style="display: inline-block;width: 90%;">
          <div>{{ pageData.detailForm.pointLocation }}</div>
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
import patrolPoint from "@/api/autoee/patrolPoint";
import moment from 'moment';

export default {
  name: "detailDialog",
  dicts: ['sys_user', 'sys_dept'],

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
    openPatrolPointDetailDialog(row) {
      patrolPoint.selectDetailByPkPatrolPoint(row.id).then(response => {
        this.pageData.detailForm = response.data;
        this.pageData.open = true;
        this.pageData.title = "查看巡更点位";
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

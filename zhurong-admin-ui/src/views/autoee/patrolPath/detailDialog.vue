<template>
  <div class="app-container">
    <!--查看巡更路线详细对话框 去掉高度设置  style="height: 800px"-->
    <el-dialog class="ruoyi_dialog" :title="pageData.title" :visible.sync="pageData.open" width="1000px" style="" append-to-body>
      <!-- form中el-form-item加上display: inline-block;控制一行两列、一行一列 -->
      <el-form ref="detailFormRef" :model="pageData.detailForm" label-width="150px" style="padding-right: 30px">
        <el-form-item v-if="true" label="路线编号：" prop="routeCode" style="display: inline-block;width: 90%;">
          <div>{{ pageData.detailForm.routeCode }}</div>
        </el-form-item>
        <el-form-item v-if="true" label="路线名称：" prop="routeName" style="display: inline-block;width: 90%;">
          <div>{{ pageData.detailForm.routeName }}</div>
        </el-form-item>
        <el-form-item v-if="true" label="状态：" prop="patrolRouteStatus" style="display: inline-block;width: 90%;">
          <div>{{ pageData.detailForm.patrolRouteStatusExtend }}</div>
        </el-form-item>
        <el-form-item v-if="true" label="巡更点列表：" prop="pointList" style="display: inline-block;width: 90%;">
          <div style="display: inline-block;width: 90%;">
            <template v-if="pageData.detailForm.pointList">
              <div class="point-tags-container">
                <el-tag
                  v-for="pointId in pageData.detailForm.pointList.split(',')"
                  :key="pointId"
                  type="primary"
                  size="small"
                  style="margin-right: 8px; margin-bottom: 8px"
                >
                  {{ getPointName(pointId) }}
                </el-tag>
              </div>
            </template>
            <span v-else>-</span>
          </div>
        </el-form-item>
        <el-form-item v-if="true" label="备注：" prop="remark" style="display: inline-block;width: 90%;">
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
import {getToken} from "@/utils/auth";
import patrolPath from "@/api/autoee/patrolPath";
import moment from 'moment';

export default {
  name: "detailDialog",
  dicts: ['sys_user', 'patrol_route_status', 'a_patrol_point', 'sys_dept'],

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
    // 根据ID获取巡更点名称
    getPointName(pointId) {
      if (!pointId) return '';
      const point = this.dict.type.a_patrol_point.find(item => item.value === pointId.trim());
      return point ? point.label : pointId; // 如果找不到对应名称，则显示ID
    },
    /** 打开详细窗口 */
    openPatrolPathDetailDialog(row) {
      patrolPath.selectDetailByPkPatrolPath(row.id).then(response => {
        this.pageData.detailForm = response.data;
        this.pageData.open = true;
        this.pageData.title = "查看巡更路线";
      });
    },

    // 关闭按钮
    cancel() {
      this.pageData.open = false;
    }
  }
};
</script>

<style scoped>
.point-tags-container {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  margin-top: 5px;
}
</style>

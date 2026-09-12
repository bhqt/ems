<template>
  <div class="app-container bg-container" v-loading="loading">
    <el-row :gutter="20">
      <el-col :lg="14" :sm="24" :xs="24">
        <el-card class="box-card personal-card">
          <div class="overview-title">
            <div class="overview-icon"></div><span>{{ $t('profileModule.personalInfo') }}</span>
          </div>
          <div class="info-content">
            <div style="flex: 1;text-align:center;">
              <userAvatar :user="user" />
            </div>
            <div class="info-box">
              <div class="info-body">
                <div><svg-icon icon-class="user" style="margin-right:6px;"/>{{ $t('profileModule.userName') }}：{{user.userName}}</div>
                <div><svg-icon icon-class="nickname" style="margin-right:6px;"/>{{ $t('profileModule.nickName') }}：{{user.nickName}}</div>
                <div><svg-icon icon-class="sex" style="margin-right:6px;"/>{{ $t('profileModule.sex') }}：{{handleSex(user.sex)}}</div>
                <div><svg-icon icon-class="phone" style="margin-right:6px;"/>{{ $t('profileModule.phone') }}：{{user.phonenumber || '--'}}</div>
                <div style="height:21px;"><svg-icon icon-class="email" style="margin-right:6px;"/>{{ $t('profileModule.email') }}：{{user.email || '--'}}</div>
              </div>
              <div class="info-body">
                <div><svg-icon icon-class="tree" style="margin-right:6px;"/>{{ $t('profileModule.dept') }}：{{ user.dept ? (user.dept.deptName || '--') : '--'}}</div>
                <div><svg-icon icon-class="post" style="margin-right:6px;"/>{{ $t('profileModule.post') }}：{{postGroup || '--'}}</div>
                <div><svg-icon icon-class="peoples" style="margin-right:6px;"/>{{ $t('profileModule.role') }}：{{roleGroup || '--'}}</div>
                <div><svg-icon icon-class="date" style="margin-right:6px;"/>{{ $t('profileModule.createTime') }}：{{parseTime(user.createTime, '{y}-{m}-{d}')}}</div>
                <div style="height:21px;">
                  <el-button size="mini" type="primary" @click="openInfoDialog">{{ $t('profileModule.editInfo') }}</el-button>
                  <el-button size="mini" type="primary" @click="openPwdDialog">{{ $t('profileModule.resetPwd') }}</el-button>
                </div>
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :lg="5" :sm="12" :xs="12">
        <el-card class="box-card my-order-card">
          <div class="overview-title">
            <div class="overview-icon"></div><span>{{ $t('profileModule.myOrder') }}</span>
          </div>
          <div class="count-body">
            <div class="count-item" @click="toRepairOrder">
              <div style="font-size:14px;color:var(--base-color-3);padding: 8px 10px 0;">{{ $t('profileModule.pending') }}</div>
              <div class="count-num">
                {{repairCount.inProgress}}
              </div>
            </div>
            <div class="count-item" @click="toRepairOrder">
              <div style="font-size:14px;color:var(--base-color-3);padding: 8px 10px 0;">{{ $t('profileModule.completed') }}</div>
              <div class="count-num">
                {{repairCount.completed}}
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :lg="5" :sm="12" :xs="12">
        <el-card class="box-card my-inspection-card">
          <div class="overview-title">
            <div class="overview-icon"></div><span>{{ $t('profileModule.myInspection') }}</span>
          </div>
          <div class="count-body">
            <div class="count-item" @click="toInspection">
              <div style="font-size:14px;color:var(--base-color-3);padding: 8px 10px 0;">{{ $t('profileModule.pending') }}</div>
              <div class="count-num">
                {{inspectionCount.inProgress}}
              </div>
            </div>
            <div class="count-item" @click="toInspection">
              <div style="font-size:14px;color:var(--base-color-3);padding: 8px 10px 0;">{{ $t('profileModule.completed') }}</div>
              <div class="count-num">
                {{inspectionCount.completed}}
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :lg="12" :sm="24" :xs="24">
        <el-card class="box-card chart-card" style="height: 480px;margin-bottom: 20px;">
          <div class="overview-title">
            <div class="overview-icon"></div><span>{{ $t('profileModule.orderCountMonthly') }}</span>
          </div>
          <OrderChartVue style="height:calc(100% - 41px);" height="100%" :setInfo="{yName: $t('profileModule.chart.quantity'), xName: $t('profileModule.chart.month'), legendName: [$t('profileModule.chart.finished'), $t('profileModule.chart.unfinished')]}" :barData="repairs"/>
        </el-card>
      </el-col>
      <el-col :lg="12" :sm="24" :xs="24">
        <el-card class="box-card chart-card" style="height: 480px;margin-bottom: 20px;">
          <div class="overview-title">
            <div class="overview-icon"></div><span>{{ $t('profileModule.inspectionCountMonthly') }}</span>
          </div>
          <OrderChartVue style="height:calc(100% - 41px);" height="100%" :setInfo="{yName: $t('profileModule.chart.quantity'), xName: $t('profileModule.chart.month'), legendName: [$t('profileModule.chart.finished'), $t('profileModule.chart.unfinished')]}" :barData="inspections"/>
        </el-card>
      </el-col>
    </el-row>

    <el-dialog :title="title" :visible.sync="infoDialog" append-to-body destroy-on-close width="500px">
      <user-info :user="user" @closeDialog="closeInfoDialog"/>
    </el-dialog>
    <el-dialog :title="title" :visible.sync="pwdDialog" append-to-body destroy-on-close width="500px">
      <resetPwd @closeDialog="closePwdDialog"/>
    </el-dialog>
  </div>
</template>

<script>
import userAvatar from "./userAvatar";
import resetPwd from "./resetPwd";
import { getUserProfile, countRepairAndInspection } from "@/api/system/user";
import {listOrder} from "@/api/system/repairOrder"
import OrderChartVue from './orderChart.vue';
import PieChartVue from './pieChart.vue';
import UserInfo from './userInfo.vue';
export default {
  name: "Profile",
  components: { userAvatar, resetPwd, PieChartVue, OrderChartVue, UserInfo },
  data() {
    return {
      user: {},
      roleGroup: {},
      postGroup: {},
      activeTab: "userinfo",
      infoDialog: false,
      pwdDialog: false,
      title: this.$t('profileModule.editInfo'),
      repairCount: {},
      inspectionCount: {},
      repairs: {},
      inspections: {},
      loading: true,
    };
  },
  created() {
    this.getUser();
  },
  methods: {
    getUser() {
      this.loading = true
      getUserProfile().then(response => {
        this.user = response.data.user;
        this.roleGroup = response.data.roleGroup;
        this.postGroup = response.data.postGroup;
        this.getRepairOeder()
      }).finally(() => {
        this.loading = false
      });
    },
    handleSex(sex) {
      if(!sex) return '--'
      switch (sex) {
        case '1':
          return this.$t('profileModule.female')
        case '0':
          return this.$t('profileModule.male')
        case '2':
          return this.$t('profileModule.unknown')
        default:
          break;
      }
    },
    getRepairOeder() {
      countRepairAndInspection().then(res => {
        this.repairCount = res.data.repairCount
        this.inspectionCount = res.data.inspectionCount
        let repairData = {
          xData: [],
          currentData: [],
          sameData: []
        }
        res.data.repairs.forEach(item => {
          repairData.xData.push(item.time)
          repairData.currentData.push(item.finished)
          repairData.sameData.push(item.unfinished)
        })
        this.repairs = repairData
        let inspectionData = {
          xData: [],
          currentData: [],
          sameData: []
        }
        res.data.inspections.forEach(item => {
          inspectionData.xData.push(item.time)
          inspectionData.currentData.push(item.finished)
          inspectionData.sameData.push(item.unfinished)
        })
        this.inspections = inspectionData
      })
    },
    openInfoDialog() {
      this.title = this.$t('profileModule.editInfo')
      this.infoDialog = true
    },
    openPwdDialog() {
      this.title = this.$t('profileModule.resetPwd')
      this.pwdDialog = true
    },
    closeInfoDialog(value) {
      if(value) this.getUser()
      this.infoDialog = false
    },
    closePwdDialog(value) {
      this.pwdDialog = false
    },
    toRepairOrder() {
      this.$router.push({path: '/maintenance/my-order'})
    },
    toInspection() {
      this.$router.push({path: '/maintenance/my-inspection'})
    },
  }
};
</script>
<style scoped>
.app-container {
  width: 100%;
  min-height: calc(100vh - 84px);
}
.info-content {
  height: calc(100% - 41px);
  display:flex;
  align-items:center;
  padding:20px 0;
}
.order-content {
  height:100%;
  display:flex;
}
.count-body {
  height:calc(100% - 41px);
  display:flex;
  flex-direction:column;
  align-items:center;
  justify-content:space-evenly;
}
.count-item {
  cursor: pointer;
  height:40%;
  width:70%;
  border:1px solid var(--border-color-1);
  transition: all 0.3s;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
}
.count-item:hover {
  background: var(--base-color-8);
  border: 1px solid var(--theme-color);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
  transform: translateY(-1px);
  transition: all 0.3s;
}
.count-num {
  color:var(--theme-color);
  font-size:36px;
  font-weight:bold;
  text-align:center;
  height: calc(100% - 35px);
  display:flex;
  align-items:center;
  justify-content:center;
}
.box-card {
  height: 330px;
  margin-bottom: 20px;
}
.box-card>>>.el-card__body {
  height: 100%;
}
.overview-title {
  color:var(--base-color-1);
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}
.overview-icon {
  width: 5px;
  height: 20px;
  margin-right: 10px;
  background: var(--current-color);
}
.info-box {
  flex:3;
  display:flex;
  height:100%;
}
.info-body {
  flex: 1;
  height: 100%;
  display:flex;
  flex-direction:column;
  justify-content: space-between;
}
/* 统一淡蓝色主题卡片 */
.my-order-card,
.my-inspection-card,
.personal-card {
  background-color: #eef4ff;
  border: 1px solid #d6e4ff;
}
.my-order-card .el-card__header,
.my-inspection-card .el-card__header,
.personal-card .el-card__header {
  background-color: #eef4ff;
  border-bottom-color: #d6e4ff;
  color: var(--base-color-1);
}
.my-order-card .overview-title,
.my-inspection-card .overview-title,
.personal-card .overview-title {
  color: var(--base-color-1);
}
.my-order-card .overview-icon,
.my-inspection-card .overview-icon,
.personal-card .overview-icon {
  background: var(--theme-color);
}
.my-order-card .count-item,
.my-inspection-card .count-item {
  border-color: #d6e4ff;
}
.my-order-card .count-num,
.my-inspection-card .count-num {
  color: var(--theme-color);
}
.my-order-card .count-item:hover,
.my-inspection-card .count-item:hover {
  background: #dbe6ff;
  border-color: var(--theme-color);
}
/* 个人卡片内容区美化 */
.personal-card .el-card__body {
  background-color: #eef4ff;
}
.personal-card .info-content {
  background-color: #f8fbff;
  border-radius: 8px;
  padding: 16px;
  border: 1px solid #d6e4ff;
}
/* 待处理、已处理 统计卡片美化 */
.count-item {
  cursor: pointer;
  height:40%;
  width:70%;
  border:1px solid #d6e4ff;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  background: #fff;
  transition: all 0.3s ease;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.04);
}
.count-item:hover {
  background: #dbe6ff;
  border-color: var(--theme-color);
  box-shadow: 0 4px 12px rgba(26, 115, 232, 0.15);
  transform: translateY(-2px);
}
.count-item:first-child {
  background: linear-gradient(135deg, #e8f4fd 0%, #d0e8ff 100%);
  border-color: #a8cfff;
}
.count-item:last-child {
  background: linear-gradient(135deg, #e6f7ed 0%, #c8f0d8 100%);
  border-color: #8dd4a8;
}
.count-item:first-child:hover {
  background: #c8e8f8;
  border-color: var(--theme-color);
  box-shadow: 0 4px 12px rgba(26, 115, 232, 0.2);
}
.count-item:last-child:hover {
  background: #c0edd8;
  border-color: var(--theme-color);
  box-shadow: 0 4px 12px rgba(48, 176, 143, 0.2);
}
.count-num {
  color:var(--theme-color);
  font-size:36px;
  font-weight:bold;
  text-align:center;
  height: calc(100% - 35px);
  display:flex;
  align-items:center;
  justify-content:center;
}
.count-item:first-child .count-num {
  color: var(--theme-color);
}
.count-item:last-child .count-num {
  color: #30b08f;
}
.count-item > div:first-child {
  color: var(--base-color-1);
  font-size: 13px;
}
/* 内部展示图表卡片 */
.chart-card {
  background-color: #eef4ff;
  border: 1px solid #d6e4ff;
}
.chart-card .el-card__header {
  background-color: #eef4ff;
  border-bottom-color: #d6e4ff;
  color: var(--base-color-1);
}
.chart-card .overview-title {
  color: var(--base-color-1);
}
.chart-card .overview-icon {
  background: var(--theme-color);
}
.chart-card .el-card__body {
  color: var(--base-color-1);
}
.chart-card:hover {
  border-color: var(--theme-color);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.06);
}
@media (max-width: 1200px) {
  .info-box {
    border: none
  }
}
</style>

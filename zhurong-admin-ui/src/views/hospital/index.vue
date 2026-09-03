<template>
  <div class="hospital-home">
    <el-card class="hero-card" shadow="never">
      <div class="hero-content">
        <h2 class="hero-title">{{ $t('hospital.homeTitle') }}</h2>
        <p class="hero-desc">{{ $t('hospital.homeDesc') }}</p>
      </div>
    </el-card>

    <el-row :gutter="16" class="module-grid">
      <el-col :xs="24" :sm="12" :md="8">
        <el-card shadow="hover" class="module-card" @click.native="go('/hospital/device')">
          <div class="module-icon hospital-icon-device"></div>
          <div class="module-title">{{ $t('hospital.deviceManage') }}</div>
          <div class="module-desc">{{ $t('hospital.deviceManageDesc') }}</div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="12" :md="8">
        <el-card shadow="hover" class="module-card" @click.native="go('/hospital/metric')">
          <div class="module-icon hospital-icon-metric"></div>
          <div class="module-title">{{ $t('hospital.metricManage') }}</div>
          <div class="module-desc">{{ $t('hospital.metricManageDesc') }}</div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="12" :md="8">
        <el-card shadow="hover" class="module-card" @click.native="go('/hospital/callbackLog')">
          <div class="module-icon hospital-icon-callback"></div>
          <div class="module-title">{{ $t('hospital.callbackLogManage') }}</div>
          <div class="module-desc">{{ $t('hospital.callbackLogDesc') }}</div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="12" :md="8">
        <el-card shadow="hover" class="module-card" @click.native="go('/hospital/monitor')">
          <div class="module-icon hospital-icon-monitor"></div>
          <div class="module-title">{{ $t('hospital.monitorManage') }}</div>
          <div class="module-desc">{{ $t('hospital.monitorManageDesc') }}</div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="12" :md="8">
        <el-card shadow="hover" class="module-card" @click.native="go('/hospital/alarmRule')">
          <div class="module-icon hospital-icon-alarm"></div>
          <div class="module-title">{{ $t('hospital.alarmRuleManage') }}</div>
          <div class="module-desc">{{ $t('hospital.alarmRuleManageDesc') }}</div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="12" :md="8">
        <el-card shadow="hover" class="module-card" @click.native="go('/hospital/alarmRecord')">
          <div class="module-icon hospital-icon-record"></div>
          <div class="module-title">{{ $t('hospital.alarmRecordManage') }}</div>
          <div class="module-desc">{{ $t('hospital.alarmRecordManageDesc') }}</div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="12" :md="8">
        <el-card shadow="hover" class="module-card" @click.native="go('/hospital/energy')">
          <div class="module-icon hospital-icon-energy"></div>
          <div class="module-title">{{ $t('hospital.energyManage') }}</div>
          <div class="module-desc">{{ $t('hospital.energyManageDesc') }}</div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="12" :md="8">
        <el-card shadow="hover" class="module-card" @click.native="go('/hospital/efficiency')">
          <div class="module-icon hospital-icon-efficiency"></div>
          <div class="module-title">{{ $t('hospital.efficiencyManage') }}</div>
          <div class="module-desc">{{ $t('hospital.efficiencyManageDesc') }}</div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="12" :md="8">
        <el-card shadow="hover" class="module-card" @click.native="go('/hospital/area')">
          <div class="module-icon hospital-icon-area"></div>
          <div class="module-title">{{ $t('hospital.areaManage') }}</div>
          <div class="module-desc">{{ $t('hospital.areaManageDesc') }}</div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="12" :md="8">
        <el-card shadow="hover" class="module-card" @click.native="go('/hospital/workload')">
          <div class="module-icon hospital-icon-workload"></div>
          <div class="module-title">{{ $t('hospital.workloadManage') }}</div>
          <div class="module-desc">{{ $t('hospital.workloadManageDesc') }}</div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="12" :md="8">
        <el-card shadow="hover" class="module-card" @click.native="go('/hospital/bigScreen')">
          <div class="module-icon hospital-icon-screen"></div>
          <div class="module-title">{{ $t('hospital.screenManage') }}</div>
          <div class="module-desc">{{ $t('hospital.screenManageDesc') }}</div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "HospitalHome",
  methods: {
    go(path) {
      this.$router.push(path);
    }
  }
};
</script>

<style scoped>
.hospital-home {
  padding: 8px;
}
.hero-card {
  background: linear-gradient(135deg, #1f6fb2 0%, #2b8cbe 60%, #43a2d6 100%);
  border: none;
  border-radius: 8px;
  margin-bottom: 16px;
}
.hero-card .hero-content {
  color: #fff;
  padding: 24px 8px;
}
.hero-title {
  margin: 0 0 12px 0;
  font-size: 26px;
  font-weight: 600;
}
.hero-desc {
  margin: 0;
  font-size: 14px;
  opacity: 0.9;
  line-height: 1.7;
}
.module-grid {
  margin-top: 4px;
}
.module-card {
  cursor: pointer;
  border-radius: 8px;
  margin-bottom: 16px;
  text-align: center;
  padding: 12px 0;
}
.module-card:hover {
  transform: translateY(-2px);
  transition: all 0.2s ease-in-out;
}
.module-icon {
  width: 56px;
  height: 56px;
  margin: 8px auto 12px auto;
  border-radius: 12px;
}
.hospital-icon-device {
  background: linear-gradient(135deg, #1f6fb2, #43a2d6);
}
.hospital-icon-metric {
  background: linear-gradient(135deg, #2ca4a4, #53c9c9);
}
.hospital-icon-callback {
  background: linear-gradient(135deg, #e08a3c, #f0b060);
}
.hospital-icon-monitor {
  background: linear-gradient(135deg, #1f6fb2, #5bb8e8);
}
.hospital-icon-alarm {
  background: linear-gradient(135deg, #d84a4a, #f07878);
}
.hospital-icon-record {
  background: linear-gradient(135deg, #7a6ff0, #a89bf5);
}
.hospital-icon-energy {
  background: linear-gradient(135deg, #0e9f6e, #3fdb8f);
}
.hospital-icon-efficiency {
  background: linear-gradient(135deg, #2563eb, #60a5fa);
}
.hospital-icon-area {
  background: linear-gradient(135deg, #0891b2, #22d3ee);
}
.hospital-icon-workload {
  background: linear-gradient(135deg, #7c3aed, #a78bfa);
}
.hospital-icon-screen {
  background: linear-gradient(135deg, #ea580c, #fb923c);
}
.module-title {
  font-size: 16px;
  font-weight: 600;
  color: #303133;
  margin-bottom: 6px;
}
.module-desc {
  font-size: 13px;
  color: #909399;
  line-height: 1.6;
}
</style>

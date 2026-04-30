<template>
  <div class="digital-twin-container">
    <el-row :gutter="20">
      <el-col :span="4">
        <el-card class="tree-card">
          <div slot="header">{{ $t('digitaltwinModule.modelStructure') }}</div>
          <el-tree :data="modelTree" :props="treeProps" @node-click="handleNodeClick" default-expand-all />
        </el-card>
      </el-col>
      <el-col :span="20">
        <el-card class="view-card">
          <div slot="header">
            <span>{{ $t('digitaltwinModule.threeDFactoryView') }}</span>
            <el-button style="float: right;" size="small" @click="refreshView">{{ $t('digitaltwinModule.refresh') }}</el-button>
          </div>
          <div class="view-container">
            <div class="demo-view">
              <div class="demo-title">{{ $t('digitaltwinModule.demoTitle') }}</div>
              <div class="demo-content">
                <svg width="100%" height="400" viewBox="0 0 800 400">
                  <defs>
                    <marker id="arrowhead" markerWidth="10" markerHeight="7" refX="0" refY="3.5" orient="auto">
                      <polygon points="0 0, 10 3.5, 0 7" fill="#409eff" />
                    </marker>
                  </defs>
                  <rect x="50" y="50" width="150" height="100" fill="#67c23a" stroke="#2d5a27" stroke-width="2" rx="5" />
                  <text x="125" y="100" text-anchor="middle" fill="white" font-size="14">{{ $t('digitaltwinModule.workshopA') }}</text>
                  <rect x="250" y="50" width="120" height="80" fill="#e6a23c" stroke="#b8860b" stroke-width="2" rx="5" />
                  <text x="310" y="90" text-anchor="middle" fill="white" font-size="14">{{ $t('digitaltwinModule.powerRoom') }}</text>
                  <rect x="400" y="50" width="120" height="80" fill="#f56c6c" stroke="#a82828" stroke-width="2" rx="5" />
                  <text x="460" y="90" text-anchor="middle" fill="white" font-size="14">{{ $t('digitaltwinModule.boilerRoom') }}</text>
                  <rect x="550" y="50" width="150" height="100" fill="#909399" stroke="#606266" stroke-width="2" rx="5" />
                  <text x="625" y="100" text-anchor="middle" fill="white" font-size="14">{{ $t('digitaltwinModule.officeArea') }}</text>
                  <line x1="200" y1="100" x2="250" y2="90" stroke="#409eff" stroke-width="3" marker-end="url(#arrowhead)" />
                  <text x="220" y="80" fill="#409eff" font-size="12">{{ $t('digitaltwinModule.power') }}</text>
                  <line x1="370" y1="90" x2="400" y2="90" stroke="#e6a23c" stroke-width="3" marker-end="url(#arrowhead)" />
                  <text x="380" y="80" fill="#e6a23c" font-size="12">{{ $t('digitaltwinModule.heat') }}</text>
                  <line x1="125" y1="150" x2="125" y2="250" stroke="#67c23a" stroke-width="2" stroke-dasharray="5,5" />
                  <text x="135" y="200" fill="#67c23a" font-size="11">{{ $t('digitaltwinModule.energyFlow') }}</text>
                  <circle cx="625" cy="200" r="30" fill="#409eff" opacity="0.3">
                    <animate attributeName="r" values="30;40;30" dur="2s" repeatCount="indefinite" />
                    <animate attributeName="opacity" values="0.3;0.1;0.3" dur="2s" repeatCount="indefinite" />
                  </circle>
                  <text x="625" y="205" text-anchor="middle" fill="white" font-size="12">{{ $t('digitaltwinModule.threeDArea') }}</text>
                  <text x="400" y="380" text-anchor="middle" fill="#909399" font-size="12">{{ $t('digitaltwinModule.tip') }}</text>
                </svg>
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" class="mt20">
      <el-col :span="12">
        <el-card>
          <div slot="header">{{ $t('digitaltwinModule.energyFlowMonitor') }}</div>
          <el-table :data="flowData" border height="250">
            <el-table-column prop="flowType" :label="$t('digitaltwinModule.energyType')" width="80" />
            <el-table-column prop="input" :label="$t('digitaltwinModule.input')" />
            <el-table-column prop="output" :label="$t('digitaltwinModule.output')" />
            <el-table-column prop="loss" :label="$t('digitaltwinModule.loss')" />
            <el-table-column prop="efficiency" :label="$t('digitaltwinModule.efficiency')" />
          </el-table>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <div slot="header">{{ $t('digitaltwinModule.twinDeviceStatus') }}</div>
          <el-table :data="deviceData" border height="250">
            <el-table-column prop="deviceName" :label="$t('digitaltwinModule.deviceName')" />
            <el-table-column prop="deviceType" :label="$t('digitaltwinModule.deviceType')" />
            <el-table-column prop="location" :label="$t('digitaltwinModule.location')" />
            <el-table-column prop="status" :label="$t('common.status')">
              <template slot-scope="scope">
                <el-tag :type="getStatusType(scope.row.status)">{{ getStatusLabel(scope.row.status) }}</el-tag>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { getModelTree } from "@/api/digitaltwin/threeDModel";
import { listTwinDevice } from "@/api/digitaltwin/twinDevice";
import { getEnergyBalance, listEnergyFlow } from "@/api/digitaltwin/energyFlow";

export default {
  name: "DigitalTwin",
  data() {
    return {
      modelTree: [],
      treeProps: { children: 'children', label: 'label' },
      flowData: [],
      deviceData: []
    };
  },
  created() {
    this.getModelTree();
    this.getDeviceData();
    this.getFlowData();
  },
  methods: {
    getModelTree() {
      getModelTree().then(res => { this.modelTree = [res.data] || []; });
    },
    getDeviceData() {
      listTwinDevice().then(res => { this.deviceData = res.data || []; });
    },
    getFlowData() {
      getEnergyBalance().then(res => { this.flowData = res.data || []; });
    },
    handleNodeClick(data) { console.log("点击节点:", data); },
    refreshView() { this.getFlowData(); this.$modal.msgSuccess("视图已刷新"); },
    getStatusType(status) { return { 1: "success", 2: "info", 3: "danger", 4: "warning" }[status] || "info"; },
    getStatusLabel(status) { return { 1: "运行中", 2: "停机", 3: "故障", 4: "维护" }[status] || status; }
  }
};
</script>

<style scoped>
.digital-twin-container { padding: 20px; }
.tree-card { height: 450px; overflow: auto; }
.view-card { height: 450px; }
.view-container { height: 380px; overflow: auto; }
.demo-view { border: 1px dashed #ccc; padding: 10px; }
.demo-title { text-align: center; font-size: 16px; font-weight: bold; margin-bottom: 10px; color: #409eff; }
.demo-content { background: #f5f7fa; border-radius: 4px; }
.mt20 { margin-top: 20px; }
</style>

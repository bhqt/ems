<template>
  <div class="app-container">
    <el-row :gutter="20" class="mb20">
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-title">累计节约成本</div>
          <div class="stat-value text-success">{{ summary.totalCostSaving || 0 }} 万元</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-title">平均节约率</div>
          <div class="stat-value text-primary">{{ summary.avgSavingRate || 0 }}%</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-title">效率提升</div>
          <div class="stat-value text-success">{{ summary.totalEfficiencyImprovement || 0 }}%</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-title">碳减排量</div>
          <div class="stat-value text-success">{{ summary.totalEmissionReduction || 0 }} 吨</div>
        </el-card>
      </el-col>
    </el-row>

    <el-tabs v-model="activeTab" type="border-card">
      <el-tab-pane :label="$t('dispatchModule.costSaving')" name="cost">
        <el-table v-loading="loading" :data="costSavingList" border>
          <el-table-column label="记录ID" prop="recordId" width="80" />
          <el-table-column label="记录日期" prop="recordDate" width="120" />
          <el-table-column label="优化前成本" prop="beforeCost" />
          <el-table-column label="优化后成本" prop="afterCost" />
          <el-table-column label="节约金额" prop="savingAmount" />
          <el-table-column label="节约率(%)" prop="savingRate" />
          <el-table-column label="成本类型" prop="costType" />
          <el-table-column label="描述" prop="description" :show-overflow-tooltip="true" />
        </el-table>
      </el-tab-pane>

      <el-tab-pane :label="$t('dispatchModule.efficiencyImprovement')" name="efficiency">
        <div class="chart-container">
          <div id="efficiencyChart" style="width: 100%; height: 350px;"></div>
        </div>
      </el-tab-pane>

      <el-tab-pane :label="$t('dispatchModule.emissionReduction')" name="emission">
        <div class="chart-container">
          <div id="emissionChart" style="width: 100%; height: 350px;"></div>
        </div>
      </el-tab-pane>

      <el-tab-pane :label="$t('dispatchModule.trendAnalysis')" name="trend">
        <el-form :inline="true" class="query-form">
          <el-form-item label="开始日期">
            <el-date-picker v-model="trendQuery.startDate" type="date" placeholder="选择开始日期" value-format="yyyy-MM-dd" size="small" />
          </el-form-item>
          <el-form-item label="结束日期">
            <el-date-picker v-model="trendQuery.endDate" type="date" placeholder="选择结束日期" value-format="yyyy-MM-dd" size="small" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="getTrendData">查询</el-button>
          </el-form-item>
        </el-form>
        <el-row :gutter="20">
          <el-col :span="12">
            <div id="costTrendChart" style="width: 100%; height: 350px;"></div>
          </el-col>
          <el-col :span="12">
            <div id="savingRateChart" style="width: 100%; height: 350px;"></div>
          </el-col>
        </el-row>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import { listCostSaving, getEvaluationSummary, getCostSavingTrend, getEfficiencyTrend, getEmissionTrend } from "@/api/dispatch/evaluation";

export default {
  name: "EffectEvaluation",
  data() {
    return {
      activeTab: "cost",
      loading: false,
      costSavingList: [],
      summary: {},
      trendQuery: { startDate: "", endDate: "" }
    };
  },
  created() {
    this.getCostSavingList();
    this.getSummary();
  },
  methods: {
    getCostSavingList() {
      this.loading = true;
      listCostSaving().then(res => { this.costSavingList = res.data || []; this.loading = false; });
    },
    getSummary() {
      getEvaluationSummary().then(res => { this.summary = res.data || {}; });
    },
    getTrendData() {
      getCostSavingTrend(this.trendQuery.startDate, this.trendQuery.endDate).then(res => { });
      getEfficiencyTrend(this.trendQuery.startDate, this.trendQuery.endDate).then(res => { });
      getEmissionTrend(this.trendQuery.startDate, this.trendQuery.endDate).then(res => { });
    }
  }
};
</script>

<style scoped>
.stat-card { text-align: center; padding: 10px; }
.stat-title { font-size: 14px; color: #666; margin-bottom: 10px; }
.stat-value { font-size: 24px; font-weight: bold; }
.text-success { color: #67c23a; }
.text-primary { color: #409eff; }
.mb20 { margin-bottom: 20px; }
.chart-container { padding: 20px; }
</style>

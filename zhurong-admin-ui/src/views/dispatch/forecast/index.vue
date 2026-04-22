<template>
  <div class="app-container">
    <el-tabs v-model="activeTab" type="border-card">
      <el-tab-pane label="负荷预测" name="load">
        <el-form :inline="true" class="query-form">
          <el-form-item label="预测类型">
            <el-select v-model="loadQuery.forecastType" placeholder="请选择" size="small">
              <el-option label="短期" :value="1" />
              <el-option label="中期" :value="2" />
              <el-option label="长期" :value="3" />
            </el-select>
          </el-form-item>
          <el-form-item label="负荷类型">
            <el-select v-model="loadQuery.loadType" placeholder="请选择" size="small">
              <el-option label="生产负荷" :value="1" />
              <el-option label="非生产负荷" :value="2" />
              <el-option label="总负荷" :value="3" />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="getLoadList">查询</el-button>
            <el-button type="success" icon="el-icon-refresh" size="mini" @click="doLoadForecast">预测</el-button>
          </el-form-item>
        </el-form>
        <el-table v-loading="loading" :data="loadList" border>
          <el-table-column label="预测ID" prop="loadId" width="80" />
          <el-table-column label="预测类型" prop="forecastType">
            <template slot-scope="scope">{{ getTypeLabel(scope.row.forecastType, 'forecast') }}</template>
          </el-table-column>
          <el-table-column label="负荷类型" prop="loadType">
            <template slot-scope="scope">{{ getTypeLabel(scope.row.loadType, 'load') }}</template>
          </el-table-column>
          <el-table-column label="预测时间" prop="predictedTime" width="160" />
          <el-table-column label="预测负荷(kW)" prop="predictedLoad" />
          <el-table-column label="实际负荷(kW)" prop="actualLoad" />
          <el-table-column label="误差率(%)" prop="errorRate" />
          <el-table-column label="模型" prop="modelType" />
        </el-table>
      </el-tab-pane>

      <el-tab-pane label="价格预测" name="price">
        <el-form :inline="true" class="query-form">
          <el-form-item label="能源类型">
            <el-select v-model="priceQuery.energyType" placeholder="请选择" size="small">
              <el-option label="电力" :value="1" />
              <el-option label="天然气" :value="2" />
              <el-option label="煤炭" :value="3" />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="getPriceList">查询</el-button>
            <el-button type="success" icon="el-icon-refresh" size="mini" @click="doPriceForecast">预测</el-button>
          </el-form-item>
        </el-form>
        <el-table v-loading="loading" :data="priceList" border>
          <el-table-column label="预测ID" prop="priceId" width="80" />
          <el-table-column label="能源类型" prop="energyType">
            <template slot-scope="scope">{{ getEnergyTypeLabel(scope.row.energyType) }}</template>
          </el-table-column>
          <el-table-column label="预测时间" prop="predictedTime" width="160" />
          <el-table-column label="预测价格" prop="predictedPrice" />
          <el-table-column label="实际价格" prop="actualPrice" />
          <el-table-column label="误差率(%)" prop="errorRate" />
          <el-table-column label="价格类型" prop="priceType" />
        </el-table>
      </el-tab-pane>

      <el-tab-pane label="天气预测" name="weather">
        <el-form :inline="true" class="query-form">
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="getWeatherList">查询</el-button>
            <el-button type="success" icon="el-icon-refresh" size="mini" @click="doWeatherForecast">预测</el-button>
          </el-form-item>
        </el-form>
        <el-table v-loading="loading" :data="weatherList" border>
          <el-table-column label="预测ID" prop="weatherId" width="80" />
          <el-table-column label="预测时间" prop="predictedTime" width="160" />
          <el-table-column label="温度(℃)" prop="temperature" />
          <el-table-column label="湿度(%)" prop="humidity" />
          <el-table-column label="风速(m/s)" prop="windSpeed" />
          <el-table-column label="太阳辐射" prop="solarRadiation" />
          <el-table-column label="天气状况" prop="weatherCondition" />
        </el-table>
      </el-tab-pane>

      <el-tab-pane label="模型管理" name="model">
        <el-row :gutter="10" class="mb8">
          <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddModel">新增模型</el-button>
        </el-row>
        <el-table v-loading="loading" :data="modelList" border>
          <el-table-column label="模型ID" prop="configId" width="80" />
          <el-table-column label="模型名称" prop="modelName" />
          <el-table-column label="模型类型" prop="modelType" />
          <el-table-column label="目标变量" prop="targetVariable" />
          <el-table-column label="准确率(%)" prop="accuracy" />
          <el-table-column label="状态" prop="status">
            <template slot-scope="scope">
              <el-tag :type="scope.row.status === 1 ? 'success' : 'info'">{{ scope.row.status === 1 ? '启用' : '禁用' }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="最后训练" prop="lastTrainTime" width="160" />
          <el-table-column label="操作" width="180">
            <template slot-scope="scope">
              <el-button size="mini" type="text" @click="trainModel(scope.row)">训练</el-button>
              <el-button size="mini" type="text" @click="handleUpdateModel(scope.row)">修改</el-button>
              <el-button size="mini" type="text" @click="handleDeleteModel(scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import { listLoadForecast, doLoadForecast } from "@/api/dispatch/loadForecast";
import { listPriceForecast, doPriceForecast } from "@/api/dispatch/priceForecast";
import { listWeatherForecast, doWeatherForecast } from "@/api/dispatch/weatherForecast";
import { listModelConfig, trainModel as trainModelApi } from "@/api/dispatch/modelConfig";

export default {
  name: "ForecastAnalysis",
  data() {
    return {
      activeTab: "load",
      loading: false,
      loadList: [],
      priceList: [],
      weatherList: [],
      modelList: [],
      loadQuery: { forecastType: 1, loadType: 1 },
      priceQuery: { energyType: 1 }
    };
  },
  created() {
    this.getLoadList();
    this.getPriceList();
    this.getWeatherList();
    this.getModelList();
  },
  methods: {
    getLoadList() {
      this.loading = true;
      listLoadForecast(this.loadQuery).then(res => { this.loadList = res.data || []; this.loading = false; });
    },
    getPriceList() {
      this.loading = true;
      listPriceForecast(this.priceQuery).then(res => { this.priceList = res.data || []; this.loading = false; });
    },
    getWeatherList() {
      this.loading = true;
      listWeatherForecast().then(res => { this.weatherList = res.data || []; this.loading = false; });
    },
    getModelList() {
      this.loading = true;
      listModelConfig().then(res => { this.modelList = res.data || []; this.loading = false; });
    },
    doLoadForecast() {
      doLoadForecast(this.loadQuery.forecastType, this.loadQuery.loadType).then(res => {
        this.$modal.msgSuccess("预测完成: " + res.data?.toFixed(2) + " kW");
        this.getLoadList();
      });
    },
    doPriceForecast() {
      doPriceForecast(this.priceQuery.energyType, '峰电价').then(res => {
        this.$modal.msgSuccess("预测完成: " + res.data?.toFixed(4) + " 元/kWh");
        this.getPriceList();
      });
    },
    doWeatherForecast() {
      doWeatherForecast().then(res => {
        this.$modal.msgSuccess("预测完成: " + res.data?.toFixed(2) + " ℃");
        this.getWeatherList();
      });
    },
    trainModel(row) {
      trainModelApi(row.configId).then(() => { this.$modal.msgSuccess("训练完成"); this.getModelList(); });
    },
    handleAddModel() { this.$modal.msg("功能开发中"); },
    handleUpdateModel(row) { this.$modal.msg("功能开发中"); },
    handleDeleteModel(row) { this.$modal.msg("功能开发中"); },
    getTypeLabel(val, type) {
      if (type === 'forecast') return { 1: '短期', 2: '中期', 3: '长期' }[val] || val;
      return { 1: '生产负荷', 2: '非生产负荷', 3: '总负荷' }[val] || val;
    },
    getEnergyTypeLabel(val) { return { 1: '电力', 2: '天然气', 3: '煤炭', 4: '燃油' }[val] || val; }
  }
};
</script>

<style scoped>
.query-form { margin-bottom: 15px; }
</style>

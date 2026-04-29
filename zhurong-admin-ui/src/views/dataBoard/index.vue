<template>
  <div class="board-container">
    <div v-if="showWhich" class="back-btn" @click="backHome"><img src="@/assets/images/home.png">{{ $t('dataBoardModule.energyPlatform') }}</div>
    <img v-else src="@/assets/images/fullscreen.png" alt="" class="fullscreen-img" @click="toDataBoard">
    <div class="time-stamp">{{ nowTime }}</div>
    <div class="board-title"><span>{{ $t('dataBoardModule.carbonDataVisualizationPlatform') }}</span></div>
    <div class="board-content flex-between">
      <div class="content-left flex-column-between">
        <div class="content-overview">
          <div class="box-title">{{ $t('dataBoardModule.projectOverview') }}</div>
          <div class="box-content flex-between">
            <div class="overview-object flex-column-center">
              <div class="object-count">3</div>
              <div class="object-name">{{ $t('dataBoardModule.projectTotal') }}</div>
            </div>
            <div class="overview-meter flex-column-center">
              <div class="object-count">{{ deviceTotal }}</div>
              <div class="object-name">{{ $t('dataBoardModule.meterTotal') }}</div>
            </div>
            <div class="overview-alarm flex-column-center">
              <div class="object-count">{{ alarmTotal }}</div>
              <div class="object-name">{{ $t('dataBoardModule.alarmRecord') }}</div>
            </div>
          </div>
        </div>
        <div class="content-status">
          <div class="box-title">{{ $t('dataBoardModule.equipmentStatus') }}</div>
          <div class="box-content">
            <CirclePieChartVue height="100%" :pieData="pieData"/>
          </div>
        </div>
        <div class="content-alarm">
          <div class="box-title">{{ $t('dataBoardModule.alarmInfo') }}</div>
          <div class="box-content">
            <alarmInfo />
          </div>
        </div>
      </div>
      <div class="content-middle flex-column-between">
        <div class="content-map" id="boardMap"></div>
        <div class="content-chart">
          <div class="box-title">{{ $t('dataBoardModule.todayEnergyTrend') }}</div>
          <div class="box-content">
            <el-tabs
              v-model="activeName"
              @tab-click="handleClick"
              class="trend-tabs"
            >
              <el-tab-pane :label="$t('dataBoardModule.totalEnergy')" name="total">
                <TrendLineChart
                  v-if="activeName === 'total'"
                  :height="'100%'"
                  :yName="'kgce'"
                  :xData="xData"
                  :yData="energyData"
                />
              </el-tab-pane>
              <el-tab-pane :label="$t('dataBoardModule.electricity')" name="electricity">
                <TrendLineChart
                  v-if="activeName === 'electricity'"
                  :height="'100%'"
                  :yName="'kM·h'"
                  :xData="xData"
                  :yData="electricityData"
                />
              </el-tab-pane>
              <el-tab-pane :label="$t('dataBoardModule.water')" name="water">
                <TrendLineChart
                  v-if="activeName === 'water'"
                  :height="'100%'"
                  :yName="'t'"
                  :xData="xData"
                  :yData="waterData"
                />
              </el-tab-pane>
            </el-tabs>
          </div>
        </div>
      </div>
      <div class="content-right flex-column-between">
        <div class="content-statistics">
          <div class="box-title">{{ $t('dataBoardModule.todayEnergyStatistics') }}</div>
          <div class="box-content flex-between">
            <div class="statistics-item flex-column-center">
              <lightning theme="outline" size="25" fill="#00d1ff" />
              <div class="item-count">{{ electricityTotal }}</div>
              <div style="text-align: center">{{ $t('dataBoardModule.electricityKwh') }}</div>
            </div>
            <div class="statistics-item flex-column-center">
              <dashboard theme="outline" size="25" fill="#00d1ff" />
              <div class="item-count">{{ powerTotal}}</div>
              <div style="text-align: center">{{ $t('dataBoardModule.comprehensiveEnergy') }}</div>
            </div>
            <div class="statistics-item flex-column-center">
              <cycle theme="outline" size="25" fill="#00d1ff" />
              <div class="item-count">{{ waterTotal }}</div>
              <div style="text-align: center">{{ $t('dataBoardModule.todayWaterUsage') }}</div>
            </div>
          </div>
        </div>
        <div class="content-trend">
          <div class="box-title">{{ $t('dataBoardModule.dailyPowerCurve') }}</div>
          <div class="box-content">
            <LineChart height="100%" :chartData="dailyP" :yName="'kW'" xName=""/>
          </div>
        </div>
        <div class="content-carbon">
          <div class="box-title">{{ $t('dataBoardModule.todayWaterInfo') }}</div>
          <div class="box-content">
            <alarmInfo />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import CirclePieChartVue from "@/views/dataBoard/CirclePieChart.vue";
import alarmInfo from "@/views/dataBoard/alarmInfo";
import LineChart from "@/views/dataBoard/LineChart.vue";
import barChart from "@/views/dataBoard/BarChart";
import TrendLineChart from "@/views/dataBoard/TrendLineChart";
import { Lightning, Dashboard, Cycle } from "@icon-park/vue";
import moment from "moment";
import { getAllStatus } from '@/api/system/equipmentInfo'
import { listHistory } from '@/api/system/alarmHistory'
import { topologyTreeSelect } from '@/api/system/itemTopology'
import { getChainData, getDailyP, getDayTrend, getConsumptionStatistics } from '@/api/system/energy'
export default {
  name: "dataBoard",
  components: {
    CirclePieChartVue,
    alarmInfo,
    LineChart,
    Lightning,
    Cycle,
    Dashboard,
    barChart,
    TrendLineChart
  },
  data() {
    return {
      map: null,
      activeName: "total",
      nowTime: "",
      timer: null, // 定时任务
      isActive: false, // P2优化：组件活跃状态标记
      showWhich: false, // 全屏按钮切换
      pieData: [],
      deviceTotal: 0,
      // 历史报警总条数
      alarmTotal: 0,
      energyType: "0",
      areaId: 1,
      xData:[],
      electricityData:[],
      waterData:[],
      energyData:[], // 综合能耗数据（电+水）
      carbonData:[],
      electricityTotal:0,
      powerTotal:0,
      carbonTotal: 0,
      dailyP:{},
      waterTotal: 0, // 用水总量
    };
  },
  methods: {
    // 辅助方法：将数据转为数值（处理无效值如"--"）
    parseNumber(value) {
      const num = parseFloat(value);
      return isNaN(num) ? 0 : num;
    },
    getEquipmentData() {
      getAllStatus().then(res => {
        this.pieData = [
          { value: res.data[0].count, name: this.$t('dataBoardModule.normal'), itemStyle: {color: '#6be6c3'} },
          { value: res.data[1].count, name: this.$t('dataBoardModule.alarm'), itemStyle: {color: '#e0c464'} },
          { value: res.data[2].count, name: this.$t('dataBoardModule.offline'), itemStyle: {color: '#297ef8'} },
        ]
        res.data.forEach(item=>{
          this.deviceTotal = item.count + this.deviceTotal;
        })
      })
    },
    /** 查询历史报警列表 */
    getAlarmList() {
      listHistory().then(response => {
        this.alarmTotal = response.total;
      });
    },
    // 获取区域拓扑
    getAreaList() {
      topologyTreeSelect().then((res) => {
        if(res.data.length>0){
          this.areaId = res.data[0].id
        }
      });
    },
    //根据能源类型查询（电/水单独数据）
    getEnergyType(){
      let date = moment().format("yyyy-MM-DD HH:mm:ss");
      this.xData = [];
      this.electricityData = [];
      this.waterData = [];
      let data = {
        energyType:this.energyType,
        areaId:this.areaId,
        nowTime: date,
      };
      getDayTrend(data).then((res) => {
        if (res.data.length <= 0) {
          this.xData = [0,1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23];
          return;
        }
        res.data.forEach((item, index) => {
          this.xData.push(index);
        });
        if(this.energyType == "0"){
          // 处理电数据，转换为数值
          this.electricityData = res.data.map(item => this.parseNumber(item));
        }else if(this.energyType == "1"){
          // 处理水数据，转换为数值
          this.waterData = res.data.map(item => this.parseNumber(item));
          this.calculateWaterTotal(); // 切换到水数据时重新计算
        }
      });
    },
    // 获取总能耗统计
    getConsumption() {
      getConsumptionStatistics({areaId: this.areaId}).then(res => {
        this.electricityTotal = res.data.electricity
        this.powerTotal = res.data.kgce
        this.carbonTotal = res.data.kg
      })
    },

    // 计算用水总量
    calculateWaterTotal() {
      let total = 0;
      this.waterData.forEach(item => {
        total += item; // 已通过parseNumber处理为数值
      });
      this.waterTotal = total.toFixed(2); // 保留两位小数
    },

    // 获取综合能耗数据（电+水）
    async getEnergy() {
      try {
        const date = moment().format("yyyy-MM-DD HH:mm:ss");
        this.xData = [];
        this.energyData = []; // 重置综合能耗数据
        this.carbonData = [];
        this.electricityData = [];
        this.waterData = [];

        // 初始化默认数据（24小时）
        const defaultData = Array(24).fill(0);
        this.xData = [...Array(24).keys()]; // [0,1,2,...,23]

        // 获取电数据
        const electricityResponse = await getDayTrend({
          energyType: '0',
          areaId: this.areaId,
          nowTime: date,
        });
        this.electricityData = electricityResponse.data?.length > 0
          ? electricityResponse.data.map(item => this.parseNumber(item)) // 处理非数值
          : defaultData;

        // 获取水数据
        const waterResponse = await getDayTrend({
          energyType: '1',
          areaId: this.areaId,
          nowTime: date,
        });
        this.waterData = waterResponse.data?.length > 0
          ? waterResponse.data.map(item => this.parseNumber(item)) // 处理非数值
          : defaultData;

        // 计算综合能耗：电 + 水（按小时对应相加）
        this.energyData = this.electricityData.map((ele, index) => {
          const water = this.waterData[index] || 0;
          return (ele + water).toFixed(2); // 保留两位小数
        });

        this.calculateWaterTotal(); // 计算用水总量
      } catch (error) {
        console.error("获取能源数据失败：", error);
        // 错误时兜底
        this.xData = [...Array(24).keys()];
        this.energyData = Array(24).fill(0);
        this.electricityData = Array(24).fill(0);
        this.waterData = Array(24).fill(0);
        this.waterTotal = 0;
      }
    },

    // 获取当日-昨日电功率曲线
    getDailyPData() {
      let param= {
        areaId: this.areaId,
        energyType: '0'
      }
      getDailyP(param).then((res) => {
        let result = {
          todayData: res.data.todayData,
          yesterdayData: res.data.yesterdayData,
        };
        this.dailyP = result;
      });
    },
    handleClick(tab) {
      if(tab.name === "total"){
        this.getEnergy(); // 切换到综合能耗时重新计算
      } else if(tab.name === "electricity"){
        this.energyType = "0";
        this.getEnergyType();
      } else if(tab.name === "water"){
        this.energyType = "1";
        this.getEnergyType();
      }
    },
    getNowTime() {
      this.nowTime = moment().format("yyyy-MM-DD HH:mm:ss");
    },
    // P2优化：使用递归setTimeout替代setInterval，更安全可控
    startTimer() {
      if (!this.isActive) return;
      this.timer = setTimeout(() => {
        this.getNowTime();
        this.startTimer(); // 递归调用
      }, 1000);
    },
    // P2优化：清理定时器
    clearTimer() {
      if (this.timer) {
        clearTimeout(this.timer);
        this.timer = null;
      }
    },
    toDataBoard() {
      this.$router.push('/system/data-board')
    },
    backHome() {
      this.$router.push('/data-board')
    }
  },
  mounted() {
    this.$nextTick(() => {
      // 移除地图初始化调用，保留空区域
    })
  },
  created() {
    this.isActive = true;
    this.getNowTime();
    this.startTimer();

    this.showWhich = this.$router.currentRoute.path == '/data-board' ? false : true;
    this.getEquipmentData();
    this.getAlarmList();
    this.getAreaList();

    this.$nextTick(() => {
      this.getConsumption();
      this.getEnergy(); // 初始化综合能耗数据
      this.getDailyPData();
    })
  },
  beforeDestroy() {
    this.isActive = false;
    this.clearTimer();
    this.map = null;
  },
};
</script>

<style scoped>
.board-container {
  width: 100%;
  height: 100vh;
  background-image: url("../../assets/images/board-bg.png");
  background-size: 100% 100%;
  color: #fff;
  position: relative;
}
.back-btn {
  position: absolute;
  left: 2.5%;
  top: 5%;
  height: 3%;
  color: #00d0ff;
  font-size: 17px;
  display: flex;
  align-items: center;
  cursor: pointer;
}
.back-btn img {
  height: 100%;
  margin-right: 6px;
}
.fullscreen-img {
  position: absolute;
  left: 2.5%;
  top: 5%;
  height: 3%;
  cursor: pointer;
}
.time-stamp {
  position: absolute;
  right: 2.5%;
  top: 5%;
  color: #00d1ff;
  font-size: 20px;
}
.board-title {
  background-image: linear-gradient(to top, #2571e9, #00e7ff);
  background-clip: text;
  -webkit-background-clip: text;
  color: transparent;
  font-size: 40px;
  font-weight: bold;
  letter-spacing: 8px;
  height: 9%;
  display: flex;
  align-items: center;
  justify-content: center;
}
.board-content {
  height: 86%;
  width: 95%;
  margin: 6px auto 0;
}
.content-left {
  width: calc(25% - 12px);
  margin-right: 12px;
}
.content-overview,
.content-status,
.content-alarm,
.content-statistics,
.content-trend,
.content-carbon,
.content-chart {
  background-image: url("../../assets/images/box-bg1.png");
  background-size: 100% 100%;
}
.content-overview {
  height: 20%;
  margin-bottom: 12px;
  padding: 12px 16px;
}
.box-title {
  height: 20px;
  margin-left: 12px;
  display: flex;
  align-items: center;
  color: #01d1ff;
}
.box-title::before {
  content: " ";
  width: 6px;
  height: 100%;
  border-radius: 10px;
  display: inline-block;
  margin-right: 6px;
  background: linear-gradient(to bottom, #00d1ff, #2869e8);
}
.box-content {
  height: calc(100% - 20px);
}
.overview-object,
.overview-meter,
.overview-alarm {
  width: calc(33% - 10px);
  align-items: center;
}
.object-count {
  color: #1be5e7;
  font-size: 35px;
  font-weight: bold;
  margin-bottom: 6px;
}
.object-name {
  font-size: 12px;
}
.content-status {
  height: calc(40% - 12px);
  margin-bottom: 12px;
  padding: 20px 16px;
}
.content-alarm {
  height: calc(40% - 12px);
  padding: 16px 16px;
}
.content-middle {
  width: 50%;
  margin-right: 12px;
}
.content-map {
  height: 65%;
}
.content-chart {
  height: calc(35% - 12px);
  padding: 18px 30px;
}
.content-right {
  width: calc(25% - 12px);
}
.content-statistics {
  height: 20%;
  padding: 12px 16px;
}
.content-trend {
  height: calc(40% - 12px);
  padding: 18px 16px;
}
.content-carbon {
  height: calc(40% - 12px);
  padding: 18px 16px;
}
.statistics-item {
  width: calc(33% - 8px);
  align-items: center;
  font-size: 12px;
}
.item-count {
  font-size: 18px;
  font-weight: bold;
  color: #1be5e7;
  margin: 6px 0 6px;
}
.trend-tabs {
  height: 100%;
}
/* 新增用水信息样式 */
.water-info {
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.water-total {
  font-size: 24px;
  font-weight: bold;
  color: #1be5e7;
  margin-bottom: 10px;
}
.water-desc {
  font-size: 14px;
  color: #88b7e9;
}
</style>
<style>
.trend-tabs,
.trend-tabs .el-tabs__content .el-tab-pane {
  height: 100%;
}
.trend-tabs .el-tabs__item {
  color: #fff;
}
.trend-tabs .el-tabs__item:hover,
.trend-tabs .el-tabs__item.is-active {
  color: #00d0fe;
}
.trend-tabs .el-tabs__active-bar {
  background-color: #00d0fe;
}
.trend-tabs .el-tabs__nav-wrap::after {
  background-color: #1d3666;
}
.trend-tabs .el-tabs__content {
  height: calc(100% - 55px);
}

/* 地图信息窗口样式修改（保留但不影响空区域） */
#boardMap .BMap_bubble_pop {
  background-color: rgba(28, 37, 80, 0.8) !important;
  border: 1px solid #186dbf !important;
}
#boardMap .BMap_bubble_pop img {
  display: none;
}
#boardMap .BMap_bubble_pop .BMap_bubble_top .BMap_bubble_title,
#boardMap .BMap_bubble_pop .BMap_bubble_center .BMap_bubble_content {
  color: #fff !important;
}
</style>
